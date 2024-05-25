package com.example.reservationshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/manager/signup")
    public ResponseEntity<?> ManagerSignup() {
        return null;
    }

    @PostMapping("/manager/signin")
    public ResponseEntity<?> ManagerSignin() {
        return null;
    }

    @PostMapping("/shop/signup")
    public ResponseEntity<?> signup() {
        return null;
    }

    @PostMapping("/shop/signin")
    public ResponseEntity<?> signin() {
        return null;
    }

}
