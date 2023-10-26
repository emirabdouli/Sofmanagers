package com.Sofrecom.gestionapplication.repository;
import com.Sofrecom.gestionapplication.model.TalentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TalentApplicationRepository extends JpaRepository<TalentApplication, Long> {

    List<TalentApplication> findByTalentId(Long talentCode);

    TalentApplication findByTalentIdAndApplicationId(Long talentId, Long applicationId);

    List<TalentApplication> findAllByTalentId(Long talentCode);
    List<TalentApplication> findByApplicationId(Long applicationId);

}
