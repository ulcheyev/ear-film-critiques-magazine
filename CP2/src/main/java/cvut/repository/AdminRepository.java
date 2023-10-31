package cvut.repository;

import cvut.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findAdminWithSpecifiedCritiqueQuantity(int quantity);

    Optional<Admin> findByUsername(String username);
}
