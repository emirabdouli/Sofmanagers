package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.AbsenceDto;
import com.Sofrecom.gestiontalent.mapper.ITalentMapper;
import com.Sofrecom.gestiontalent.model.Absence;
import com.Sofrecom.gestiontalent.model.Equipe;
import com.Sofrecom.gestiontalent.model.Talent;
import com.Sofrecom.gestiontalent.repository.AbsenceRepository;
import com.Sofrecom.gestiontalent.repository.EquipeRepository;
import com.Sofrecom.gestiontalent.repository.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor

public class AbsenceServices implements IAbsenceServices{
    private final AbsenceRepository absenceRepository;
    private final TalentRepository talentRepository;
    private final EquipeRepository equipeRepository;
    private final ITalentMapper iTalentMapper;
    @Override
    public List<AbsenceDto> getAllAbsences() {
        List<Absence> absences = absenceRepository.findAll(); // Retrieve absences from the database
        // Convert Absence objects to AbsenceDto objects
        List<AbsenceDto> absenceDtos = new ArrayList<>();
        for (Absence absence : absences) {
            AbsenceDto absenceDto = iTalentMapper.convertToDto(absence);
            absenceDtos.add(absenceDto);
        }
        return absenceDtos;
    }
    @Override
    public AbsenceDto getAbsenceById(Long absenceId) {
        Optional<Absence> optionalAbsence = absenceRepository.findById(absenceId);
        if (optionalAbsence.isPresent()) {
            Absence absence = optionalAbsence.get();
            return iTalentMapper.convertToDto(absence);
        }
        return null; // Or you can throw an exception or handle the case differently
    }



    @Override
    public List<AbsenceDto> getAbsencesByTalentCode(Long talentCode) {
        Talent talent = talentRepository.findByCode(talentCode); // Retrieve the talent by code from the database

        if (talent != null) {
            List<AbsenceDto> absenceDtos = new ArrayList<>();

            for (Absence absence : talent.getAbsences()) {
                AbsenceDto absenceDto = iTalentMapper.convertToDto(absence);
                absenceDtos.add(absenceDto);
            }

            return absenceDtos;
        }

        return Collections.emptyList(); // Return an empty list if talent with the given code is not found
    }
    @Override
    public List<AbsenceDto> getAllAbsencesByEtatTrue() {
        List<Absence> absences = absenceRepository.findAllByEtatIsTrue(); // Retrieve absences with etat=true from the database

        List<AbsenceDto> absenceDtos = new ArrayList<>();
        for (Absence absence : absences) {
            AbsenceDto absenceDto = iTalentMapper.convertToDto(absence);
            absenceDtos.add(absenceDto);
        }
        return absenceDtos;
    }
    @Override
    public AbsenceDto addAbsenceAndAssignToTalent(Long talentCode, AbsenceDto absenceDto) {
        Talent talent = talentRepository.findByCode(talentCode); // Retrieve the talent by code from the database

        if (talent != null) {
            Absence absence = iTalentMapper.convertAbsenceDtoToEntity(absenceDto);
            talent.getAbsences().add(absence); // Assign the absence to the talent
            absence.setTalent(talent); // Set the talent for the absence

            Absence savedAbsence = absenceRepository.save(absence); // Save the absence to the database

            return iTalentMapper.convertToDto(savedAbsence);
        }

        return null; // Return null if talent with the given code is not found
    }
    @Override
    public int getAbsenceCountByEquipeId(Long equipeId) {
        Optional<Equipe> equipeOptional = equipeRepository.findById(equipeId); // Retrieve the equipe by ID from the database

        if (equipeOptional.isPresent()) {
            Equipe equipe = equipeOptional.get();
            int absenceCount = 0;
            for (Talent talent : equipe.getTalentsAEquipe()) {
                absenceCount += calculateAbsenceCount(talent);
            }
            return absenceCount;
        }

        return 0; // Return 0 if equipe with the given ID is not found
    }
    @Override
    public int calculateAbsenceCount(Talent talent) {
        int absenceCount = 0;
        if (talent != null && talent.getAbsences() != null) {
            absenceCount = talent.getAbsences().size();
        }
        return absenceCount;
    }


    @Override
    public Map<String, Integer> calculateAbsentDaysByMonth() {
        List<AbsenceDto> absences = getAllAbsencesByEtatTrue();
        Map<String, Integer> absentDaysByMonth = new HashMap<>();
        int currentYear = LocalDate.now().getYear();

        for (int month = 1; month <= 12; month++) {
            int absentDays = 0;
            for (AbsenceDto absence : absences) {
                Date startDate = absence.getStartDate();
                Date endDate = absence.getEndDate();
                Calendar calStart = Calendar.getInstance();
                calStart.setTime(startDate);
                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(endDate);
                int startYear = calStart.get(Calendar.YEAR);
                int startMonth = calStart.get(Calendar.MONTH) + 1;
                int endYear = calEnd.get(Calendar.YEAR);
                int endMonth = calEnd.get(Calendar.MONTH) + 1;

                // Check if the absence spans across the current month
                if ((startYear < currentYear || (startYear == currentYear && startMonth <= month)) &&
                        (endYear > currentYear || (endYear == currentYear && endMonth >= month))) {
                    // Calculate the number of days in the absence that fall within the current month
                    Calendar currentDate = (Calendar) calStart.clone();
                    while (!currentDate.after(calEnd)) {
                        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
                        if (currentMonth == month) {
                            absentDays++;
                        }
                        currentDate.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }
            }
            String monthName = Month.of(month).toString();
            absentDaysByMonth.put(monthName, absentDays);
        }
        return absentDaysByMonth;
    }


    @Override
    public Long updateAbsenceAndAssignToTalent(Long id, AbsenceDto existingAbsence) {
        Absence updatedAbsence = absenceRepository.findById(id).orElse(null);
        if (updatedAbsence == null) {
            return null; // Talent with the given code doesn't exist
        }
        Talent existingTalent = updatedAbsence.getTalent();

        if (existingAbsence.getStartDate() != null) {
                updatedAbsence.setStartDate(existingAbsence.getStartDate());
            }
            if (existingAbsence.getEndDate() != null) {
                updatedAbsence.setEndDate(existingAbsence.getEndDate());
            }
            if (existingAbsence.getEtat() != null) {
                updatedAbsence.setEtat(existingAbsence.getEtat());
            }
            if (existingAbsence.getType() != null) {
                updatedAbsence.setType(existingAbsence.getType());
            }
        updatedAbsence.setTalent(existingTalent);
        Absence savedAbsence = absenceRepository.save(updatedAbsence); // Save the absence to the database

            return savedAbsence.getId();
    }
    @Override
    public void deleteAbsenceById(Long id) {
        absenceRepository.deleteById(id);
    }



}
