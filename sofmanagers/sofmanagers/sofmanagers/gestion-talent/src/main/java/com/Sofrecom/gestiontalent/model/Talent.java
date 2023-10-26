package com.Sofrecom.gestiontalent.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_talent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Talent {
    @Id
    private Long code;
    private String name;
    @Lob
    @Column(name = "image")
    private byte[] image;
    private String email;
    private Long phone;
    private String jobTitle;
    private boolean typem;
    @Temporal(TemporalType.DATE)
    private Date dateDebutContrat;
    @Temporal(TemporalType.DATE)
    private Date dateFinContrat;
    @ManyToOne
    private Talent manager;
    private int yearsOfExperience;
    @OneToMany(mappedBy = "talent")
    private List<Absence> absences;
    @OneToMany(mappedBy = "talent")
    private List<OToMeet> OToMeets;
    @ManyToOne
    private Equipe equipe;
}
