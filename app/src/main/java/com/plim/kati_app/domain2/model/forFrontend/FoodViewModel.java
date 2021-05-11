package com.plim.kati_app.domain2.model.forFrontend;

import androidx.lifecycle.ViewModel;

import com.plim.kati_app.domain2.model.forBackend.searchFood.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodViewModel extends ViewModel {
    FoodDetailResponse foodDetailResponse;
}
