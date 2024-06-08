package com.example.reservationshop.model;

import lombok.Builder;
import lombok.Data;

public class Review {
    @Data
    public static class Request{
        private Long reservationId;
        private Float shopRating;
        private String document;

    }
    @Data
    public static class Update{
        private Long reviewId;
        private double shopRating;
        private String document;
    }
    @Data
    @Builder
    public static class Response{
        private Long reviewId;
        private String customerName;
        private double shopRating;
        private String document;
    }


}
