package com.plim.kati_app.domain.model.dto.entity;

import lombok.Getter;
import lombok.Setter;

//@Table(name = "favorite")
//@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

//    @Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "favorite_id")
    private Long id;

//    @Column(name = "user_id")
    private Long userId;			//매핑아직안함

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "food_id")
    private Food food;			//이것두
//
//    @Builder
//    public Favorite(Long userId, Food food) {
//        this.userId = userId;
//        this.food = food;
//        this.food.getFavoriteList().add(this);
//    }
}
