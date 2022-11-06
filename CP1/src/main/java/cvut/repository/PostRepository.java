package cvut.repository;

import cvut.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByDateOfPublic(Date date);
    List<Post> findAllByDateOfPublicAfter(Date date);
    List<Post> findAllByDateOfPublicBefore(Date date);
    List<Post> findAllByDateOfPublicBetween(Date date1, Date date2);
    List<Post> findAllByCritique_Id(Long id);
}
