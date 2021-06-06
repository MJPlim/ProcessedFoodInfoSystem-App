package com.plim.kati_app.kati.domain.nnew.main.category;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.foodDetail.FoodDetailActivity;
import com.plim.kati_app.kati.domain.nnew.foodDetail.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.nnew.main.category.CategoryAdapter.CategoryAdapter;
import com.plim.kati_app.kati.domain.nnew.main.category.model.CategoryFoodListResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class CategoryResultFragment extends KatiCategoryFragment {

    private Vector<FoodResponse> vector;

    //associate
    //view
    private ChipGroup chipGroup;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    private Constant.ECategory category;


    public CategoryResultFragment() {
        this.vector = new Vector<>();
    }

    public CategoryResultFragment(Constant.ECategory category) {
        this.vector = new Vector<>();
        this.category = category;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_result;
    }

    @Override
    protected void associateView(View view) {
        this.chipGroup = view.findViewById(R.id.categoryResultFragment_chipGroup);
        this.recyclerView = view.findViewById(R.id.categoryResultFragment_recyclerView);
        this.chipGroup.removeAllViews();
        boolean firstChipCheck = true;

        this.chipGroup.setSelectionRequired(true);

        for (Constant.EChildCategory childCategory : this.category.getChildCategories()) {
            this.createChip(childCategory, firstChipCheck);
            if (firstChipCheck) firstChipCheck = false;
        }

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        this.recyclerView.setLayoutManager(manager);
        this.adapter = new CategoryAdapter(v -> intentDetailPage((Long) v.getTag()));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setNestedScrollingEnabled(false);


        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findLastCompletelyVisibleItemPosition();
                if (position == vector.size() - 1 && categoryModel.isHasNext()) {
                    loadMore();
                    Log.d("태그", vector.size() + " " + categoryModel.isHasNext() + " " + position);
                }

            }
        });
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void categoryDataUpdated() {

    }

    public void createChip(Constant.EChildCategory childCategory, boolean chipCheck) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        Chip chip = (Chip) inflater.inflate(R.layout.jsh_chip, null);
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> loadCategory(isChecked, childCategory.getName(), chip));
        chip.setChecked(chipCheck);
        chip.setText(childCategory.getName());
        this.chipGroup.addView(chip);
    }

    private void loadCategory(boolean isChecked, String categoryName, Chip categoryChip) {
        Log.d(categoryName + " 하나 불러오기", "리프레시");
        if (isChecked) {
            Log.d("디버그, 칩 선택", categoryName);
            this.vector.clear();
            this.categoryModel.setPageSize(10);
            this.categoryModel.setCategoryName(categoryName);
            this.load();
        }
    }

    private void loadMore() {
        Log.d("더 불러오기" + this.categoryModel.getCategoryName(), "더더" + this.categoryModel.getPageSize() + 1);
        this.categoryModel.setPageSize(this.categoryModel.getPageSize() + 1);
        this.load();
    }

    private void load() {
        KatiRetrofitTool.getAPI().getCategoryFood(
                this.categoryModel.getSearchPageNum(),
                this.categoryModel.getPageSize(),
                this.categoryModel.getFoodSortElement(),
                this.categoryModel.getSortOrder().getMessage(),
                this.categoryModel.getCategoryName(),
                this.categoryModel.getAllergyList()
        ).enqueue(JSHRetrofitTool.getCallback(new FoodCategoryCallback(this.getActivity())));
    }

    private void intentDetailPage(Long foodId) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        startActivity(intent);
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }


    private class FoodCategoryCallback extends SimpleRetrofitCallBackImpl<FindFoodBySortingResponse> {
        public FoodCategoryCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindFoodBySortingResponse> response) {
            FindFoodBySortingResponse findFoodBySortingResponse = response.body();

            categoryModel.setHasNext(findFoodBySortingResponse.isHas_next());
            vector.addAll(findFoodBySortingResponse.getData());
            adapter.setItems(vector);
        }
    }
}