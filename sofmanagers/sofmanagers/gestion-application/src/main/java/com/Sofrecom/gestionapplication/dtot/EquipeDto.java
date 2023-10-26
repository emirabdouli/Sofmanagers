package com.Sofrecom.gestionapplication.dtot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeDto {
    private Long id;
    private String nom;
    private String description;
    private List<TalentDto> talentsAEquipe;
    private ManagerEquipeDto managerequipe;
}
