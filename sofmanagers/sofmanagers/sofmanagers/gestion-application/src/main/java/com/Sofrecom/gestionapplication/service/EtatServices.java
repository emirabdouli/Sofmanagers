package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.EtatApplication;
import com.Sofrecom.gestionapplication.repository.ApplicationRepository;
import com.Sofrecom.gestionapplication.repository.EtatApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor

public class EtatServices implements  IEtatServices{
    private final EtatApplicationRepository etatApplicationRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationServices applicationServices;
    private final IMapper imapper;

    Date date = new Date(); // Your existing Date object
    LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    @Override
    public Long addetat(EtatApplicationDto etatApplicationDto) {
        EtatApplication etatApplication = new EtatApplication();
        etatApplication.setEtatcourant(etatApplicationDto.getEtatcourant());
        etatApplication.setDesciption(etatApplicationDto.getDesciption());
        etatApplication.setAlert(etatApplicationDto.getAlert());
        etatApplication.setDateaffectationEtat(localDateTime);
        etatApplication.setDatedebutEtat(localDateTime);
        etatApplication.setSatisfaction(etatApplicationDto.getSatisfaction());
        etatApplication.setDatefinEtat(null);
        etatApplication.setApplication(null);
        etatApplicationRepository.save(etatApplication);
        return etatApplication.getId();

    }
    @Override
    public void updateetat(Long etatId, EtatApplicationDtoForUpdate etatApplicationDto) {
        EtatApplication etat = etatApplicationRepository.findById(etatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etat not found"));
        if (etatApplicationDto.getEtatcourant() != null) {
            etat.setEtatcourant(etatApplicationDto.getEtatcourant());
        }
        if (etatApplicationDto.getDesciption() != null) {
            etat.setDesciption(etatApplicationDto.getDesciption());
        }
        if (etatApplicationDto.getDateaffectationEtat() != null) {
            etat.setDateaffectationEtat(etatApplicationDto.getDateaffectationEtat());
        }
        if (etatApplicationDto.getDatedebutEtat() != null) {
            etat.setDatedebutEtat(etatApplicationDto.getDatedebutEtat());
        }
        if (etatApplicationDto.getDatefinEtat() != null) {
            etat.setDatefinEtat(etatApplicationDto.getDatefinEtat());
        }
        if (etatApplicationDto.getSatisfaction() != 0) {
            etat.setSatisfaction(etatApplicationDto.getSatisfaction());
        }
        if (etatApplicationDto.getAlert() != null) {
            etat.setAlert(etatApplicationDto.getAlert());
            etat.setSatisfaction(0);
        } else {
            if (etatApplicationDto.getSatisfaction() != 0) {
                etat.setSatisfaction(etatApplicationDto.getSatisfaction());
            }
        }
        etatApplicationRepository.save(etat);
    }


    @Transactional
    @Override
    public void deleteEtat(Long etatId) {
        Optional<EtatApplication> optionalEtatApplication = etatApplicationRepository.findById(etatId);
        if (optionalEtatApplication.isPresent()) {
            EtatApplication etat = optionalEtatApplication.get();
            etatApplicationRepository.delete(etat);
        }
    }

    @Override
    public void affectEtatToApplications(Long etatId, ApplicationIdDto applicationDto) {
        // Find the EtatApplication instance by id
        Optional<EtatApplication> optionalEtat = etatApplicationRepository.findById(etatId);
        if (optionalEtat.isPresent()) {
            EtatApplication etat = optionalEtat.get();

            // Check if applicationDto is null or not
            if (applicationDto != null && applicationDto.getApplicationId() != null) {
                // Find the Application instance by id
                Application application = applicationRepository.findById(applicationDto.getApplicationId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

                 // Set previously associated EtatApplications as non-current
                for (EtatApplication oldEtat : application.getEtats()) {
                    if (!oldEtat.equals(etat)) {
                        oldEtat.setCurrent(false);
                    }
                }

                // Assign the EtatApplication to the Application instance
                etat.setApplication(application);
                etat.setCurrent(true);
                application.getEtats().add(etat);
                applicationRepository.save(application);
            } else {
                throw new RuntimeException("ApplicationIdDto or its applicationId property is null");
            }

        } else {
            throw new RuntimeException("EtatApplication with id " + etatId + " not found");
        }
    }
    @Override
    public void createAndAffectEtatToApplication(Long applicationId, EtatApplicationDto etatDto) {
        // Find the Application instance by id
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();

            // Create a new EtatApplication instance
            EtatApplication etat = new EtatApplication();
            etat.setCurrent(true); // Set as current

            // Set the attributes from the DTO
            etat.setEtatcourant(etatDto.getEtatcourant());
            etat.setDesciption(etatDto.getDesciption());
            etat.setDateaffectationEtat(localDateTime);
            etat.setDatedebutEtat(etatDto.getDatedebutEtat());
            etat.setAlert(etatDto.getAlert());
            etat.setDatefinEtat(null); // Set as null initially
            etat.setSatisfaction(etatDto.getSatisfaction());
            etat.setApplication(application);

            // Set previously associated EtatApplications as non-current
            for (EtatApplication oldEtat : application.getEtats()) {
                if (!oldEtat.equals(etat)) {
                    oldEtat.setCurrent(false);
                }
                if (!oldEtat.equals(etat) && (oldEtat.getDatefinEtat() == null)) {
                    oldEtat.setDatefinEtat(localDateTime);
                }
            }

            // Add the new EtatApplication to the Application instance and save changes
            application.getEtats().add(etat);
            applicationRepository.save(application);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
        }
    }
    @Override
    public EtatApplicationDto getApplicationEtatWithDetails(Long appRequestId) {
        // Fetch the Application entity object from the database by appRequestId
        Application application = applicationRepository.findById(appRequestId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Find the etat with isCurrent set to true
        EtatApplication currentEtat = null;
        for (EtatApplication etat : application.getEtats()) {
            if (etat.isCurrent()) {
                currentEtat = etat;
                break;
            }
        }

        if (currentEtat == null) {
            throw new RuntimeException("No current etat found for the given appRequestId");
        }

        // Create a new instance of the EtatApplicationDto and map the properties of the currentEtat to it
        EtatApplicationDto etatApplicationDto = new EtatApplicationDto();
        etatApplicationDto.setId(currentEtat.getId());
        etatApplicationDto.setEtatcourant(currentEtat.getEtatcourant());
        etatApplicationDto.setDesciption(currentEtat.getDesciption());
        etatApplicationDto.setDateaffectationEtat(currentEtat.getDateaffectationEtat());
        etatApplicationDto.setDatedebutEtat(currentEtat.getDatedebutEtat());
        etatApplicationDto.setAlert(currentEtat.isAlert());
        etatApplicationDto.setSatisfaction(currentEtat.getSatisfaction());

        etatApplicationDto.setDatefinEtat(currentEtat.getDatefinEtat());

        return etatApplicationDto;
    }
    @Override
    public List<EtatApplicationDto> getAllCurrentEtatApplications() {
        List<EtatApplication> etatApplications = etatApplicationRepository.findByCurrentIsTrue();
        List<EtatApplicationDto> currentEtatApplications = new ArrayList<>();

        for (EtatApplication etatApplication : etatApplications) {
            EtatApplicationDto etatApplicationDto = new EtatApplicationDto();
            imapper.mapToEtatApplicationDTO(etatApplication, etatApplicationDto);

            Application application = etatApplication.getApplication();
                AppRequest appRequest = new AppRequest();
                appRequest.setId(application.getId()); // Add this line to retrieve the id value
                appRequest.setNom(application.getNom());
                appRequest.setDescription(application.getDescription());
                appRequest.setTechnologie(application.getTechnologie());
                appRequest.setEtp(application.getEtp());
                etatApplicationDto.setAppRequest(appRequest);

                currentEtatApplications.add(etatApplicationDto);
        }

        return currentEtatApplications;
    }
    @Override
    public EtatApplicationDto getEtatApplicationById(Long etatApplicationId) {
        Optional<EtatApplication> etatApplication = etatApplicationRepository.findById(etatApplicationId);
        if (etatApplication.isPresent()) {

            EtatApplicationDto etatApplicationDto = new EtatApplicationDto();
            etatApplicationDto.setId(etatApplication.get().getId());
            etatApplicationDto.setEtatcourant(etatApplication.get().getEtatcourant());
            etatApplicationDto.setDesciption(etatApplication.get().getDesciption());
            etatApplicationDto.setDateaffectationEtat(etatApplication.get().getDateaffectationEtat());
            etatApplicationDto.setDatedebutEtat(etatApplication.get().getDatedebutEtat());
            etatApplicationDto.setDatefinEtat(etatApplication.get().getDatefinEtat());
            etatApplicationDto.setCurrent(etatApplication.get().isCurrent());
            etatApplicationDto.setAlert(etatApplication.get().isAlert());
            etatApplicationDto.setSatisfaction(etatApplication.get().getSatisfaction());

            return etatApplicationDto;
        }
        return null;
    }

}


