package com.plim.kati_app.kati.crossDomain.domain.view.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.kati.domain.main.search.model.FoodViewModel;

public abstract class KatiFoodActivity extends KatiViewModelActivity {

    public FoodViewModel foodModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.foodModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }
}
