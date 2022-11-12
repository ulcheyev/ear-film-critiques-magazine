package cvut.repository;

import cvut.model.AppUser;
import cvut.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByAppUser_Id(Long id);

    Optional<List<Comment>> findAllByDateOfPublic(Date date);
    Optional<List<Comment>> findAllByCritique_Id(Long id);
}
