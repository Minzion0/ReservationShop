package com.example.reservationshop.model;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.entity.ShopEntity;
import lombok.Data;

public class Shop {
    @Data
    public static class Request{
        private String shopName;
        private String arr;
        private String document;

        public static ShopEntity toEntity(ManagerEntity managerId, Shop.Request request){
            return ShopEntity.from(managerId,request.getShopName(), request.arr,request.getDocument());
        }
    }

}
