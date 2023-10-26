package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.AppRequest;
import com.Sofrecom.gestionapplication.dtoapp.DasDto;
import com.Sofrecom.gestionapplication.dtoapp.DomaineDto;
import com.Sofrecom.gestionapplication.dtoapp.RDDto;
import com.Sofrecom.gestionapplication.mapper.IMapper;
import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.DAS;
import com.Sofrecom.gestionapplication.model.Domaine;
import com.Sofrecom.gestionapplication.model.RD;
import com.Sofrecom.gestionapplication.repository.ApplicationRepository;
import com.Sofrecom.gestionapplication.repository.DASRepository;
import com.Sofrecom.gestionapplication.repository.RDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RDServices implements IRDServices {
    private final RDRepository rdRepository;
    private final DASRepository dasRepository;
    private final ApplicationRepository applicationRepository;

    private final IMapper imapper;

    @Override
    public List<RDDto> getAllRdsnotassignedtodas() {
        List<RD> allrds = rdRepository.findAll();
        List<RD> rdsnotassignedtodas = new ArrayList<>();
        if (allrds != null && !allrds.isEmpty()) {
            for (RD rd : allrds) {
                if (rd.getDas() == null) {
                    rdsnotassignedtodas.add(rd);
                }
            }
        }

        List<RDDto> rdDtos = new ArrayList<>();

        for (RD rd : rdsnotassignedtodas) {
            RDDto rdDto = new RDDto();
            rdDto.setId(rd.getId());
            rdDto.setNom(rd.getNom());
            rdDtos.add(rdDto);
        }

        return rdDtos;
    }

    public Long addRD(RDDto rdDto) {
        RD rd =  imapper.mapRddtoToRD(rdDto);

        List<AppRequest> appRequests = rdDto.getAppRequests();

        List<Application> applications = new ArrayList<>();
        if (appRequests != null) {
            for (AppRequest appRequest : appRequests) {
                // Perform actions for each RDDto object
                Application application = applicationRepository.findByNom(appRequest.getNom());
                if (application != null){
                    applications.add(application);
                }
            }
            rd.setApplications(applications);
        }
        DasDto dasDto = rdDto.getDas();
        if (dasDto != null) {
            DAS das = dasRepository.findByNom(dasDto.getNom());
            if (das == null) {
                rd.setDas(null);
            } else {
                rd.setDas(das); }

        }
        rdRepository.save(rd);
        for(Application app: applications){
            app.setRd(rd);
            applicationRepository.save(app);
        }
        return rd.getId();
    }

}
