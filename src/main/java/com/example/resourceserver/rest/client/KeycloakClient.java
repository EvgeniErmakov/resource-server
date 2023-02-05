package com.example.resourceserver.rest.client;

/**
 * Client for Auth Server (Keycloak)
 */
public interface KeycloakClient {
    /**
     * Create role with name.
     *
     * @param name role name
     */
    void createRole(String name);

    /**
     * Remove role with name.
     *
     * @param name role name
     */
    void removeRole(String name);

    /**
     * Mapping Role to User by role name and username.
     *
     * @param roleName role name
     * @param username name for User
     */
    void addRoleToUser(String roleName, String username);

    /**
     * Remove Role Mapping for User by role name and username.
     *
     * @param roleName role name
     * @param username name for User
     */
    void removeRoleToUser(String roleName, String username);

    /**
     * Search id by username
     *
     * @param username name for User
     * @return id user
     */
    String findByUsername(String username);
}
