package com.example.reservationshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopReviewController {

    @PostMapping("/review")
    public ResponseEntity<?> shopReview(){
        return null;
    }

    @PatchMapping("/review")
    public ResponseEntity<?> shopReviewPatch(){
        return null;
    }

    @DeleteMapping("/review")
    public ResponseEntity<?> deleteShopReview(){
        return null;
    }

    

}
