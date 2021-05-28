package com.plim.kati_app.kati.domain.nnew.main.search.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindFoodBySortingResponse {
    private int total_page;
    private int current_page;
    private int total_elements;
    private int current_elements;
    private boolean has_previous;
    private boolean has_next;
    private List<FoodResponse> data;
    private boolean last;


}
