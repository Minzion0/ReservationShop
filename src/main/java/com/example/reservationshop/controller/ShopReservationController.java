package com.example.reservationshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class ShopReservationController {

    @PostMapping("/reservation")
    public ResponseEntity<?> reservationShop() {
        return null;
    }

    @GetMapping("/reservation/{shopId}")
    public ResponseEntity<?> serchReservationShop(@RequestParam LocalDateTime dateTime) {
        return null;
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> shopCheckIn(){return null;}


}
