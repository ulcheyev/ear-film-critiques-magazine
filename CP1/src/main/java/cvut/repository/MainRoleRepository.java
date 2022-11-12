package cvut.repository;

import cvut.model.Film;
import cvut.model.FilmRole;
import cvut.model.MainRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MainRoleRepository extends JpaRepository<MainRole, Long> {
    Optional<List<MainRole>> findAllByFilmRole(FilmRole filmRole);
}
