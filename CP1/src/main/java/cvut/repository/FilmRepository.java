package cvut.repository;

import cvut.model.Critique;
import cvut.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<List<Film>> findAllByDateOfRelease(Date date);

    Optional<List<Film>> findAllByDateOfReleaseAfter(Date date);

    Optional<List<Film>> findAllByDateOfReleaseBefore(Date date);

    Optional<List<Film>> findAllByDateOfReleaseBetween(Date date1, Date date2);
}
