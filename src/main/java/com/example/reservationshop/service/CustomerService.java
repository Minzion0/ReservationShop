package com.example.reservationshop.service;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.model.Auth;
import com.example.reservationshop.repository.CustomerRepository;
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
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 이름을 이용하여 사용자 정보를 로드하는 메서드입니다.
     *
     * @param username 사용자 이름
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다. ->" + username));
    }

    /**
     * 새로운 고객을 등록하는 메서드입니다.
     *
     * @param customer 고객 가입 정보
     * @return 등록된 고객 엔티티
     */
    @Transactional
    public CustomerEntity signUpCustomer(Auth.SignUp customer) {
        if (this.customerRepository.existsByUsername(customer.getUsername())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        CustomerEntity customerEntity = CustomerEntity.from(
                customer.getUsername(),
                passwordEncoder.encode(customer.getPassword()),
                customer.getRoles()
        );

        return this.customerRepository.save(customerEntity);
    }

    /**
     * 고객 로그인을 처리하는 메서드입니다.
     *
     * @param customer 로그인 요청 정보
     * @return 로그인된 고객 엔티티
     */
    public CustomerEntity signInCustomer(Auth.SignIn customer) {
        CustomerEntity customerEntity = customerRepository.findByUsername(customer.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다. ->" + customer.getUsername()));

        if (!this.passwordEncoder.matches(customer.getPassword(), customerEntity.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return customerEntity;
    }
}





