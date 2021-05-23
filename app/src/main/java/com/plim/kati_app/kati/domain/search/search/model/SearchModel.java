package com.plim.kati_app.kati.domain.search.search.model;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;

import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchModel {
    private String searchMode=Constant.ESearchMode.제품.name(), searchText,foodSortElement;
    private int pageSize=10,searchPageNum=1;
    private boolean isFiltered=false;
    private Vector<String> allergyList;

    public SearchModel(){
    }
}
