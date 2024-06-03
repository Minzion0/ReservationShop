package com.example.reservationshop.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_MANAGER("MANAGER"),
    ROLE_EMPLOYEE("EMPLOYEE"),
    ROLE_CUSTOMER("CUSTOMER");

    private final String role;

}
