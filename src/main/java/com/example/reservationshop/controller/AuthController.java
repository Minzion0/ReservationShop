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
    @PostMapping("/manager/signup")
    public ResponseEntity<?> ManagerSignup(@RequestBody Auth.SignUpManager request) {
        ManagerEntity managerEntity = managerService.signUpManager(request);

        return ResponseEntity.ok(managerEntity);
    }

    @PostMapping("/manager/signin")
    public ResponseEntity<?> ManagerSignIn(@RequestBody Auth.SignIn request) {

        ManagerEntity managerEntity = managerService.signInManager(request);
        //TODO JWT 발급 구현해야함 구현이 끝나면 토큰을 반환

        String token = tokenProvider.generateToken(managerEntity.getUsername(), managerEntity.getRoles());
        log.info("{} login",managerEntity.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/shop/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUpManager request) {
        CustomerEntity customerEntity = customerService.signUpCustomer(request);

        return ResponseEntity.ok(customerEntity);
    }

    @PostMapping("/shop/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        CustomerEntity customerEntity = customerService.signInCustomer(request);
        String token = tokenProvider.generateToken(customerEntity.getUsername(), customerEntity.getRoles());
        return ResponseEntity.ok(token);
    }

}
