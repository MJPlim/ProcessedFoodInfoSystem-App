package com.plim.kati_app.kati.domain.main.myKati.allergy.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadUserAllergyResponse {
    List<String> userAllergyMaterials;
}
