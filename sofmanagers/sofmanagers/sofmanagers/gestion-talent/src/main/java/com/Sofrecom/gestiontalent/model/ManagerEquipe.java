package com.Sofrecom.gestiontalent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_manager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerEquipe {
    @Id
    private Long code;
    private String name;
    @Lob
    @Column(name = "image")
    private byte[] image;
    private String email;
    private Long phone;
    @Temporal(TemporalType.DATE)
    private Date dateDebutContrat;
    @Temporal(TemporalType.DATE)
    private Date dateFinContrat;
    private int yearsOfExperience;
    @OneToMany(mappedBy = "managerequipe")
    private List<Equipe> equipes;
}
