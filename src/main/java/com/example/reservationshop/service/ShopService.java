package com.example.reservationshop.service;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.entity.ManagerShopEntity;
import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Auth;
import com.example.reservationshop.model.Review;
import com.example.reservationshop.model.Shop;
import com.example.reservationshop.repository.ManagerRepository;
import com.example.reservationshop.repository.ManagerShopRepository;
import com.example.reservationshop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final ManagerRepository managerRepository;
    private final ManagerShopRepository managerShopRepository;
    private final PasswordEncoder passwordEncoder;
    final int MANAGER_VALID = 1;
    final int EMPLOYEE_VALID = 0;

    /**
     * 매장을 생성하는 메서드입니다.
     *
     * @param manager 로그인한 매니저 엔티티
     * @param request 생성할 매장 요청 정보
     * @return 생성된 매장 엔티티
     */
    @Transactional(readOnly = true)
    public ShopEntity createShop(ManagerEntity manager, Shop.Request request) {

        ManagerEntity managerEntity = this.managerRepository.findById(manager.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 매니저 입니다."));

        ShopEntity shopEntity = ShopEntity.from(managerEntity, request);


        ShopEntity saveShop = shopRepository.save(shopEntity);

        //1 이면 매니저

        shopManagerSaving(managerEntity, saveShop, MANAGER_VALID);

        return saveShop;
    }

    /**
     * 매장 직원을 등록하는 메서드입니다.
     *
     * @param manager  로그인한 매니저 엔티티
     * @param employee 등록할 직원 정보
     * @return 등록된 직원 엔티티
     */
    @Transactional(readOnly = true)
    public ManagerEntity signUpShopEmployee(ManagerEntity manager, Auth.SignUpEmployee employee){
        ShopEntity shopEntity = shopRepository.findById(employee.getShopId()).orElseThrow(() -> new RuntimeException("해당 가계를 찾을수 없습니다."));
        if (!Objects.equals(shopEntity.getManagerId().getId(), manager.getId())){
            throw new RuntimeException(shopEntity.getShopName()+"의 매니저만 등록이 가능합니다.");
        }
        ;
        ManagerEntity managerEntity = ManagerEntity.from(employee.getUsername(), passwordEncoder.encode(employee.getPassword()), employee.getRoles());
        ManagerEntity result = managerRepository.save(managerEntity);
        managerShopRepository.save(ManagerShopEntity.from(result, shopEntity, EMPLOYEE_VALID));

        return result;
    }

    private void shopManagerSaving(ManagerEntity managerEntity, ShopEntity saveShop, int managerValid) {
        ManagerShopEntity managerShopEntity =
                ManagerShopEntity.from(managerEntity, saveShop, managerValid);

        this.managerShopRepository.save(managerShopEntity);
    }
    /**
     * 매장을 조회하는 메서드입니다.
     *
     * @param pageable 페이지 정보
     * @return 페이지로 분할된 매장 응답
     */

    public Page<Shop.Response> shopList(Pageable pageable) {

        Page<ShopEntity> allBy = shopRepository.findAllBy(pageable);

        return Shop.Response.toPage(allBy);

    }
    /**
     * 매장 정보를 수정하는 메서드입니다.
     *
     * @param request 수정할 매장 정보
     * @param manager 로그인한 매니저 엔티티
     * @return 수정된 매장 엔티티
     */

    @Transactional(readOnly = true)
    public ShopEntity updateShop(Shop.Modify request, ManagerEntity manager) {
        ShopEntity shopEntity = getShopEntity(request.getShopId());

        if (!Objects.equals(shopEntity.getManagerId().getId(), manager.getId())) {
            throw new RuntimeException(shopEntity.getShopName() + "의 매니저가 아님니다.");
        }

        return shopEntity.modify(request);
    }
    /**
     * 매장을 삭제하는 메서드입니다.
     *
     * @param manager 로그인한 매니저 엔티티
     * @param shopId  삭제할 매장 ID
     */

    @Transactional(readOnly = true)
    public void removeShop(ManagerEntity manager, Long shopId) {
        ShopEntity shopEntity = getShopEntity(shopId);

        if (!Objects.equals(shopEntity.getManagerId().getId(), manager.getId())) {
            throw new RuntimeException(manager.getUsername() + "은" + shopEntity.getShopName() + "의 관리자가 아님니다.");
        }

        shopRepository.delete(shopEntity);

    }

    private ShopEntity getShopEntity(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 않습니다."));
    }
    /**
     * 매장 상세 정보를 조회하는 메서드입니다.
     *
     * @param shopId 조회할 매장 ID
     * @return 매장 상세 정보 응답
     */

    public Shop.Detail shopDetail(Long shopId) {
        ShopEntity shopEntity = getShopEntity(shopId);

        return Shop.Detail.builder()
                .shopId(shopEntity.getId())
                .shopName(shopEntity.getShopName())
                .arr(shopEntity.getArr())
                .document(shopEntity.getDocument())
                .averageRating(shopEntity.getAverageRating())
                .reviews(shopEntity.getReviews().stream().map(itme ->
                        Review.Response.
                                builder()
                                .shopRating(itme.getReviewRating())
                                .customerName(itme.getReservationId().getCustomerId().getUsername())
                                .reviewId(itme.getReservationId().getId())
                                .document(itme.getDocument())
                                .build()).collect(Collectors.toList()))
                .build();
    }
}

