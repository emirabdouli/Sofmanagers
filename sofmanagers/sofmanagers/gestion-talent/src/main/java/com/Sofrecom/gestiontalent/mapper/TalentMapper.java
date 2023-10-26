package com.Sofrecom.gestiontalent.mapper;

import com.Sofrecom.gestiontalent.dtot.*;
import com.Sofrecom.gestiontalent.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TalentMapper implements ITalentMapper{
    @Override
    public TalentDto convertTalentToDto(Talent talent) {
        TalentDto talentDto = new TalentDto();
        talentDto.setCode(talent.getCode());
        talentDto.setName(talent.getName());
        talentDto.setImage(talent.getImage());
        talentDto.setEmail(talent.getEmail());
        talentDto.setPhone(talent.getPhone());
        talentDto.setJobTitle(talent.getJobTitle());
        talentDto.setTypem(talent.isTypem());
        talentDto.setDateDebutContrat(talent.getDateDebutContrat());
        talentDto.setDateFinContrat(talent.getDateFinContrat());
        talentDto.setYearsOfExperience(talent.getYearsOfExperience());
        Talent manager = talent.getManager();
        if (manager != null) {
            talentDto.setManager(convertTalentToDto(manager));
        }

        talentDto.setEquipe(convertEquipeToDto(talent.getEquipe()));
        return talentDto;
    }


    @Override
    public ManagerEquipeDto convertmanagerToDto(ManagerEquipe managerEquipe) {
        ManagerEquipeDto managerEquipeDtoDto = new ManagerEquipeDto();
        managerEquipeDtoDto.setCode(managerEquipe.getCode());
        managerEquipeDtoDto.setName(managerEquipe.getName());
        managerEquipeDtoDto.setImage(managerEquipe.getImage());
        managerEquipeDtoDto.setEmail(managerEquipe.getEmail());
        managerEquipeDtoDto.setPhone(managerEquipe.getPhone());
        managerEquipeDtoDto.setDateDebutContrat(managerEquipe.getDateDebutContrat());
        managerEquipeDtoDto.setDateFinContrat(managerEquipe.getDateFinContrat());
        managerEquipeDtoDto.setYearsOfExperience(managerEquipe.getYearsOfExperience());
        managerEquipeDtoDto.setEquipes(convertEquipeListToDtoList(managerEquipe.getEquipes()));
        return managerEquipeDtoDto;
    }

    @Override
    public ManagerEquipe convertDtoToManager(ManagerEquipeDto managerEquipeDto) {
        ManagerEquipe managerEquipe = new ManagerEquipe();
        managerEquipe.setCode(managerEquipeDto.getCode());
        managerEquipe.setName(managerEquipeDto.getName());
        managerEquipe.setImage(managerEquipeDto.getImage());
        managerEquipe.setEmail(managerEquipeDto.getEmail());
        managerEquipe.setPhone(managerEquipeDto.getPhone());
        managerEquipe.setDateDebutContrat(managerEquipeDto.getDateDebutContrat());
        managerEquipe.setDateFinContrat(managerEquipeDto.getDateFinContrat());
        managerEquipe.setYearsOfExperience(managerEquipeDto.getYearsOfExperience());
        managerEquipe.setEquipes(convertDtoListToEquipeList(managerEquipeDto.getEquipes()));
        return managerEquipe;
    }

    @Override
    public List<AbsenceDto> convertAbsenceListToDtoList(List<Absence> absences) {
        List<AbsenceDto> absenceDtos = new ArrayList<>();
        for (Absence absence : absences) {
            AbsenceDto absenceDto = convertToDto(absence);
            absenceDtos.add(absenceDto);
        }
        return absenceDtos;
    }
    @Override
    public AbsenceDto convertToDto(Absence absence) {
        AbsenceDto absenceDto = new AbsenceDto();
        absenceDto.setId(absence.getId());
        absenceDto.setTalent(convertTalentToDto(absence.getTalent()));
        absenceDto.setStartDate(absence.getStartDate());
        absenceDto.setEndDate(absence.getEndDate());
        absenceDto.setEtat(absence.getEtat());
        absenceDto.setType(absence.getType());
        return absenceDto;
    }
    // Helper method to convert List<OToMeet> to List<OToMeetDto>
    @Override
    public List<OToMeetDto> convertOToMeetListToDtoList(List<OToMeet> oToMeets) {
        List<OToMeetDto> oToMeetDtos = new ArrayList<>();
        for (OToMeet oToMeet : oToMeets) {
            OToMeetDto oToMeetDto = convertToDto(oToMeet);
            oToMeetDtos.add(oToMeetDto);
        }
        return oToMeetDtos;
    }
    // Helper method to convert OToMeet object to OToMeetDto object
    @Override
    public OToMeetDto convertToDto(OToMeet oToMeet) {
        OToMeetDto oToMeetDto = new OToMeetDto();
        oToMeetDto.setId(oToMeet.getId());
        oToMeetDto.setStartdate(oToMeet.getStartdate());
        oToMeetDto.setFinishdate(oToMeet.getFinishdate());
        oToMeetDto.setNotes(oToMeet.getNotes());
        oToMeetDto.setManagerConfirm(oToMeet.getManagerConfirm());
        oToMeetDto.setTalentConfirm(oToMeet.getTalentConfirm());
        oToMeetDto.setTalent(convertTalentToDto(oToMeet.getTalent()));
        return oToMeetDto;
    }


    @Override
    public EquipeDto convertEquipeToDto(Equipe equipe) {
        EquipeDto equipeDto = new EquipeDto();
        equipeDto.setId(equipe.getId());
        equipeDto.setNom(equipe.getNom());
        equipeDto.setDescription(equipe.getDescription());
        return equipeDto;
    }


    @Override
    public Equipe convertDtoToEquipe(EquipeDto equipeDto) {
        Equipe equipe = new Equipe();
        equipe.setId(equipeDto.getId());
        equipe.setNom(equipeDto.getNom());
        equipe.setManagerequipe(convertDtoToManager(equipeDto.getManagerequipe()));
        equipe.setDescription(equipeDto.getDescription());
        return equipe;
    }
    @Override
    public List<EquipeDto> convertEquipeListToDtoList(List<Equipe> equipes) {
        List<EquipeDto> equipeDtos = new ArrayList<>();
        for (Equipe equipe : equipes) {
            EquipeDto equipeDto = convertEquipeToDto(equipe);
            equipeDtos.add(equipeDto);
        }
        return equipeDtos;
    }

    @Override
    public List<Equipe> convertDtoListToEquipeList(List<EquipeDto> equipeDtos) {
        List<Equipe> equipes = new ArrayList<>();
        for (EquipeDto equipeDto : equipeDtos) {
            Equipe equipe = convertDtoToEquipe(equipeDto);
            equipes.add(equipe);
        }
        return equipes;
    }
    @Override
    public Absence convertAbsenceDtoToEntity(AbsenceDto absenceDto) {
        Absence absence = new Absence();
        absence.setId(absenceDto.getId());
        absence.setStartDate(absenceDto.getStartDate());
        absence.setEndDate(absenceDto.getEndDate());
        absence.setEtat(absenceDto.getEtat());
        absence.setType(absenceDto.getType());
        return absence;
    }

    @Override
    public List<TalentDto> convertTalentsToDto(List<Talent> talents) {
        List<TalentDto> talentDtos = new ArrayList<>();
        for (Talent talent : talents) {
            TalentDto talentDto = new TalentDto();
            talentDto.setCode(talent.getCode());
            talentDto.setName(talent.getName());
            talentDto.setEmail(talent.getEmail());
            talentDto.setPhone(talent.getPhone());
            talentDto.setJobTitle(talent.getJobTitle());
            talentDto.setTypem(talent.isTypem());
            talentDto.setDateDebutContrat(talent.getDateDebutContrat());
            talentDto.setDateFinContrat(talent.getDateFinContrat());
            talentDto.setYearsOfExperience(talent.getYearsOfExperience());
            Talent manager = talent.getManager();
            if (manager != null) {
                talentDto.setManager(convertTalentToDto(manager));
            }

            talentDtos.add(talentDto);
        }
        return talentDtos;
    }

    @Override
    public Talent convertTalentDtoToEntity(TalentDto talentdto) {
        Talent talent = new Talent();
        talent.setCode(talentdto.getCode());
        talent.setName(talentdto.getName());
        talent.setImage(talentdto.getImage());
        talent.setEmail(talentdto.getEmail());
        talent.setPhone(talentdto.getPhone());
        talent.setJobTitle(talentdto.getJobTitle());
        talent.setTypem(talentdto.isTypem());
        talent.setDateDebutContrat(talentdto.getDateDebutContrat());
        talent.setDateFinContrat(talentdto.getDateFinContrat());
        talent.setYearsOfExperience(talentdto.getYearsOfExperience());
        TalentDto manager = talentdto.getManager();
        if (manager != null) {
            talent.setManager(convertTalentDtoToEntity(manager));
        }
        return talent;
    }
    @Override
    public OToMeet convertOToMeetsDtoToEntity(OToMeetDto oToMeetDto) {
        OToMeet oToMeet = new OToMeet();
        oToMeet.setId(oToMeetDto.getId());
        oToMeet.setStartdate(oToMeetDto.getStartdate());
        oToMeet.setFinishdate(oToMeetDto.getFinishdate());
        oToMeet.setNotes(oToMeetDto.getNotes());
        oToMeet.setManagerConfirm(oToMeetDto.getManagerConfirm());
        oToMeet.setTalentConfirm(oToMeet.getTalentConfirm());
        return oToMeet;
    }

    @Override
    public List<ManagerEquipeDto> convertManagerEquipesToDto(List<ManagerEquipe> managerEquipes) {
        List<ManagerEquipeDto> managerEquipeDtos = new ArrayList<>();
        for (ManagerEquipe managerEquipe : managerEquipes) {
            ManagerEquipeDto managerEquipeDto = new ManagerEquipeDto();
            managerEquipeDto.setCode(managerEquipe.getCode());
            managerEquipeDto.setName(managerEquipe.getName());
            managerEquipeDto.setImage(managerEquipe.getImage());
            managerEquipeDto.setEmail(managerEquipe.getEmail());
            managerEquipeDto.setPhone(managerEquipe.getPhone());
            managerEquipeDto.setDateDebutContrat(managerEquipe.getDateDebutContrat());
            managerEquipeDto.setDateFinContrat(managerEquipe.getDateFinContrat());
            managerEquipeDto.setYearsOfExperience(managerEquipe.getYearsOfExperience());
            managerEquipeDto.setEquipes(convertEquipeListToDtoList(managerEquipe.getEquipes()));
            managerEquipeDtos.add(managerEquipeDto);
        }
        return managerEquipeDtos;
    }

    @Override
    public OToMeetDto convertOToMeetsToDto(OToMeet oToMeet) {
        OToMeetDto oToMeetDto = new OToMeetDto();
        oToMeetDto.setId(oToMeet.getId());
        oToMeetDto.setTalent(convertTalentToDto(oToMeet.getTalent()));
        oToMeetDto.setManager(oToMeet.getTalent().getEquipe().getManagerequipe().getName());
        oToMeetDto.setStartdate(oToMeet.getStartdate());
        oToMeetDto.setFinishdate(oToMeet.getFinishdate());
        oToMeetDto.setNotes(oToMeet.getNotes());
        oToMeetDto.setManagerConfirm(oToMeet.getManagerConfirm());
        oToMeetDto.setTalentConfirm(oToMeet.getTalentConfirm());
        return oToMeetDto;
    }


}
