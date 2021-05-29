package com.plim.kati_app.kati.domain.old.model;

import lombok.Getter;

@Getter
public class ReadReviewIdResponse {
    private Long reviewId;

    private String foodName;

    private String manufacturerName;

    private String pictureUrl;

    private Integer reviewRating;

    private String reviewDescription;
}
