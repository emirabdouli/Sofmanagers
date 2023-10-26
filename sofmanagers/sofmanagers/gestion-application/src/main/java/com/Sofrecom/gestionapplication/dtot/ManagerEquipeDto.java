package com.Sofrecom.gestionapplication.dtot;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerEquipeDto {
    private Long code;
    private String name;
    private byte[] image;
    private String email;
    private Long phone;
    private Date dateDebutContrat;
    private Date dateFinContrat;
    private int yearsOfExperience;
    private List<EquipeDto> equipes;
}
