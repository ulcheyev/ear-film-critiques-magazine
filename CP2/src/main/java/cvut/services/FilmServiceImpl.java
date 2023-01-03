package cvut.services;

import cvut.config.utils.Mapper;
import cvut.exception.NotFoundException;
import cvut.model.Film;
import cvut.model.dto.CritiqueCreationDTO;
import cvut.model.dto.MainRoleDTO;
import cvut.model.dto.creation.FilmCreationDTO;
import cvut.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService{

    private final FilmRepository filmRepository;
    private final MainRoleService mainRoleService;
    private final Mapper mapper;


    public List<Film> findAll(){
        return filmRepository.findAll();
    }

    public void save(@NonNull Film film){
        film
                .getMainRoleList()
                .forEach(mainRole -> mainRoleService.findById(mainRole.getId()));
        filmRepository.save(film);
    }

    public void deleteById(@NonNull Long filmId){
        boolean ex = filmRepository.existsById(filmId);
        if(!ex){
            throw new NotFoundException("Filn with id "+filmId+" does not exist");
        }
        filmRepository.deleteById(filmId);
    }

    public Film findById(@NonNull Long id){
        return filmRepository.findById(id).orElseThrow(
                ()->new NotFoundException("Film with id "+ id+ " does not exist")
        );
    }

    public List<Film> findByDate(@NonNull Date date){
        List<Film> allByDateOfRelease = filmRepository.findAllByDateOfRelease(date);
        if(allByDateOfRelease.isEmpty()){
            throw new NotFoundException("Film with date "+ date + " does not exist");
        }
        return allByDateOfRelease;
    }

    @Transactional
    @Override
    public void update(@NonNull Long filmId, @NonNull FilmCreationDTO filmDto) {
        Film film = findById(filmId);

        String filmName = filmDto.getFilmName();
        if(filmName != null &&
           !film.getName().equals(filmName)
        ){
            film.setName(filmName);
        }
        String desc = filmDto.getFilmDescription();
        if(desc != null &&
        !film.getDescription().equals(desc)){
            film.setDescription(desc);
        }

        List<MainRoleDTO> mainRoles = filmDto.getMainRoles();
        if(mainRoles != null &&
         !film.getMainRoleList().equals(mainRoles)){
            film.setMainRoleList(mapper.toMainRoles(filmDto.getMainRoles()));
        }


    }
}
