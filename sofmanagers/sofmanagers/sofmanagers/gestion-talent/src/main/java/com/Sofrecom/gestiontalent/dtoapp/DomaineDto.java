package com.Sofrecom.gestiontalent.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomaineDto {
    private String nom;
    private String description;
    private List<AppRequest> appRequests;
    private List<DasDto> das;

}
