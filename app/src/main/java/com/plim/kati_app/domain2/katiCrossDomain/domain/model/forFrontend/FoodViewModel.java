package com.plim.kati_app.domain2.katiCrossDomain.domain.model.forFrontend;

import androidx.lifecycle.ViewModel;

import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forBackend.searchFood.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodViewModel extends ViewModel {

    FoodDetailResponse foodDetailResponse;

    // Food Search
    String searchPageNum, searchMode, searchText;

}
