package cvut.controllers;
import cvut.model.Critique;
import cvut.model.CritiqueSearchCriteria;
import cvut.services.CritiqueService;
import cvut.services.security.validators.NotStringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "api/critiques")
@Validated
public class CritiqueController {

    private final CritiqueService service;

    @Autowired
    public CritiqueController(CritiqueService service) {
        this.service = service;
    }


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
    public void addNewCritique(@Valid @NonNull @RequestBody Critique critique){
        service.save(critique);
    }


    //TODO tolko svoi.
    @PutMapping(value = "/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCritique(@PathVariable @NotStringValidator String critiqueId, @Valid @NonNull @RequestBody Critique critique)
    {
        service.updateCritique(Long.parseLong(critiqueId), critique);
    }


    //TODO tolko svoi. Esli admin - to mozhno lubuyu.
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCritique(@Valid @NonNull @RequestBody Critique critique){
        service.save(critique);
    }

    @GetMapping
    @ResponseBody
    public List<Critique> searchCritiqueByFilter(@NonNull CritiqueSearchCriteria critiqueSearchCriteria){
        return service.getAll(critiqueSearchCriteria);
    }



}
