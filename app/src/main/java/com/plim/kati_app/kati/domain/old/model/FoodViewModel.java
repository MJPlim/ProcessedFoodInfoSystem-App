package com.plim.kati_app.kati.domain.old.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.plim.kati_app.kati.domain.old.search.foodInfo.model.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodViewModel extends ViewModel {
    private String barcode="";
    private Long foodId=0L;
    private boolean isAd=false;
    private MutableLiveData<FoodDetailResponse> foodDetailResponse;
    private MutableLiveData<ReadSummaryResponse> readSummaryResponse;

    public FoodViewModel(){
        this.foodDetailResponse= new MutableLiveData<>();
        this.foodDetailResponse.setValue(new FoodDetailResponse());
        this.readSummaryResponse= new MutableLiveData<>();
        this.readSummaryResponse.setValue(new ReadSummaryResponse());
    }
}
