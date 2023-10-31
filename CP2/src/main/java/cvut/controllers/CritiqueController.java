package cvut.controllers;

import cvut.config.utils.EarUtils;
import cvut.model.Critique;
import cvut.model.dto.CritiqueDTO;
import cvut.security.validators.NotStringValidator;
import cvut.services.CritiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "api/critiques")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class CritiqueController {

    private final CritiqueService critiqueService;

    @GetMapping(value = "/{critiqueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("(hasRole('ROLE_USER') " +
            "and @critiqueServiceImpl.isAccepted(#critiqueId))" +
            "or hasRole('ROLE_CRITIC') and @critiqueServiceImpl.isAcceptedOrInProcessed(#critiqueId) " +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Critique> getCritiqueById(@NotStringValidator @PathVariable("critiqueId") @NonNull String critiqueId) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/{critiqueId}", critiqueId);
        Critique byId = critiqueService.findById(Long.parseLong(critiqueId));
        return ResponseEntity.ok().headers(headers).body(byId);

    }

    @GetMapping(value = "/search/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Critique> searchCritiqueByFilter(@NonNull CritiqueDTO critiqueDTO) {
        return critiqueService.findByCriteria(critiqueDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PostFilter("hasRole('ROLE_ADMIN') " +
            "or (hasRole('ROLE_CRITIC') and @critiqueServiceImpl.isAcceptedOrInProcessed(filterObject.id)) " +
            "or @critiqueServiceImpl.isAccepted(filterObject.id)")
    public List<Critique> fetchAll() {
        return critiqueService.findAll();
    }


}
