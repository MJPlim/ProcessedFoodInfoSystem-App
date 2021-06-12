package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.kati.domain.foodDetail.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.main.search.model.FoodViewModel;
import com.plim.kati_app.kati.domain.main.search.model.ReadSummaryResponse;

public abstract class KatiFoodFragment extends KatiLoginCheckViewModelFragment implements Observer{

    public FoodViewModel foodModel;
    public FoodDetailResponse foodDetailResponse;
    public ReadSummaryResponse readSummaryResponse;

    private SummaryObserver summaryObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.summaryObserver= new SummaryObserver();
        this.foodModel = new ViewModelProvider(this.getActivity()).get(FoodViewModel.class);
        this.foodDetailResponse=this.foodModel.getFoodDetailResponse().getValue();
        this.readSummaryResponse=this.foodModel.getReadSummaryResponse().getValue();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.foodModel.getFoodDetailResponse().observe(this.getViewLifecycleOwner(),this);
        this.foodModel.getReadSummaryResponse().observe(this.getViewLifecycleOwner(),this.summaryObserver);
    }
    @Override
    public void onPause() {
        super.onPause();
        this.foodModel.getFoodDetailResponse().removeObserver(this);
        this.foodModel.getReadSummaryResponse().removeObserver(this.summaryObserver);
    }

    @Override
    public void onChanged(Object o) {
        this.foodDetailResponse = this.foodModel.getFoodDetailResponse().getValue();
//        this.readSummaryResponse=this.foodModel.getReadSummaryResponse().getValue();
        if(this.dataset!=null)
        this.foodModelDataUpdated();
    }

    public void saveFoodDetail(){
        this.foodModel.getFoodDetailResponse().setValue(this.foodDetailResponse);
    }
    public void saveReadSummary(){
        this.foodModel.getReadSummaryResponse().setValue(this.readSummaryResponse);
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() { }

    @Override
    protected void katiEntityUpdatedAndNoLogin() { }

    public abstract void foodModelDataUpdated();
    protected abstract void summaryDataUpdated();

    private class SummaryObserver implements Observer{

        @Override
        public void onChanged(Object o) {
        readSummaryResponse=foodModel.getReadSummaryResponse().getValue();
            if(dataset!=null)
        summaryDataUpdated();
        }
    }


}
