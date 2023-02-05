package com.example.resourceserver.config;

import lombok.Getter;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KeycloakClientConfig {
    public static final int DEFAULT_CONNECTION_POOL_SIZE = 10;

    @Value("${oauth.authorization-uri}")
    private String serverUrl;
    @Value("${oauth.realm}")
    private String realm;
    @Value("${oauth.client-id}")
    private String clientId;
    @Value("${oauth.client-secret}")
    private String clientSecret;

    @Bean
    public ResteasyClient resteasyClient() {
        return new ResteasyClientBuilder()
                .connectionPoolSize(DEFAULT_CONNECTION_POOL_SIZE)
                .build();
    }

    @Bean
    public Keycloak keycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .username("evgeni")
                .password("evgeni")
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(resteasyClient())
                .build();

        return keycloak;
    }
}
