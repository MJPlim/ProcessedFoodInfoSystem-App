package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.kati.domain.nnew.foodDetail.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.nnew.main.category.model.CategoryModel;
import com.plim.kati_app.kati.domain.nnew.main.category.model.CategoryViewModel;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodViewModel;
import com.plim.kati_app.kati.domain.nnew.main.search.model.ReadSummaryResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.SearchModel;
import com.plim.kati_app.kati.domain.nnew.main.search.model.SearchViewModel;

public abstract class KatiCategoryFragment extends KatiLoginCheckViewModelFragment{

    protected CategoryModel categoryModel;
    private CategoryViewModel viewModel;

    private CategoryObserver categoryObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(this.getActivity()).get(CategoryViewModel.class);
        this.categoryModel = viewModel.getCategoryModel().getValue();
        this.categoryObserver= new CategoryObserver();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.viewModel.getCategoryModel().observe(this.getViewLifecycleOwner(), this.categoryObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.viewModel.getCategoryModel().removeObserver(this.categoryObserver);
    }

    public void saveCategory() {
        Log.d("서치 프래그먼트", "저장하다");
        this.viewModel.getCategoryModel().setValue(this.categoryModel);
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }


    protected abstract void categoryDataUpdated();



    private class CategoryObserver implements Observer{

        @Override
        public void onChanged(Object o) {
        categoryModel=viewModel.getCategoryModel().getValue();
            if(dataset!=null)
                categoryDataUpdated();
        }
    }


}