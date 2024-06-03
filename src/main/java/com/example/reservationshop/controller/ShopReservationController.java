package com.example.reservationshop.controller;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.model.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class ShopReservationController {

    @PostMapping("/reservation")
    public ResponseEntity<?> reservationShop(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Reservation.Request request) {
        CustomerEntity customerEntity = (CustomerEntity) userDetails;

        return null;
    }

    @GetMapping("/reservation/{shopId}")
    public ResponseEntity<?> serchReservationShop(@RequestParam LocalDateTime dateTime) {
        return null;
    }

    @PatchMapping("/reservation")
    public ResponseEntity<?> shopCheckIn(){return null;}


}
