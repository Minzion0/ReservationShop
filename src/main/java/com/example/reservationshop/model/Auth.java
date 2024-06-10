package com.example.reservationshop.model;

import lombok.Data;

import java.util.List;

public class Auth {

    @Data
   public static class SignIn{
       private String username;
       private String password;
   }

   @Data
    public static class SignUpManager {
        private String username;
        private String password;
        private List<String> roles;
   }
    @Data
    public static class SignUpEmployee {
        private Long shopId;
        private String username;
        private String password;
        private List<String> roles;
    }
}
