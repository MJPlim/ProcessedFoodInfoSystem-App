package com.plim.kati_app.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateReviewLikeRequest {

    private Long reviewId;
    private boolean likeCheck;
}