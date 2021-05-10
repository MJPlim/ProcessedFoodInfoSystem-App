package com.plim.kati_app.domain2.model;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.domain2.view.KatiViewModelFragment;

public abstract class KatiFoodFragment extends KatiViewModelFragment {

    public FoodViewModel foodModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.foodModel = new ViewModelProvider(this.getActivity()).get(FoodViewModel.class);
    }
}
