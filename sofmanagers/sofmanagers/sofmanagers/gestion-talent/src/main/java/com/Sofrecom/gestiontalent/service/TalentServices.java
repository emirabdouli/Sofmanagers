package com.Sofrecom.gestiontalent.service;


import com.Sofrecom.gestiontalent.dtoapp.AppRequest;
import com.Sofrecom.gestiontalent.dtoapp.TalentApplicationDto;
import com.Sofrecom.gestiontalent.dtot.AbsenceDto;
import com.Sofrecom.gestiontalent.dtot.EquipeDto;
import com.Sofrecom.gestiontalent.dtot.OToMeetDto;
import com.Sofrecom.gestiontalent.dtot.TalentDto;
import com.Sofrecom.gestiontalent.mapper.ITalentMapper;
import com.Sofrecom.gestiontalent.model.Equipe;
import com.Sofrecom.gestiontalent.model.OToMeet;
import com.Sofrecom.gestiontalent.model.Talent;
import com.Sofrecom.gestiontalent.repository.AbsenceRepository;
import com.Sofrecom.gestiontalent.repository.EquipeRepository;
import com.Sofrecom.gestiontalent.repository.OToMeetRepository;
import com.Sofrecom.gestiontalent.repository.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentServices implements ITalentServices{
    private final AbsenceRepository absenceRepository;
    private final TalentRepository talentRepository;
    private final ITalentMapper iTalentMapper;

    private final EquipeRepository equipeRepository;
    private final OToMeetRepository oToMeetRepository;
    private final WebClient.Builder webClientBuilder;

    private List<AbsenceDto> absences;
    private List<OToMeet> oToMeets;
    @Override
    public Long addTalent(TalentDto talentDto) {
        Long code = talentDto.getCode();
        if (talentRepository.existsById(code)) {
            return null;
        }

        Talent talent = new Talent();
        talent.setCode(code);
        talent.setName(talentDto.getName());
        talent.setEmail(talentDto.getEmail());
        talent.setPhone(talentDto.getPhone());
        talent.setJobTitle(talentDto.getJobTitle());
        talent.setImage(talentDto.getImage());
        talent.setTypem(talentDto.isTypem());
        talent.setDateDebutContrat(talentDto.getDateDebutContrat());
        talent.setDateFinContrat(talentDto.getDateFinContrat());
        talent.setYearsOfExperience(talentDto.getYearsOfExperience());

        // Set the manager associated with the talent
        if (talentDto.getManager() != null && talentDto.getManager().getCode() != null) {
            Talent manager = talentRepository.findById(talentDto.getManager().getCode()).orElse(null);
            talent.setManager(manager);
        }
    else {talent.setManager(null);}
        // Assign an existing equipe to the talent
        if (talentDto.getEquipe().getId() != null) {
            Long equipeId = talentDto.getEquipe().getId();
            Equipe equipe = equipeRepository.findById(equipeId).orElse(null);
            talent.setEquipe(equipe);
        }

        talentRepository.save(talent);
        return talent.getCode();
    }
    @Override
    public Long updateTalent(Long code, TalentDto talentDto) {
        Talent talent = talentRepository.findById(code).orElse(null);
        if (talent == null) {
            return null; // Talent with the given code doesn't exist
        }

        // Create a new Talent entity with updated attributes
        if (talentDto.getName() != null) {
            talent.setName(talentDto.getName());
        }
        if (talentDto.getEmail() != null) {
            talent.setEmail(talentDto.getEmail());
        }
        if(talentDto.getImage() != null){
            talent.setImage(talentDto.getImage());

        }
        if (talentDto.getPhone() != null) {
            talent.setPhone(talentDto.getPhone());
        }
        if (talentDto.getJobTitle() != null && !"team lead".equalsIgnoreCase(talentDto.getJobTitle())) {
            talent.setJobTitle(talentDto.getJobTitle());
            if (talentDto.isTypem()) {
                talent.setTypem(talentDto.isTypem());
            }
            if (talentDto.getDateDebutContrat() != null) {
                talent.setDateDebutContrat(talentDto.getDateDebutContrat());
            }
            if (talentDto.getDateFinContrat() != null) {
                talent.setDateFinContrat(talentDto.getDateFinContrat());
            }
            Integer yearsOfExperience = talentDto.getYearsOfExperience();
            if (yearsOfExperience != null) {
                talent.setYearsOfExperience(talentDto.getYearsOfExperience());
            }

            // Set the manager associated with the talent
            if (talentDto.getManager() != null && talentDto.getManager().getCode() != null) {
                Talent manager = talentRepository.findById(talentDto.getManager().getCode()).orElse(null);
                talent.setManager(manager);
            }

            // Assign an existing equipe to the talent
            if (talentDto.getEquipe().getId() != null) {
                Long equipeId = talentDto.getEquipe().getId();
                Equipe equipe = equipeRepository.findById(equipeId).orElse(null);
                talent.setEquipe(equipe);
            }
        } else if ("team lead".equalsIgnoreCase(talentDto.getJobTitle())) {
            talent.setJobTitle("Team Lead");
            talent.setTypem(true);
            talent.setManager(null);

            if (talentDto.getDateDebutContrat() != null) {
                talent.setDateDebutContrat(talentDto.getDateDebutContrat());
            }
            if (talentDto.getDateFinContrat() != null) {
                talent.setDateFinContrat(talentDto.getDateFinContrat());
            }
            Integer yearsOfExperience = talentDto.getYearsOfExperience();
            if (yearsOfExperience != null) {
                talent.setYearsOfExperience(talentDto.getYearsOfExperience());
            }


            // Assign an existing equipe to the talent
            if (talentDto.getEquipe().getId() != null) {
                Long equipeId = talentDto.getEquipe().getId();
                Equipe equipe = equipeRepository.findById(equipeId).orElse(null);
                talent.setEquipe(equipe);
            }
        }
        Talent savedTalent = talentRepository.save(talent);
        return savedTalent.getCode();
    }


    @Override
    public List<TalentDto> getAllTalents() {
        List<Talent> talents = talentRepository.findAll(); // Retrieve talents from the database
        // Convert Talent objects to TalentDto objects
        List<TalentDto> talentDtos = new ArrayList<>();
        for (Talent talent : talents) {
            TalentDto talentDto = iTalentMapper.convertTalentToDto(talent);
            talentDto.setImage(null);
            List<AbsenceDto> absenceDtos = iTalentMapper.convertAbsenceListToDtoList(talent.getAbsences());
            talentDto.setAbsences(absenceDtos);
            List<OToMeetDto> oToMeetDtos = iTalentMapper.convertOToMeetListToDtoList(talent.getOToMeets());
            talentDto.setOToMeets(oToMeetDtos);
            talentDtos.add(talentDto);
        }
        return talentDtos;
    }

    public List<TalentDto> getAllTalentsforapps() {
        List<Talent> talents = talentRepository.findAll(); // Retrieve talents from the database
        // Convert Talent objects to TalentDto objects
        List<TalentDto> talentDtos = new ArrayList<>();
        for (Talent talent : talents) {
            TalentDto talentDto = new TalentDto();
            talentDto.setCode(talent.getCode());
            talentDto.setName(talent.getName());

            talentDtos.add(talentDto);
        }
        return talentDtos;
    }
    @Override
    public TalentDto getTalentByCode(Long code) {
        Talent talent = talentRepository.findByCode(code); // Retrieve talent from the database by code
        if (talent == null) {
            return null;
        }

        TalentDto talentDto = iTalentMapper.convertTalentToDto(talent);
        List<AbsenceDto> absenceDtos = iTalentMapper.convertAbsenceListToDtoList(talent.getAbsences());
        talentDto.setAbsences(absenceDtos);
        List<OToMeetDto> oToMeetDtos = iTalentMapper.convertOToMeetListToDtoList(talent.getOToMeets());
        talentDto.setOToMeets(oToMeetDtos);

        return talentDto;
    }
    public TalentDto getTalentByCodeforapp(Long code) {
        Talent talent = talentRepository.findByCode(code); // Retrieve talent from the database by code
        if (talent == null) {
            return null;
        }

        TalentDto talentDto = new TalentDto();
        talentDto.setCode(talent.getCode());
        talentDto.setName(talent.getName());

        return talentDto;
    }
    @Override
    public List<TalentDto> getTalentsByEquipeId(EquipeDto equipe) {
        Long equipeId = equipe.getId(); // Retrieve the Equipe ID from the Equipe object

        if (equipeId == null) {
            // Handle the case where Equipe ID is not assigned to any talent
            // Return an empty list or perform any other desired action
            return Collections.emptyList();
        }

        // Assuming you have a TalentService or TalentRepository to interact with the database
        List<Talent> talents = talentRepository.getTalentsByEquipeId(equipeId); // Retrieve talents by Equipe ID

        // Convert Talent objects to TalentDto objects
        List<TalentDto> talentDtos = new ArrayList<>();
        for (Talent talent : talents) {
            TalentDto talentDto = iTalentMapper.convertTalentToDto(talent);
            talentDtos.add(talentDto);
        }

        return talentDtos;
    }
    @Override
    public EquipeDto getEquipeById(Long equipeId) {
        Equipe equipe = equipeRepository.findById(equipeId).orElse(null);

        if (equipe == null) {
            return null; // or throw an exception if needed
        }

        return iTalentMapper.convertEquipeToDto(equipe);
    }

    @Override
    public List<TalentDto> getTalentsManager() {
        List<Talent> talents = talentRepository.findAll(); // Retrieve talents by Equipe ID

        // Convert Talent objects to TalentDto objects
        List<TalentDto> talentDtos = new ArrayList<>();
        for (Talent talent : talents) {
            TalentDto talentDto = new TalentDto();
            talentDto.setCode(talent.getCode());
            talentDto.setName(talent.getName());
            talentDto.setTypem(talent.isTypem());

            if (talentDto.isTypem()){
            talentDtos.add(talentDto);}
        }

        return talentDtos;
    }

    public Long getTalentCodeByName(String talentName) {
        Talent talent = talentRepository.findByName(talentName);
        if (talent != null) {
            TalentDto talentDto = new TalentDto();
            talentDto.setCode(talent.getCode());
            return talentDto.getCode();
        } else {
            throw new RuntimeException("Talent not found with name: " + talentName);
        }
    }

    public TalentApplicationDto gettalentaffectationsfromappmicroByEquipeApplicationName(Long talentId, Long applicationId) {
        TalentApplicationDto talentApplicationDto =
                webClientBuilder.build().get()
                        .uri("http://gestion-application/api/applications/findtalentaffectationforgroupes/{talentId}/{applicationId}", talentId, applicationId)
                        .retrieve()
                        .bodyToMono(TalentApplicationDto.class)
                        .block();
       return talentApplicationDto;
    }

}
