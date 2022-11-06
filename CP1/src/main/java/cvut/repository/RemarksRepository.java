package cvut.repository;
import cvut.model.Remarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RemarksRepository extends JpaRepository<Remarks, Long> {
    List<Remarks>  findAllByAdmin_Id(Long id);
    List<Remarks> findAllByRemarksMakeDay(Date date);
    List<Remarks> findAllByRemarksMakeDayAfter(Date date);
    List<Remarks> findAllByRemarksMakeDayBefore(Date date);
    List<Remarks> findAllByRemarksMakeDayBetween(Date date1, Date date2);
}
