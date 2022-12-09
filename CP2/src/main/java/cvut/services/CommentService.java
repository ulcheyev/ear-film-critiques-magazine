package cvut.services;

import cvut.model.Comment;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

public interface CommentService {
    void save(String text, Long appUserId, Long critiqueId);
    Comment findById(Long commentId);
    List<Comment> findAll();
    List<Comment> findCommentsByCritique_Id(Long id);
    List<Comment> findCommentsByCritique_IdOrderDateDesc(Long id);
    List<Comment> findCommentsByCritique_IdOrderDateAsc(Long id);
    List<Comment> findCommentsByDate(Date date);
    void deleteComment(Long commentId);
    void update(Long commentId, String text);

}
