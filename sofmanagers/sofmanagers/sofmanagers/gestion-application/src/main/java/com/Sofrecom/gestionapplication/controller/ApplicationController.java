package com.Sofrecom.gestionapplication.controller;

import com.Sofrecom.gestionapplication.dtoapp.*;
import com.Sofrecom.gestionapplication.dtot.EquipeDto;
import com.Sofrecom.gestionapplication.dtot.TalentDto;
import com.Sofrecom.gestionapplication.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationServices applicationServices;
    private final EtatServices etatServices;
    private final TalentApplicationServices talentApplicationServices;
    private final EquipeApplicationServices equipeApplicationServices;

    private final DASServices dasServices;
    private final RDServices rdServices;

    private final DomaineServices domaineServices;

    @PostMapping("/addapp")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addapp(@RequestBody AppRequest appRequest) {
        Long newAppId = applicationServices.addApp(appRequest);
        String responseMessage = String.valueOf(newAppId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @PutMapping("/update/{appId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> update(@PathVariable Long appId, @RequestBody AppRequest appRequest) {
        Long updatedAppId = applicationServices.updateApp(appId, appRequest);
        String responseMessage = String.valueOf(updatedAppId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{appId}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long appId) {

        applicationServices.deleteApplication(appId);
        return "The application and its related information have been deleted successfully";

    }

    @GetMapping("/all")
    public ResponseEntity<List<AppRequest>> getAllApplications() {
        List<AppRequest> appRequests = applicationServices.getAllApplications();
        if (appRequests.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appRequests);
    }
    @GetMapping("/allhistory")
    public ResponseEntity<List<AppRequest>> getAllApplicationsHistory() {
        List<AppRequest> appRequests = applicationServices.getAllApplicationsHistory();
        if (appRequests.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appRequests);
    }
    @GetMapping("/alldomaines")
    public ResponseEntity<List<DomaineDto>> getAlldomaines() {
        List<DomaineDto> domaineDtos = domaineServices.getAlldomaines();
        if (domaineDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(domaineDtos);
    }


    @GetMapping("/getallinfo/{id}")
    public ResponseEntity<AppRequest> getApplicationById(@PathVariable Long id) {
        AppRequest appRequest = applicationServices.getApplicationWithDetails(id);
        if (appRequest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appRequest);
    }

    @PutMapping("/Terminate/{appId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateApplicationtoterminateetat(@PathVariable Long appId, @RequestBody AppRequest appRequest) {
        applicationServices.TerminateAppStatus(appId, appRequest);
    }


    @PostMapping("/add/etat")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addetat(@RequestBody EtatApplicationDto etatApplicationDto) {
        Long newAppId = etatServices.addetat(etatApplicationDto);
        String responseMessage = String.valueOf(newAppId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }



    @PutMapping("/update/etat/{etatId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateetat(@PathVariable Long etatId, @RequestBody EtatApplicationDtoForUpdate etatApplicationDto) {
        etatServices.updateetat(etatId, etatApplicationDto);
    }

    @DeleteMapping("/delete/etat/{etatId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteetat(@PathVariable Long etatId) {
        etatServices.deleteEtat(etatId);
    }

    @PutMapping("affect/{etatId}/toapplications")
    @ResponseStatus(HttpStatus.CREATED)
    public String affectEtatToApplications(@PathVariable Long etatId, @RequestBody ApplicationIdDto applicationDto) {
        etatServices.affectEtatToApplications(etatId, applicationDto);
        return "The etat" + etatId + "is affected successfully to the " + applicationDto + "application";
    }


    @DeleteMapping("/delete/talent/{talentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deletetalent(@PathVariable Long talentId) {

        talentApplicationServices.deleteTalentApplication(talentId);
        return "The Talent does no longer exist";
    }

    @PutMapping("affecttalent/{talentId}/toapplications")
    @ResponseStatus(HttpStatus.CREATED)
    public String affectTalentToApplications(@PathVariable Long talentId, @RequestBody ApplicationIdDto applicationDto) {
        talentApplicationServices.affectTalentToApplications(talentId, applicationDto);
        return "The talent" + talentId + "is affected successfully to the " + applicationDto + "application";
    }

    @PutMapping("/update/talentonapp/{talentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void>  updatetalentonapp(@PathVariable Long talentId, @RequestBody TalentApplicationDtoForUpdate talentApplicationDto) {
        talentApplicationServices.updatetalentonapp(talentId, talentApplicationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/adddomaine")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> adddomaine(@RequestBody DomaineDto domaineDto) {
        Long newdomaineId = domaineServices.addDomaine(domaineDto);
        String responseMessage = String.valueOf(newdomaineId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @PutMapping("/assign-domain-to-app/{applicationid}/{domainenom}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> assignDomainToApp(@PathVariable("applicationid") Long applicationid, @PathVariable("domainenom") String domainenom) {
        domaineServices.affecttoapp(applicationid, domainenom);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-etat-and-affect-to-app/{applicationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createAndAffectEtatToApplicationController(@PathVariable Long applicationId, @RequestBody EtatApplicationDto etatDTO) {
        etatServices.createAndAffectEtatToApplication(applicationId, etatDTO);
            return ResponseEntity.ok().build();

    }

    @GetMapping("/getallinfocurrentetat/{id}")
    public ResponseEntity<EtatApplicationDto> getApplicationEtatById(@PathVariable Long id) {
        EtatApplicationDto etatApplicationDto = etatServices.getApplicationEtatWithDetails(id);
        if (etatApplicationDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(etatApplicationDto);
    }

    @GetMapping("/currentetats")
    public ResponseEntity<List<EtatApplicationDto>> getAllCurrentEtatApplications() {
        List<EtatApplicationDto> etatApplicationDtos = etatServices.getAllCurrentEtatApplications();
        if (etatApplicationDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(etatApplicationDtos);
    }

    @GetMapping("/calculateNiveauMaitrisePercentage/{niveauMaitriseLevel}")
    public ResponseEntity<Float> calculateNiveauMaitrisePercentage(@PathVariable String niveauMaitriseLevel) {
        float percentage = talentApplicationServices.calculateNiveauMaitrisePercentage(niveauMaitriseLevel);
        return ResponseEntity.ok(percentage);
    }

    @GetMapping("/getalletatinfo/{id}")
    public ResponseEntity<EtatApplicationDto> getEtatById(@PathVariable Long id) {
        EtatApplicationDto etatApplicationDto = etatServices.getEtatApplicationById(id);
        if (etatApplicationDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(etatApplicationDto);
    }

    @DeleteMapping("/unliktalent/{talentid}/{appId}")
    public void unlinkTalentFromApplication(@PathVariable Long talentid,@PathVariable Long appId) {
        talentApplicationServices.unlinkTalentFromApplication(talentid, appId);
    }

    @GetMapping("/alltalentsaffected")
    public ResponseEntity<List<TalentApplicationDto>> getAllTalentaffecteds() {
        List<TalentApplicationDto> talentApplicationDtos = talentApplicationServices.getAllTalents();
        if (talentApplicationDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentApplicationDtos);
    }

    @PostMapping("/affectnewtalent")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addTalentApp(@RequestBody List<TalentApplicationDto> talentApplicationDtoList) {
        Long newTalentId = talentApplicationServices.addTalentApp(talentApplicationDtoList);
        String responseMessage = String.valueOf(newTalentId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/allequipesfromtalentmicroservice")
    public ResponseEntity<List<EquipeDto>> getAllEquipesinfofromTalentMicroservice() {
        List<EquipeDto> equipeDtos = equipeApplicationServices.retrieveEquipesFromFirstMicroservice();
        if (equipeDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipeDtos);
    }
    @GetMapping("/getEquipefromothermicro/{equipeName}")
    public EquipeDto getEquipeByNamefromtalentmicroservice(@PathVariable String equipeName) {
        return equipeApplicationServices.getEquipeByNamefromtalentmicroservice(equipeName);
    }

    @GetMapping("/alltalentsfromtalentmicroservice")
    public ResponseEntity<List<TalentDto>> getAllTalentsinfofromTalentMicroservice() {
        List<TalentDto> talentDto = equipeApplicationServices.getallTalentsfromtalentmicroservice();
        if (talentDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentDto);
    }


    @GetMapping("/applications/{equipeApplicationName}")
    public List<AppRequest> getApplicationsByEquipeApplicationName(@PathVariable String equipeApplicationName) {
        List<AppRequest> apprequests = applicationServices.getApplicationsByEquipeApplicationName(equipeApplicationName);
        if (apprequests.isEmpty()) {
            return null;
        }
        return apprequests;
    }

    @GetMapping("/findtalentaffectationforgroupes/{talentId}/{applicationId}")
    public TalentApplicationDto getTalentsAffectationBytalentandapplicationId(@PathVariable Long talentId, @PathVariable Long applicationId) {
        TalentApplicationDto talentAppDto = talentApplicationServices.findTalentApplication(talentId, applicationId);
        if (talentAppDto ==  null) {
            TalentApplicationDto talentApplicationdto = new TalentApplicationDto();
            return  talentApplicationdto;
        }
        return talentAppDto;
    }


    @PostMapping("/adddas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> adddas(@RequestBody DasDto dasDto) {
        Long newId = dasServices.addDas(dasDto);
        String responseMessage = String.valueOf(newId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/allrdsnotassignedtodas")
    public ResponseEntity<List<RDDto>> getAllRdsnotassignedtodas() {
        List<RDDto> rdDtos = rdServices.getAllRdsnotassignedtodas();
        return ResponseEntity.ok(rdDtos);
    }

    @PostMapping("/addrd")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addrd(@RequestBody RDDto rdDto) {
        Long newId = rdServices.addRD(rdDto);
        String responseMessage = String.valueOf(newId);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/alldases")
    public ResponseEntity<List<DasDto>> getAllDases() {
        List<DasDto> dasDtos = dasServices.getAllDases();
        return ResponseEntity.ok(dasDtos);
    }

    @GetMapping("/gettalentaffectioninfo/{talentcode}")
    public ResponseEntity<List<TalentApplicationDto>> gettalentApplicationBytalentCode(@PathVariable Long talentcode) {
        List<TalentApplicationDto> talentApplicationDtos = talentApplicationServices.getAlltalentApplicationsByTalentId(talentcode);
        if (talentApplicationDtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talentApplicationDtos);
    }

    @GetMapping("/affectedTalentsonTheApplication/{applicationId}")
    public ResponseEntity<List<TalentApplicationDto>> getTalentApplications(@PathVariable Long applicationId) {
        List<TalentApplicationDto> talentApplications = talentApplicationServices.findTalentApplication(applicationId);
        if (talentApplications != null) {
            return ResponseEntity.ok(talentApplications);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
