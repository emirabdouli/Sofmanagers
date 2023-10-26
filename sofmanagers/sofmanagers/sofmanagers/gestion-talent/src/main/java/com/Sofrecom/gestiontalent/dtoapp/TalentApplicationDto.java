package com.Sofrecom.gestiontalent.dtoapp;

import com.Sofrecom.gestiontalent.dtot.TalentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalentApplicationDto {
    private Long talentId;
    private TypeTalent talenttype;
    private String niveauMaitrise;
    private Date periodeAffectation;
    private float occupation;
    private AppRequest appRequest;
    private TalentDto talentDto;

}
