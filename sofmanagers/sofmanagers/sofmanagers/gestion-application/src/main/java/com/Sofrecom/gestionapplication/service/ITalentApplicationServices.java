package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.AppRequest;
import com.Sofrecom.gestionapplication.dtoapp.ApplicationIdDto;
import com.Sofrecom.gestionapplication.dtoapp.TalentApplicationDto;
import com.Sofrecom.gestionapplication.dtoapp.TalentApplicationDtoForUpdate;

import java.util.List;

public interface ITalentApplicationServices {
    Long addTalentApp(List<TalentApplicationDto> talentApplicationDtoList);
    void deleteTalentApplication(Long talentApplicationId);
    void affectTalentToApplications(Long talentAppId, ApplicationIdDto applicationDto);
    void updatetalentonapp(Long talentId, TalentApplicationDtoForUpdate talentApplicationDto);
    float calculateNiveauMaitrisePercentage(String niveauMaitriseLevel);

    void unlinkTalentFromApplication(Long talentid, Long applicationId);
    List<TalentApplicationDto> getAllTalents();
    TalentApplicationDto findTalentApplication(Long talentId, Long applicationId);
    List<TalentApplicationDto> getAlltalentApplicationsByTalentId(Long talentId);
}
