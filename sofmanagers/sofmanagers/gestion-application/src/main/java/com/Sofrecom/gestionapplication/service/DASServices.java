package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.*;
import com.Sofrecom.gestionapplication.repository.DASRepository;
import com.Sofrecom.gestionapplication.repository.DomaineRepository;
import com.Sofrecom.gestionapplication.repository.RDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DASServices implements IDASServices{
    private final DASRepository dasRepository;
    private final DomaineRepository domaineRepository ;
    private final RDRepository rdRepository ;

    private final IMapper imapper;


    public Long addDas(DasDto dasDto) {
        DAS das =  imapper.mapDasdtoToDAS(dasDto);
        List<RDDto> rddtos = dasDto.getRds();
        List<RD> rds = new ArrayList<>();
        if (rddtos != null) {
            for (RDDto dto : rddtos) {
                // Perform actions for each RDDto object
                RD rd = rdRepository.findByNom(dto.getNom());
                if (rd != null){
                    rds.add(rd);
                }
            }
            das.setRds(rds);
            }
        DomaineDto domaineDto = dasDto.getDomainedas();
        if (domaineDto != null) {
            Domaine domaine = domaineRepository.findByNom(domaineDto.getNom());
            if (domaine == null) {
                das.setDomainedas(null);
            } else {
            imapper.mapDomaineDtoToDomaine(domaineDto, domaine);
            das.setDomainedas(domaine); }

        }
        dasRepository.save(das);
        for (RD rd : rds){
            rd.setDas(das);
            rdRepository.save(rd);
        }
        return das.getId();
    }

    public List<DasDto> getAllDases() {
        List<DAS> alldas = dasRepository.findAll();

        List<DasDto> dasDtos = new ArrayList<>();

        for (DAS das : alldas) {
            DasDto dasDto = new DasDto();
            dasDto.setId(das.getId());
            dasDto.setNom(das.getNom());
            dasDto.setDomainedas(imapper.mapsDomaineToDomaineDto(das.getDomainedas()));
            dasDtos.add(dasDto);
        }

        return dasDtos;
    }


}
