package com.example.reservationshop.service;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ReservationShopEntity;
import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Reservation;
import com.example.reservationshop.repository.ReservationShopRepository;
import com.example.reservationshop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopReservationService {
    private final ReservationShopRepository reservationShopRepository;
    private final ShopRepository shopRepository;

    public ReservationShopEntity makeReservation(Reservation.Request request, CustomerEntity customerEntity){
        ShopEntity shopEntity = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("해당 shop은 존제하지 않습니다."));

        LocalDateTime requestDateTime = LocalDateTime.of(request.getReservationDate(), request.getReservationTime());
        LocalDateTime endTime = requestDateTime.plusHours(1);

        reservationShopRepository.findByShopIdAndReservationTimeBetween(shopEntity,requestDateTime,endTime);

        return null;

    }
}
