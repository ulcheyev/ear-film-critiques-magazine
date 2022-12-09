package cvut.repository;

import cvut.model.AppUser;
import cvut.model.Comment;
import cvut.model.Critic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAppUser_Id(Long id);

    List<Comment> findAllByDateOfPublic(Date date);

    List<Comment> findAllByCritique_IdOrderByDateOfPublicDesc(Long id);

    List<Comment> findAllByCritique_IdOrderByDateOfPublicAsc(Long id);

   List<Comment> findAllByCritique_Id(Long id);
}
