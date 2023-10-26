package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.AbsenceDto;
import com.Sofrecom.gestiontalent.model.Talent;

import java.util.List;
import java.util.Map;

public interface IAbsenceServices {
    List<AbsenceDto> getAllAbsences();
    AbsenceDto getAbsenceById(Long absenceId);
    List<AbsenceDto> getAbsencesByTalentCode(Long talentCode);
    List<AbsenceDto> getAllAbsencesByEtatTrue();
    AbsenceDto addAbsenceAndAssignToTalent(Long talentCode, AbsenceDto absenceDto);
    int getAbsenceCountByEquipeId(Long equipeId);
    int calculateAbsenceCount(Talent talent);
    Map<String, Integer> calculateAbsentDaysByMonth();
    Long updateAbsenceAndAssignToTalent(Long id, AbsenceDto existingAbsence);
    void deleteAbsenceById(Long id);
}
