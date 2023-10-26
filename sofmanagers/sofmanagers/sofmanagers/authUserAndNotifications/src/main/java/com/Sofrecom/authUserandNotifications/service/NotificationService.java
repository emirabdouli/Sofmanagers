package com.Sofrecom.authUserandNotifications.service;

import com.Sofrecom.authUserandNotifications.controller.UserController;
import com.Sofrecom.authUserandNotifications.dto.Notificationdto;
import com.Sofrecom.authUserandNotifications.dto.UserInfo;
import com.Sofrecom.authUserandNotifications.model.Notification;
import com.Sofrecom.authUserandNotifications.repository.NotificationRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public void sendNotification(String userName, String userEmail, String userRole, Notificationdto notificationdto) {
        // Retrieve authenticated user info
        UserInfo senderInfo = new UserInfo(userName, userEmail, userRole);
        // Get all users with "ADMIN" role
        List<UserInfo> Users = userService.getUsers();
        List<String> adminUsers = new ArrayList<>();
        for (UserInfo user : Users) {
            if ("ADMIN".equals(user.getRole()) && !senderInfo.getName().equals(user.getName())) {
                adminUsers.add(user.getName());
            }
        }
        // Create and send notifications to admin users
        Notification notification = new Notification();
        notification.setType(notificationdto.getType());
        notification.setMessage(notificationdto.getMessage());
        notification.setSender(senderInfo.getName());
        notification.setRecipients(new Gson().toJson(adminUsers));
        notification.setCreatedAt(LocalDateTime.now());

        // Save the notification
        notificationRepository.save(notification);
    }

    public List<Notificationdto> getLastNotifications(String userName, String userEmail, String userRole) {
        UserInfo receiverInfo = new UserInfo(userName, userEmail, userRole);
        List<Notificationdto> notificationdtos = new ArrayList<>();
        if ("ADMIN".equals(receiverInfo.getRole())) {
            List<Notification> notifs = notificationRepository.findAll();
            notificationdtos = notifs.stream()
                    .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                    .map(notification -> {
                        Notificationdto notficationdto = new Notificationdto();
                        notficationdto.setType(notification.getType());
                        notficationdto.setMessage(notification.getMessage());
                        notficationdto.setSender(notification.getSender());
                        notficationdto.setCreatedAt(notification.getCreatedAt());
                        notficationdto.setSeenBy(notification.getSeenBy());
                        return notficationdto;
                    })
                    .collect(Collectors.toList());
        }
        return notificationdtos;
    }

    public void terminateNotification(String userName, String userEmail, String userRole) {
        List<Notification> notifs = notificationRepository.findAll();
        if("ADMIN".equals(userRole)) {
            for (Notification notif : notifs) {
                String seenBy = notif.getSeenBy();
                List<String> seenByList = new ArrayList<>();

                // Check if seenBy is not null or empty
                if (seenBy != null && !seenBy.isEmpty()) {
                    // Parse the JSON array string into a List
                    seenByList = new Gson().fromJson(seenBy, new TypeToken<List<String>>() {
                    }.getType());
                }

                // Check if the userName is not already present in the list
                if (!seenByList.contains(userName)) {
                    seenByList.add(userName);

                    // Convert the List back to JSON array string
                    String updatedSeenBy = new Gson().toJson(seenByList);

                    // Set the updated seenBy value and save the notification
                    notif.setSeenBy(updatedSeenBy);
                    notificationRepository.save(notif);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 */50 * *")
    public void deleteOldNotifications() {
        LocalDateTime fiftyDaysAgo = LocalDateTime.now().minusDays(50);
        List<Notification> notifications = notificationRepository.findAllByCreatedAtBefore(fiftyDaysAgo);
        notificationRepository.deleteAll(notifications);
    }

    public void deleteAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        notificationRepository.deleteAll(notifications);
    }


}

