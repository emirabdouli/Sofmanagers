package com.Sofrecom.gestionapplication.repository;

import com.Sofrecom.gestionapplication.model.DAS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DASRepository extends JpaRepository<DAS, Long> {
    DAS findByNom(String nom);
}
