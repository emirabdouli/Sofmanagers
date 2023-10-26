package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.EquipeDto;
import com.Sofrecom.gestiontalent.dtot.TalentDto;

import java.util.List;

public interface ITalentServices {
    Long addTalent(TalentDto talentDto);
    Long updateTalent(Long code, TalentDto talentDto);
    List<TalentDto> getAllTalents();
    TalentDto getTalentByCode(Long code);
    List<TalentDto> getTalentsByEquipeId(EquipeDto equipe);
    EquipeDto getEquipeById(Long equipeId);
    List<TalentDto> getTalentsManager();
}
