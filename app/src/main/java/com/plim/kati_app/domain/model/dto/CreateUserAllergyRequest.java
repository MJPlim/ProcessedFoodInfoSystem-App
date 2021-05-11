package com.plim.kati_app.domain.model.dto;

import java.util.List;

import lombok.Setter;

@Setter
public class CreateUserAllergyRequest {
    private List<String> allergyList;
}