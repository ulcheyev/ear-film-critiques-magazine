package cvut.services;

import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Comment;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.repository.AppUserRepository;
import cvut.repository.CommentRepository;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;
    private final CritiqueRepository critiqueRepository;
    private final CriticRepository criticRepository;

    private final static int COMMENT_MAX_LENGTH = 250;
    private final static int COMMENT_MIN_LENGTH = 1;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, AppUserRepository appUserRepository, CritiqueRepository critiqueRepository, CriticRepository criticRepository) {
        this.commentRepository = commentRepository;
        this.appUserRepository = appUserRepository;
        this.critiqueRepository = critiqueRepository;
        this.criticRepository = criticRepository;
    }

    public Comment save(@NonNull String text, @NonNull String username, @NonNull Long critiqueId) {

        if (text.length() > COMMENT_MAX_LENGTH || text.length() < COMMENT_MIN_LENGTH) {
            throw new ValidationException("Comment length must be max " + COMMENT_MAX_LENGTH + " and min " + COMMENT_MIN_LENGTH + " symbols");
        }

        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username " + username + " does not found"));

        Critique critique = critiqueRepository.findById(critiqueId)
                .orElseThrow(() -> new NotFoundException("Critique with id " + critiqueId + " does not found"));

        Comment comment = new Comment(text, new Date(), appUser, critique);

        if (critique.getCritiqueState() == CritiqueState.IN_PROCESSED) {

            if (criticRepository.findByUsername(username).isPresent()) {
                commentRepository.save(comment);
            } else {
                throw new ValidationException("You do not have permission");
            }
        } else {
            commentRepository.save(comment);
        }

        return comment;
    }

    public List<Comment> findCommentsByCritique_Id(@NonNull Long id) {
        List<Comment> allByCritique_id = commentRepository.findAllByCritique_Id(id);
        if (allByCritique_id.isEmpty()) {
            throw new NotFoundException("Critique with id " + id + " does not have comments");
        }
        return allByCritique_id;
    }

    public List<Comment> findCommentsByCritique_IdOrderDateDesc(@NonNull Long id) {
        List<Comment> allByCritique_idOrderByDateOfPublicDesc = commentRepository.findAllByCritique_IdOrderByDateOfPublicDesc(id);
        if (allByCritique_idOrderByDateOfPublicDesc.isEmpty()) {
            throw new NotFoundException("Critique with id " + id + " does not have comments");
        }
        return allByCritique_idOrderByDateOfPublicDesc;
    }

    public List<Comment> findCommentsByCritique_IdOrderDateAsc(@NonNull Long id) {
        List<Comment> allByCritique_idOrderByDateOfPublicAsc = commentRepository.findAllByCritique_IdOrderByDateOfPublicAsc(id);
        if (allByCritique_idOrderByDateOfPublicAsc.isEmpty()) {
            throw new NotFoundException("Critique with id " + id + " does not have comments");
        }
        return allByCritique_idOrderByDateOfPublicAsc;
    }

    public List<Comment> findCommentsByDate(@NonNull Date date) {
        List<Comment> allByDateOfPublic = commentRepository.findAllByDateOfPublic(date);
        if (allByDateOfPublic.isEmpty()) {
            throw new NotFoundException("Critique does not have comments with this date");
        }
        return allByDateOfPublic;
    }

    public Comment deleteComment(@NonNull Long commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Comment with specified id: " + commentId + " does not found");
        }
        commentRepository.deleteById(commentId);
        return byId.get();
    }

    public Comment findById(@NonNull Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " does not exist"));
        return comment;
    }

    @Transactional
    public void update(@NonNull Long commentId, @NonNull String text) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with specified id: " + commentId + " does not found")
        );

        if (text.length() < COMMENT_MAX_LENGTH
                &&
                text.length() > COMMENT_MIN_LENGTH
                &&
                !comment.getText().equals(text)
        ) {
            comment.setText(text);
        }
    }

    public boolean checkOwner(@NonNull Long commentId, @NonNull String username) {
        return findById(commentId).getAppUser().getUsername().equals(username);
    }


    public List<Comment> findAll() {
        return commentRepository.findAll();
    }


}


