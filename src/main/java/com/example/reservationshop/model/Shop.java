package com.example.reservationshop.model;

import lombok.Data;

public class Shop {
    @Data
    public static class Request{
        private String shopName;
        private String arr;
        private String document;
    }

}
