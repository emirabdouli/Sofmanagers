package com.Sofrecom.authUserandNotifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificationdto {
    private String type;
    private String message;
    private String recipients;
    private String sender;
    private LocalDateTime createdAt;
    private String seenBy;
}
