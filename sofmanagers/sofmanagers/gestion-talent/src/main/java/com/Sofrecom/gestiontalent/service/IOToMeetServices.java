package com.Sofrecom.gestiontalent.service;

import com.Sofrecom.gestiontalent.dtot.OToMeetDto;

import java.util.List;

public interface IOToMeetServices {
    List<OToMeetDto> getAllOToMeets();
    OToMeetDto getAOneToOneById(Long oneToOneId);
    List<OToMeetDto> getOnetooneByTalentCode(Long talentCode);
    OToMeetDto addOnetooneAndAssignToTalent(Long talentCode, OToMeetDto oToMeetDto);
    Long updateOnetoone(Long oneToMeetId, OToMeetDto updatedOToMeetDto);
    void deleteOneToOneById(Long id);
}
