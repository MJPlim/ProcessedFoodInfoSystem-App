package com.plim.kati_app.kati.domain.nnew.main.category.model;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;

import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryModel {

    private String searchMode= Constant.ESearchMode.카테고리.name(),
            categoryName="",
            foodSortElement= Constant.SortElement.RANK.getMessage();
    private int pageSize=10,
            searchPageNum=1;
    private boolean isFiltered=true, hasNext=false;
    private Constant.SortOrder sortOrder= Constant.SortOrder.asc;
    private Vector<String> allergyList;

}
