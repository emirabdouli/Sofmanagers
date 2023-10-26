package com.Sofrecom.gestionapplication.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtatApplicationDtoForUpdate {
    private String etatcourant;
    private String desciption;
    private LocalDateTime dateaffectationEtat;
    private LocalDateTime datedebutEtat;
    private LocalDateTime datefinEtat;
    private Boolean current;
    private Boolean alert;
    private float satisfaction;


}
