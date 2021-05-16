package com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.userAccount;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindPasswordResponse {
    private final String email;
}
