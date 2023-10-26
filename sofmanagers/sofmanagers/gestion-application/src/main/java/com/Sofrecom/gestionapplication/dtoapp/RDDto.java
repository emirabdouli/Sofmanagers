package com.Sofrecom.gestionapplication.dtoapp;

import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.DAS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RDDto {
    private Long id;
    private String nom;
    private List<AppRequest> appRequests;
    private DasDto das;

}
