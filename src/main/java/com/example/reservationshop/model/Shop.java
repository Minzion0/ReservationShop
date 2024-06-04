package com.example.reservationshop.model;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.entity.ShopEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class Shop {
    @Data
    public static class Request{
        private String shopName;
        private String arr;
        private String document;
        private int tableCount;

        public static ShopEntity toEntity(ManagerEntity managerId, Shop.Request request){
            return ShopEntity.from(managerId,request.getShopName(), request.getArr(),request.getDocument(), request.getTableCount());
        }
    }
    @Data
    public static class Modify{
        private Long shopId;
        private String shopName;
        private String arr;
        private String document;

        public static ShopEntity toEntity(Long shopId,ManagerEntity managerId, Shop.Request request){
            return ShopEntity.from(shopId,managerId,request.getShopName(), request.getArr(),request.getDocument(), request.getTableCount());
        }

    }
    @Data
    @Builder
    public static class Response{
        private Long shopId;
        private String shopName;
        private String arr;

        public static Page<Shop.Response> toPage(Page<ShopEntity> shopEntities){
            List<Response> responses = shopEntities.stream().map(item -> {
                return Response.builder()
                        .shopId(item.getId())
                        .shopName(item.getShopName())
                        .arr(item.getArr())
                        .build();
            }).collect(Collectors.toList());

            return new PageImpl<>(responses,shopEntities.getPageable(),shopEntities.getTotalElements());
        }

    }


}
