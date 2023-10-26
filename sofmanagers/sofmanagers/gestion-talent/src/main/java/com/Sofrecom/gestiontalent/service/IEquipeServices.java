package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.EquipeDto;

import java.util.List;

public interface IEquipeServices {
    Long addEquipe(EquipeDto equipeDto);
    List<EquipeDto> getAllEquipes();
    EquipeDto getEquipeById(Long equipeId);
    Long getEquipeIdByName(String equipeName);
    EquipeDto getanEquipeByName(String equipeName);

}
