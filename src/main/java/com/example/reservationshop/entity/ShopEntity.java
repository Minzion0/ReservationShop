package com.example.reservationshop.entity;

import com.example.reservationshop.model.Shop;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Table(name = "shop")
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ManagerEntity.class)
    @JoinColumn(name = "manager_id", nullable = false)
    private ManagerEntity managerId;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String arr;

    private int tableCount; // 테이블 수 추가

    private double averageRating; // 평균 별점 추가

    @OneToMany(mappedBy = "shopId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationShopEntity> reservations;

    @OneToMany(mappedBy = "shopId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewShopEntity> reviews;

    private ShopEntity(ManagerEntity managerId, String shopName, String document, String arr, int tableCount) {
        this.id = null;
        this.managerId = managerId;
        this.document = document;
        this.shopName = shopName;
        this.arr = arr;
        this.tableCount = tableCount;
        this.averageRating = 0.0;
    }

    private ShopEntity(Long shopId, ManagerEntity managerId, String shopName, String document, String arr, int tableCount) {
        this.id = shopId;
        this.managerId = managerId;
        this.document = document;
        this.shopName = shopName;
        this.arr = arr;
        this.tableCount = tableCount;
        this.averageRating = 0.0;
    }

    public static ShopEntity from(ManagerEntity managerId, String shopName, String document, String arr, int tableCount) {
        return new ShopEntity(managerId, shopName, document, arr, tableCount);
    }

    public static ShopEntity from(ManagerEntity managerId, Shop.Request request) {
        return new ShopEntity(managerId, request.getShopName(), request.getDocument(), request.getArr(), request.getTableCount());
    }

    public static ShopEntity from(Long shopId, ManagerEntity managerId, String shopName, String document, String arr, int tableCount) {
        return new ShopEntity(shopId, managerId, shopName, document, arr, tableCount);
    }
    //엔티티 수정 메소드
    public ShopEntity modify(Shop.Modify request) {
        if (request.getArr() != null) {
            this.arr = request.getArr();
        }
        if (request.getShopName() != null) {
            this.shopName = request.getShopName();
        }
        if (request.getDocument() != null) {
            this.document = request.getDocument();
        }
        return this;
    }
    //평균 별점 입력 메소드
    public void reviewAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}

