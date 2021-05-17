package com.plim.kati_app.domain.view.search.food.category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.dto.AdvertisementResponse;
import com.plim.kati_app.domain.model.dto.CategoryFoodListResponse;
import com.plim.kati_app.domain.view.search.food.detail.NewDetailActivity;
import com.plim.kati_app.domain.view.search.food.list.list.FoodSearchResultListFragment;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.List;
import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.constants.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.constants.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class FoodCategoryFragment extends GetResultFragment {

    private LoadingDialog dialog;

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private TabLayout categoryTabLayout;

    private Vector<Fragment> categoryViewFragmentVector;
    private int page;
    private String categoryName="";
    private boolean hasNext;

    private Vector<FoodResponse> vector;

    public FoodCategoryFragment() {
        this.page = 0;
        this.vector = new Vector<>();
    }


    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "searchCategory";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        String categoryName = result.getString("categoryName");
        this.searchFood(categoryName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.dialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.dialog = new LoadingDialog(getContext());
        this.recyclerView = view.findViewById(R.id.foodCategoryFragment_FoodInfoRecyclerView);
        this.categoryTabLayout = view.findViewById(R.id.foodCategoryFragment_menuTabLayout);

        this.adapter = new MyAdapter();

        this.recyclerView.setAdapter(this.adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findLastCompletelyVisibleItemPosition();
                if (position == vector.size()-1) loadMOre();

            }
        });

        // Set View Attribute
        //enum 값을 바탕으로 카테고리 구성하기.
        this.categoryViewFragmentVector = new Vector<>();
        Vector<String> val = new Vector<>();
        int i = 0;
        for (Constant_yun.ECategory category : Constant_yun.ECategory.values()) {
            categoryTabLayout.addTab(categoryTabLayout.newTab().setText(category.getName()), i);
            val.addAll(category.getChildNames());
            this.categoryViewFragmentVector.add(i, new FoodCategoryDetailListFragment(val));

            //재사용하기 위해 다시 세팅
            i++;
            val.clear();
        }
        getParentFragmentManager().beginTransaction().replace(R.id.foodCategoryFragment_frameLayout, categoryViewFragmentVector.get(0)).commit();
        this.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = categoryViewFragmentVector.get(position);
                getParentFragmentManager().beginTransaction().replace(R.id.foodCategoryFragment_frameLayout, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void loadMOre() {
        this.page++;

        if (categoryName != null && hasNext)
            this.searchFood(this.categoryName);
    }

    private void intentDetailPage(Long foodId) {
        Intent intent = new Intent(getActivity(), NewDetailActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        startActivity(intent);
    }

    private void searchFood(String categoryName) {
        Log.d("검색시작", categoryName);
        this.dialog.show();
        Call<CategoryFoodListResponse> call = RestAPIClient.getApiService().getCategoryFood(categoryName, this.page);
        call.enqueue(new FoodCategoryCallBack(categoryName));
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private Vector<FoodResponse> items;

        private MyAdapter() {
            items = new Vector<>();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_food, parent, false);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            FoodResponse item = items.get(position);
            holder.setValue(item);
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        public void clearItems() {
            this.items.clear();
            this.notifyDataSetChanged();
        }

        public void setItems(Vector<FoodResponse> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        public void addItems(Vector<FoodResponse> items) {
            int position = this.items.size();
            this.items.addAll(items);
            this.notifyItemInserted(position);
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView productName, companyName, reviewCount, score;
            private String imageAddress;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
                this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);
                this.reviewCount = itemView.findViewById(R.id.foodItem_reviewCountTextView);
                this.score = itemView.findViewById(R.id.foodItem_scoreTextView);
                itemView.setOnClickListener(v -> intentDetailPage(items.get(this.getAdapterPosition()).getFoodId()));

            }

            public void setValue(FoodResponse item) {
                this.imageAddress = item.getFoodImageAddress();
                Glide.with(getContext()).load(this.imageAddress).fitCenter().transform(new CenterCrop()).into(imageView);
                this.score.setText(item.getReviewRate() == null ? "0.00" : item.getReviewRate());
                this.reviewCount.setText("(" + item.getReviewCount() + ")");
                this.productName.setText(item.getFoodName());
                this.companyName.setText(item.getManufacturerName().split("_")[0]);
            }
        }
    }


    @AllArgsConstructor
    private class FoodCategoryCallBack implements Callback<CategoryFoodListResponse> {
        private String category;

        @Override
        public void onResponse(Call<CategoryFoodListResponse> call, Response<CategoryFoodListResponse> response) {
            if (!response.isSuccessful()) {
                KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.code() + "", null);
            } else {
                CategoryFoodListResponse categoryFoodListResponse = response.body();
                Vector<FoodResponse> items = new Vector<>();
                items.addAll(categoryFoodListResponse.getData());

                hasNext = categoryFoodListResponse.isHas_next();

                getActivity().runOnUiThread(() -> {
                    if (!categoryName.equals(category)) {
                        categoryName = this.category;
                        vector=items;
                        adapter.setItems(vector);
                    } else {
                        vector.addAll(items);
                        adapter.addItems(items);
                    }
                    dialog.hide();
                });
            }
        }

        @Override
        public void onFailure(Call<CategoryFoodListResponse> call, Throwable t) {
            getActivity().runOnUiThread(() -> {
                dialog.hide();
            });
            KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
        }
    }
}
