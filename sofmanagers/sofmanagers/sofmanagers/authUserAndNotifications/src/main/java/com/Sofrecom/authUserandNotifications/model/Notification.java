package com.Sofrecom.authUserandNotifications.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String message;
    @Column(name = "recipients", columnDefinition = "TEXT")
    private String recipients;
    private String sender;
    private LocalDateTime createdAt;
    @Column(name = "seenBy", columnDefinition = "TEXT")
    private String seenBy;
}

