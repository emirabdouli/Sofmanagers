package com.Sofrecom.gestiontalent.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DasDto {
    private Long id;
    private String nom;
    private List<RDDto> rds;
    private DomaineDto domainedas;
}
