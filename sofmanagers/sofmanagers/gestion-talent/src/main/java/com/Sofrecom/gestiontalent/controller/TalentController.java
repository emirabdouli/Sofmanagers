package com.Sofrecom.gestiontalent.controller;

import com.Sofrecom.gestiontalent.dtoapp.AppRequest;
import com.Sofrecom.gestiontalent.dtoapp.TalentApplicationDto;
import com.Sofrecom.gestiontalent.dtot.*;
import com.Sofrecom.gestiontalent.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TalentController {
    @Autowired
    private TalentServices talentService;
    @Autowired
    private AbsenceServices absenceServices;
    @Autowired
    private EquipeServices equipeServices;
    @Autowired
    private OToMeetServices oToMeetServices;
    @Autowired
    private ManagerEquipeService managerEquipeService;


    @PostMapping("/addTalent")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addTalent(@RequestBody TalentDto talentDto) {
        Long talentId = talentService.addTalent(talentDto);
        String responseMessage = String.valueOf(talentId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
    @PutMapping("/updatetalent/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateTalent(@PathVariable Long code, @RequestBody TalentDto talentDto) {
        Long talentId = talentService.updateTalent(code, talentDto);
        String responseMessage = String.valueOf(talentId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
    @PostMapping("/addEquipe")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addEquipe(@RequestBody EquipeDto equipeDto) {
        Long equipeId = equipeServices.addEquipe(equipeDto);
        String responseMessage = String.valueOf(equipeId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
    @GetMapping("/alltalents")
    public ResponseEntity<List<TalentDto>> getAllTalentsinfo() {
        List<TalentDto> talentDtos = talentService.getAllTalents();
        if (talentDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDtos);
    }
    @GetMapping("/alltalentsforapp")
    public ResponseEntity<List<TalentDto>> getAllTalentsforapps() {
        List<TalentDto> talentDtos = talentService.getAllTalentsforapps();
        if (talentDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDtos);
    }


    @GetMapping("/talent/{code}")
    public ResponseEntity<TalentDto> getTalentinfo(@PathVariable Long code) {
        TalentDto talentDto = talentService.getTalentByCode(code);
        if (talentDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDto);
    }
    @GetMapping("/talentforapp/{code}")
    public ResponseEntity<TalentDto> getTalentinfoforapp(@PathVariable Long code) {
        TalentDto talentDto = talentService.getTalentByCodeforapp(code);
        if (talentDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDto);
    }

    @GetMapping("/talentByEquipe/{equipeId}")
    public ResponseEntity <List<TalentDto>> getTalentsbyEquipe(@PathVariable("equipeId") Long equipeId) {
        EquipeDto equipeDto = talentService.getEquipeById(equipeId); // Assuming you have a method to retrieve Equipe by ID
        List<TalentDto> talentDtos = talentService.getTalentsByEquipeId(equipeDto);
        if (talentDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDtos);
    }

    @GetMapping("/allequipes")
    public ResponseEntity<List<EquipeDto>> getAllEquipesinfo() {
        List<EquipeDto> equipeDtos = equipeServices.getAllEquipes();
        if (equipeDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipeDtos);
    }
    @GetMapping("/allequipesfromtalentmicroservice")
    public ResponseEntity<List<EquipeDto>> getAllEquipesforappmicroserviceinfo() {
        List<EquipeDto> equipeDtos = equipeServices.getAllEquipesforappmicroservice();
        if (equipeDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipeDtos);
    }

        @GetMapping("/equipe/{equipeId}")
    public ResponseEntity<EquipeDto> getEquipeinfo(@PathVariable Long equipeId) {
        EquipeDto equipeDto = equipeServices.getEquipeById(equipeId);
        if (equipeDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipeDto);
    }

    @GetMapping("/equipeIdByName/{equipeName}")
    public Long getEquipeIdByName(@PathVariable String equipeName) {
        return equipeServices.getEquipeIdByName(equipeName);
    }


    @GetMapping("/alltalentmanager")
    public ResponseEntity<List<TalentDto>> getAllTalentsmanager() {
        List<TalentDto> talentDtos = talentService.getTalentsManager();
        if (talentDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDtos);
    }
    @GetMapping("/allabsences")
    public ResponseEntity<List<AbsenceDto>> getAllAbsencesinfo() {
        List<AbsenceDto> absenceDtos = absenceServices.getAllAbsences();
        if (absenceDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(absenceDtos);
    }
    @GetMapping("/absences/{talentCode}")
    public List<AbsenceDto> getAbsencesByTalentCode(@PathVariable Long talentCode) {
        return absenceServices.getAbsencesByTalentCode(talentCode);
    }
    @GetMapping("/absencebyid/{absenceid}")
    public AbsenceDto getAbsencesByabsencedto(@PathVariable Long absenceid) {
        return absenceServices.getAbsenceById(absenceid);
    }

    @GetMapping("/allimprevuabsences")
    public ResponseEntity<List<AbsenceDto>> getAllimprevuAbsencesinfo() {
        List<AbsenceDto> absenceDtos = absenceServices.getAllAbsencesByEtatTrue();
        if (absenceDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(absenceDtos);
    }


    @PostMapping("addabsencebytalent/{talentCode}")
    public ResponseEntity<AbsenceDto> addAbsenceAndAssignToTalent(
            @PathVariable Long talentCode,
            @RequestBody AbsenceDto absenceDto) {
        AbsenceDto addedAbsenceDto = absenceServices.addAbsenceAndAssignToTalent(talentCode, absenceDto);

        if (addedAbsenceDto != null) {
            return ResponseEntity.ok(addedAbsenceDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("getAbsenceCountByEquipeId/{equipeId}")
    public ResponseEntity<Integer> getAbsenceCountByEquipeId(@PathVariable Long equipeId) {
        int absenceCount = absenceServices.getAbsenceCountByEquipeId(equipeId);
        return ResponseEntity.ok(absenceCount);
    }
    @GetMapping("/allabsences-days-by-month")
    public ResponseEntity<Map<String, Integer>> getAbsentDaysByMonth() {
        Map<String, Integer> absentDaysByMonth = absenceServices.calculateAbsentDaysByMonth();
        return ResponseEntity.ok(absentDaysByMonth);
    }

    @PutMapping("/updateabsence/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateAbsence(@PathVariable Long id, @RequestBody AbsenceDto absence) {
        Long absenceid = absenceServices.updateAbsenceAndAssignToTalent(id, absence);
        String responseMessage = String.valueOf(absenceid);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteabsence/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAbsence(@PathVariable("id") Long absenceId) {
        absenceServices.deleteAbsenceById(absenceId);
    }

    @GetMapping("/allOneToOnes")
    public ResponseEntity<List<OToMeetDto>> getAlloneToonesinfo() {
        List<OToMeetDto> oToMeetDtos = oToMeetServices.getAllOToMeets();
        if (oToMeetDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oToMeetDtos);
    }
    @GetMapping("/onetoonebyid/{onetooneid}")
    public OToMeetDto getonetooneByid(@PathVariable Long onetooneid) {
        return oToMeetServices.getAOneToOneById(onetooneid);
    }

    @GetMapping("/onetoonesbytalent/{talentCode}")
    public List<OToMeetDto> getONETOONEsByTalentCode(@PathVariable Long talentCode) {
        return oToMeetServices.getOnetooneByTalentCode(talentCode);
    }

    @PostMapping("addonetoonebytalent/{talentCode}")
    public ResponseEntity<OToMeetDto> addonetooneAndAssignToTalent(
            @PathVariable Long talentCode,
            @RequestBody OToMeetDto oToMeetDto) {
        OToMeetDto addedOToMeetDto = oToMeetServices.addOnetooneAndAssignToTalent(talentCode, oToMeetDto);

        if (addedOToMeetDto != null) {
            return ResponseEntity.ok(addedOToMeetDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateonetoone/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateOneToOne(@PathVariable Long id, @RequestBody OToMeetDto oToMeetDto) {
        Long onetooneid = oToMeetServices.updateOnetoone(id, oToMeetDto);
        String responseMessage = String.valueOf(onetooneid);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteonetoone/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneToOne(@PathVariable("id") Long onetooneId) {
        oToMeetServices.deleteOneToOneById(onetooneId);
    }

    @GetMapping("/equipeByNameforapp/{equipeName}")
    public EquipeDto equipeByNameforapp(@PathVariable String equipeName) {
        return equipeServices.getanEquipeByName(equipeName);
    }

    @GetMapping("/talentcodebyname/{name}")
    public Long getTalentcodebyname(@PathVariable String name) {
        Long talentDtocode = talentService.getTalentCodeByName(name);
        Long not = null;
        if (talentDtocode == null) {
            return not;
        }
        return talentDtocode;
    }

    @PostMapping("/addManager")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addManager(@RequestBody ManagerEquipeDto managerEquipeDto) {
        Long managerId = managerEquipeService.addManager(managerEquipeDto);
        String responseMessage = String.valueOf(managerId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
    @PutMapping("/updatemanager/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateManager(@PathVariable Long code, @RequestBody ManagerEquipeDto managerEquipeDto) {
        Long managerId = managerEquipeService.updateManager(code, managerEquipeDto);
        String responseMessage = String.valueOf(managerId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/allmanagers")
    public ResponseEntity<List<ManagerEquipeDto>> getAllManagerssinfo() {
        List<ManagerEquipeDto> managerEquipeDtos = managerEquipeService.getManagers();
        if (managerEquipeDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(managerEquipeDtos);
    }

    @GetMapping("/manager/{code}")
    public ResponseEntity<ManagerEquipeDto> getManagerinfo(@PathVariable Long code) {
        ManagerEquipeDto managerEquipeDto = managerEquipeService.getManagerByCode(code);
        if (managerEquipeDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(managerEquipeDto);
    }
    @GetMapping("/applicationsfromappmicroservice/{equipeApplicationName}")
    public List<AppRequest> getApplicationsByEquipeApplicationName(@PathVariable String equipeApplicationName) {
        List<AppRequest> apprequests = equipeServices.getApplicationsfromappmicroByEquipeApplicationName(equipeApplicationName);
        if (apprequests.isEmpty()) {
            return null;
        }
        return apprequests;
    }

    @GetMapping("/talentapplicationaffectationsfromappmicroservice/{talentId}/{applicationId}")
    public TalentApplicationDto gettalentaffectationsfromappmicroByEquipeApplicationName(@PathVariable Long talentId, @PathVariable Long applicationId) {
        TalentApplicationDto talentApplicationDto = talentService.gettalentaffectationsfromappmicroByEquipeApplicationName(talentId, applicationId);
        if (talentApplicationDto ==  null) {
            TalentApplicationDto talentApplicationdto = new TalentApplicationDto();
            return  talentApplicationdto;
        }
        return talentApplicationDto;
    }
}
