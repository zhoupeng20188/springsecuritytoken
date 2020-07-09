package com.zp.springsecuritytoken.controller;

import com.zp.springsecuritytoken.token.MyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ControllerTest {

//    @Autowired
//    RedisTemplate redisTemplate;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;


    @RequestMapping("/content")
    public String content(){
        return "content";
    }


    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    @RequestMapping("/login")
    public String login(String username, String password){
        System.out.println(username);
        return generateToken(username, password);
    }
    /**
     * 登陆与授权.
     *
     * @param username .
     * @param password .
     * @return
     */
    private String generateToken(String username, String password) {
//        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
//        // Perform the security
//        final Authentication authentication = authenticationManager.authenticate(upToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        // Reload password post-security so we can generate token
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 持久化的redis
//        String token = CommonUtils.encrypt(userDetails.getUsername());
        String token = UUID.randomUUID().toString();
//        redisTemplate.opsForValue().set(token, userDetails.getUsername());
        MyToken.tokenMap.put(token, username);
        return token;
    }
}
