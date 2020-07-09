package com.zp.springsecuritytoken.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //返回的信息包括：用户认证和授权信息（如果有必要的话），用户名和密码匹配上了，就算认证成功，spring security会根据BrowserSecurityConfig配置的权限去验证这个权限
//        return new User(username,passwordEncoder.encode("123"),
//                true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        return new User(username,"123",
                true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }
}