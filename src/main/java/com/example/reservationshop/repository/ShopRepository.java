package com.example.reservationshop.repository;

import com.example.reservationshop.entity.ShopEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity,Long> {

    Page<ShopEntity> findAllBy(Pageable pageable);
}
