package com.Sofrecom.gestionapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_talentapp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TalentApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true) // Add this annotation to enforce uniqueness
    private Long talentId;
    private TypeTalent talenttype;
    // The "niveauMaitrise" attribut indicates the level of mastery on the application of the talent
    private String niveauMaitrise;
    private Date periodeAffectation;
    private float occupation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

}
