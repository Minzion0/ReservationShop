package com.example.reservationshop.controller;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ReviewShopEntity;
import com.example.reservationshop.model.Review;
import com.example.reservationshop.service.ShopReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopReviewController {
    private final ShopReviewService shopReviewService;

    @PostMapping("/review")
    public ResponseEntity<?> shopReview(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Review.Request request){
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        ReviewShopEntity reviewShopEntity = shopReviewService.createReview(customerEntity, request);
        return ResponseEntity.ok(reviewShopEntity);
    }

    @PatchMapping("/review")
    public ResponseEntity<?> shopReviewUpdate(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Review.Update request){
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        ReviewShopEntity reviewShopEntity = shopReviewService.shopReviewUpdate(customerEntity,request);
        return ResponseEntity.ok(reviewShopEntity);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> deleteShopReview(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long reviewId){
        this.shopReviewService.deleteShopReview(userDetails,reviewId);
        return ResponseEntity.ok(1);
    }



}
