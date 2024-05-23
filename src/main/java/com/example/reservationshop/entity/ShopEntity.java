package com.example.reservationshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "shop")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = ManagerEntity.class)
    private ManagerEntity managerId;
    @Column(nullable = false)
    private String document;
    @Column(nullable = false)
    private String arr;

    private ShopEntity (ManagerEntity managerId,String document,String arr){
        this.id=null;
        this.managerId=managerId;
        this.document=document;
        this.arr=arr;
    }

    public static ShopEntity from(ManagerEntity managerId,String document,String arr){
        return new ShopEntity(managerId,document,arr);
    }

}

