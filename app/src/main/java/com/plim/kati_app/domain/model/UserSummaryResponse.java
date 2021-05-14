package com.plim.kati_app.domain.model;

import com.google.gson.annotations.SerializedName;
import com.plim.kati_app.domain.model.dto.FoodDetailResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummaryResponse {
    @SerializedName("favorite_count")
    private String favorite_count;
    @SerializedName("review_count")
    private String review_count;
    @SerializedName("user_name")
    private String user_name;


}
