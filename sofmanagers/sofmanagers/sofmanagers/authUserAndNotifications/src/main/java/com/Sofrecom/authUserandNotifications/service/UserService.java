package com.Sofrecom.authUserandNotifications.service;

import com.Sofrecom.authUserandNotifications.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakUserService keycloakUserService;
    public List<UserInfo> getUsers() {
        List<UserRepresentation> users = keycloakUserService.getAllUsers();
        List<UserInfo> userInfos = new ArrayList<>();
        for (UserRepresentation user : users) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(user.getFirstName() + " " + user.getLastName());
            userInfo.setEmail(user.getEmail());
            List<String> realmRoles = user.getRealmRoles();
            if (realmRoles.contains("ADMIN")) {
                userInfo.setRole("ADMIN");
            } else if (realmRoles.contains("USER")) {
                userInfo.setRole("USER");
            } else {
                userInfo.setRole("Unknown");
            }
            userInfos.add(userInfo);
        }
        return userInfos;
    }

}
