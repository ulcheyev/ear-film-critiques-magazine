package cvut.controllers;
import cvut.config.utils.EarUtils;
import cvut.model.dto.CritiqueDTO;
import cvut.model.Critique;
import cvut.repository.CritiqueSearchCriteria;
import cvut.services.CritiqueService;
import cvut.security.validators.NotStringValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "api/critiques")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class CritiqueController {

    private final CritiqueService critiqueService;

    @GetMapping(value = "/{critiqueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Critique getCritiqueById(@NotStringValidator @PathVariable("critiqueId") @NonNull String critiqueId)
    {
        return critiqueService.findById(Long.parseLong(critiqueId));
    }


    @PostMapping(value = "/create", consumes = {   "multipart/form-data" })
    @PreAuthorize("hasAnyRole('ROLE_CRITIC')")
    public ResponseEntity<String> addNewCritique(@RequestPart("critique") @NonNull  @Valid CritiqueDTO dto,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        if(file != null && !file.isEmpty()){
            dto.setText(critiqueService.readPdf(file));
        }

        Critique critique = critiqueService.save(dto);
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/{critiqueId}", critique.getId());
        return ResponseEntity.ok().headers(headers).body("Critique successfully created");
    }


    @PutMapping(value = "/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("(hasRole('ROLE_CRITIC') and @critiqueServiceImpl.checkOwner(#critiqueId, principal.username)) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateCritique(@PathVariable @NotStringValidator String critiqueId, @Valid @NonNull @RequestBody CritiqueDTO critiqueDto)
    {
        critiqueService.updateCritique(Long.parseLong(critiqueId), critiqueDto);
        return ResponseEntity.ok("Critique successfully updated");
    }

    @DeleteMapping(value = "/{critiqueId}")
    @PreAuthorize("(hasRole('ROLE_CRITIC') and @critiqueServiceImpl.checkOwner(#critiqueId, principal.username)) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCritique(@Valid @NonNull @RequestBody @NotStringValidator String critiqueId){
        critiqueService.deleteById(Long.parseLong(critiqueId));
        return ResponseEntity.ok("Critique successfully deleted");
    }

    @GetMapping(value = "search/",  consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Critique> searchCritiqueByFilter(@NonNull CritiqueSearchCriteria critiqueSearchCriteria){
        return critiqueService.findByCriteria(critiqueSearchCriteria);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Critique> fetchAll(){
        return critiqueService.findAll();
    }



}
