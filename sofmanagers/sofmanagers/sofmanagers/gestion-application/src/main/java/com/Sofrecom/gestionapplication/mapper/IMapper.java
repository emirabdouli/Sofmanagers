package com.Sofrecom.gestionapplication.mapper;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.model.*;

import java.util.List;
import java.util.Set;


public interface IMapper {
    EtatApplication mapToEtatApplicationWadd(EtatApplicationDto etatApplicationDto, Application app);

    TalentApplication mapToTalentApplication(TalentApplicationDto talentApplicationDto, Application app);

    EtatApplication mapToEtatApplication(EtatApplicationDto etatApplicationDto, Application app);

    Application mapAppRequestToApplication(AppRequest appRequest, Application app);

    Domaine mapDomaineDtoToDomaine(DomaineDto domaineDto, Domaine domaine);

    AppRequest mapAppToAppRequest(Application application, AppRequest appRequest);

    TalentApplicationDto mapToTalentApplicationDTO(TalentApplication talentApplication, TalentApplicationDto talentApplicationDto);
    DomaineDto mapDomaineToDomaineDto(Domaine domaine, DomaineDto domaineDto);
    EtatApplicationDto mapToEtatApplicationDTO(EtatApplication etatApplication, EtatApplicationDto etatApplicationDto);
    EquipeApplication mapEquipeappDtoToEquippe(EquipeApplicationDto equipeApplicationDto, EquipeApplication equipeApplication);
    //This mapper is used to retriev the apps by equipe name and send them to the other microservice
    EquipeApplicationDto mapEquipeToEquippeappDto(EquipeApplication equipeApplication);
    Set<TalentApplicationDto> convertTalentListToDtoList(Set<TalentApplication> talents);
    //This mapper is used to retriev the apps by equipe name and send them to the other microservice
    TalentApplicationDto mapToTalentApplicationDTO2(TalentApplication talentApplication);
    DAS mapDasdtoToDAS(DasDto dasDto);
    RD mapRddtoToRD(RDDto rdDto);
    AppRequest mapAppRequToAppRequest(Application application);
    DasDto mapDasToDASdto(DAS das);
    DomaineDto mapsDomaineToDomaineDto(Domaine domaine);
    }