package cvut.repository;
import cvut.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    AppUser findUserByUsername(String username);
    List<AppUser> findUsersByLastname(String lastname);
    List<AppUser> findUsersByFirstname(String firstname);
    List<AppUser> findUsersByFirstnameAndLastname(String firstname, String lastname);
}
