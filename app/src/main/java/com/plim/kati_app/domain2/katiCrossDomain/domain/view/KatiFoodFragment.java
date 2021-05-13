package com.plim.kati_app.domain2.katiCrossDomain.domain.view;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forBackend.searchFood.FoodDetailResponse;
import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forFrontend.FoodViewModel;

public abstract class KatiFoodFragment extends KatiViewModelFragment implements Observer {

    public FoodViewModel foodModel;
    public FoodDetailResponse foodDetailResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.foodModel = new ViewModelProvider(this.getActivity()).get(FoodViewModel.class);
        this.foodModel.setFoodDetailResponse(new MutableLiveData<>());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.foodModel.getFoodDetailResponse().observe(this.getViewLifecycleOwner(),this);
    }
    @Override
    public void onPause() {
        super.onPause();
        this.foodModel.getFoodDetailResponse().removeObserver(this);
    }

    @Override
    public void onChanged(Object o) {
        foodDetailResponse = this.foodModel.getFoodDetailResponse().getValue();
        this.foodModelDataUpdated();
    }

    public abstract void foodModelDataUpdated();
}
