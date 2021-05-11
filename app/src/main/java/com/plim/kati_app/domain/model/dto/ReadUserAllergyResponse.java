package com.plim.kati_app.domain.model.dto;

import java.util.List;
import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadUserAllergyResponse {
    List<String> userAllergyMaterials;
}
