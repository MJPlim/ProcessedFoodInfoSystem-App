package com.plim.kati_app.kati.domain.search.search.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchViewModel extends ViewModel {
    private String searchMode, searchText,foodSortElement;
    private int pageSize=10,searchPageNum=1;
    private boolean isFiltered=false;
    private Vector<String> allergyList;
}
