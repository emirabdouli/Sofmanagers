package com.Sofrecom.gestionapplication.repository;

import com.Sofrecom.gestionapplication.model.Application;
import com.Sofrecom.gestionapplication.model.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository <Application, Long> {
    List<Application> findByDomaine(Domaine domaine);

    Application findByNom(String nom);
}
