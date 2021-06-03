package com.plim.kati_app.kati.domain.nnew.main.category;

import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;

public class CategoryFilterFragment extends KatiCategoryFragment {

    private Chip rankingChip, manufacturerChip, reviewCountChip,assendChip, desendChip;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_filter;
    }

    @Override
    protected void associateView(View view) {
    this.rankingChip= view.findViewById(R.id.categoryFilterFragment_rankingChip);
    this.manufacturerChip= view.findViewById(R.id.categoryFilterFragment_manufacturerChip);
    this.reviewCountChip= view.findViewById(R.id.categoryFilterFragment_reviewCountChip);

    this.assendChip= view.findViewById(R.id.categoryFilterFragment_assendChip);
    this.desendChip= view.findViewById(R.id.categoryFilterFragment_desendChip);
    }

    @Override
    protected void initializeView() {

        this.rankingChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                categoryModel.setFoodSortElement(Constant.SortElement.RANK.getMessage());
                saveCategory();
            }
        });
        this.manufacturerChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                categoryModel.setFoodSortElement(Constant.SortElement.MANUFACTURER.getMessage());
                saveCategory();
            }
        });
        this.reviewCountChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                categoryModel.setFoodSortElement(Constant.SortElement.REVIEW_COUNT.getMessage());
                saveCategory();
            }
        });

        this.assendChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                categoryModel.setSortOrder(Constant.SortOrder.asc);
                saveCategory();
            }
        });
        this.desendChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                categoryModel.setSortOrder(Constant.SortOrder.desc);
                saveCategory();
            }
        });

        this.rankingChip.setChecked(true);
        this.assendChip.setChecked(true);

    }

    @Override
    protected void categoryDataUpdated() {

    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }


}
