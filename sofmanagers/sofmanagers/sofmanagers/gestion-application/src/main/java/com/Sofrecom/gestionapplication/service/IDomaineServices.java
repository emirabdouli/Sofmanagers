package com.Sofrecom.gestionapplication.service;

import com.Sofrecom.gestionapplication.dtoapp.DomaineDto;

import java.util.List;

public interface IDomaineServices {
    List<DomaineDto> getAlldomaines();
    void affecttoapp(Long applicationid, String domainenom);
    Long addDomaine(DomaineDto domaineDto);
}
