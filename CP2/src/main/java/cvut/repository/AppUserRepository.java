package cvut.repository;
import cvut.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@NamedQueries({
        @NamedQuery(name = "AppUserRepository.findByUsername", query = "SELECT appU FROM AppUser appU WHERE appU.username = :username"),
        @NamedQuery(name = "AppUserRepository.findAppUserByEmail", query = "SELECT appU FROM AppUser appU WHERE appU.email = :email"),
        @NamedQuery(name = "AppUserRepository.findAppUsersByLastname", query = "SELECT appU FROM AppUser appU WHERE appU.lastname = :lastname"),
        @NamedQuery(name = "AppUserRepository.findAppUsersByFirstname", query = "SELECT appU FROM AppUser appU WHERE appU.firstname = :firstname"),
        @NamedQuery(name = "AppUserRepository.findAppUsersByFirstnameAndLastname", query = "SELECT appU FROM AppUser appU WHERE appU.firstname = :firstname AND appU.lastname = :lastname")
})
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findAppUserByUsername(String username);

    Optional<AppUser> findAppUserByEmail(String email);

    List<AppUser> findAppUsersByLastname(String lastname);

    List<AppUser> findAppUsersByFirstname(String firstname);

    List<AppUser> findAppUsersByFirstnameAndLastname(String firstname, String lastname);
}
