package com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodViewModel extends ViewModel {
    MutableLiveData<FoodDetailResponse> foodDetailResponse;
}
