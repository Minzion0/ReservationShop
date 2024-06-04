package com.example.reservationshop.repository;

import com.example.reservationshop.entity.ReservationShopEntity;
import com.example.reservationshop.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationShopRepository extends JpaRepository<ReservationShopEntity,Long> {

    Optional<List<ReservationShopEntity>>findByShopIdAndReservationTimeBetween(ShopEntity shopEntity, LocalDateTime reservationTime, LocalDateTime endTime);

}
