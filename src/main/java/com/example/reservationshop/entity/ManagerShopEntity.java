package com.example.reservationshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "manager_shop")
public class ManagerShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private ManagerEntity managerId;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shopId;

    private int managerValid;

    private ManagerShopEntity(ManagerEntity managerId, ShopEntity shopId, int managerValid) {
        this.id=null;
        this.managerId = managerId;
        this.shopId = shopId;
        this.managerValid = managerValid;
    }

    public static ManagerShopEntity from(ManagerEntity managerId,ShopEntity shopId,int managerValid){
        return new ManagerShopEntity(managerId,shopId,managerValid);
    }
}
