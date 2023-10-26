package com.Sofrecom.gestionapplication.mapper;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationMapper implements IMapper{

    private LocalDateTime localDateTime;

    public ApplicationMapper() {
        Date date = new Date();
        localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public  EtatApplication mapToEtatApplicationWadd(EtatApplicationDto etatApplicationDto, Application app) {
        EtatApplication etatApplication = new EtatApplication();
        etatApplication.setEtatcourant(etatApplicationDto.getEtatcourant());
        etatApplication.setDesciption(etatApplicationDto.getDesciption());
        etatApplication.setDateaffectationEtat(localDateTime);
        etatApplication.setDatedebutEtat(localDateTime);
        etatApplication.setDatefinEtat(null);
        etatApplication.setCurrent(true);
        etatApplication.setAlert(etatApplicationDto.getAlert());
        etatApplication.setSatisfaction(etatApplicationDto.getSatisfaction());
        etatApplication.setApplication(app);
        return etatApplication;
    }
    @Override
    public  TalentApplication mapToTalentApplication(TalentApplicationDto talentApplicationDto, Application app) {
        TalentApplication talentApplication = new TalentApplication();
        talentApplication.setTalentId(talentApplicationDto.getTalentId());
        talentApplication.setTalenttype(talentApplicationDto.getTalenttype());
        talentApplication.setNiveauMaitrise(talentApplicationDto.getNiveauMaitrise());
        talentApplication.setPeriodeAffectation(talentApplicationDto.getPeriodeAffectation());
        talentApplication.setOccupation(talentApplicationDto.getOccupation());
        talentApplication.setApplication(app);
        return talentApplication;
    }

    @Override
    public  EtatApplication mapToEtatApplication(EtatApplicationDto etatApplicationDto, Application app) {
        EtatApplication etatApplication = new EtatApplication();
        etatApplication.setEtatcourant(etatApplicationDto.getEtatcourant());
        etatApplication.setDesciption(etatApplicationDto.getDesciption());
        etatApplication.setDateaffectationEtat(etatApplicationDto.getDateaffectationEtat());
        etatApplication.setDatedebutEtat(etatApplicationDto.getDatedebutEtat());
        etatApplication.setDatefinEtat(null);
        etatApplication.setCurrent(true);
        etatApplication.setAlert(etatApplicationDto.getAlert());
        etatApplication.setSatisfaction(etatApplicationDto.getSatisfaction());
        etatApplication.setApplication(app);
        return etatApplication;
    }

    @Override
    public Application mapAppRequestToApplication(AppRequest appRequest, Application app) {
        app.setNom(appRequest.getNom());
        app.setDescription(appRequest.getDescription());
        app.setTechnologie(appRequest.getTechnologie());
        app.setEtp(appRequest.getEtp());
        return  app;
    }

    @Override
    public Domaine mapDomaineDtoToDomaine(DomaineDto domaineDto, Domaine domaine) {
            domaine.setNom(domaineDto.getNom());
            domaine.setDescription(domaineDto.getDescription());
            return domaine;
        }
    @Override
    public AppRequest mapAppToAppRequest(Application application, AppRequest appRequest) {
        appRequest.setNom(application.getNom());
        appRequest.setDescription(application.getDescription());
        appRequest.setTechnologie(application.getTechnologie());
        appRequest.setEtp(application.getEtp());
        return appRequest;
    }
    @Override
    public AppRequest mapAppRequToAppRequest(Application application) {
        AppRequest appRequest = new AppRequest();
        appRequest.setNom(application.getNom());
        appRequest.setDescription(application.getDescription());
        appRequest.setTechnologie(application.getTechnologie());
        appRequest.setEtp(application.getEtp());
        return appRequest;
    }

    @Override
    public  TalentApplicationDto mapToTalentApplicationDTO(TalentApplication talentApplication, TalentApplicationDto talentApplicationDto) {
        talentApplicationDto.setTalentId(talentApplication.getTalentId());
        talentApplicationDto.setTalenttype(talentApplication.getTalenttype());
        talentApplicationDto.setNiveauMaitrise(talentApplication.getNiveauMaitrise());
        talentApplicationDto.setPeriodeAffectation(talentApplication.getPeriodeAffectation());
        talentApplicationDto.setOccupation(talentApplication.getOccupation());
        return talentApplicationDto;
    }
    @Override
    public  TalentApplicationDto mapToTalentApplicationDTO2(TalentApplication talentApplication) {
        TalentApplicationDto talentApplicationDto = new  TalentApplicationDto();
        talentApplicationDto.setTalentId(talentApplication.getTalentId());
        talentApplicationDto.setTalenttype(talentApplication.getTalenttype());
        talentApplicationDto.setNiveauMaitrise(talentApplication.getNiveauMaitrise());
        talentApplicationDto.setPeriodeAffectation(talentApplication.getPeriodeAffectation());
        talentApplicationDto.setOccupation(talentApplication.getOccupation());
        return talentApplicationDto;
    }

    @Override
    public DomaineDto mapDomaineToDomaineDto(Domaine domaine, DomaineDto domaineDto) {
        domaineDto.setNom(domaine.getNom());
        domaineDto.setDescription(domaine.getDescription());
        return domaineDto;
    }

    @Override
    public  EtatApplicationDto mapToEtatApplicationDTO(EtatApplication etatApplication, EtatApplicationDto etatApplicationDto) {
        etatApplicationDto.setId(etatApplication.getId());
        etatApplicationDto.setEtatcourant(etatApplication.getEtatcourant());
        etatApplicationDto.setDesciption(etatApplication.getDesciption());
        etatApplicationDto.setDateaffectationEtat(etatApplication.getDateaffectationEtat());
        etatApplicationDto.setDatedebutEtat(etatApplication.getDatedebutEtat());
        etatApplicationDto.setDatefinEtat(etatApplication.getDatefinEtat());
        etatApplicationDto.setCurrent(etatApplication.isCurrent());
        etatApplicationDto.setAlert(etatApplication.isAlert());
        etatApplicationDto.setSatisfaction(etatApplication.getSatisfaction());

        return etatApplicationDto;
    }

    @Override
    public EquipeApplication mapEquipeappDtoToEquippe(EquipeApplicationDto equipeApplicationDto, EquipeApplication equipeApplication) {
        equipeApplication.setNom(equipeApplicationDto.getNom());
        return equipeApplication;
    }

    @Override
    public Set<TalentApplicationDto> convertTalentListToDtoList(Set<TalentApplication> talents) {
        Set<TalentApplicationDto> talentDtos = new HashSet<>();
        for (TalentApplication talent : talents) {
            TalentApplicationDto talentDto = mapToTalentApplicationDTO2(talent);
            talentDtos.add(talentDto);
        }
        return talentDtos;
    }
    @Override
    public EquipeApplicationDto mapEquipeToEquippeappDto(EquipeApplication equipeApplication) {
        EquipeApplicationDto equipeApplicationDto = new EquipeApplicationDto();
        equipeApplicationDto.setId(equipeApplication.getId());
        equipeApplicationDto.setNom(equipeApplication.getNom());

        AppRequest appRequest = new AppRequest();
        appRequest.setId(equipeApplication.getApplication().getId());
        appRequest.setNom(equipeApplication.getApplication().getNom());
        appRequest.setDescription(equipeApplication.getApplication().getDescription());
        appRequest.setTechnologie(equipeApplication.getApplication().getTechnologie());
        appRequest.setEtp(equipeApplication.getApplication().getEtp());
        DomaineDto domaineDto = new DomaineDto();
        domaineDto.setNom(equipeApplication.getApplication().getDomaine().getNom());
        domaineDto.setDescription(equipeApplication.getApplication().getDomaine().getDescription());
        domaineDto.setDas(mapDasListToDtoList(equipeApplication.getApplication().getDomaine().getDas()));
        appRequest.setDomaine(domaineDto);
        equipeApplicationDto.setAppRequest(appRequest);


        return equipeApplicationDto;
    }
    public List<DasDto> mapDasListToDtoList(List<DAS> dasList) {
        List<DasDto> dasDtoList = new ArrayList<>();
        for (DAS das : dasList) {
            DasDto dasDto = new DasDto();
            dasDto.setId(das.getId());
            dasDto.setNom(das.getNom());
            dasDto.setRds(mapRDListToDtoList(das.getRds()));
            dasDtoList.add(dasDto);
        }
        return dasDtoList;
    }
    public List<RDDto> mapRDListToDtoList(List<RD> rdList) {
        List<RDDto> rdDtoList = new ArrayList<>();
        for (RD rd : rdList) {
            RDDto rdDto = new RDDto();
            rdDto.setId(rd.getId());
            rdDto.setNom(rd.getNom());
            rdDto.setAppRequests(mapAppRequestListToDtoList(rd.getApplications()));
            rdDtoList.add(rdDto);
        }

        return rdDtoList;
    }
    public List<AppRequest> mapAppRequestListToDtoList(List<Application> applicationList) {
        List<AppRequest> appRequestDtoList = new ArrayList<>();

        for (Application application : applicationList) {
            AppRequest appRequestDto = new AppRequest();
            appRequestDto.setId(application.getId());
            appRequestDto.setNom(application.getNom());
            appRequestDto.setDescription(application.getDescription());
            appRequestDto.setTechnologie(application.getTechnologie());
            appRequestDto.setEtp(application.getEtp());
            appRequestDto.setTalentApplications(convertTalentListToDtoList(application.getTalentApplications()));
            appRequestDtoList.add(appRequestDto);
        }

        return appRequestDtoList;
    }


    @Override
    public DAS mapDasdtoToDAS(DasDto dasDto) {
        DAS das = new DAS();
        das.setNom(dasDto.getNom());
        return  das;
    }

    @Override
    public DasDto mapDasToDASdto(DAS das) {
        DasDto dasDto = new DasDto();
        dasDto.setNom(das.getNom());
        return  dasDto;
    }

    @Override
    public RD mapRddtoToRD(RDDto rdDto) {
        RD rd = new RD();
        rd.setNom(rdDto.getNom());
        return  rd;
    }

    @Override
    public DomaineDto mapsDomaineToDomaineDto(Domaine domaine) {
        DomaineDto domaineDto = new DomaineDto();
        domaineDto.setId(domaine.getId());
        domaineDto.setNom(domaine.getNom());
        domaineDto.setDescription(domaine.getDescription());
        return domaineDto;
    }

}
