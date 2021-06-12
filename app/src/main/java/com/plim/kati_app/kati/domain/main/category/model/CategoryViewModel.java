package com.plim.kati_app.kati.domain.main.category.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryViewModel extends ViewModel {

    private MutableLiveData<CategoryModel> categoryModel;

    public CategoryViewModel(){
        this.categoryModel= new MutableLiveData<>();
        this.categoryModel.setValue(new CategoryModel());
    }


}
