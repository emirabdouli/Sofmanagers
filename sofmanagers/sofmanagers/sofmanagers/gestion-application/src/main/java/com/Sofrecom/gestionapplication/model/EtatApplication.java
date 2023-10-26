package com.Sofrecom.gestionapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_etatapp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtatApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String etatcourant;
    private String desciption;
// Formatting the dateaffectationEtat attribut so we now exactly the moment of changing the application situation
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateaffectationEtat;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datedebutEtat;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datefinEtat;
    private boolean current;
    private boolean alert;
    private float satisfaction;

    @ManyToOne
    private Application application;

}
