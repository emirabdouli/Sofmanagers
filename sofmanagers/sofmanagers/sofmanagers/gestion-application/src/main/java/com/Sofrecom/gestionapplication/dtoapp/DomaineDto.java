package com.Sofrecom.gestionapplication.dtoapp;

import com.Sofrecom.gestionapplication.model.DAS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomaineDto {
    private Long Id;
    private String nom;
    private String description;
    private List<AppRequest> appRequests;
    private List<DasDto> das;

}
