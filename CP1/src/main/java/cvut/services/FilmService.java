package cvut.services;

import cvut.exception.NotFoundException;
import cvut.model.Film;
import cvut.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAll(){
        return filmRepository.findAll();
    }

    public void save(@NonNull Film film){
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
}
