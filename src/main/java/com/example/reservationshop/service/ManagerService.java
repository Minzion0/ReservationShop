package com.example.reservationshop.service;

import com.example.reservationshop.entity.ManagerEntity;
import com.example.reservationshop.model.Auth;
import com.example.reservationshop.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService implements UserDetailsService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.managerRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("존재 하지 않는 ID 입니다. ->"+username));

    }
    @Transactional
    public ManagerEntity signUpManager(Auth.SignUp manager){
        if (this.managerRepository.existsByUsername(manager.getUsername())){
            throw new RuntimeException("이미 사용 중인 아이디 입니다.");
        }
        ManagerEntity managerEntity = ManagerEntity.from(
                manager.getUsername(),
                passwordEncoder.encode(manager.getPassword()),
                manager.getRoles()
        );

        return this.managerRepository.save(managerEntity);
    }

    public ManagerEntity signInManager(Auth.SignIn manager){

        ManagerEntity managerEntity = managerRepository.findByUsername(manager.getUsername())
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 ID 입니다. ->" + manager.getUsername()));

        if (!this.passwordEncoder.matches(manager.getPassword(),managerEntity.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        }

        return managerEntity;

    }




}
