//package cvut.controllers;
//import cvut.config.utils.EarUtils;
//import cvut.model.Critique;
//import cvut.model.dto.CritiqueCreationDTO;
//import cvut.security.validators.NotStringValidator;
//import cvut.services.CriticService;
//import cvut.services.CritiqueService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.NonNull;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.io.IOException;
//
//@RestController
//@RequestMapping(path = "api/profile")
//@Validated
//@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('ROLE_CRITIC')")
//public class CriticController {
//
//    private final CriticService criticService;
//    private final CritiqueService critiqueService;
//
//    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, "multipart/form-data"})
//    @PreAuthorize("hasAnyRole('ROLE_CRITIC')")
//    public ResponseEntity<String> addNewCritique(@RequestPart("critique") @NonNull @Valid CritiqueCreationDTO dto,
//                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
//
//        if(file != null && !file.isEmpty()){
//            dto.setText(critiqueService.readPdf(file));
//        }
//
//        Critique critique = critiqueService.save(dto);
//        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/{critiqueId}", critique.getId());
//        return ResponseEntity.ok().headers(headers).body("Critique successfully created");
//    }
//
//    @PutMapping(value = "/{critiqueId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("(hasRole('ROLE_CRITIC') and @critiqueServiceImpl.checkOwner(#critiqueId, principal.username)) or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<String> updateCritique(@PathVariable @NotStringValidator String critiqueId,
//                                                 @Valid @NonNull @RequestBody CritiqueCreationDTO critiqueCreationDTO)
//    {
//        critiqueService.updateCritique(Long.parseLong(critiqueId), critiqueCreationDTO);
//        return ResponseEntity.ok("Critique successfully updated");
//    }
//
//    @DeleteMapping(value = "/{critiqueId}")
//    @PreAuthorize("(hasRole('ROLE_CRITIC') and @critiqueServiceImpl.checkOwner(#critiqueId, principal.username)) or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<String> deleteCritique(@Valid @NonNull @PathVariable @NotStringValidator String critiqueId)
//    {
//        critiqueService.deleteById(Long.parseLong(critiqueId));
//        return ResponseEntity.ok("Critique successfully deleted");
//    }
//
//}
