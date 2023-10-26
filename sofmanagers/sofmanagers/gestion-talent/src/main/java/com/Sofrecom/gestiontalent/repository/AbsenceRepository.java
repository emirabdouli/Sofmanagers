package com.Sofrecom.gestiontalent.repository;

import com.Sofrecom.gestiontalent.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findAllByEtatIsTrue();
}
