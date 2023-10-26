package com.Sofrecom.authUserandNotifications.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakUserService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        System.out.println("KeycloakUserService initialized!");
    }
    public List<UserRepresentation> getAllUsers() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")  // Use the "master" realm to authenticate as an admin
                .clientId("admin-cli")  // Use the built-in Keycloak admin client
                .username(adminUsername)  // Replace with your Keycloak admin username
                .password(adminPassword)  // Replace with your Keycloak admin password
                .build();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> userRepresentations = usersResource.list();

        for (UserRepresentation user : userRepresentations) {
            List<RoleRepresentation> realmRoles = realmResource
                    .users()
                    .get(user.getId())
                    .roles()
                    .realmLevel()
                    .listEffective();

            // Extract role names as strings
            List<String> realmRoleNames = realmRoles.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());

            user.setRealmRoles(realmRoleNames);
            }
        return userRepresentations;

    }
}

