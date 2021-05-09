package com.plim.kati_app.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindEmailResponse {

    private final String email;

}
