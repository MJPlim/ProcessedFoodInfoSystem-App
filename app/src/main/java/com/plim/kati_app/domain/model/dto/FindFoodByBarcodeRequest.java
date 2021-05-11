package com.plim.kati_app.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FindFoodByBarcodeRequest {
    private String barcode;
}
