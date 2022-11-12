package cvut.services;

import cvut.exception.ValidationException;
import cvut.model.Critic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CriticService {

    private CriticService criticService;

    @Autowired
    public CriticService(CriticService criticService) {
        this.criticService = criticService;
    }

    public List<Critic> findAllByCriticRating(double rating){
        if(rating < 0 || rating > 5){
            throw new ValidationException("Stars must be greater or equals than 0 and less or equals than 5, but was: " + rating);
        }
        return criticService.findAllByCriticRating(rating);
    }

    List<Critic> findByOrderByCriticRatingDesc(){
        return criticService.findByOrderByCriticRatingDesc();
    }
    List<Critic> findByOrderByCriticRatingAsc(){
        return criticService.findByOrderByCriticRatingAsc();
    };


}
