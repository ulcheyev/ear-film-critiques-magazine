package cvut.config.utils;

import cvut.model.*;
import cvut.model.dto.FilmDTO;
import cvut.model.dto.creation.FilmCreationDTO;
import cvut.model.dto.MainRoleDTO;
import cvut.model.dto.creation.MainRoleCreationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public Film toFilm(FilmCreationDTO filmCreationDTO) {
        List<MainRole> mainRoleList = new ArrayList<>();

        for(MainRoleDTO dto: filmCreationDTO.getMainRoles()){
            MainRole mainRole = new MainRole();
            mainRole.setId(dto.getId());
            mainRoleList.add(mainRole);
        }

        return new Film(mainRoleList, filmCreationDTO.getFilmName(),
                filmCreationDTO.getDateOfRelease(), filmCreationDTO.getFilmDescription());
    }

    public List<MainRole> toMainRoles(List<MainRoleDTO> mainRoleDTOS){
        List<MainRole> res = new ArrayList<>();
        for(MainRoleDTO dto: mainRoleDTOS){
            MainRole mainRole = new MainRole();
            mainRole.setId(dto.getId());
            res.add(mainRole);
        }
        return res;
    }

    public MainRole toMainRole(MainRoleCreationDTO mainRole){
        MainRole res = new MainRole(
                FilmRole.fromDBName(mainRole.getFilmRole()),
                mainRole.getFirstname(), mainRole.getLastname(),
                toFilms(mainRole.getFilms()));
        return res;
    }

    public Critic toCritic(AppUser appUser){
        return new Critic(appUser.getFirstname(),
                appUser.getLastname(),
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.toString());
    }

    public List<Film> toFilms(List<FilmDTO> films){
        List<Film> filmList = new ArrayList<>();
        for(FilmDTO filmDTO: films){
            Film film = new Film();
            film.setId(filmDTO.getId());
            filmList.add(film);
        }
        return filmList;
    }

}
