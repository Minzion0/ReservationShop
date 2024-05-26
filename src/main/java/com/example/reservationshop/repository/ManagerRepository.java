package com.example.reservationshop.repository;

import com.example.reservationshop.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity,Long> {

    Optional<ManagerEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
