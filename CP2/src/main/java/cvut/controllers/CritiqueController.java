package cvut.controllers;
import cvut.model.Critique;
import cvut.repository.CritiqueSearchCriteria;
import cvut.services.CritiqueService;
import cvut.services.security.validators.NotStringValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "api/critiques")
@Validated
@RequiredArgsConstructor
public class CritiqueController {

    private final CritiqueService service;

    @GetMapping(value = "/critic/{ownerId}")
    @ResponseBody
    public List<Critique> getCritiquesByOwnerId(@NotStringValidator @PathVariable("ownerId") @NonNull String ownerId)
    {
            return service.findByCritiqueOwnerId(Long.parseLong(ownerId));
    }

    @GetMapping(value = "/{critiqueId}")
    @ResponseBody
    public Critique getCritiqueById(@NotStringValidator @PathVariable("critiqueId") @NonNull String critiqueId)
    {
        return service.findById(Long.parseLong(critiqueId));
    }

    //TODO toklo autorizovaniy
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewCritique(@Valid @NonNull @RequestBody Critique critique){
        service.save(critique);
        return ResponseEntity.ok("Critique successfully added");
    }


    //TODO tolko svoi.
    @PutMapping(value = "/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCritique(@PathVariable @NotStringValidator String critiqueId, @Valid @NonNull @RequestBody Critique critique)
    {
        service.updateCritique(Long.parseLong(critiqueId), critique);
        return ResponseEntity.ok("Critique successfully updated");
    }


    //TODO tolko svoi. Esli admin - to mozhno lubuyu.
    @DeleteMapping(value = "/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCritique(@Valid @NonNull @RequestBody @NotStringValidator String critiqueId){
        service.deleteById(Long.parseLong(critiqueId));
        return ResponseEntity.ok("Critique successfully deleted");
    }

    @GetMapping("search/")
    @ResponseBody
    public List<Critique> searchCritiqueByFilter(@NonNull CritiqueSearchCriteria critiqueSearchCriteria){
        return service.findByCriteria(critiqueSearchCriteria);
    }

    @GetMapping
    @ResponseBody
    public List<Critique> fetchAll(){
        return service.findAll();
    }



}
