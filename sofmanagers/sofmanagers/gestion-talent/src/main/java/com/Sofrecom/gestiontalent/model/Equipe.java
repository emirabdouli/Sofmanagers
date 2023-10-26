package com.Sofrecom.gestiontalent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_equipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;


    @OneToMany(mappedBy = "equipe")
    private List<Talent> talentsAEquipe;

    @ManyToOne
    private ManagerEquipe managerequipe;
}
