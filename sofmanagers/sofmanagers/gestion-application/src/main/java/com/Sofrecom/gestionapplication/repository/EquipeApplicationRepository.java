package com.Sofrecom.gestionapplication.repository;

import com.Sofrecom.gestionapplication.model.EquipeApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface EquipeApplicationRepository extends JpaRepository<EquipeApplication, Long> {
    List<EquipeApplication> findByNom(String equipeApplicationName);
}
