package com.Sofrecom.gestionapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_rd")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @ManyToOne
    private DAS das;
    @OneToMany(mappedBy = "rd", cascade = CascadeType.ALL)
    private List<Application> applications;

}
