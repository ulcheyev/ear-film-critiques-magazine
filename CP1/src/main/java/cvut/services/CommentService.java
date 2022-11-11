package cvut.services;

import cvut.config.exception.NotFoundException;
import cvut.config.exception.ValidationException;
import cvut.model.*;
import cvut.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;
    private final CritiqueRepository critiqueRepository;
    private final CriticRepository criticRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository, AppUserRepository appUserRepository, CritiqueRepository critiqueRepository, CriticRepository criticRepository) {
        this.commentRepository = commentRepository;
        this.appUserRepository = appUserRepository;
        this.critiqueRepository = critiqueRepository;
        this.criticRepository = criticRepository;
    }


    public List<Comment> findCritiquesByAppUser_Id(@NonNull Long id){
        if(appUserRepository.findById(id).isPresent()){
            throw new NotFoundException("User with specified id: "+id+"does not found");
        }
        List<Comment> comments = commentRepository.findAllByAppUser_Id(id);
        return commentRepository.findAllByAppUser_Id(id);
    }


    public void createComment(@NonNull String text, @NonNull Long appUserId, @NonNull Long critiqueId){

        checkReps(text, appUserId, critiqueId);

        AppUser appUser = appUserRepository.findById(appUserId).get();
        Critique critique = critiqueRepository.findById(critiqueId).get();

        if(critique.getCritiqueState() == CritiqueState.IN_PROCESSED){
            if(criticRepository.findById(appUserId).isPresent()){
                Comment comment = new Comment(text, new Date(), appUser, critique);
                commentRepository.save(comment);
            }
        }
        else{
            Comment comment = new Comment(text, new Date(), appUser, critique);
            commentRepository.save(comment);
        }

    }

    public void deleteComemnt(@NonNull Long commentId){
        Comment comment = commentRepository.findById(commentId).get();
        if(!comment.getId().equals(commentId)){
            throw new NotFoundException("Comment with specified id: "+commentId+"does not found");
        }
        commentRepository.deleteById(commentId);
    }


    private void checkReps(@NonNull String text, @NonNull Long appUserId, @NonNull Long critiqueId) {
        AppUser appUser = appUserRepository.findById(appUserId).get();
        Critique critique = critiqueRepository.findById(critiqueId).get();
        if(critique.getId().equals(critiqueId)){
            throw new NotFoundException("Critique with specified id: "+critiqueId+"does not found");
        }
        if(text.length() > 180){
            throw new ValidationException("Invalid text length. Max. 180, but was " + text.length());
        }
        else if(appUser.getId() != appUserId){
            throw new NotFoundException("User with specified id: "+appUserId+"does not found");
        }
    }
}
