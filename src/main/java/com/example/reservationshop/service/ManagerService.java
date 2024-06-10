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

    /**
     * 사용자 이름을 이용하여 매니저 정보를 로드하는 메서드입니다.
     *
     * @param username 사용자 이름
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.managerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다. ->" + username));
    }

    /**
     * 새로운 매니저를 등록하는 메서드입니다.
     *
     * @param manager 매니저 가입 정보
     * @return 등록된 매니저 엔티티
     */
    @Transactional(readOnly = true)
    public ManagerEntity signUpManager(Auth.SignUp manager) {
        if (this.managerRepository.existsByUsername(manager.getUsername())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        ManagerEntity managerEntity = ManagerEntity.from(
                manager.getUsername(),
                passwordEncoder.encode(manager.getPassword()),
                manager.getRoles()
        );

        return this.managerRepository.save(managerEntity);
    }

    /**
     * 매니저 로그인을 처리하는 메서드입니다.
     *
     * @param manager 로그인 요청 정보
     * @return 로그인된 매니저 엔티티
     */
    public ManagerEntity signInManager(Auth.SignIn manager) {
        ManagerEntity managerEntity = managerRepository.findByUsername(manager.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다. ->" + manager.getUsername()));

        if (!this.passwordEncoder.matches(manager.getPassword(), managerEntity.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return managerEntity;
    }
}






