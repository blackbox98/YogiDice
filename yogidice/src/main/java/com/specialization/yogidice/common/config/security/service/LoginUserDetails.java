package com.specialization.yogidice.common.config.security.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class LoginUserDetails extends User {
    private com.specialization.yogidice.domain.entity.User user;

    public LoginUserDetails(String nickName, Collection<? extends GrantedAuthority> authorities) {
        super(nickName, "", authorities);
    }
}
