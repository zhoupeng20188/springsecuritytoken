package com.zp.springsecuritytoken.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @RequestMapping("/content")
    public String content(){
        return "content";
    }


    @RequestMapping("/user")
    public String user(){
        return "user";
    }
}
