package com.plim.kati_app.kati.domain.main.category;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.foodDetail.FoodDetailActivity;
import com.plim.kati_app.kati.domain.main.category.CategoryAdapter.CategoryAdapter;
import com.plim.kati_app.kati.domain.main.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.main.search.model.FoodResponse;

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

    //working variable
    private Constant.ECategory category;
    private String selectedChildCategory;


    public CategoryResultFragment() {
        this.vector = new Vector<>();
    }

    public CategoryResultFragment(Constant.ECategory category) {
        this.vector = new Vector<>();
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.chipGroup = view.findViewById(R.id.categoryResultFragment_chipGroup);
        this.recyclerView = view.findViewById(R.id.categoryResultFragment_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        this.recyclerView.setLayoutManager(manager);
        this.adapter = new CategoryAdapter(v -> intentDetailPage((Long) v.getTag()),this.getActivity());
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
                }
            }
        });
        this.createSmallCategoryChip();

        this.initializeView();
        this.checkChip();
        this.load();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_result;
    }

    @Override
    protected void associateView(View view) {

    }

    @Override
    protected void initializeView() {}

    @Override
    public void onResume() {
        super.onResume();
        this.refresh();
    }

    @Override
    protected void categoryDataUpdated() {
        this.load();
    }

    @Override
    protected void katiEntityUpdatedAndLogin() { }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {}

    /**
     * callback
     */
    private class FoodCategoryCallback extends SimpleRetrofitCallBackImpl<FindFoodBySortingResponse> {
        public FoodCategoryCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindFoodBySortingResponse> response) {
            FindFoodBySortingResponse findFoodBySortingResponse = response.body();
            categoryModel.setHasNext(findFoodBySortingResponse.isHas_next());
            if (categoryModel.isClearVector())
                vector.clear();
            vector.addAll(findFoodBySortingResponse.getData());
            adapter.setItems(vector);
        }
    }

    /**
     * method
     */
    public void createChip(Constant.EChildCategory childCategory, boolean chipCheck) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        Chip chip = (Chip) inflater.inflate(R.layout.jsh_chip, null);
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> loadCategory(isChecked, childCategory.getName()));
        chip.setChecked(chipCheck);
        chip.setText(childCategory.getName());
        this.chipGroup.addView(chip);
    }

    private void checkChip() {
        Chip chip = this.chipGroup.findViewById(this.chipGroup.getCheckedChipId());
        chip.setChecked(true);
    }

    private void loadCategory(boolean isChecked, String categoryName) {
        if (isChecked) {
            this.recyclerView.scrollToPosition(0);
            this.categoryModel.setClearVector(true);
            this.selectedChildCategory = categoryName;
            this.categoryModel.setSearchPageNum(1);
            this.saveCategory();
        }
    }

    private void refresh(){
        this.recyclerView.scrollToPosition(0);
        this.categoryModel.setClearVector(true);
        this.categoryModel.setSearchPageNum(1);
        this.saveCategory();
    }

    private void loadMore() {

        this.categoryModel.setClearVector(false);
        this.categoryModel.setSearchPageNum(this.categoryModel.getSearchPageNum() + 1);
        this.saveCategory();
    }

    private void load() {
        KatiRetrofitTool.getAPI().getCategoryFood(
                this.categoryModel.getSearchPageNum(),
                this.categoryModel.getPageSize(),
                this.categoryModel.getFoodSortElement(),
                this.categoryModel.getSortOrder().getMessage(),
                this.selectedChildCategory,
                this.categoryModel.getAllergyList()
        ).enqueue(JSHRetrofitTool.getCallback(new FoodCategoryCallback(this.getActivity())));
    }

    private void intentDetailPage(Long foodId) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        startActivity(intent);
    }

    private void createSmallCategoryChip() {
        this.chipGroup.removeAllViews();
        boolean firstChipCheck = true;
        this.chipGroup.setSelectionRequired(true);
        for (Constant.EChildCategory childCategory : this.category.getChildCategories()) {
            this.createChip(childCategory, firstChipCheck);
            if (firstChipCheck) firstChipCheck = false;
        }
    }


}