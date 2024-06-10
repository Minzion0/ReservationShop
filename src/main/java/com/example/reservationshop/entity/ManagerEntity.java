package com.example.reservationshop.entity;

import com.example.reservationshop.config.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manager")
public class ManagerEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Convert(converter = StringListConverter.class)
    private List<String> roles;

    private ManagerEntity(String username, String password, List<String> roles) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // 정적 팩토리 메서드를 통한 객체 생성
    public static ManagerEntity from(String name, String password, List<String> roles){
        return new ManagerEntity(name,password,roles);
    }

    // 사용자의 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return this.password;
    }

    // 사용자의 이름(아이디) 반환
    @Override
    public String getUsername() {
        return this.username;
    }

    // 사용자 계정이 만료되지 않았는지 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 현재는 계정 만료 기능 비활성화
    }

    // 사용자 계정이 잠겨있지 않은지 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // 현재는 계정 잠금 기능 비활성화
    }

    // 사용자의 자격 증명이 만료되지 않았는지 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 현재는 자격 증명 만료 기능 비활성화
    }

    // 사용자가 활성화되어 있는지 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // 현재는 사용자 활성화 기능 활성화
    }
}

