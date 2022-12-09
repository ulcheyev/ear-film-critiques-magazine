package cvut.repository;
import cvut.model.Admin;
import cvut.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findAdminWithSpecifiedCritiqueQuantity(int quantity);

}
