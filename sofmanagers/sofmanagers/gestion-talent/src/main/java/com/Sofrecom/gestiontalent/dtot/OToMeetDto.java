package com.Sofrecom.gestiontalent.dtot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OToMeetDto {
    private Long id;
    private TalentDto talent;
    private String manager;
    private LocalDateTime startdate;
    private LocalDateTime finishdate;
    private String notes;
    private Boolean managerConfirm;
    private Boolean talentConfirm;

}
