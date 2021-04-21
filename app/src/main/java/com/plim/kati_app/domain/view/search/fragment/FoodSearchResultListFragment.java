package com.plim.kati_app.domain.view.search.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.BlankFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FoodSearchListItem;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.adapter.FoodInfoRecyclerViewAdapter;
import com.plim.kati_app.domain.view.search.adapter.SortButtonRecyclerViewAdapter;
import com.plim.kati_app.tech.RestAPI;

import java.util.List;
import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 음식 검색하여 나온 리스트와 정렬화면 프래그먼트.
 */
public class FoodSearchResultListFragment extends Fragment {

    //working variable
    private boolean isFiltered = true;
    private boolean showAllergy = false;
    private int foodSearchIndex;
    private String foodSearchMode;
    private String foodSearchText;

    // Associate
    // View
    //카테고리를 선택.
    private RecyclerView sortButtonsRecyclerView;
    private TabLayout categoryTabLayout;
    //알레르기 필터링
    private ImageView allergyImageView;
    private TextView allergyTextView;
    private LoadingDialog dialog;

    //음식 리스트
    private RecyclerView adFoodInfoRecyclerView;
    private RecyclerView foodInfoRecyclerView;

    //component
    private Vector<Fragment> categoryViewFragmentVector;
    private Fragment allergyViewFragment;
    private BlankFragment blankFragment;

    private RecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_search_result_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.allergyImageView = view.findViewById(R.id.searchFragment_allergyImageView);
        this.allergyTextView = view.findViewById(R.id.searchFragment_allergyTextView);

        this.adFoodInfoRecyclerView = view.findViewById(R.id.searchFragment_adFoodInfoRecyclerView);
        this.foodInfoRecyclerView = view.findViewById(R.id.searchFragment_foodInfoRecyclerView);
        this.sortButtonsRecyclerView = view.findViewById(R.id.searchFragment_sortButtonRecyclerView);
        this.categoryTabLayout = view.findViewById(R.id.searchFragment_tabLayout);

        this.recyclerAdapter = new RecyclerAdapter();

        this.dialog = new LoadingDialog(getContext());

        // Set View Attribute
        //enum 값을 바탕으로 카테고리 구성하기.
        this.categoryViewFragmentVector = new Vector<>();
        Vector<String> val = new Vector<>();
        int i = 0;
        for (ECategory category : ECategory.values()) {
            categoryTabLayout.addTab(categoryTabLayout.newTab().setText(category.getName()), i);
            val.addAll(category.getChildNames());
            this.categoryViewFragmentVector.add(i, new FoodCategoryDetailListFragment(val));

            //재사용하기 위해 다시 세팅
            i++;
            val.clear();
        }
        getParentFragmentManager().beginTransaction().replace(R.id.searchFragment_frameLayout, categoryViewFragmentVector.get(0)).commit();
        this.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = categoryViewFragmentVector.get(position);
                getParentFragmentManager().beginTransaction().replace(R.id.searchFragment_frameLayout, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        Vector<String> data = this.getData();
        this.allergyViewFragment = new AllergyViewFragment(data);
        this.blankFragment = new BlankFragment();
        this.allergyTextView.setOnClickListener(v -> {
            this.showAllergy = !showAllergy;
            if (showAllergy)
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, allergyViewFragment).commit();
            else
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, blankFragment).commit();
        });

        this.allergyImageView.setOnClickListener((v) -> {
            setColor();
        });


        this.sortButtonsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        val.clear();
        val.add("랭킹 순");
        val.add("가격 순");
        val.add("별점 순");
        val.add("리뷰 개수 순");
        this.sortButtonsRecyclerView.setAdapter(new SortButtonRecyclerViewAdapter(val, new myListener()));


        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(5));
        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(1));


        this.setColor();

        this.getActivity().getSupportFragmentManager().setFragmentResultListener("result", getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                set(Integer.parseInt(result.getString("index")), result.getString("mode"), result.getString("text"));
            }
        });
    }

    /**
     * 검색을 위한 정보를 넣고 시작.
     * @param index 페이지 번호
     * @param mode 회사 혹은 제품
     * @param text 검색어
     */
    public void set(int index, String mode, String text) {
        foodSearchIndex = index;
        foodSearchMode = mode;
        foodSearchText = text;
        Thread thread = new Thread(new FoodSearch());
        thread.start();
    }

    public void setColor() {
        int newTint = this.isFiltered ? R.color.kati_red : R.color.kati_yellow;
        this.isFiltered = !isFiltered;
        this.allergyImageView.setColorFilter(ContextCompat.getColor(getContext(), newTint), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private Vector<String> getData() {
        Vector<String> data = new Vector<>();
        data.add("땅콩");
        data.add("대두");
        data.add("우유");
        data.add("호두");
        return data;
    }


    /**
     *검색D을 하는 부분.
     */
    public class FoodSearch implements Runnable {

        @Override
        public void run() {
            getActivity().runOnUiThread(() -> {
                dialog.show();
            });


            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.URL)
                    .build();
            RestAPI service = retrofit.create(RestAPI.class);
            Call<List<FoodSearchListItem>> listCall;
            if (foodSearchMode.equals(FoodSearchFieldFragment.ESearchMode.제품.name())) {
                Log.d("디버그","제품이름 검색");
                listCall = service.getFoodListByProductName(
                        database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION),
                        foodSearchText,
                        foodSearchIndex + "");
            } else {
                Log.d("디버그","회사이름 검색");
                listCall = service.getFoodListByCompanyName(
                        database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION),
                        foodSearchText,
                        foodSearchIndex + "");
            }

            listCall.enqueue(new Callback<List<FoodSearchListItem>>() {
                @Override
                public void onResponse(Call<List<FoodSearchListItem>> call, Response<List<FoodSearchListItem>> response) {
                    Log.d("디버그","응답");
                    Vector<FoodSearchListItem> items = new Vector<>(response.body());
                            new Thread(()->
                                    database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION,response.headers().get(KatiDatabase.AUTHORIZATION)))).start();
                    getActivity().runOnUiThread(() -> {
                        dialog.hide();
                        recyclerAdapter.setItems(items);
                        foodInfoRecyclerView.setAdapter(recyclerAdapter);
                    });

                }

                @Override
                public void onFailure(Call<List<FoodSearchListItem>> call, Throwable t) {
                    KatiDialog dialog = new KatiDialog(getContext());
                    dialog.setTitle("연결 실패");
                    dialog.setMessage(t.getMessage());
                    dialog.setPositiveButton("확인", null);
                }
            });
        }
    }


    /**
     * 큰 카테고리
     */
    @AllArgsConstructor
    @Getter
    public enum ECategory {
        tea("차", ETeaCategory.values()), drink("음료", EDrinkCategory.values()), simple("간편식", ESimpleCategory.values());
        private String name;
        private EChildCategory[] childCategories;

        public Vector<String> getChildNames() {
            Vector<String> childNames = new Vector<>();
            for (EChildCategory childCategory : childCategories)
                childNames.add(childCategory.name());
            return childNames;
        }
    }

    /**
     * 작은 카테고리
     */
    public interface EChildCategory {
        String name();
    }

    public enum ETeaCategory implements EChildCategory {
        커피, 핫초코, 아이스티, 녹차, 홍차, 보이차, 꽃차;

    }

    public enum EDrinkCategory implements EChildCategory {
        생수, 콜라, 사이다, 기타_탄산음료, 보리차, 두유, 과채주스, 전통주, 어린이음료;
    }

    public enum ESimpleCategory implements EChildCategory {
        라면, 즉석밥, 즉석국, 통조림, 카레, 짜장, 밀키트;
    }

    private class myListener implements View.OnClickListener {

        private boolean show;
        private Fragment menuFragment, blankFragment;

        public myListener() {
            this.menuFragment = new FoodSearchSortMenuFragment();
            this.blankFragment = new BlankFragment();
        }

        @Override
        public void onClick(View v) {
            this.show = !show;
            if (show)
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_sortFrameLayout, menuFragment).commit();
            else
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_sortFrameLayout, blankFragment).commit();
        }
    }

    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter {

        private Vector<FoodSearchListItem> items;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_food, parent, false);
            RecyclerViewViewHolder rankRecyclerViewViewHolder = new RecyclerViewViewHolder(view);

            return rankRecyclerViewViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            FoodSearchListItem item = items.get(position);
            ((RecyclerViewViewHolder) holder).setValue(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clearItems() {
            this.items = new Vector<>();
        }

        public void addItems(Vector<FoodSearchListItem> items) {
            this.items.addAll(items);
        }

        public void setItems(Vector<FoodSearchListItem> items) {
            this.clearItems();
            this.addItems(items);
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private TextView productName, companyName;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
            }

            public void setValue(FoodSearchListItem item) {
                this.productName.setText(item.getPrdlstName());
                this.companyName.setText(item.getBsshName());
            }
        }
    }


}