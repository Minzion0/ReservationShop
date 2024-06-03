package com.example.reservationshop.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    @Data
    public static class Request{
        private Long shopId;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private String customerPhoneNumber;
    }
}
