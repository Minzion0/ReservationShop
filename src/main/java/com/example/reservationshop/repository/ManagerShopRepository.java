package com.example.reservationshop.repository;

import com.example.reservationshop.entity.ManagerShopEntity;
import com.example.reservationshop.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerShopRepository extends JpaRepository<ManagerShopEntity,Long> {

    //List<ManagerShopEntity> findByShopId(Long shopId);
}
