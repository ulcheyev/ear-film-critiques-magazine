package cvut.repository;
import cvut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findUserByUsername(String username);
    List<User> findUsersByLastname(String lastname);
    List<User> findUsersByFirstname(String firstname);
    List<User> findUsersByFirstnameAndLastname(String firstname, String lastname);
}
