package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.TalentApplication;
import com.Sofrecom.gestionapplication.repository.ApplicationRepository;
import com.Sofrecom.gestionapplication.repository.TalentApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TalentApplicationServices implements ITalentApplicationServices{
    private final ApplicationRepository applicationRepository;
    private final TalentApplicationRepository talentApplicationRepository;
    private final IMapper imapper;

    @Override
    public Long addTalentApp(List<TalentApplicationDto> talentApplicationDtoList) {
        Long totalCount = 0L; // Initialize the total count to zero
        for (TalentApplicationDto talentApplicationDto : talentApplicationDtoList) {
            TalentApplication talentApplication = new TalentApplication();
            talentApplication.setTalentId(talentApplicationDto.getTalentId());
            talentApplication.setTalenttype(talentApplicationDto.getTalenttype());
            talentApplication.setNiveauMaitrise(talentApplicationDto.getNiveauMaitrise());
            talentApplication.setPeriodeAffectation(talentApplicationDto.getPeriodeAffectation());
            talentApplication.setOccupation(talentApplicationDto.getOccupation());
            AppRequest appRequest = talentApplicationDto.getAppRequest();
            if (appRequest != null) {
                Application application = applicationRepository.findById(appRequest.getId()).orElse(null);
                talentApplication.setApplication(application);
            } else {
                talentApplication.setApplication(null);
            }
            talentApplicationRepository.save(talentApplication);
            totalCount++; // Increment the total count for each talent application saved
        }
        return totalCount; // Return the total count of talent applications saved
    }


        @Transactional
    @Override

    public void deleteTalentApplication(Long talentApplicationId) {
        Optional<TalentApplication> optionalTalentApplication = talentApplicationRepository.findById(talentApplicationId);
        if (optionalTalentApplication.isPresent()) {
            TalentApplication talent = optionalTalentApplication.get();
            talentApplicationRepository.delete(talent);
        }
    }


    @Override
    public void affectTalentToApplications(Long talentAppId, ApplicationIdDto applicationDto) {
        // Find the EtatApplication instance by id
        Optional<TalentApplication> optionalTalent = talentApplicationRepository.findById(talentAppId);
        if (optionalTalent.isPresent()) {
            TalentApplication talent = optionalTalent.get();

            // Check if applicationDto is null or not
            if (applicationDto != null && applicationDto.getApplicationId() != null) {
                // Find the Application instance by id
                Application application = applicationRepository.findById(applicationDto.getApplicationId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

                // Assign the EtatApplication to the Application instance
                talent.setApplication(application);
                application.getTalentApplications().add(talent);
                applicationRepository.save(application);
            } else {
                throw new RuntimeException("ApplicationIdDto or its applicationId property is null");
            }

        } else {
            throw new RuntimeException("EtatApplication with id " + talentAppId + " not found");
        }
    }
    @Override
    public void updatetalentonapp(Long talentId, TalentApplicationDtoForUpdate talentApplicationDto) {
        TalentApplication talent = talentApplicationRepository.findById(talentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Talent not found"));
        if (talentApplicationDto.getTalentId() != null) {
            talent.setTalentId(talentApplicationDto.getTalentId());
        }
        if (talentApplicationDto.getTalenttype() != null) {
            talent.setTalenttype(talentApplicationDto.getTalenttype());
        }
        if (talentApplicationDto.getNiveauMaitrise() != null) {
            talent.setNiveauMaitrise(talentApplicationDto.getNiveauMaitrise());
        }
        if (talentApplicationDto.getPeriodeAffectation() != null) {
            talent.setPeriodeAffectation(talentApplicationDto.getPeriodeAffectation());
        }
        if (talentApplicationDto.getOccupation() != 0) {
            talent.setOccupation(talentApplicationDto.getOccupation());
        }
        talentApplicationRepository.save(talent);
    }
    @Override
    public float calculateNiveauMaitrisePercentage(String niveauMaitriseLevel) {
        List<TalentApplication> talentApplications = talentApplicationRepository.findAll();
        List<TalentApplicationDto> talentApplicationDtos = new ArrayList<>();
        for (TalentApplication talentApplication: talentApplications) {
            TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
            talentApplicationDto.setNiveauMaitrise(talentApplication.getNiveauMaitrise());
            talentApplicationDtos.add(talentApplicationDto);
        }
        if (talentApplicationDtos.isEmpty()) {
            return 0.0f;
        }
        int totalTalentApplications = talentApplicationDtos.size();
        int levelCount = 0;

        for (TalentApplicationDto talentApplication : talentApplicationDtos) {
            String niveauMaitrise = talentApplication.getNiveauMaitrise();
            if (niveauMaitrise.equalsIgnoreCase(niveauMaitriseLevel)) {
                levelCount++;
            }
        }
        float levelPercentage = (levelCount * 100.0f) / totalTalentApplications;
        return levelPercentage;
    }

    @Override
    public void unlinkTalentFromApplication(Long talentId, Long applicationId) {
        TalentApplication optionalTalent = talentApplicationRepository.findByTalentIdAndApplicationId(talentId, applicationId);
            talentApplicationRepository.delete(optionalTalent);
    }


    @Override
    public List<TalentApplicationDto> getAllTalents() {
        List<TalentApplication> talents = talentApplicationRepository.findAll();
        List<TalentApplicationDto> talentdtos = new ArrayList<>();

        for (TalentApplication talent : talents) {
            TalentApplicationDto talentdto = new TalentApplicationDto();
            talentdto.setTalentId(talent.getId()); // Add this line to retrieve the id value
            imapper.mapToTalentApplicationDTO(talent,talentdto);
            talentdtos.add(talentdto);
        }
        return talentdtos;
    }

    @Override
        public TalentApplicationDto findTalentApplication(Long talentId, Long applicationId) {
            TalentApplication talentApplication = talentApplicationRepository.findByTalentIdAndApplicationId(talentId, applicationId);

            if (talentApplication != null) {
                TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
                talentApplicationDto.setTalentId(talentApplication.getTalentId());
                talentApplicationDto.setTalenttype(talentApplication.getTalenttype());
                talentApplicationDto.setNiveauMaitrise(talentApplication.getNiveauMaitrise());
                talentApplicationDto.setPeriodeAffectation(talentApplication.getPeriodeAffectation());
                talentApplicationDto.setOccupation(talentApplication.getOccupation());
                return talentApplicationDto;
            }

            return null; // or throw an exception if not found
        }

        @Override
    public List<TalentApplicationDto> getAlltalentApplicationsByTalentId(Long talentId) {
        List<TalentApplication> talentApplications = talentApplicationRepository.findAllByTalentId(talentId);
            List<TalentApplicationDto> talentApplicationDtos = new ArrayList<>();
        for (TalentApplication talentApplication : talentApplications) {
            TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
            talentApplicationDto.setTalentId(talentApplication.getTalentId());
            talentApplicationDto.setPeriodeAffectation(talentApplication.getPeriodeAffectation());
            talentApplicationDto.setOccupation(talentApplication.getOccupation());
            talentApplicationDto.setAppRequest(imapper.mapAppRequToAppRequest(talentApplication.getApplication()));
            talentApplicationDtos.add(talentApplicationDto);
        }

        return talentApplicationDtos;
    }

    public List<TalentApplicationDto> findTalentApplication(Long applicationId) {
        List<TalentApplication> talentApplications = talentApplicationRepository.findByApplicationId(applicationId);
        List<TalentApplicationDto> talentApplicationDtos = new ArrayList<>();
        if (talentApplications != null) {
            for (TalentApplication talentApplication:talentApplications) {
                TalentApplicationDto talentApplicationDto = new TalentApplicationDto();
                talentApplicationDto.setTalentId(talentApplication.getTalentId());
                talentApplicationDto.setTalenttype(talentApplication.getTalenttype());
                talentApplicationDto.setOccupation(talentApplication.getOccupation());
                talentApplicationDtos.add(talentApplicationDto);
            }
            return talentApplicationDtos;
        }

        return null; // or throw an exception if not found
    }


}



