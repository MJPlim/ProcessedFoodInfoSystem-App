package com.plim.kati_app.domain2.katiCrossDomain.domain.view;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forFrontend.FoodViewModel;
import com.plim.kati_app.domain2.katiCrossDomain.domain.view.KatiViewModelFragment;

public abstract class KatiFoodFragment extends KatiViewModelFragment {

    public FoodViewModel foodModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.foodModel = new ViewModelProvider(this.getActivity()).get(FoodViewModel.class);
    }
}
