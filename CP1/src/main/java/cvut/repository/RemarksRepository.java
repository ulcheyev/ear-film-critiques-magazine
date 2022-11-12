package cvut.repository;
import cvut.model.Remarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RemarksRepository extends JpaRepository<Remarks, Long> {

    Optional<List<Remarks>> findAllByAdmin_Id(Long id);

    Optional<List<Remarks>> findAllByRemarksMakeDay(Date date);

}
