package com.plim.kati_app.domain.model.dto;

import lombok.Setter;

@Setter
public class UpdateReviewRequest {
    private Long reviewId;
    private int reviewRating;
    private String reviewDescription;
}
