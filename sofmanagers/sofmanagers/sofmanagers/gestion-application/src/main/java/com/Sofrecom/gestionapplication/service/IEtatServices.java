package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.ApplicationIdDto;
import com.Sofrecom.gestionapplication.dtoapp.EtatApplicationDto;
import com.Sofrecom.gestionapplication.dtoapp.EtatApplicationDtoForUpdate;

import java.util.List;

public interface IEtatServices {
    Long addetat(EtatApplicationDto etatApplicationDto);
    void updateetat(Long etatId, EtatApplicationDtoForUpdate etatApplicationDto);
    void deleteEtat(Long etatId);
    void affectEtatToApplications(Long etatId, ApplicationIdDto applicationDto);
    void createAndAffectEtatToApplication(Long applicationId, EtatApplicationDto etatDto);
    EtatApplicationDto getApplicationEtatWithDetails(Long appRequestId);
    List<EtatApplicationDto> getAllCurrentEtatApplications();
    EtatApplicationDto getEtatApplicationById(Long etatApplicationId);
}
