package com.example.reservationshop.controller;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Auth;
import com.example.reservationshop.model.Shop;
import com.example.reservationshop.service.ManagerService;
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
    @PostMapping
    public ResponseEntity<?> createShop(@AuthenticationPrincipal UserDetails userDetails,@Valid @RequestBody Shop.Request request) {
        ManagerEntity manager = (ManagerEntity) userDetails;
        ShopEntity shop = this.shopService.createShop(manager,request);
        return ResponseEntity.ok(shop);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUpShopEmployee(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Auth.SignUpEmployee request){
        ManagerEntity manager = (ManagerEntity) userDetails;
        ManagerEntity managerEntity = this.shopService.signUpShopEmployee(manager, request);
        return ResponseEntity.ok(managerEntity);
    }

    @GetMapping
    public ResponseEntity<?> shopList(final Pageable pageable) {
        Page<Shop.Response> responses = this.shopService.shopList(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?>ShopDetail(@PathVariable Long shopId){
        Shop.Detail detail = shopService.shopDetail(shopId);
        return ResponseEntity.ok(detail);
    }

    @PatchMapping
    public ResponseEntity<?> modifyShop(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Shop.Modify request){
        ManagerEntity manager = (ManagerEntity) userDetails;
        ShopEntity shopEntity=this.shopService.updateShop(request,manager);
        return ResponseEntity.ok(shopEntity);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long shopId) {
        ManagerEntity manager = (ManagerEntity) userDetails;
        this.shopService.removeShop(manager,shopId);
        return ResponseEntity.ok("1");
    }




}
