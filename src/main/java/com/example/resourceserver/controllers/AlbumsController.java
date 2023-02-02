package com.example.resourceserver.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumsController {

    @GetMapping("/albums")
    public String getAlbums(@AuthenticationPrincipal OidcUser principal) {
        OidcIdToken idToken = principal.getIdToken();
        String tokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + tokenValue);

        return "albums";
    }
}
