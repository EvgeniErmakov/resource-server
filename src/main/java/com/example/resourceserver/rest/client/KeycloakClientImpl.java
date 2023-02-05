package com.example.resourceserver.rest.client;

import com.example.resourceserver.exceptions.DuplicateRoleException;
import com.example.resourceserver.exceptions.KeycloakException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.resourceserver.config.KeycloakClientConfig;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class KeycloakClientImpl implements KeycloakClient {
    private final Keycloak keycloak;
    private final KeycloakClientConfig keycloakClientConfig;

    @Autowired
    public KeycloakClientImpl(Keycloak keycloak, KeycloakClientConfig keycloakClientConfig) {
        this.keycloak = keycloak;
        this.keycloakClientConfig = keycloakClientConfig;
    }

    @Override
    public void createRole(String name) {
        try {
            keycloak.realm(keycloakClientConfig.getRealm())
                    .roles().create(new RoleRepresentation(name, null, false));
        } catch (ClientErrorException ex) {
            throw new DuplicateRoleException(String.format("Keycloak: role with name '%s' exists", name));
        }
    }

    @Override
    public void removeRole(String name) {
        try {
            keycloak.realm(keycloakClientConfig.getRealm())
                    .roles().deleteRole(name);
        } catch (ClientErrorException ex) {
            throw new RuntimeException(String.format("Keycloak: role with name '%s' does not exist", name));
        }
    }

    public void addRoleToUser(String roleName, String username) {
        UserRepresentation userRepresentation = getUserRepresentation(username);
        RoleRepresentation roleRepresentation = getRoleRepresentation(roleName);

        keycloak.realm(keycloakClientConfig.getRealm())
                .users().get(userRepresentation.getId())
                .roles().realmLevel()
                .add(List.of(roleRepresentation));
    }

    public void removeRoleToUser(String roleName, String username) {
        UserRepresentation userRepresentation = getUserRepresentation(username);
        RoleRepresentation roleRepresentation = getRoleRepresentation(roleName);

        keycloak.realm(keycloakClientConfig.getRealm())
                .users().get(userRepresentation.getId())
                .roles().realmLevel()
                .remove(List.of(roleRepresentation));
    }

    @Override
    public String findByUsername(String username) {

        UserRepresentation userRepresentation = getUserRepresentation(username);
        String lastName = userRepresentation.getLastName();
        System.out.println(lastName);
        return lastName;
    }

    private UserRepresentation getUserRepresentation(String username) {
        UsersResource users = keycloak.realm(keycloakClientConfig.getRealm())
                .users();
        String s = users.toString();
        System.out.println(s);
        List<UserRepresentation> list = users.list();
        return keycloak.realm(keycloakClientConfig.getRealm())
                .users().search(username)
                .stream().findFirst()
                .orElseThrow(() -> new KeycloakException(createMessage(username)));
    }

    private RoleRepresentation getRoleRepresentation(String roleName) {
        try {
            return keycloak.realm(keycloakClientConfig.getRealm())
                    .roles().get(roleName).toRepresentation();
        } catch (NotFoundException ex) {
            throw new KeycloakException(String.format("Keycloak: role with name '%s' does not exist", roleName));
        }
    }

    private String createMessage(String parameter) {
        return String.format("Keycloak: user with username '%s' does not exist", parameter);
    }
}
