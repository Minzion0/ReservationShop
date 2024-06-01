package com.example.reservationshop.controller;

import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Shop;
import com.example.reservationshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    @PostMapping
    public ResponseEntity<?> createShop(@RequestBody Shop.Request request) {
        ShopEntity shop = this.shopService.createShop(request);
        return ResponseEntity.ok(shop);
    }

    @GetMapping
    public ResponseEntity<?> shopList(final Pageable pageable) {
        this.shopService.shopList(pageable);
        return null;
    }

    @PatchMapping
    public ResponseEntity<?> patchShop(){
        return null;
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@PathVariable Long shopId) {
        return null;
    }




}
