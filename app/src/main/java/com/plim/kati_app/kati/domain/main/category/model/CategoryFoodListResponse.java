package com.plim.kati_app.kati.domain.main.category.model;

import com.plim.kati_app.kati.domain.main.search.model.FoodResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryFoodListResponse {

    private int total_page;
    private int current_page;
    private int total_elements;
    private int current_elements;
    private boolean has_previous;
    private boolean has_next;
    private List<FoodResponse> data;

}
