package cvut.services;

import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CriticService {

    private final CriticRepository criticRepository;

    @Autowired
    public CriticService(CriticRepository criticRepository) {
        this.criticRepository = criticRepository;
    }


    public Critic findById(Long id){
        return criticRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Critic with id "+id+" does not exist")
        );
    }

    public Critic getAll(Long id){
        return criticRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Critic with id "+id+" does not exist")
        );
    }

   public List<Critic> findAllByLastnameAndFirstnameLike(String lastname, String firstname){
       return criticRepository.findAllByLastnameAndFirstnameLike(lastname, firstname)
               .orElseThrow(()-> new NotFoundException("Critics were not found"));

    }

    List<Critic> findByOrderByCriticRatingDesc(){
        return criticRepository.findByOrderByCriticRatingDesc().orElseThrow(()-> new NotFoundException(
                "Critics not found"
        ));
    }

    List<Critic> findByOrderByCriticRatingAsc(){
        return criticRepository.findByOrderByCriticRatingAsc().orElseThrow(()-> new NotFoundException(
                "Critics not found"
        ));
    };


    public List<Critic> findAllByCriticRating(double rating){
        if(rating < 0 || rating > 5){
            throw new ValidationException("Stars must be greater or equals than 0 and less or equals than 5, but was: " + rating);
        }
        return criticRepository.findAllByCriticRating(rating).orElseThrow(
                ()-> new NotFoundException("Critics not found")
        );
    }

    @Transactional
    public void update(@NonNull Long criticId, String username, String email){
        Critic critic = findById(criticId);

        if(username != null && username.length()>0 && !critic.getUsername().equals(username)){
            Optional<Critic> criticUsername = criticRepository
                    .findByUsername(username);
            if(criticUsername.isPresent()){
                throw new ValidationException("Username " + username +" has been taken");
            }
            critic.setUsername(username);
        }
        if(email != null && email.length()>0 && !critic.getEmail().equals(email)){
            Optional<Critic> appUserByEmail = criticRepository
                    .findByEmail(email);
            if(appUserByEmail.isPresent()){
                throw new ValidationException("Email " + email +" has been taken");
            }
            critic.setEmail(email);
        }
    }


    public void deleteById(@NonNull Long criticId){
        boolean exists = criticRepository.existsById(criticId);
        if(!exists){
            throw new NotFoundException("Can not delete critic with id "+criticId+". Critic does not exist");
        }
        criticRepository.deleteById(criticId);
    }
}
