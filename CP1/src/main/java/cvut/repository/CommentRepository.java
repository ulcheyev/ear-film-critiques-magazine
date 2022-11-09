package cvut.repository;

import cvut.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAppUser_Id(Long id);
    List<Comment> findAllByDateOfPublic(Date date);
    List<Comment> findAllByDateOfPublicAfter(Date date);
    List<Comment> findAllByDateOfPublicBefore(Date date);
    List<Comment> findAllByDateOfPublicBetween(Date date1, Date date2);
}
