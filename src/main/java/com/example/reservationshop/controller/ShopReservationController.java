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

    // 매장 예약 생성 요청 처리
    @PostMapping("/{shopId}/reservation")
    public ResponseEntity<?> reservationShop(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long shopId, @RequestBody Reservation.Request request) {
        // 현재 로그인한 사용자 정보 가져오기
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        // 매장 예약 서비스를 통해 예약 생성 요청 처리
        ReservationShopEntity reservationShopEntity = shopReservationService.makeReservation(shopId, request, customerEntity);
        // 생성된 예약 정보 반환
        return ResponseEntity.ok(reservationShopEntity);
    }

    // 매장 예약 조회 요청 처리
    @GetMapping("/{shopId}/reservation")
    public ResponseEntity<?> searchReservationShop(@PathVariable Long shopId, @RequestParam LocalDate date) {
        // 매장 예약 서비스를 통해 예약 조회 요청 처리
        List<Reservation.Response> responses = shopReservationService.searchReservationShop(shopId, date);
        // 조회된 예약 정보 반환
        return ResponseEntity.ok(responses);
    }

    // 매장 방문 확인 요청 처리
    @PatchMapping("/reservation/{reservationId}")
    public ResponseEntity<?> shopCheckIn(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long reservationId) {
        // 현재 로그인한 사용자 정보 가져오기
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        // 매장 예약 서비스를 통해 방문 확인 요청 처리
        shopReservationService.shopCheckIn(customerEntity, reservationId);
        // 방문 확인 성공 메시지 반환
        return ResponseEntity.ok(1);
    }
}
