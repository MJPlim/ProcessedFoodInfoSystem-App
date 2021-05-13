package com.plim.kati_app.domain.model;

import com.google.gson.annotations.SerializedName;
import com.plim.kati_app.domain.model.dto.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFavoriteResponse {
    @SerializedName("foodId")
    private Long foodId;
    @SerializedName("food")
    private FoodDetailResponse food;


}
