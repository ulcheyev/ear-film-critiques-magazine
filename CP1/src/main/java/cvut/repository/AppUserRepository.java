package cvut.repository;
import cvut.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findAppUserByUsername(String username);

    Optional<AppUser> findAppUserByEmail(String email);

    Optional<List<AppUser>> findAppUsersByLastname(String lastname);

    Optional<List<AppUser>> findAppUsersByFirstname(String firstname);

    Optional<List<AppUser>> findAppUsersByFirstnameAndLastname(String firstname, String lastname);
}
