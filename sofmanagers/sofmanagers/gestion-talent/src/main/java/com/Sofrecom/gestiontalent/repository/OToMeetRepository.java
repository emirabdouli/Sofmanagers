package com.Sofrecom.gestiontalent.repository;

import com.Sofrecom.gestiontalent.model.OToMeet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OToMeetRepository extends JpaRepository<OToMeet, Long> {
}
