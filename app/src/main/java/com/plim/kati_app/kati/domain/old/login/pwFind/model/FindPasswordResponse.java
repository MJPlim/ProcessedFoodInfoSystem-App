package com.plim.kati_app.kati.domain.old.login.pwFind.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindPasswordResponse {
    private final String email;
}
