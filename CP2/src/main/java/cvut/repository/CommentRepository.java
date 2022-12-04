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
@NamedQueries({
        @NamedQuery(name = "CommentRepository.findByCritiqueId", query = "SELECT comm FROM Comment comm WHERE comm.id = ?1 AND comm.critique.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CommentRepository.findByCritiqueId", query = "SELECT comm FROM Comment comm WHERE comm.critique.id = ?1 AND comm.critique.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CommentRepository.findAllByAppUser_Id", query = "SELECT comm FROM Comment comm WHERE comm.appUser.id = ?1 AND comm.critique.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CommentRepository.findAllByDateOfPublic", query = "SELECT comm FROM Comment comm WHERE comm.dateOfPublic = ?1 AND comm.critique.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CommentRepository.findAllByCritique_IdOrderByDateOfPublicDesc", query = "SELECT comm FROM Comment comm WHERE comm.dateOfPublic = ?1 AND comm.critique.critiqueState = cvut.model.CritiqueState.ACCEPTED ORDER BY comm.dateOfPublic DESC"),
        @NamedQuery(name = "CommentRepository.findAllByCritique_IdOrderByDateOfPublicAs", query = "SELECT comm FROM Comment comm WHERE comm.dateOfPublic = ?1 AND comm.critique.critiqueState = cvut.model.CritiqueState.ACCEPTED ORDER BY comm.dateOfPublic ASC")
})
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAppUser_Id(Long id);

    List<Comment> findAllByDateOfPublic(Date date);

    List<Comment> findAllByCritique_IdOrderByDateOfPublicDesc(Long id);

    List<Comment> findAllByCritique_IdOrderByDateOfPublicAsc(Long id);

   List<Comment> findAllByCritique_Id(Long id);
}
