package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.DasDto;
import com.Sofrecom.gestionapplication.dtoapp.DomaineDto;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.DAS;
import com.Sofrecom.gestionapplication.model.Domaine;
import com.Sofrecom.gestionapplication.repository.ApplicationRepository;
import com.Sofrecom.gestionapplication.repository.DASRepository;
import com.Sofrecom.gestionapplication.repository.DomaineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomaineServices implements IDomaineServices{
    private final DomaineRepository domaineRepository;
    private final DASRepository dasRepository;

    private final DASServices dasServices;
    private final ApplicationRepository applicationRepository;
    private final IMapper imapper;
    @Override
    public List<DomaineDto> getAlldomaines() {
        List<Domaine> domaines = domaineRepository.findAll();
        List<DasDto> dases = dasServices.getAllDases();
        List<DomaineDto> domaineDtos = new ArrayList<>();

        for (Domaine domaine : domaines) {
            List<DasDto> domainedases = new ArrayList<>(); // Move the declaration inside the loop

            DomaineDto domaineDto = new DomaineDto();
            imapper.mapDomaineToDomaineDto(domaine, domaineDto);

            for (DasDto das : dases) {
                if (domaine.getId().equals(das.getDomainedas().getId())) {
                    domainedases.add(das);
                }
            }

            domaineDto.setDas(domainedases); // Set the dasdtos list for the current DomaineDto
            domaineDtos.add(domaineDto);
        }

        return domaineDtos;
    }

    @Override
    public void affecttoapp(Long applicationid, String domainenom) {
        // Find the domain by its nom attribute
        Domaine domaine = domaineRepository.findByNom(domainenom);
        Optional<Application> applications = applicationRepository.findById(applicationid);
        if (!applications.isPresent()) {
            return;
        }
        if (domaine != null) {

            Application application = applications.get();
                application.setDomaine(domaine);
            applicationRepository.save(application);
        }
    }
    @Override
    public Long addDomaine(DomaineDto domaineDto) {
        // Check if domain name already exists
        if (domaineRepository.existsByNom(domaineDto.getNom())) {
            throw new RuntimeException("Domain name already exists");
        }

        // Create domain object from DTO
        Domaine domaine = new Domaine();
        imapper.mapDomaineDtoToDomaine(domaineDto, domaine);
        Domaine savedDomaine = domaineRepository.save(domaine);

        return savedDomaine.getId();
    }

}
