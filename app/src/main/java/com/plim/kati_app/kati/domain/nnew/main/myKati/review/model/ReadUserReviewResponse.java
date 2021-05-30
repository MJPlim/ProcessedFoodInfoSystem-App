package com.plim.kati_app.kati.domain.nnew.main.myKati.review.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadUserReviewResponse<T> {
    private T userReviewList;
    private Integer userReviewCount;
}
