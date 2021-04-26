package com.plim.kati_app.domain.view.search.food.list.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.food.list.adapter.FoodInfoRecyclerViewAdapter;
import com.plim.kati_app.tech.GlideApp;
import com.plim.kati_app.tech.RestAPI;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

/**
 * 음식 검색하여 나온 리스트와 정렬화면 프래그먼트.
 */
public class FoodSearchResultListFragment extends Fragment {

    private String foodSearchMode;
    private String foodSearchText;

    //음식 리스트
    private RecyclerView adFoodInfoRecyclerView;
    private RecyclerView foodInfoRecyclerView;
    private LoadingDialog dialog;

    private RecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_search_result_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View


        this.adFoodInfoRecyclerView = view.findViewById(R.id.searchFragment_adFoodInfoRecyclerView);
        this.foodInfoRecyclerView = view.findViewById(R.id.searchFragment_foodInfoRecyclerView);


        this.recyclerAdapter = new RecyclerAdapter();

        this.dialog = new LoadingDialog(getContext());

        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(5));
        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(1));


        this.getActivity().getSupportFragmentManager().setFragmentResultListener(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY, getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                set(Integer.parseInt(result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX)), result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE), result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT));
            }
        });
    }

    /**
     * 검색을 위한 정보를 넣고 시작.
     *
     * @param index 페이지 번호
     * @param mode  회사 혹은 제품
     * @param text  검색어
     */
    public void set(int index, String mode, String text) {
        foodSearchMode = mode;
        foodSearchText = text;
        Thread thread = new Thread(new FoodSearch());
        thread.start();
    }


    /**
     * 검색D을 하는 부분.
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
            Call<List<FoodResponse>> listCall;
            if (foodSearchMode.equals(Constant_yun.ESearchMode.제품.name())) {
                listCall = service.getFoodListByProductName(foodSearchText);
            } else {
                listCall = service.getFoodListByCompanyName(foodSearchText);
            }

            listCall.enqueue(new Callback<List<FoodResponse>>() {
                @Override
                public void onResponse(Call<List<FoodResponse>> call, Response<List<FoodResponse>> response) {
                    Vector<FoodResponse> items = new Vector<>(response.body());
                    new Thread(() ->
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)))).start();
                    getActivity().runOnUiThread(() -> {
                        dialog.hide();
                        recyclerAdapter.setItems(items);
                        foodInfoRecyclerView.setAdapter(recyclerAdapter);
                    });

                }

                @Override
                public void onFailure(Call<List<FoodResponse>> call, Throwable t) {
                    getActivity().runOnUiThread(() -> {
                        dialog.hide();
                    });
                    KatiDialog.simpleAlertDialog(getContext(),
                            FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,
                            t.getMessage(), null,
                            getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                    ).showDialog();
                }
            });
        }
    }


    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter {

        private Vector<FoodResponse> items;

        private RecyclerAdapter() {
            items = new Vector<FoodResponse>();
        }

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
            FoodResponse item = items.get(position);
            ((RecyclerViewViewHolder) holder).setValue(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clearItems() {
            this.items = new Vector<>();
        }

        public void addItems(Vector<FoodResponse> items) {
            this.items.addAll(items);
        }

        public void setItems(Vector<FoodResponse> items) {
            this.clearItems();
            this.addItems(items);
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView productName, companyName;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
                this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);
            }

            public void setValue(@NotNull FoodResponse item) {

//                Log.d("이미지 주소",item.getFoodImageAddress());
                GlideApp.with(getContext()).load(item.getFoodImageAddress()).into(imageView);


                this.productName.setText(item.getFoodName());
                this.companyName.setText(item.getManufacturerName());
            }
        }
    }



}