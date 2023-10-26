package com.Sofrecom.gestionapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_domaine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Domaine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String description;

    @OneToMany(mappedBy = "domaine")
    private List<Application> applications;
    @OneToMany(mappedBy = "domainedas", cascade = CascadeType.ALL)
    private List<DAS> das;
}
