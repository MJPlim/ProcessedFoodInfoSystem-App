package com.plim.kati_app.domain.model;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.plim.kati_app.constants.Constant_yun;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchViewModel extends ViewModel {

    public String foodSearchMode;
    public String foodSearchText;
    public Constant_yun.SortElement foodSortElement;
    public int pageNum;

}
