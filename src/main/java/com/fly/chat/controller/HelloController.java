package com.fly.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fly
 * @Description
 * @Date Created in 13:44 2017/12/12
 * @Modified by
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "Hello World";
    }
}
