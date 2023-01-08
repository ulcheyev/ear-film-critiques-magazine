package cvut.repository;

import cvut.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByDateOfRelease(Date date);

    List<Film> findMovieNamesForASpecificPeriod(Date from, Date to);

    List<Film> findMovieNamesCriticizedByAParticularCritic(Long id);

}
