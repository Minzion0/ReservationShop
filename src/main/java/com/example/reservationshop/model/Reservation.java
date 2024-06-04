package com.example.reservationshop.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    @Data
    public static class Request{
        private Long shopId;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private String customerPhoneNumber;
    }
    @Data
    @Builder
    public static class Response{
        private Long reservationId;
        private LocalDateTime reservationTime;
    }
}
