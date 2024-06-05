package com.example.reservationshop.service;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ReservationShopEntity;
import com.example.reservationshop.entity.ReviewShopEntity;
import com.example.reservationshop.model.Review;
import com.example.reservationshop.repository.ReservationShopRepository;
import com.example.reservationshop.repository.ShopReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopReviewService {

    private final ShopReviewRepository shopReviewRepository;
    private final ReservationShopRepository reservationShopRepository;

    @Transactional(readOnly = true)
    public ReviewShopEntity createReview(CustomerEntity customer, Review.Request request){
        ReservationShopEntity reservationShopEntity = reservationShopRepository.findById(request.getReservationId()).orElseThrow(() -> new RuntimeException("찾을수 없습니다."));

        if (!Objects.equals(reservationShopEntity.getCustomerId().getId(), customer.getId())){
            throw new RuntimeException("본인만 리뷰가 가능합니다.");
        }
        if (reservationShopEntity.getCheckIn()!=1){
            throw new RuntimeException("체크인 하지 않았습니다.");
        }

        ReviewShopEntity reviewShopEntity = ReviewShopEntity.from(reservationShopEntity, request);

        return this.shopReviewRepository.save(reviewShopEntity);
    }
}
