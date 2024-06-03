package com.example.reservationshop.service;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ManagerEntity;
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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("존재 하지 않는 ID 입니다. ->"+username));

    }
    @Transactional
    public CustomerEntity signUpCustomer(Auth.SignUp customer){
        if (this.customerRepository.existsByUsername(customer.getUsername())){
            throw new RuntimeException("이미 사용 중인 아이디 입니다.");
        }
        CustomerEntity customerEntity = CustomerEntity.from(
                customer.getUsername(),
                passwordEncoder.encode(customer.getPassword()),
                customer.getRoles()
        );

        return this.customerRepository.save(customerEntity);
    }

    public CustomerEntity signInCustomer(Auth.SignIn customer){
        CustomerEntity customerEntity = customerRepository.findByUsername(customer.getUsername())
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 ID 입니다. ->" + customer.getUsername()));

        if (!this.passwordEncoder.matches(customer.getPassword(),customerEntity.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        }

        return customerEntity;
    }
}
