package com.zp.springsecuritytoken.config;

import com.zp.springsecuritytoken.filter.TokenCheckFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                /** authorizeRequests表示需要认证的request请求*/
                .authorizeRequests()
                /** 设置只有ROLE_USER的权限才能访问/index页面*/
                .antMatchers("/user").hasRole("USER")
                /** 设置只有ROlE_ADMIN的权限才能访问/content页面*/
                .antMatchers("/content").hasRole("ADMIN")
                /** anyRequest()表示除上面之外的所有请求都需要认证*/
                .anyRequest().authenticated()
                .and()
                /** 增加自定义过滤器*/
                .addFilter(new TokenCheckFilter(authenticationManager()))
                /** 禁用session*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                



    }

}
