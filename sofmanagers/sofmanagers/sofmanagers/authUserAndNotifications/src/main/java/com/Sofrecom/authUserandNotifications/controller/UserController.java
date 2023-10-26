package com.Sofrecom.authUserandNotifications.controller;

import com.Sofrecom.authUserandNotifications.dto.UserInfo;
import com.Sofrecom.authUserandNotifications.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/userInfos")
    public UserInfo getUserInfo(@RequestHeader(name = "X-User-Name") String userName,
                              @RequestHeader(name = "X-User-Email") String userEmail,
                              @RequestHeader(name = "X-User-Role") String userRole) {
    UserInfo userInfo = new UserInfo(userName, userEmail, userRole);
        return userInfo;
    }

    @GetMapping("/users")
    public List<UserInfo> getUsers() {
        return userService.getUsers();
    }


}



