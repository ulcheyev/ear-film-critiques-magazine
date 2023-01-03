package cvut.services;

import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Critic;
import cvut.repository.CriticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CriticServiceImpl implements CriticService {

    private final CriticRepository criticRepository;

    @Autowired
    public CriticServiceImpl(CriticRepository criticRepository) {
        this.criticRepository = criticRepository;
    }


    public Critic findById(@NonNull Long id){
        return criticRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Critic with id "+id+" does not exist")
        );
    }

    public List<Critic> findAll(){
        List<Critic> all = criticRepository.findAll();
        if(all.isEmpty()){
            throw new NotFoundException("There are no critics");
        }
        return all;
    }

    public List<Critic> findAllByLastnameAndFirstnameLike(@NonNull String lastname, @NonNull String firstname){
        List<Critic> allByLastnameAndFirstnameLike = findAllByLastnameAndFirstnameLike(lastname, firstname);
        if(allByLastnameAndFirstnameLike.isEmpty()){
            throw new NotFoundException("Critics were not found");
        }
        return allByLastnameAndFirstnameLike;

    }

    public List<Critic> findByOrderByCriticRatingDesc(){
        List<Critic> byOrderByCriticRatingDesc = criticRepository.findByOrderByCriticRatingDesc();
        if(byOrderByCriticRatingDesc.isEmpty()){
            throw new NotFoundException(
                    "Critics not found"
            );
        }
        return byOrderByCriticRatingDesc;
    }

    public List<Critic> findByOrderByCriticRatingAsc(){
        List<Critic> byOrderByCriticRatingAsc = criticRepository.findByOrderByCriticRatingAsc();
        if(byOrderByCriticRatingAsc.isEmpty()){
            throw new NotFoundException(
                    "Critics not found"
            );
        }
        return byOrderByCriticRatingAsc;
    };


    public List<Critic> findAllByCriticRating(@NonNull double rating){
        if(rating < 0 || rating > 5){
            throw new ValidationException("Stars must be greater or equals than 0 and less or equals than 5, but was: " + rating);
        }
        List<Critic> allByCriticRating = criticRepository.findAllByCriticRating(rating);
        if(allByCriticRating.isEmpty()){
            throw new NotFoundException("Critics not found");
        }
        return allByCriticRating;
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
