package com.Sofrecom.gestionapplication.dtot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalentDto {
    private Long code;
    private String name;
    private byte[] image;
    private String email;
    private Long phone;
    private String jobTitle;
    private boolean typem;
    private Date dateDebutContrat;
    private Date dateFinContrat;
    private TalentDto manager;
    private int yearsOfExperience;
    private List<AbsenceDto> absences;
    private List<OToMeetDto> OToMeets;
    private EquipeDto equipe;
}
