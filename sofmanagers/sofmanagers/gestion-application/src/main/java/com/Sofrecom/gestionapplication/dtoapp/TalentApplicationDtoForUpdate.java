package com.Sofrecom.gestionapplication.dtoapp;

import com.Sofrecom.gestionapplication.model.TypeTalent;
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
    private float occupation;
    private Date periodeAffectation;
}
