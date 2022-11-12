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

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;
    private final CritiqueRepository critiqueRepository;
    private final CriticRepository criticRepository;

    private final static int COMMENT_MAX_LENGTH = 250;
    private final static int COMMENT_MIN_LENGTH = 1;


    @Autowired
    public CommentService(CommentRepository commentRepository, AppUserRepository appUserRepository, CritiqueRepository critiqueRepository, CriticRepository criticRepository) {
        this.commentRepository = commentRepository;
        this.appUserRepository = appUserRepository;
        this.critiqueRepository = critiqueRepository;
        this.criticRepository = criticRepository;
    }

    public void save(@NonNull String text, @NonNull Long appUserId, @NonNull Long critiqueId){

        if(text.length() > COMMENT_MAX_LENGTH || text.length() < COMMENT_MIN_LENGTH){
            throw new ValidationException("Comment length must be max "+COMMENT_MAX_LENGTH + " and min "+ COMMENT_MIN_LENGTH +" symbols");
        }

        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(()-> new NotFoundException("User with id "+ appUserId+" does not found"));
        Critique critique = critiqueRepository.findById(critiqueId).orElseThrow(()-> new NotFoundException("Critique with id "+ critiqueId+" does not found"));

        Comment comment = new Comment(text, new Date(), appUser, critique);

        if(critique.getCritiqueState() == CritiqueState.IN_PROCESSED){

            if(criticRepository.findById(appUserId).isPresent()){
                commentRepository.save(comment);
            }
        }
        else{
            commentRepository.save(comment);
        }

    }

    public List<Comment> findCommentsByCritique_Id(Long id){
        return commentRepository.findAllByCritique_Id(id).orElseThrow(
                ()-> new NotFoundException("Critique with id "+id+ " does not have comments"));
    }

    public List<Comment> findCommentsByCritique_IdOrderDateDesc(Long id){
        return commentRepository.findAllByCritique_IdOrderByDateOfPublicDesc(id).orElseThrow(
                ()-> new NotFoundException("Critique with id "+id+ " does not have comments"));
    }

    public List<Comment> findCommentsByCritique_IdOrderDateAsc(Long id){
        return commentRepository.findAllByCritique_IdOrderByDateOfPublicAsc(id).orElseThrow(
                ()-> new NotFoundException("Critique with id "+id+ " does not have comments"));
    }

    public List<Comment> findCommentsByDate(Date date){
        return commentRepository.findAllByDateOfPublic(date).orElseThrow(
                ()-> new NotFoundException("Critique does not have comments with this date"));
    }

    public void deleteComment(@NonNull Long commentId){
        boolean ex = commentRepository.existsById(commentId);
        if(!ex){
            throw new NotFoundException("Comment with specified id: "+commentId+" does not found");
        }
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void updateComment(@NonNull Long commentId, @NonNull String text){

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new NotFoundException("Comment with specified id: "+commentId+" does not found")
        );

        if(text.length() < COMMENT_MAX_LENGTH
                &&
                text.length() > COMMENT_MIN_LENGTH
                &&
                !comment.getText().equals(text)
        ){
            comment.setText(text);
        }
    }






}

