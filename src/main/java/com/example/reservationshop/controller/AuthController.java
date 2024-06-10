package com.example.reservationshop.controller;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.model.Auth;
import com.example.reservationshop.security.TokenProvider;
import com.example.reservationshop.service.CustomerService;
import com.example.reservationshop.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final ManagerService managerService;
    private final TokenProvider tokenProvider;

    // 매니저 회원 가입 요청 처리
    @PostMapping("/manager/signup")
    public ResponseEntity<?> ManagerSignup(@RequestBody Auth.SignUp request) {
        // 매니저 서비스를 통해 매니저 회원 가입 요청 처리
        ManagerEntity managerEntity = managerService.signUpManager(request);
        // 회원 가입된 매니저 정보 반환
        return ResponseEntity.ok(managerEntity);
    }

    // 매니저 로그인 요청 처리
    @PostMapping("/manager/signin")
    public ResponseEntity<?> ManagerSignIn(@RequestBody Auth.SignIn request) {
        // 매니저 서비스를 통해 매니저 로그인 요청 처리
        ManagerEntity managerEntity = managerService.signInManager(request);
        // 매니저 정보를 기반으로 JWT 토큰 생성
        String token = tokenProvider.generateToken(managerEntity.getUsername(), managerEntity.getRoles());
        // 로그인 성공 메시지와 함께 토큰 반환
        log.info("{} login", managerEntity.getUsername());
        return ResponseEntity.ok(token);
    }

    // 고객 회원 가입 요청 처리
    @PostMapping("/shop/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        // 고객 서비스를 통해 고객 회원 가입 요청 처리
        CustomerEntity customerEntity = customerService.signUpCustomer(request);
        // 회원 가입된 고객 정보 반환
        return ResponseEntity.ok(customerEntity);
    }

    // 고객 로그인 요청 처리
    @PostMapping("/shop/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 고객 서비스를 통해 고객 로그인 요청 처리
        CustomerEntity customerEntity = customerService.signInCustomer(request);
        // 고객 정보를 기반으로 JWT 토큰 생성
        String token = tokenProvider.generateToken(customerEntity.getUsername(), customerEntity.getRoles());
        // 로그인 성공 메시지와 함께 토큰 반환
        return ResponseEntity.ok(token);
    }

}

