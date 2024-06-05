package com.example.reservationshop.repository;

import com.example.reservationshop.entity.ReviewShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopReviewRepository extends JpaRepository<ReviewShopEntity,Long> {
}
