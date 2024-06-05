package com.example.reservationshop.model;

import lombok.Data;

public class Review {
    @Data
    public static class Request{
        private Long ReservationId;
        private Float shopRating;
        private String document;

    }
}
