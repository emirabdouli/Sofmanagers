package com.Sofrecom.gestiontalent.mapper;

import com.Sofrecom.gestiontalent.dtot.*;
import com.Sofrecom.gestiontalent.model.*;

import java.util.List;

public interface ITalentMapper {
    TalentDto convertTalentToDto(Talent talent);
    List<AbsenceDto> convertAbsenceListToDtoList(List<Absence> absences);
    AbsenceDto convertToDto(Absence absence);
    List<OToMeetDto> convertOToMeetListToDtoList(List<OToMeet> oToMeets);
    OToMeetDto convertToDto(OToMeet oToMeet);
    EquipeDto convertEquipeToDto(Equipe equipe);
    Absence convertAbsenceDtoToEntity(AbsenceDto absenceDto);
    List<TalentDto> convertTalentsToDto(List<Talent> talents);
    Talent convertTalentDtoToEntity(TalentDto talentdto);
    OToMeet convertOToMeetsDtoToEntity(OToMeetDto oToMeetDto);
    OToMeetDto convertOToMeetsToDto(OToMeet oToMeet);
    List<ManagerEquipeDto> convertManagerEquipesToDto(List<ManagerEquipe> managerEquipes);
    List<Equipe> convertDtoListToEquipeList(List<EquipeDto> equipeDtos);
    List<EquipeDto> convertEquipeListToDtoList(List<Equipe> equipes);
    Equipe convertDtoToEquipe(EquipeDto equipeDto);
    ManagerEquipe convertDtoToManager(ManagerEquipeDto managerEquipeDto);
    ManagerEquipeDto convertmanagerToDto(ManagerEquipe managerEquipe);

}
