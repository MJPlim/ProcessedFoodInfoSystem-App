package com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAndUpdateReviewRequest {
    private Long foodId;
    private Long reviewId;
    private int reviewRating;
    private String reviewDescription;
}
