package cvut.controllers;


import cvut.config.security_utils.AuthenticationFacade;
import cvut.config.utils.EarUtils;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.Remarks;
import cvut.model.dto.creation.RemarksCreationDTO;
import cvut.security.validators.NotStringValidator;
import cvut.services.CritiqueService;
import cvut.services.RemarksService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/system")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class RemarksController {

    private final CritiqueService critiqueService;
    private final RemarksService remarksService;

    @PostMapping(value = "/critiques/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRemarks(@PathVariable @NonNull Long critiqueId,
                                                @NonNull @RequestBody RemarksCreationDTO request){
        remarksService.makeRemarksAndSave(
                request.getContent(),
                critiqueId,
                AuthenticationFacade.getAuthentication().getName()
        );
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/remarks");
        return ResponseEntity.ok().headers(headers).body("Remarks successfully created");
    }

    @GetMapping(value = "/critiques/{critiqueId}")
    @PostFilter("filterObject.admin.username == principal.username")
    public List<Remarks> getRemarksByCritiqueId(@PathVariable @NonNull Long critiqueId){
        return remarksService.findAllByCritiqueId(critiqueId);
    }


    @PutMapping(value = "/remarks/{remarksId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@remarksServiceImpl.checkOwner(#remarksId, principal.username)")
    public ResponseEntity<String> updateRemarks(@PathVariable @NonNull Long remarksId,
                                                @RequestBody RemarksCreationDTO request){
        remarksService.update(request.getContent(), remarksId);
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/remarks/{remarksId}", remarksId);
        return ResponseEntity.ok().headers(headers).body("Remarks successfully updated");
    }

    @DeleteMapping(value = "/remarks/{remarksId}")
    @PreAuthorize("@remarksServiceImpl.checkOwner(#remarksId, principal.username)")
    public ResponseEntity<String> deleteRemarks(@PathVariable @NotStringValidator String remarksId){
        remarksService.deleteById(Long.parseLong(remarksId));
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/remarks");
        return ResponseEntity.ok().headers(headers).body("Remarks successfully deleted");
    }

    @GetMapping(value = "/remarks", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostFilter("filterObject.admin.username.equals(principal.username)")
    public List<Remarks> fetchAll(){
        return remarksService.findAll();
    }

    @GetMapping(value = "/critiques", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Critique> showAllForProcessing(){
        return critiqueService.findAllToProcess();
    }

    @GetMapping(value = "/remarks/{remarksId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Remarks findById(@PathVariable @NotStringValidator String remarksId){
        return remarksService.findById(Long.parseLong(remarksId));
    }

    @GetMapping(value = "/remarks", produces = MediaType.APPLICATION_JSON_VALUE, params = "crq")
    public List<Remarks> findByCritiqueId(@RequestParam(value = "crq") Long critiqueId){
        return remarksService.findAllByCritiqueId(critiqueId);
    }

    @GetMapping(value = "/critiques/{critiqueId}", params = "state")
    public ResponseEntity<String> changeCritiqueState(@PathVariable String critiqueId,
                                                        @RequestParam("state") String state)
    {
        CritiqueState convert =
                (CritiqueState)
                new EarUtils.EnumConverter<>(CritiqueState.class).convert(state);

        critiqueService.updateCritiqueState(Long.valueOf(critiqueId), convert);
        return ResponseEntity.ok().body("Successfully changed to " + convert.name());
    }







}
