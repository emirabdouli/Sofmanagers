package com.Sofrecom.gestiontalent.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalentApplicationDtoForUpdate {
    private Long talentId;
    private TypeTalent talenttype;
    private String niveauMaitrise;
    private Date periodeAffectation;
    private float occupation;

}
