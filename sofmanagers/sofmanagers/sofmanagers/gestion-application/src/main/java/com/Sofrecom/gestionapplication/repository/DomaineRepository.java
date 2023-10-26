package com.Sofrecom.gestionapplication.repository;

import com.Sofrecom.gestionapplication.model.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DomaineRepository extends JpaRepository<Domaine, Long> {
    Domaine findByNom(String nom);

    boolean existsByNom(String nom);
}
