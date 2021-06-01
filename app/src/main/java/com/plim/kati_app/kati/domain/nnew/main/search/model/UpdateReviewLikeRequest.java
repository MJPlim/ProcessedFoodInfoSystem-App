package com.plim.kati_app.kati.domain.nnew.main.search.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateReviewLikeRequest {

    private Long reviewId;
    private boolean likeCheck;
}