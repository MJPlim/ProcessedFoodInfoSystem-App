package com.plim.kati_app.domain2.model;

import androidx.lifecycle.ViewModel;

import com.plim.kati_app.retrofit.dto.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodViewModel extends ViewModel {
    FoodDetailResponse foodDetailResponse;
}
