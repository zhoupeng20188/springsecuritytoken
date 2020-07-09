package com.zp.springsecuritytoken.config;

import com.zp.springsecuritytoken.filter.LindTokenAuthenticationFilter;
import com.zp.springsecuritytoken.filter.TokenCheckFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * token过滤器.
     */
    @Autowired
    LindTokenAuthenticationFilter lindTokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                /** authorizeRequests表示需要认证的request请求*/
                .authorizeRequests()
                /** login接口放行*/
                .antMatchers("/login").permitAll()
                /** anyRequest()表示除上面之外的所有请求都需要认证*/
                .anyRequest().authenticated()
                .and()
                /** 增加自定义过滤器*/
//                .addFilter(new TokenCheckFilter(authenticationManager()))
                .addFilterBefore(lindTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                /** 禁用session*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                



    }

    /**
     * 密码生成策略.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
