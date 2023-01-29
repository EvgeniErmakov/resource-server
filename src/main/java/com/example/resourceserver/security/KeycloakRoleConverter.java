package com.example.resourceserver.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.*;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (CollectionUtils.isEmpty(realmAccess)) {
            return new ArrayList<>();
        }

        List<GrantedAuthority> roles = ((List<String>) realmAccess.get("roles"))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return roles;
    }



}
