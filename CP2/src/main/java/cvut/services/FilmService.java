package cvut.services;

import cvut.model.Film;
import java.util.Date;
import java.util.List;

public interface FilmService {
    List<Film> findAll();
    void save(Film film);
    Film findById(Long id);
    void deleteById(Long filmId);
    List<Film> findByDate(Date date);
}
