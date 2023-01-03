package cvut.services;

import cvut.model.FilmRole;
import cvut.model.MainRole;
import cvut.model.dto.creation.FilmCreationDTO;
import cvut.model.dto.creation.MainRoleCreationDTO;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MainRoleService {
    List<MainRole> findAll();
    void save(MainRole mainRole);
    void deleteById(Long mainRoleId);
    MainRole findById(Long id);
    List<MainRole> findByFilmRole(FilmRole filmRole);
    List<MainRole> findAllByCriteria(MainRoleCreationDTO criteria);
    void update(Long mainRoleId, MainRoleCreationDTO mainRoleCreationDTO);
}
