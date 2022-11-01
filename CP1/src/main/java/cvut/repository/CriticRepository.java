package cvut.repository;

import cvut.model.Critic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {}
