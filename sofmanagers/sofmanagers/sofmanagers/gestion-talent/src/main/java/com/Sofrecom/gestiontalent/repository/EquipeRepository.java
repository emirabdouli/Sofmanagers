package com.Sofrecom.gestiontalent.repository;

import com.Sofrecom.gestiontalent.model.Equipe;
import com.Sofrecom.gestiontalent.model.ManagerEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    Equipe findByNom(String nom);

    boolean existsByNom(String nom);

    List<Equipe> findByManagerequipe(ManagerEquipe managerEquipe);
}
