package com.plim.kati_app.kati.domain.search.search.model;

import android.widget.SearchView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchViewModel extends ViewModel {

    MutableLiveData<SearchModel> searchModel;

    public SearchViewModel(){
        this.searchModel=new MutableLiveData<>();
        this.searchModel.setValue(new SearchModel());
    }




}
