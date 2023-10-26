package com.Sofrecom.gestiontalent.repository;

import com.Sofrecom.gestiontalent.model.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalentRepository extends JpaRepository<Talent, Long> {
    Talent findByCode(Long code);

    List<Talent> getTalentsByEquipeId(Long equipeId);

    Talent findByName(String name);
}
