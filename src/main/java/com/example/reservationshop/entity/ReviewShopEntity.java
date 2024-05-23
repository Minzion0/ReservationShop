package com.example.reservationshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_shop")
public class ReviewShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ShopEntity shopId;
    @ManyToOne
    private ReservationShopEntity reviewId;
    @Column(nullable = false,length = 5)
    private float reviewRating;
    @Column(nullable = false,length = 10)
    private String document;

    private ReviewShopEntity(ShopEntity shopId,ReservationShopEntity reviewId,float reviewRating,String document){
        this.id=null;
        this.shopId=shopId;
        this.reviewId=reviewId;
        this.reviewRating=reviewRating;
        this.document=document;
    }

    public static ReviewShopEntity from(ShopEntity shopId,ReservationShopEntity reviewId,float reviewRating,String document){
        return new ReviewShopEntity(shopId,reviewId,reviewRating,document);
    }
}
