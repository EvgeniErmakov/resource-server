package com.example.resourceserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getMessage() {
        // узнать возможности -> http://localhost:8180/realms/second/.well-known/openid-configuration
        return "YES, I still love you!";
    }
}
