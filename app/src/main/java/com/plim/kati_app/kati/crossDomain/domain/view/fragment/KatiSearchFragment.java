package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.kati.domain.search.search.model.SearchViewModel;

public abstract class KatiSearchFragment extends KatiViewModelFragment {

    public SearchViewModel searchModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.searchModel = new ViewModelProvider(this.getActivity()).get(SearchViewModel.class);
    }
}
