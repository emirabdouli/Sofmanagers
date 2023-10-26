package com.Sofrecom.gestionapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_application")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String description;
    private String technologie;
    private Float etp;
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<EtatApplication> etats;
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private Set<TalentApplication> talentApplications;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domaine_id")
    private Domaine domaine;
    @OneToOne(mappedBy = "application",cascade = CascadeType.ALL)
    private EquipeApplication equipeApplication;
    @ManyToOne
    private RD rd;

}
