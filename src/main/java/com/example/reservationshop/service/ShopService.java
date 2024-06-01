package com.example.reservationshop.service;

import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Shop;
import com.example.reservationshop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopEntity createShop(Shop.Request request){
        ShopEntity shopEntity = ShopEntity.from(null, request);
        return shopRepository.save(shopEntity);
    }

    public void shopList(Pageable pageable) {
        
    }
}
