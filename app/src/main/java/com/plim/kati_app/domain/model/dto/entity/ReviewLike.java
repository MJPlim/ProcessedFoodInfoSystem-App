package com.plim.kati_app.domain.model.dto.entity;

import lombok.Getter;
import lombok.Setter;

//@Table(name = "review_like")
//@Entity
@Setter
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "review_like_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "review_id")
    private Review review;

//    @Column(name = "user_id")
    private Long userId;

//    @Builder
//    public ReviewLike(Review review, Long userId) {
//        this.review = review;
//        this.userId = userId;
//        this.review.getReviewLikeList().add(this);
//    }


}
