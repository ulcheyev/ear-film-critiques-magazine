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

    List<AppUser> findAppUsersByLastname(String lastname);

    List<AppUser> findAppUsersByFirstname(String firstname);

    List<AppUser> findAppUsersByFirstnameAndLastname(String firstname, String lastname);

}
