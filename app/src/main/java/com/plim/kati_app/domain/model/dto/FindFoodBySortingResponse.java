package com.plim.kati_app.domain.model.dto;

import com.plim.kati_app.domain.model.FoodResponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindFoodBySortingResponse {
    private int pageNo;
    private int pageSize;
    private int maxPage;
    private int totalDataCount;
    private List<FoodResponse> resultList;
}
