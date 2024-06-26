package com.example.reservationshop.model;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.entity.ShopEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {
    @Data
    public static class Request{
        @NotBlank(message = "Shop name is required")
        private String shopName;

        @NotBlank(message = "Address is required")
        private String arr;

        @NotBlank(message = "Document is required")
        private String document;

        @Min(value = 1, message = "Table count must be at least 1")
        private int tableCount;


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
        private double averageRating;

        public static Page<Shop.Response> toPage(Page<ShopEntity> shopEntities){
            List<Response> responses = shopEntities.stream().map(item -> {
                return Response.builder()
                        .shopId(item.getId())
                        .shopName(item.getShopName())
                        .arr(item.getArr())
                        .averageRating(item.getAverageRating())
                        .build();
            }).collect(Collectors.toList());

            return new PageImpl<>(responses,shopEntities.getPageable(),shopEntities.getTotalElements());
        }

    }
    @Data
    @Builder
    public static class Detail{
        private Long shopId;
        private String shopName;
        private String arr;
        private String document;
        private double averageRating;
        private List<Review.Response> reviews;
    }


}
