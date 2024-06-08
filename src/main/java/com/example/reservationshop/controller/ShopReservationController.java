package com.example.reservationshop.controller;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ReservationShopEntity;
import com.example.reservationshop.model.Reservation;
import com.example.reservationshop.service.ShopReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class ShopReservationController {
    private final ShopReservationService shopReservationService;
    @PostMapping("/{shopId}/reservation")
    public ResponseEntity<?> reservationShop(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long shopId, @RequestBody Reservation.Request request) {
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        ReservationShopEntity reservationShopEntity = shopReservationService.makeReservation(shopId,request, customerEntity);
        return ResponseEntity.ok(reservationShopEntity);
    }

    @GetMapping("/{shopId}/reservation")
    public ResponseEntity<?> serchReservationShop(@PathVariable Long shopId,@RequestParam LocalDate date) {
        List<Reservation.Response> responses = shopReservationService.serchReservationShop(shopId, date);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/reservation/{reservationId}")
    public ResponseEntity<?> shopCheckIn(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long reservationId){
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        shopReservationService.shopCheckIn(customerEntity, reservationId);

        return ResponseEntity.ok(1);
    }


}
