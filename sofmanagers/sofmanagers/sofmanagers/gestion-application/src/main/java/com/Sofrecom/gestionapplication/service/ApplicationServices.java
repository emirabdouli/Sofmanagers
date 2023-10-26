package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.dtot.TalentDto;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.*;
import com.Sofrecom.gestionapplication.repository.ApplicationRepository;
import com.Sofrecom.gestionapplication.repository.EquipeApplicationRepository;
import com.Sofrecom.gestionapplication.repository.EtatApplicationRepository;
import com.Sofrecom.gestionapplication.repository.TalentApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ApplicationServices implements IApplicationServices{

    private final EtatApplicationRepository etatApplicationRepository;
    private final ApplicationRepository applicationRepository;
    private final TalentApplicationRepository talentApplicationRepository;
    private final EquipeApplicationRepository equipeApplicationRepository;

    private final IMapper imapper;
    private final WebClient.Builder webClientBuilder;

    Date date = new Date();
    LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    @Override
    public Long addApp(AppRequest appRequest) {
        Application app = new Application();
        imapper.mapAppRequestToApplication(appRequest, app);
        // Set the EtatApplication associated with the application
        List<EtatApplicationDto> etatApplicationsDto = appRequest.getEtats();
        if (etatApplicationsDto != null) {
            List<EtatApplication> etatApplications = etatApplicationsDto.stream()
                    .map(dto -> imapper.mapToEtatApplicationWadd(dto, app))
                    .collect(Collectors.toList());
            app.setEtats(etatApplications);
        }
        // Set the talent applications associated with the application
        Set<TalentApplicationDto> talentApplicationsDto = appRequest.getTalentApplications();
        if (talentApplicationsDto != null) {

            Set<TalentApplication> talentApplications = talentApplicationsDto.stream()
                    .map(dto -> imapper.mapToTalentApplication(dto, app))
                    .collect(Collectors.toSet());
            app.setTalentApplications(talentApplications);
        }
        // Set the domaine of the application
        DomaineDto domaineDto = appRequest.getDomaine();
        if (domaineDto != null) {
            Domaine domaine = new Domaine();
            imapper.mapDomaineDtoToDomaine(domaineDto, domaine);
            app.setDomaine(domaine);
        }
        applicationRepository.save(app);
        EquipeApplicationDto equipeApplicationDto = appRequest.getEquipeApplicationDto();
        if (equipeApplicationDto != null) {
            EquipeApplication equipeApplication = new EquipeApplication();
            imapper.mapEquipeappDtoToEquippe(equipeApplicationDto, equipeApplication);
            equipeApplication.setApplication(app);
            equipeApplicationRepository.save(equipeApplication);
            app.setEquipeApplication(equipeApplication);
        }
        return app.getId();
    }
    @Override
    public Long updateApp(Long appId, AppRequest appRequest) {
        Application app = applicationRepository.findById(appId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        // update application properties
        if (appRequest.getNom() != null) {
            app.setNom(appRequest.getNom());
        }
        if (appRequest.getDescription() != null) {
            app.setDescription(appRequest.getDescription());
        }
        if (appRequest.getTechnologie() != null) {
            app.setTechnologie(appRequest.getTechnologie());
        }
        if (appRequest.getEtp() != null) {
            app.setEtp(appRequest.getEtp());
        }
        // update Etat applications associated with the application
        Optional<List<EtatApplicationDto>> etatApplicationDto = Optional.ofNullable(appRequest.getEtats());
        etatApplicationDto.ifPresent(dtoSet -> {
            List<EtatApplication> etatApplications = dtoSet.stream()
                    .map(dto -> imapper.mapToEtatApplication(dto, app))
                    .collect(Collectors.toList());

            // set previously associated EtatApplications as non-current
            for (EtatApplication etatApplication : app.getEtats()) {
                if (!etatApplications.contains(etatApplication)) {
                    etatApplication.setCurrent(false);
                }
                else etatApplication.setCurrent(true);
            }
            app.setEtats(etatApplications);
        });

        // update talent applications associated with the application
        Optional<Set<TalentApplicationDto>> talentApplicationsDto = Optional.ofNullable(appRequest.getTalentApplications());
        talentApplicationsDto.ifPresent(dtoSet -> {
            Set<TalentApplication> talentApplications = dtoSet.stream()
                    .map(dto -> imapper.mapToTalentApplication(dto, app))
                    .collect(Collectors.toSet());
            app.setTalentApplications(talentApplications);
        });

        // update the domaine of the application
        Optional<DomaineDto> domaineDto = Optional.ofNullable(appRequest.getDomaine());
        domaineDto.ifPresent(dto -> {
            Domaine domaine = new Domaine();
            imapper.mapDomaineDtoToDomaine(dto, domaine);
            app.setDomaine(domaine);
        });



        if (appRequest.getEquipeApplicationDto() != null) {
            EquipeApplicationDto equipeApplicationDto = appRequest.getEquipeApplicationDto();
            if (app.getEquipeApplication() != null) {
                EquipeApplication existingEquipeApplication = app.getEquipeApplication();
                imapper.mapEquipeappDtoToEquippe(equipeApplicationDto, existingEquipeApplication);
                equipeApplicationRepository.save(existingEquipeApplication);
            } else {
                EquipeApplication equipeApplication = new EquipeApplication();
                imapper.mapEquipeappDtoToEquippe(equipeApplicationDto, equipeApplication);
                equipeApplication.setApplication(app);
                equipeApplicationRepository.save(equipeApplication);
                app.setEquipeApplication(equipeApplication);
            }
        }


        applicationRepository.save(app);


        return app.getId();
    }

    @Transactional
    @Override
    public void deleteApplication(Long applicationId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();

            // unlink related TalentApplication and Domaine entities
            for (TalentApplication talentApplication : application.getTalentApplications()) {
                talentApplication.setApplication(null);
            }
            application.setTalentApplications(null);
            Domaine domaine = application.getDomaine();
            if (domaine != null) {
                domaine.getApplications().remove(application);
            }
            application.setDomaine(null);
            // delete the Application entity
            applicationRepository.delete(application);
        }
    }

    @Override
    public AppRequest getApplicationWithDetails(Long applicationId) {
        // Fetch the Application entity object from the database
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        // Create a new instance of the AppRequest DTO and map the properties of the Application entity object to it
        AppRequest appRequest = new AppRequest();
        imapper.mapAppToAppRequest(application, appRequest);
        Domaine domaine = application.getDomaine();
        if (domaine != null) {
            DomaineDto domaineDto = new DomaineDto();
            domaineDto.setNom(domaine.getNom());
            appRequest.setDomaine(domaineDto);

        }
        EquipeApplication equipeApplication = application.getEquipeApplication();
        if (equipeApplication != null) {
            EquipeApplicationDto equipeApplicationDto = new EquipeApplicationDto();
            equipeApplicationDto.setNom(equipeApplication.getNom());
            appRequest.setEquipeApplicationDto(equipeApplicationDto);
        }
        Set<TalentApplicationDto> talentList = new HashSet<>();

        for (TalentApplication talentApplication : application.getTalentApplications()) {
            TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
            imapper.mapToTalentApplicationDTO(talentApplication, talentApplicationDto);
            Long talentId = talentApplicationDto.getTalentId();
            TalentDto talentDto = webClientBuilder.build().get()
                    .uri("http://gestion-talent/api/talents/talentforapp/{code}", talentId)
                    .retrieve()
                    .bodyToMono(TalentDto.class)
                    .block();

            if (talentDto != null) {
                talentApplicationDto.setTalentDto(talentDto);
            }
            talentList.add(talentApplicationDto);
        }
        appRequest.setTalentApplications(talentList);


        List<EtatApplicationDto> etatList = new ArrayList<>();

        for (EtatApplication etatApplication : application.getEtats()) {
            if (etatApplication.isCurrent() == true) {
                EtatApplicationDto etatApplicationDto = new EtatApplicationDto();
                etatApplicationDto.setId(etatApplication.getId());
                etatApplicationDto.setEtatcourant(etatApplication.getEtatcourant());
                etatApplicationDto.setDesciption(etatApplication.getDesciption());
                etatApplicationDto.setDateaffectationEtat(etatApplication.getDateaffectationEtat());
                etatApplicationDto.setDatedebutEtat(etatApplication.getDatedebutEtat());
                etatApplicationDto.setDatefinEtat(etatApplication.getDatefinEtat());
                etatApplicationDto.setAlert(etatApplicationDto.getAlert());
                etatApplicationDto.setSatisfaction(etatApplicationDto.getSatisfaction());

                etatList.add(etatApplicationDto);

            }

        }
        appRequest.setEtats(etatList);

        return appRequest;
        }
    @Override
    public List<AppRequest> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        List<AppRequest> appRequests = new ArrayList<>();

        for (Application application : applications) {
            AppRequest appRequest = new AppRequest();
            appRequest.setId(application.getId()); // Add this line to retrieve the id value
            imapper.mapAppToAppRequest(application,appRequest);

            Domaine domaine = application.getDomaine();
            if (domaine != null) {
                DomaineDto domaineDto = new DomaineDto();
                imapper.mapDomaineToDomaineDto(domaine, domaineDto);
                appRequest.setDomaine(domaineDto);
            }
            RD rd = application.getRd();
            if (rd != null) {
                RDDto rdDto = new RDDto();
                rdDto.setId(rd.getId());
                rdDto.setNom(rd.getNom());
                appRequest.setRd(rdDto);
            }
            EquipeApplication equipeApplication = application.getEquipeApplication();
            if (equipeApplication != null) {
                EquipeApplicationDto equipeApplicationDto = new EquipeApplicationDto();
                equipeApplicationDto.setNom(equipeApplication.getNom());
                appRequest.setEquipeApplicationDto(equipeApplicationDto);

            }
            List<EtatApplicationDto> etatList = new ArrayList<>();
            for (EtatApplication etatApplication : application.getEtats()) {
                if (etatApplication.isCurrent()) {
                    EtatApplicationDto etatApplicationDto = new EtatApplicationDto();
                    etatApplicationDto.setEtatcourant(etatApplication.getEtatcourant());
                    etatApplicationDto.setDesciption(etatApplication.getDesciption());
                    etatApplicationDto.setDateaffectationEtat(etatApplication.getDateaffectationEtat());
                    etatApplicationDto.setDatedebutEtat(etatApplication.getDatedebutEtat());
                    etatApplicationDto.setDatefinEtat(etatApplication.getDatefinEtat());
                    etatApplicationDto.setAlert(etatApplication.isAlert());
                    etatApplicationDto.setSatisfaction(etatApplication.getSatisfaction());

                    etatList.add(etatApplicationDto);
                }
            }
            appRequest.setEtats(etatList); // Set the etats property

            Set<TalentApplicationDto> talentList = new HashSet<>();
            for (TalentApplication talentApplication : application.getTalentApplications()) {
                TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
                imapper.mapToTalentApplicationDTO(talentApplication, talentApplicationDto);
                // Retrieve talent info from the second microservice
                Long talentId = talentApplicationDto.getTalentId();
                TalentDto talentDto = webClientBuilder.build().get()
                        .uri("http://gestion-talent/api/talents/talentforapp/{code}", talentId)
                        .retrieve()
                        .bodyToMono(TalentDto.class)
                        .block();

                if (talentDto != null) {
                    talentApplicationDto.setTalentDto(talentDto);
                }

                talentList.add(talentApplicationDto);
                         }
            appRequest.setTalentApplications(talentList);


            appRequests.add(appRequest);
        }
        return appRequests;
    }

    @Override
    public List<AppRequest> getAllApplicationsHistory() {
        List<Application> applications = applicationRepository.findAll();
        List<AppRequest> appRequests = new ArrayList<>();

        for (Application application : applications) {
            AppRequest appRequest = new AppRequest();
            appRequest.setId(application.getId());
            appRequest.setNom(application.getNom());
            appRequest.setDescription(application.getDescription());
            appRequest.setTechnologie(application.getTechnologie());
            appRequest.setEtp(application.getEtp());

            Domaine domaine = application.getDomaine();
            if (domaine != null) {
                DomaineDto domaineDto = new DomaineDto();
                domaineDto.setNom(domaine.getNom());
                appRequest.setDomaine(domaineDto);
            }
            EquipeApplication equipeApplication = application.getEquipeApplication();
            if (equipeApplication != null) {
                EquipeApplicationDto equipeApplicationDto = new EquipeApplicationDto();
                equipeApplicationDto.setNom(equipeApplication.getNom());
                appRequest.setEquipeApplicationDto(equipeApplicationDto);

            }

            Set<TalentApplicationDto> talentList = new HashSet<>();
            for (TalentApplication talentApplication : application.getTalentApplications()) {
                TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
                imapper.mapToTalentApplicationDTO(talentApplication, talentApplicationDto);
                Long talentId = talentApplicationDto.getTalentId();
                TalentDto talentDto = webClientBuilder.build().get()
                        .uri("http://gestion-talent/api/talents/talentforapp/{code}", talentId)
                        .retrieve()
                        .bodyToMono(TalentDto.class)
                        .block();

                if (talentDto != null) {
                    talentApplicationDto.setTalentDto(talentDto);
                }
                talentList.add(talentApplicationDto);
            }
            appRequest.setTalentApplications(talentList);

            List<EtatApplicationDto> etatList = new ArrayList<>();
            for (EtatApplication etatApplication : application.getEtats()) {
                    EtatApplicationDto etatApplicationDto = new EtatApplicationDto();
                    imapper.mapToEtatApplicationDTO(etatApplication, etatApplicationDto);
                    etatList.add(etatApplicationDto);

            }
            appRequest.setEtats(etatList);

            appRequests.add(appRequest);
        }
        return appRequests;
    }


    @Override
    public void TerminateAppStatus(Long appId, AppRequest appRequest) {
        Application app = applicationRepository.findById(appId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        // update Etat applications associated with the application
        List<EtatApplicationDto> etatApplicationDtos = appRequest.getEtats();


            // set previously associated EtatApplications as non-current
        for (EtatApplicationDto etatApplicationDto : etatApplicationDtos) {
            EtatApplication etatApplication = app.getEtats().stream()
                    .filter(etat -> etat.getId().equals(etatApplicationDto.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EtatApplication not found"));

            if (etatApplicationDto.getCurrent() == true) {
                // Set the datefinEtat and current properties
                etatApplication.setDatefinEtat(localDateTime);
                etatApplication.setCurrent(false);
            }
            }
        applicationRepository.save(app);
        }

    //retrieve the apps by equipe name and send them to the other microservice
    public List<AppRequest> getApplicationsByEquipeApplicationName(String equipeApplicationName) {
        List<EquipeApplication> equipeApplications = equipeApplicationRepository.findByNom(equipeApplicationName);
        List<AppRequest> appRequests = new ArrayList<>();

        if (equipeApplications != null && !equipeApplications.isEmpty()) {
            for (EquipeApplication equipeApplication : equipeApplications) {
                EquipeApplicationDto equipeApplicationDto = imapper.mapEquipeToEquippeappDto(equipeApplication);
                AppRequest appRequest = equipeApplicationDto.getAppRequest();
                appRequests.add(appRequest);
            }
            return appRequests;
        } else {
            return null;
        }
    }


}