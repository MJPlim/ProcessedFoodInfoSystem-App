package com.plim.kati_app.kati.domain.review.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateReviewResponse {
    private Long foodId;
    private Integer reviewRating;
    private String reviewDescription;
}
