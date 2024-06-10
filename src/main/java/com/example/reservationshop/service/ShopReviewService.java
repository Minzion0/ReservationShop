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

    /**
     * 리뷰를 생성하는 메서드입니다.
     *
     * @param customer 로그인한 고객 엔티티
     * @param request  생성할 리뷰 요청 정보
     * @return 생성된 리뷰 엔티티
     */
    @Transactional(readOnly = true)
    public ReviewShopEntity createReview(CustomerEntity customer, Review.Request request){
        ReservationShopEntity reservationShopEntity = reservationShopRepository.findById(request.getReservationId())
                .orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다."));

        if (!Objects.equals(reservationShopEntity.getCustomerId().getId(), customer.getId())){
            throw new RuntimeException("본인만 리뷰를 작성할 수 있습니다.");
        }

        if (reservationShopEntity.getCheckIn() != 1){
            throw new RuntimeException("체크인을 하지 않았습니다.");
        }

        ReviewShopEntity reviewShopEntity = ReviewShopEntity.from(reservationShopEntity, request);
        ReviewShopEntity result = this.shopReviewRepository.save(reviewShopEntity);
        updateShopRating(reservationShopEntity);

        return result;
    }

    /**
     * 매장 평점을 업데이트하는 메서드입니다.
     *
     * @param reservationShopEntity 예약 엔티티
     */
    private static void updateShopRating(ReservationShopEntity reservationShopEntity) {
        ShopEntity shopEntity = reservationShopEntity.getShopId();
        List<ReviewShopEntity> reviews = shopEntity.getReviews();
        double averageRating = reviews.stream().mapToDouble(ReviewShopEntity::getReviewRating).average().orElse(0.0);
        shopEntity.reviewAverageRating(averageRating);
    }

    /**
     * 리뷰를 수정하는 메서드입니다.
     *
     * @param customerEntity 로그인한 고객 엔티티
     * @param request        수정할 리뷰 요청 정보
     * @return 수정된 리뷰 엔티티
     */
    @Transactional(readOnly = true)
    public ReviewShopEntity shopReviewUpdate(CustomerEntity customerEntity, Review.Update request) {
        ReviewShopEntity reviewShopEntity = shopReviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new RuntimeException("해당 리뷰를 찾을 수 없습니다."));

        if (!Objects.equals(reviewShopEntity.getReservationId().getCustomerId().getId(), customerEntity.getId())){
            throw new RuntimeException("본인만 리뷰를 수정할 수 있습니다.");
        }

        reviewShopEntity.update(request);
        updateShopRating(reviewShopEntity.getReservationId());

        return reviewShopEntity;
    }

    /**
     * 리뷰를 삭제하는 메서드입니다.
     *
     * @param userDetails 로그인한 사용자 엔티티
     * @param reviewId    삭제할 리뷰 ID
     */
    @Transactional(readOnly = true)
    public void deleteShopReview(UserDetails userDetails, Long reviewId) {
        CustomerEntity customerEntity = (CustomerEntity) userDetails;
        ReviewShopEntity reviewShopEntity = getReviewShopEntity(reviewId);

        if (customerEntity.getId() == null){
            ManagerEntity manager = (ManagerEntity) userDetails;
            ShopEntity shopResult = reviewShopEntity.getReservationId().getShopId();
            ManagerEntity managerResult = shopResult.getManagerId();

            if (!Objects.equals(manager.getId(), managerResult.getId())){
                throw new RuntimeException(shopResult.getShopName() + "의 매니저만 리뷰를 삭제할 수 있습니다.");
            }
        } else {
            CustomerEntity customerResult = reviewShopEntity.getReservationId().getCustomerId();

            if (!customerEntity.getId().equals(customerResult.getId())){
                throw new RuntimeException("해당 리뷰 작성자만 리뷰를 삭제할 수 있습니다.");
            }
        }

        shopReviewRepository.delete(reviewShopEntity);
    }

    /**
     * 리뷰 엔티티를 조회하는 메서드입니다.
     *
     * @param reviewId 삭제할 리뷰 ID
     * @return 리뷰 엔티티
     */
    private ReviewShopEntity getReviewShopEntity(Long reviewId) {
        return shopReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
    }
}

