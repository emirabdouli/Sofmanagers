package com.Sofrecom.gestionapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_das")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DAS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @OneToMany(mappedBy = "das", cascade = CascadeType.ALL)
    private List<RD> rds;

    @ManyToOne
    private Domaine domainedas;
}
