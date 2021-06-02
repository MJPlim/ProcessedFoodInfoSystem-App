package com.plim.kati_app.kati.domain.nnew.main.search.model;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;

import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchModel {
    private String searchMode=Constant.ESearchMode.제품.name(), searchText,foodSortElement= Constant.SortElement.MANUFACTURER.name();
    private int pageSize=10,searchPageNum=1;
    private boolean isFiltered=true;
    private Constant.SortOrder sortOrder= Constant.SortOrder.asc;
    private Vector<String> allergyList;

    public SearchModel(){
    }
}
