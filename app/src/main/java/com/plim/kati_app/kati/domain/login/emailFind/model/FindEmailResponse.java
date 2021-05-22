package com.plim.kati_app.kati.domain.login.emailFind.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindEmailResponse {
    private final String secondEmail;
}
