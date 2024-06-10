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

    // 매장 리뷰 작성 요청 처리
    @PostMapping("/review")
    public ResponseEntity<?> shopReview(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Review.Request request) {
        // 현재 로그인한 사용자 정보 가져오기
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        // 매장 리뷰 서비스를 통해 리뷰 작성 요청 처리
        ReviewShopEntity reviewShopEntity = shopReviewService.createReview(customerEntity, request);
        // 작성된 리뷰 정보 반환
        return ResponseEntity.ok(reviewShopEntity);
    }

    // 매장 리뷰 수정 요청 처리
    @PatchMapping("/review")
    public ResponseEntity<?> shopReviewUpdate(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Review.Update request) {
        // 현재 로그인한 사용자 정보 가져오기
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        // 매장 리뷰 서비스를 통해 리뷰 수정 요청 처리
        ReviewShopEntity reviewShopEntity = shopReviewService.shopReviewUpdate(customerEntity, request);
        // 수정된 리뷰 정보 반환
        return ResponseEntity.ok(reviewShopEntity);
    }

    // 매장 리뷰 삭제 요청 처리
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> deleteShopReview(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long reviewId) {
        // 매장 리뷰 서비스를 통해 리뷰 삭제 요청 처리
        this.shopReviewService.deleteShopReview(userDetails, reviewId);
        // 삭제 성공 메시지 반환
        return ResponseEntity.ok(1);
    }
}

