package com.Sofrecom.gestiontalent.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeApplicationDto {
    private Long id;
    private String nom;
    private AppRequest appRequest;
}
