package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntityTool;
import com.plim.kati_app.kati.domain.search.search.model.SearchViewModel;

import java.util.ArrayList;

public abstract class KatiSearchFragment extends KatiViewModelFragment {

    public SearchViewModel searchModel;
    protected ArrayList<String> searchWords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.searchModel = new ViewModelProvider(this.getActivity()).get(SearchViewModel.class);
        this.searchWords= new ArrayList<>();
    }

    @Override
    public void viewModelDataUpdated() {
        super.viewModelDataUpdated();
        ArrayList<KatiEntity> katiEntityArray = KatiEntityTool.convertJSHEntityArrayToDomainArray(this.viewModelTool.getJSHEntities());
        if (katiEntityArray.size() != 0)
            this.searchWords = this.entity.getSearchWords();
    }

}
