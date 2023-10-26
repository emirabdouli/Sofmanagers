package com.Sofrecom.gestionapplication.repository;

import com.Sofrecom.gestionapplication.model.EtatApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface EtatApplicationRepository extends JpaRepository<EtatApplication, Long> {
    EtatApplication findByEtatcourant(String etatcourant);

    List<EtatApplication> findByCurrentIsTrue();
}
