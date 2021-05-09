package com.plim.kati_app.domain.model.dto;

import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewViewmodel extends ViewModel {
    private String imageUrl;
    private String productName;
    private String manufacturerName;
    private int score;
    private String reviewValue;
    private Long foodId;
}
