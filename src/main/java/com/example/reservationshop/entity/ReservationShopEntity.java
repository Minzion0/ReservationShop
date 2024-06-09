package com.example.reservationshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation_shop")
public class ReservationShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = ShopEntity.class)
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopEntity shopId;
    @ManyToOne(targetEntity = CustomerEntity.class)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerId;
    private LocalDateTime reservationTime;
    private int checkIn;

    private ReservationShopEntity (ShopEntity shopId,CustomerEntity customerId,LocalDateTime reservationTime){
        this.id=null;
        this.shopId=shopId;
        this.customerId=customerId;
        this.reservationTime=reservationTime;
        this.checkIn=0;
    }

    public static ReservationShopEntity from(ShopEntity shopId,CustomerEntity customerId,LocalDateTime reservationTime){
        return new ReservationShopEntity(shopId,customerId,reservationTime);
    }

    public ReservationShopEntity checkIn(){
        this.checkIn=1;

        return this;
    }

}
