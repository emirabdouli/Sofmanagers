package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.*;
import com.Sofrecom.gestiontalent.mapper.ITalentMapper;
import com.Sofrecom.gestiontalent.model.Equipe;
import com.Sofrecom.gestiontalent.model.ManagerEquipe;
import com.Sofrecom.gestiontalent.model.Talent;
import com.Sofrecom.gestiontalent.repository.EquipeRepository;
import com.Sofrecom.gestiontalent.repository.ManagerEquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ManagerEquipeService {
    private final ITalentMapper iTalentMapper;
    private final EquipeRepository equipeRepository;

    private final ManagerEquipeRepository managerEquipeRepository;

    public Long addManager(ManagerEquipeDto managerEquipeDto) {
        Long code = managerEquipeDto.getCode();
        if (managerEquipeRepository.existsById(code)) {
            return null;
        }

        ManagerEquipe managerEquipe = new ManagerEquipe();
        managerEquipe.setCode(code);
        managerEquipe.setName(managerEquipeDto.getName());
        managerEquipe.setEmail(managerEquipeDto.getEmail());
        managerEquipe.setPhone(managerEquipeDto.getPhone());
        managerEquipe.setImage(managerEquipeDto.getImage());
        managerEquipe.setDateDebutContrat(managerEquipeDto.getDateDebutContrat());
        managerEquipe.setDateFinContrat(managerEquipeDto.getDateFinContrat());
        managerEquipe.setYearsOfExperience(managerEquipeDto.getYearsOfExperience());
        managerEquipeRepository.save(managerEquipe);
        return managerEquipe.getCode();
    }

    public Long updateManager(Long code, ManagerEquipeDto managerEquipeDto) {
        ManagerEquipe managerEquipe = managerEquipeRepository.findById(code).orElse(null);
        if (managerEquipe == null) {
            return null; // Talent with the given code doesn't exist
        }

        // Create a new Talent entity with updated attributes
        if (managerEquipeDto.getName() != null) {
            managerEquipe.setName(managerEquipeDto.getName());
        }
        if (managerEquipeDto.getEmail() != null) {
            managerEquipe.setEmail(managerEquipeDto.getEmail());
        }
        if(managerEquipeDto.getImage() != null){
            managerEquipe.setImage(managerEquipeDto.getImage());

        }
        if (managerEquipeDto.getPhone() != null) {
            managerEquipe.setPhone(managerEquipeDto.getPhone());
        }

        if (managerEquipeDto.getDateDebutContrat() != null) {
            managerEquipe.setDateDebutContrat(managerEquipeDto.getDateDebutContrat());
            }
            Integer yearsOfExperience = managerEquipeDto.getYearsOfExperience();
            if (yearsOfExperience != null) {
                managerEquipe.setYearsOfExperience(managerEquipeDto.getYearsOfExperience());
            }
        ManagerEquipe savedManager = managerEquipeRepository.save(managerEquipe);
        return savedManager.getCode();
    }



    public ManagerEquipeDto getManagerByCode(Long code) {
        ManagerEquipe managerEquipe = managerEquipeRepository.findByCode(code); // Retrieve talent from the database by code
        if (managerEquipe == null) {
            return null;
        }
        List<Equipe> equipes = equipeRepository.findByManagerequipe(managerEquipe);
        ManagerEquipeDto managerEquipeDto = iTalentMapper.convertmanagerToDto(managerEquipe);

        return managerEquipeDto;
    }

    public List<ManagerEquipeDto> getManagers() {
        List<ManagerEquipe> managers = managerEquipeRepository.findAll();
        List<ManagerEquipeDto> managersDto = new ArrayList<>();
        for (ManagerEquipe manager: managers){
            ManagerEquipeDto managerEquipeDto = new ManagerEquipeDto();
            managerEquipeDto.setCode(manager.getCode());
            managerEquipeDto.setName(manager.getName());
            managersDto.add(managerEquipeDto);
        }
        return managersDto;
    }


}
