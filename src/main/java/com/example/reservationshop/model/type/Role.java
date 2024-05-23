package com.example.reservationshop.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {
    ROLE_MANAGER("manager"),
    ROLE_EMPLOYEE("employee"),
    ROLE_CUSTOMER("customer");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
