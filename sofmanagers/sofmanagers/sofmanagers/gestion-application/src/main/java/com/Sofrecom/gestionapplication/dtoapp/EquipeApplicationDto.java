package com.Sofrecom.gestionapplication.dtoapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeApplicationDto {
    private Long id;
    private String nom;
    private AppRequest appRequest;
}
