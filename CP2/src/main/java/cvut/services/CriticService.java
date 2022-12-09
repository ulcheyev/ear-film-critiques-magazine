package cvut.services;

import cvut.model.Critic;

import java.util.List;

public interface CriticService {
    Critic findById(Long id);
    List<Critic> findAll();
    List<Critic> findAllByLastnameAndFirstnameLike(String lastname, String firstname);
    List<Critic> findByOrderByCriticRatingDesc();
    List<Critic> findByOrderByCriticRatingAsc();
    List<Critic> findAllByCriticRating(double rating);
    void update(Long criticId, String username, String email);
    void deleteById(Long criticId);
}
