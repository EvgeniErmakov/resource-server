package com.example.resourceserver.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

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

    @PreAuthorize("hasAuthority('ROLE_user') or #id == #jwt.getSubject()")
    @GetMapping("/jwt-info/{id}")
    public String getJwtInfo(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return jwt.getSubject();
    }

    @PostAuthorize("returnObject == #number")
    @GetMapping("/post-authorize/{number}")
    public String getPostAuthorizeMessage(@PathVariable String number) {
        System.out.println("Этот метод отработал до того, как ты не прошел @PostAuthorize");
        System.out.println("Мой запрос в Postman выглядит вот так - http://localhost:8091/users/post-authorize/10");
        return "10";
    }

    @Secured("ROLE_developer")
    @GetMapping("/resource-server")
    public String getPostAuthorizeMessage(@AuthenticationPrincipal Jwt jwt) {
        String tokenValue = jwt.getTokenValue();
        System.out.println(tokenValue);
        return "resource-server передаёт привет";
    }

}
