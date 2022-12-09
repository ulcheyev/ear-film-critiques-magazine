package cvut.services;

import cvut.model.FilmRole;
import cvut.model.MainRole;
import java.util.List;

public interface MainRoleService {
    List<MainRole> findAll();
    void save(MainRole mainRole);
    void deleteById(Long mainRoleId);
    MainRole findById(Long id);
    List<MainRole> findByFilmRole(FilmRole filmRole);
}
