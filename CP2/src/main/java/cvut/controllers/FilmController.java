package cvut.controllers;

import cvut.config.utils.EarUtils;
import cvut.config.utils.Mapper;
import cvut.model.Film;
import cvut.model.MainRole;
import cvut.model.dto.creation.FilmCreationDTO;
import cvut.model.dto.creation.MainRoleCreationDTO;
import cvut.services.FilmService;
import cvut.services.MainRoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping(path = "api/system/films")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class FilmController {

    private final FilmService filmService;
    private final MainRoleService mainRoleService;
    private final Mapper mapper;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFilm(@NonNull @RequestBody FilmCreationDTO filmCreationDTO) {
        Film film = mapper.toFilm(filmCreationDTO);
        filmService.save(film);
        return ResponseEntity.ok().body("Film successfully added");
    }

    @DeleteMapping(value = "/{filmId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteFilm(@NonNull @PathVariable Long filmId){
        filmService.deleteById(filmId);
    }

    @PutMapping(value = "/{filmId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String>  updateFilm(@NonNull @RequestBody FilmCreationDTO filmCreationDTO,
                           @PathVariable Long filmId){
        filmService.update(filmId, filmCreationDTO);
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/{critiqueId}", filmId);
        return ResponseEntity.ok().headers(headers).body("Film successfully deleted");

    }

    @GetMapping(value = "/{filmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Film getFilmById(@NonNull @PathVariable Long filmId){
        return filmService.findById(filmId);
    }

    @GetMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MainRole> getMainRoles(){
        return mainRoleService.findAll();
    }

    @GetMapping(value = "/add/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MainRole> getMainRoles(@NonNull MainRoleCreationDTO criteria){
        return mainRoleService.findAllByCriteria(criteria);
    }

    @PostMapping(value = "/main_roles/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addMainRole(@NonNull @RequestBody
                                                          MainRoleCreationDTO mainRoleCreationDTO) {
        MainRole mainRole = mapper.toMainRole(mainRoleCreationDTO);
        mainRoleService.save(mainRole);
        return ResponseEntity.ok().body("Main role successfully added");
    }

    @GetMapping(value = "/main_roles/{mainRoleId}")
    public ResponseEntity<MainRole> getMainRoleById(@NonNull @PathVariable Long mainRoleId) {
        MainRole byId = mainRoleService.findById(mainRoleId);
        return ResponseEntity.ok().body(byId);
    }

    @PutMapping(value = "/main_roles/{mainRoleId}")
    public ResponseEntity<String> updateMainRole(@NonNull @PathVariable Long mainRoleId,
                                                 @NonNull @RequestBody MainRoleCreationDTO mainRoleCreationDTO) {
        mainRoleService.update(mainRoleId, mainRoleCreationDTO);
        return ResponseEntity.ok().body("Main role successfully updated");
    }








}
