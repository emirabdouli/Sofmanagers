package com.Sofrecom.gestionapplication.dtoapp;

import com.Sofrecom.gestionapplication.model.Domaine;
import com.Sofrecom.gestionapplication.model.RD;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
