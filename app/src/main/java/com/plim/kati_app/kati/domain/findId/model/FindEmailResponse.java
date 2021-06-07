package com.plim.kati_app.kati.domain.findId.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindEmailResponse {
    private final String secondEmail;
}
