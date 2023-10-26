package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.DomaineDto;
import com.Sofrecom.gestionapplication.dtoapp.EquipeApplicationDto;
import com.Sofrecom.gestionapplication.dtot.EquipeDto;
import com.Sofrecom.gestionapplication.dtot.TalentDto;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.Domaine;
import com.Sofrecom.gestionapplication.model.EquipeApplication;
import com.Sofrecom.gestionapplication.repository.DomaineRepository;
import com.Sofrecom.gestionapplication.repository.EquipeApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EquipeApplicationServices {
    private final WebClient.Builder webClientBuilder;
    private final EquipeApplicationRepository equipeApplicationRepository;
    private final IMapper imapper;
    private final DomaineRepository domaineRepository;
    public List<EquipeDto> retrieveEquipesFromFirstMicroservice() {
        List<EquipeDto> equipes = webClientBuilder.build().get()
                .uri("http://gestion-talent/api/talents/allequipesfromtalentmicroservice")
                .retrieve()
                .bodyToFlux(EquipeDto.class)
                .collectList()
                .block();
        return equipes;
    }

    public EquipeDto getEquipeByNamefromtalentmicroservice(String name) {
        EquipeDto equipe = webClientBuilder.build().get()
                .uri("http://gestion-talent/api/talents/equipeByNameforapp/{equipeName}", name)
                .retrieve()
                .bodyToMono(EquipeDto.class)
                .block();
        return equipe;
    }

    public List<TalentDto> getallTalentsfromtalentmicroservice() {
        List<TalentDto> talents = webClientBuilder.build().get()
                .uri("http://gestion-talent/api/talents/alltalentsforapp")
                .retrieve()
                .bodyToFlux(TalentDto.class)
                .collectList()
                .block();
        return talents;
    }




}
