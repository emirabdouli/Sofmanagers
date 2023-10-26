package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.OToMeetDto;
import com.Sofrecom.gestiontalent.mapper.ITalentMapper;
import com.Sofrecom.gestiontalent.model.OToMeet;
import com.Sofrecom.gestiontalent.model.Talent;
import com.Sofrecom.gestiontalent.repository.OToMeetRepository;
import com.Sofrecom.gestiontalent.repository.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OToMeetServices implements IOToMeetServices{
    private final TalentRepository talentRepository;
    private final OToMeetRepository oToMeetRepository;
    private final ITalentMapper iTalentMapper;
    @Override
    public List<OToMeetDto> getAllOToMeets() {
        List<OToMeet> oToMeets = oToMeetRepository.findAll(); // Retrieve absences from the database
        List<OToMeetDto> oToMeetDtos = new ArrayList<>();
        for (OToMeet oToMeet : oToMeets) {
            OToMeetDto oToMeetDto = iTalentMapper.convertOToMeetsToDto(oToMeet);
            oToMeetDtos.add(oToMeetDto);
        }
        return oToMeetDtos;
    }
    @Override
    public OToMeetDto getAOneToOneById(Long oneToOneId) {
        Optional<OToMeet> optionalOToMeet = oToMeetRepository.findById(oneToOneId);
        if (optionalOToMeet.isPresent()) {
            OToMeet oToMeet = optionalOToMeet.get();
            return iTalentMapper.convertOToMeetsToDto(oToMeet);
        }
        return null; // Or you can throw an exception or handle the case differently
    }
    @Override
    public List<OToMeetDto> getOnetooneByTalentCode(Long talentCode) {
        Talent talent = talentRepository.findByCode(talentCode); // Retrieve the talent by code from the database

        if (talent != null) {
            List<OToMeetDto> oToMeetDtos = new ArrayList<>();

            for (OToMeet oToMeet : talent.getOToMeets()) {
                OToMeetDto oToMeetDto = iTalentMapper.convertOToMeetsToDto(oToMeet);
                oToMeetDtos.add(oToMeetDto);
            }

            return oToMeetDtos;
        }

        return Collections.emptyList(); // Return an empty list if talent with the given code is not found
    }
    @Override
    public OToMeetDto addOnetooneAndAssignToTalent(Long talentCode, OToMeetDto oToMeetDto) {
        Talent talent = talentRepository.findByCode(talentCode); // Retrieve the talent by code from the database
        if (talent != null) {
            talent.setImage(null);
            String existingManager = talent.getEquipe().getManagerequipe().getName();
            OToMeet oToMeet = iTalentMapper.convertOToMeetsDtoToEntity(oToMeetDto);
            talent.getOToMeets().add(oToMeet); // Assign the absence to the talent

            oToMeet.setManager(existingManager);
            oToMeet.setTalent(talent); // Set the talent for the absence

            OToMeet savedOToMeet = oToMeetRepository.save(oToMeet); // Save the absence to the database

            return iTalentMapper.convertOToMeetsToDto(savedOToMeet);
        }

        return null; // Return null if talent with the given code is not found
    }
    @Override
    public Long updateOnetoone(Long oneToMeetId, OToMeetDto updatedOToMeetDto) {
        OToMeet oToMeet = oToMeetRepository.findById(oneToMeetId)
                .orElseThrow(() -> new EntityNotFoundException("One-to-one meeting not found with ID: " + oneToMeetId));

        String existingManager = oToMeet.getTalent().getEquipe().getManagerequipe().getName();

        // Update the attributes of the one-to-one meeting
        if (updatedOToMeetDto.getStartdate() != null) {
            oToMeet.setStartdate(updatedOToMeetDto.getStartdate());
        }
        if (updatedOToMeetDto.getFinishdate() != null) {
            oToMeet.setFinishdate(updatedOToMeetDto.getFinishdate());
        }
        if (existingManager != null) {
            oToMeet.setManager(existingManager);
        }
        if (updatedOToMeetDto.getNotes() != null) {
            oToMeet.setNotes(updatedOToMeetDto.getNotes());
        }
        if (updatedOToMeetDto.getManagerConfirm() != null) {
            oToMeet.setManagerConfirm(updatedOToMeetDto.getManagerConfirm());
        }
        if (updatedOToMeetDto.getTalentConfirm() != null) {
            oToMeet.setTalentConfirm(updatedOToMeetDto.getTalentConfirm());
        }
        OToMeet savedOToMeet = oToMeetRepository.save(oToMeet); // Save the updated one-to-one meeting to the database

        return savedOToMeet.getId();
    }
    @Override
    public void deleteOneToOneById(Long id) {
        oToMeetRepository.deleteById(id);
    }




}
