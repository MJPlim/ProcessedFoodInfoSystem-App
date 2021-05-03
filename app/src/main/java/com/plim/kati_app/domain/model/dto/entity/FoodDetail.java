package com.plim.kati_app.domain.model.dto.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Embeddable
//@AllArgsConstructor
//@Builder
@Getter
@Setter

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodDetail {
//    @Column(name = "expiration_date")
    private String expriationDate;

//    @Lob
//    @Column(name = "materials")
    private String materials;

//    @Lob
//    @Column(name = "nutrient")
    private String nutrient;

//    @Column(name = "capacity")
    private String capacity;
}
