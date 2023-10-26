package com.Sofrecom.gestionapplication.repository;

import com.Sofrecom.gestionapplication.dtoapp.RDDto;
import com.Sofrecom.gestionapplication.model.RD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RDRepository extends JpaRepository<RD, Long> {
    RD findByNom(String nom);
}
