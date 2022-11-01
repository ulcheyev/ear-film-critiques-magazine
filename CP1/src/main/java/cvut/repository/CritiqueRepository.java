package cvut.repository;

import cvut.model.Critique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {}
