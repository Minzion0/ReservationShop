package com.example.reservationshop.service;

import com.example.reservationshop.entity.*;
import com.example.reservationshop.model.Review;
import com.example.reservationshop.repository.ReservationShopRepository;
import com.example.reservationshop.repository.ShopReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        ReviewShopEntity result = this.shopReviewRepository.save(reviewShopEntity);
        updateShopRating(reservationShopEntity);

        return result;
    }

    private static void updateShopRating(ReservationShopEntity reservationShopEntity) {
        ShopEntity shopEntity = reservationShopEntity.getShopId();
        List<ReviewShopEntity> reviews = shopEntity.getReviews();
        double averageRating = reviews.stream().mapToDouble(ReviewShopEntity::getReviewRating).average().orElse(0.0);
        shopEntity.reviewAverageRating(averageRating);
    }

    @Transactional(readOnly = true)
    public ReviewShopEntity shopReviewUpdate(CustomerEntity customerEntity, Review.Update request) {
        ReviewShopEntity reviewShopEntity = shopReviewRepository.findById(request.getReviewId()).orElseThrow(() -> new RuntimeException("해당 리뷰가 존재하지 않습니다."));
        if (!Objects.equals(reviewShopEntity.getReservationId().getCustomerId().getId(), customerEntity.getId())){
            throw new RuntimeException("본인만 리뷰수정이 가능합니다.");
        }
        reviewShopEntity.update(request);
        updateShopRating(reviewShopEntity.getReservationId());

        return reviewShopEntity;
    }
    @Transactional(readOnly = true)
    public void deleteShopReview(UserDetails userDetails, Long reviewId) {
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        ReviewShopEntity reviewShopEntity = getReviewShopEntity(reviewId);

        if (customerEntity.getId()==null){
            ManagerEntity manager = (ManagerEntity) userDetails;

            ShopEntity shopResult = reviewShopEntity.getReservationId().getShopId();
            ManagerEntity managerResult = shopResult.getManagerId();

            if (!Objects.equals(manager.getId(), managerResult.getId())){
                throw new RuntimeException(shopResult.getShopName()+"가계의 매니저만 삭제가 가능합니다.");
            }

        }else {
            CustomerEntity customerResult = reviewShopEntity.getReservationId().getCustomerId();
            if (!customerEntity.getId().equals(customerResult.getId())){
                throw new RuntimeException("해당 리뷰 작성자만 삭제가 가능합니다.");
            }
        }
        shopReviewRepository.delete(reviewShopEntity);
    }

    private ReviewShopEntity getReviewShopEntity(Long reviewId) {
        ReviewShopEntity reviewShopEntity = shopReviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));
        return reviewShopEntity;
    }
}
