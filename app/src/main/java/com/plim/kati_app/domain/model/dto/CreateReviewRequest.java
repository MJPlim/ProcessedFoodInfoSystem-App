package com.plim.kati_app.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateReviewRequest {
    private Long foodId;

    private int reviewRating;

    private String reviewDescription;
}
