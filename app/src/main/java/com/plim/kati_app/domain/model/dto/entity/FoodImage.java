package com.plim.kati_app.domain.model.dto.entity;

import lombok.Getter;
import lombok.Setter;

//@Embeddable
//@AllArgsConstructor
//@Builder
@Setter
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodImage {
//    @Column(name = "food_image_address")
    private String foodImageAddress;

//    @Column(name = "food_mete_image_address")
    private String foodMeteImageAddress;
}
