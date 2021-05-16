package com.plim.kati_app.domain.katiCrossDomain.domain.view;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.domain.katiCrossDomain.domain.model.forFrontend.SearchViewModel;

public abstract class KatiSearchFragment extends KatiViewModelFragment {

    public SearchViewModel searchModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.searchModel = new ViewModelProvider(this.getActivity()).get(SearchViewModel.class);
    }
}
