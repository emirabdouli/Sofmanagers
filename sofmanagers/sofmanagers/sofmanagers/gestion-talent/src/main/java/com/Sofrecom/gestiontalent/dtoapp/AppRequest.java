package com.Sofrecom.gestiontalent.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRequest {
    private Long id;
    private String nom;
    private String description;
    private String technologie;
    private Float etp;
    private List<EtatApplicationDto> etats;
    private Set<TalentApplicationDto> talentApplications;
    private DomaineDto domaine;
    private EquipeApplicationDto equipeApplicationDto;
    private RDDto rd;

}
