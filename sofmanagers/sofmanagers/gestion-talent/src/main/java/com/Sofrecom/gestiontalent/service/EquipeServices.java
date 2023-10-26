package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtoapp.AppRequest;
import com.Sofrecom.gestiontalent.dtoapp.EquipeApplicationDto;
import com.Sofrecom.gestiontalent.dtot.EquipeDto;
import com.Sofrecom.gestiontalent.dtot.ManagerEquipeDto;
import com.Sofrecom.gestiontalent.dtot.TalentDto;
import com.Sofrecom.gestiontalent.mapper.ITalentMapper;
import com.Sofrecom.gestiontalent.model.Equipe;
import com.Sofrecom.gestiontalent.model.ManagerEquipe;
import com.Sofrecom.gestiontalent.model.Talent;
import com.Sofrecom.gestiontalent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipeServices implements IEquipeServices{
    private final AbsenceRepository absenceRepository;
    private final TalentRepository talentRepository;
    private final EquipeRepository equipeRepository;
    private final OToMeetRepository oToMeetRepository;
    private final ManagerEquipeRepository managerEquipeRepository;
    private final ITalentMapper iTalentMapper;
    private final WebClient.Builder webClientBuilder;

    private List<TalentDto> talents;

    @Override
    public Long addEquipe(EquipeDto equipeDto) {
        String nom = equipeDto.getNom();
        if (equipeRepository.existsByNom(nom)) {
            throw new IllegalArgumentException("Equipe with the same nom already exists.");
        }

        Equipe equipe = new Equipe();
        equipe.setNom(nom);
        equipe.setDescription(equipeDto.getDescription());

        if (equipeDto.getManagerequipe() != null && equipeDto.getManagerequipe().getCode() != null) {
            Long managerCode = equipeDto.getManagerequipe().getCode();
            ManagerEquipe manager = managerEquipeRepository.findById(managerCode).orElse(null);
            equipe.setManagerequipe(manager);
        }
        equipeRepository.save(equipe);
        return equipe.getId();
    }

    @Override
    public List<EquipeDto> getAllEquipes() {
        List<Equipe> equipes = equipeRepository.findAll(); // Retrieve equipes from the database
        // Convert Equipe objects to EquipeDto objects
        List<EquipeDto> equipeDtos = new ArrayList<>();
        for (Equipe equipe : equipes) {
            EquipeDto equipeDto = iTalentMapper.convertEquipeToDto(equipe);
            List<TalentDto> talentDtos = iTalentMapper.convertTalentsToDto(equipe.getTalentsAEquipe());
            equipeDto.setTalentsAEquipe(talentDtos);
            ManagerEquipe managerEquipe = equipe.getManagerequipe();
            if (managerEquipe != null) {
                ManagerEquipeDto managerEquipeDto = iTalentMapper.convertmanagerToDto(managerEquipe);
                equipeDto.setManagerequipe(managerEquipeDto);
            }
            equipeDtos.add(equipeDto);
        }
        return equipeDtos;
    }

    public List<EquipeDto> getAllEquipesforappmicroservice() {
        List<Equipe> equipes = equipeRepository.findAll(); // Retrieve equipes from the database
        // Convert Equipe objects to EquipeDto objects
        List<EquipeDto> equipeDtos = new ArrayList<>();
        for (Equipe equipe : equipes) {
            EquipeDto equipeDto = new EquipeDto();
            ManagerEquipe managerEquipe = managerEquipeRepository.findByEquipes(equipe);
            if (managerEquipe != null)
            { ManagerEquipeDto managerEquipedto = new ManagerEquipeDto();
                managerEquipedto.setCode(managerEquipe.getCode());
                managerEquipedto.setName(managerEquipe.getName());
                equipeDto.setManagerequipe(managerEquipedto);
            }
            equipeDto.setNom(equipe.getNom());
            equipeDtos.add(equipeDto);
        }
        return equipeDtos;
    }

    @Override
    public EquipeDto getEquipeById(Long equipeId) {
        Optional<Equipe> equipeOptional = equipeRepository.findById(equipeId); // Retrieve equipe from the database by equipeId

        if (!equipeOptional.isPresent()) {
            return null;
        }

        Equipe equipe = equipeOptional.get();
        EquipeDto equipeDto = iTalentMapper.convertEquipeToDto(equipe);
        return equipeDto;
    }
    @Override
    public Long getEquipeIdByName(String equipeName) {
        Equipe equipe = equipeRepository.findByNom(equipeName);
        if (equipe != null) {
            return equipe.getId();
        } else {
            throw new RuntimeException("Equipe not found with name: " + equipeName);
        }
    }

    @Override
    public EquipeDto getanEquipeByName(String equipeName) {
        Equipe equipe = equipeRepository.findByNom(equipeName);
        if (equipe != null) {
            EquipeDto equipeDTO = new EquipeDto();
            equipeDTO.setId(equipe.getId());
            equipeDTO.setNom(equipe.getNom());
            ManagerEquipe managerEquipe = managerEquipeRepository.findByEquipes(equipe);
            if (managerEquipe != null)
            { ManagerEquipeDto managerEquipedto = new ManagerEquipeDto();
            managerEquipedto.setCode(managerEquipe.getCode());
            managerEquipedto.setName(managerEquipe.getName());
            equipeDTO.setManagerequipe(managerEquipedto);
            }
            equipeDTO.setDescription(equipe.getDescription());

            List<TalentDto> talents = equipe.getTalentsAEquipe().stream()
                    .map(talent -> {
                        TalentDto talentDTO = new TalentDto();
                        talentDTO.setCode(talent.getCode());
                        talentDTO.setName(talent.getName());
                        return talentDTO;
                    })
                    .collect(Collectors.toList());

            equipeDTO.setTalentsAEquipe(talents);

            return equipeDTO;
        } else {
            throw new RuntimeException("Equipe not found with name: " + equipeName);
        }
    }

    public List<AppRequest> getApplicationsfromappmicroByEquipeApplicationName(String equipeApplicationName) {
        List<AppRequest> appRequests =
                webClientBuilder.build().get()
                .uri("http://gestion-application/api/applications/applications/{equipeApplicationName}", equipeApplicationName)
                .retrieve()
                .bodyToFlux(AppRequest.class)
                .collectList()
                .block();
        if (appRequests != null) {
            return appRequests;
        }
        else {return null;}
    }


}
