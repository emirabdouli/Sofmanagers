package com.Sofrecom.authUserandNotifications.controller;

import com.Sofrecom.authUserandNotifications.dto.Notificationdto;
import com.Sofrecom.authUserandNotifications.dto.UserInfo;
import com.Sofrecom.authUserandNotifications.model.Notification;
import com.Sofrecom.authUserandNotifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/addNotification")
    public void createNotification(@RequestHeader(name = "X-User-Name") String userName,
                                   @RequestHeader(name = "X-User-Email") String userEmail,
                                   @RequestHeader(name = "X-User-Role") String userRole,
                                   @RequestBody Notificationdto notificationDto) {
        notificationService.sendNotification(userName, userEmail, userRole,notificationDto);
    }
    @GetMapping("/notifications")
    public List<Notificationdto> getLastNotifications(@RequestHeader(name = "X-User-Name") String userName,
                                                      @RequestHeader(name = "X-User-Email") String userEmail,
                                                      @RequestHeader(name = "X-User-Role") String userRole) {
        return notificationService.getLastNotifications(userName, userEmail, userRole);
    }
    @PutMapping("/terminateNotif")
    public void terminateNotification(@RequestHeader(name = "X-User-Name") String userName,
                                      @RequestHeader(name = "X-User-Email") String userEmail,
                                      @RequestHeader(name = "X-User-Role") String userRole) {
        notificationService.terminateNotification(userName, userEmail, userRole);
    }

    @DeleteMapping("/deleteAllNotifications")
    public void deleteAllNotifications() {
        notificationService.deleteAllNotifications();
    }

}
