package com.plim.kati_app.domain.model.dto;

import com.plim.kati_app.domain.model.FoodResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdvertisementResponse {
    private final Long id;
    private final FoodResponse food;
}