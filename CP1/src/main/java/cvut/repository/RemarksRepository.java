package cvut.repository;
import cvut.model.Remarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemarksRepository extends JpaRepository<Remarks, Long> {}
