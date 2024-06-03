package com.example.reservationshop.repository;

import com.example.reservationshop.entity.ReservationShopEntity;
import com.example.reservationshop.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationShopRepository extends JpaRepository<ReservationShopEntity,Long> {

    List<ReservationShopEntity>findByShopIdAndReservationTimeBetween(ShopEntity shopEntity, LocalDateTime reservationTime,LocalDateTime endTime);
}
