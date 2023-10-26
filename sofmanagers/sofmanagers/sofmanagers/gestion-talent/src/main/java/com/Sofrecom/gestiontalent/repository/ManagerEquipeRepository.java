package com.Sofrecom.gestiontalent.repository;

import com.Sofrecom.gestiontalent.model.Equipe;
import com.Sofrecom.gestiontalent.model.ManagerEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerEquipeRepository extends JpaRepository<ManagerEquipe, Long> {
    ManagerEquipe findByEquipes(Equipe equipe);

    ManagerEquipe findByCode(Long code);
}
