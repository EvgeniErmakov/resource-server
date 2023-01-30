package com.example.resourceserver.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Secured("ROLE_developer")
    @GetMapping("/secured")
    public String getMethodLevelSecurityMessage() {
        return "Method-Level-Security-Message";
    }

    @Secured("ROLE_user")
    @GetMapping("/secured-forbidden")
    public String getMethodLevelSecurityForbiddenMessage() {
        return "Ты этого не получишь =)";
    }

    @PreAuthorize("hasRole('developer')")
    @GetMapping("/preAuthorize-developer")
    public String getPreAuthorizeMessage() {
        return "PreAuthorizeMessage!!! -> hasRole('developer')";
    }

    @PreAuthorize("hasAuthority('ROLE_developer')")
    @GetMapping("/preAuthorize-role-developer")
    public String getPreAuthorizeMessageSecond() {
        return "PreAuthorizeMessage!!! -> hasAuthority('ROLE_developer')";
    }
}
