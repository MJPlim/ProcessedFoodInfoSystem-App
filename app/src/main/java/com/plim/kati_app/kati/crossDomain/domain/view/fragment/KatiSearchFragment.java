package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.kati.domain.main.search.model.SearchModel;
import com.plim.kati_app.kati.domain.main.search.model.SearchViewModel;

public abstract class KatiSearchFragment extends KatiLoginCheckViewModelFragment implements Observer {

    public SearchViewModel searchViewModel;
    protected SearchModel searchModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.searchViewModel = new ViewModelProvider(this.getActivity()).get(SearchViewModel.class);
        this.searchModel = searchViewModel.getSearchModel().getValue();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.searchViewModel.getSearchModel().observe(this.getViewLifecycleOwner(), this);
       }

    @Override
    public void onPause() {
        super.onPause();
        this.searchViewModel.getSearchModel().removeObserver(this);
       }

    @Override
    public void onChanged(Object o) {
        this.searchModel = this.searchViewModel.getSearchModel().getValue();
        this.searchModelDataUpdated();

    }

    public void saveSearch() {
        this.searchViewModel.getSearchModel().setValue(this.searchModel);
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    protected abstract void searchModelDataUpdated();




}
