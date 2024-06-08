package com.example.reservationshop.entity;

import com.example.reservationshop.model.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

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
    private ReservationShopEntity reservationId;
    @Column(nullable = false,length = 5)
    private double reviewRating;
    @Column(nullable = false,length = 10)
    private String document;

    private ReviewShopEntity( ReservationShopEntity reservationId, double reviewRating, String document){
        this.id=null;
        this.reservationId = reservationId;
        this.reviewRating=reviewRating;
        this.document=document;
    }

//    public static ReviewShopEntity from(ReservationShopEntity reviewId,float reviewRating,String document){
//        return new ReviewShopEntity(reviewId,reviewRating,document);
//    }

    public static ReviewShopEntity from(ReservationShopEntity reservationId,Review.Request request){
      return new ReviewShopEntity(reservationId,request.getShopRating(),request.getDocument());
    }

    public ReviewShopEntity update(Review.Update request){
        if (StringUtils.hasText(request.getDocument())){
            this.document=request.getDocument();
        }
        if (request.getShopRating()>0){
            this.reviewRating=request.getShopRating();
        }
        return this;
    }
}
