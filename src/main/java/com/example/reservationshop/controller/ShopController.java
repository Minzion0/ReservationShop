package com.example.reservationshop.controller;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Auth;
import com.example.reservationshop.model.Shop;
import com.example.reservationshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    // 매장 등록 요청 처리
    @PostMapping
    public ResponseEntity<?> createShop(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Shop.Request request) {
        // 현재 로그인한 사용자 정보를 매니저 엔티티로 변환
        ManagerEntity manager = (ManagerEntity) userDetails;
        // 매장 서비스를 통해 매장 생성 요청 처리
        ShopEntity shop = this.shopService.createShop(manager, request);
        // 생성된 매장 정보 반환
        return ResponseEntity.ok(shop);
    }

    // 매장 직원 등록 요청 처리
    @PostMapping("/signup")
    public ResponseEntity<?> signUpShopEmployee(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Auth.SignUpEmployee request) {
        // 현재 로그인한 사용자 정보를 매니저 엔티티로 변환
        ManagerEntity manager = (ManagerEntity) userDetails;
        // 매장 서비스를 통해 매장 직원 등록 요청 처리
        ManagerEntity managerEntity = this.shopService.signUpShopEmployee(manager, request);
        // 등록된 매장 직원 정보 반환
        return ResponseEntity.ok(managerEntity);
    }

    // 매장 목록 조회 요청 처리
    @GetMapping
    public ResponseEntity<?> shopList(final Pageable pageable) {
        // 매장 서비스를 통해 매장 목록 조회 요청 처리
        Page<Shop.Response> responses = this.shopService.shopList(pageable);
        // 조회된 매장 목록 반환
        return ResponseEntity.ok(responses);
    }

    // 특정 매장 상세 정보 조회 요청 처리
    @GetMapping("/{shopId}")
    public ResponseEntity<?> ShopDetail(@PathVariable Long shopId) {
        // 매장 서비스를 통해 특정 매장의 상세 정보 조회 요청 처리
        Shop.Detail detail = shopService.shopDetail(shopId);
        // 조회된 매장 상세 정보 반환
        return ResponseEntity.ok(detail);
    }

    // 매장 정보 수정 요청 처리
    @PatchMapping
    public ResponseEntity<?> modifyShop(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Shop.Modify request) {
        // 현재 로그인한 사용자 정보를 매니저 엔티티로 변환
        ManagerEntity manager = (ManagerEntity) userDetails;
        // 매장 서비스를 통해 매장 정보 수정 요청 처리
        ShopEntity shopEntity = this.shopService.updateShop(request, manager);
        // 수정된 매장 정보 반환
        return ResponseEntity.ok(shopEntity);
    }

    // 매장 삭제 요청 처리
    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long shopId) {
        // 현재 로그인한 사용자 정보를 매니저 엔티티로 변환
        ManagerEntity manager = (ManagerEntity) userDetails;
        // 매장 서비스를 통해 매장 삭제 요청 처리
        this.shopService.removeShop(manager, shopId);
        // 삭제 성공 메시지 반환
        return ResponseEntity.ok("1");
    }

}






