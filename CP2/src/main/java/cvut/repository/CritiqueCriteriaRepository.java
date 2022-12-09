package cvut.repository;

import cvut.model.AppUser;
import cvut.model.Critique;
import cvut.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CritiqueCriteriaRepository {


    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;


    @Autowired
    public CritiqueCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<Critique> findAllByFilters(CritiqueSearchCriteria critiqueSearchCriteria){
        CriteriaQuery<Critique> critiqueCriteriaQuery = criteriaBuilder.createQuery(Critique.class);
        Root<Critique> critiqueRoot = critiqueCriteriaQuery.from(Critique.class);
        Predicate predicate = getPredicate(critiqueSearchCriteria, critiqueRoot);

        critiqueCriteriaQuery.where(predicate);
        TypedQuery<Critique> typedQuery = entityManager.createQuery(critiqueCriteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getPredicate(CritiqueSearchCriteria critiqueSearchCriteria,
                                   Root<Critique> critiqueRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(critiqueSearchCriteria.getTitle())){
            predicates.add(
                    criteriaBuilder.like(critiqueRoot.get("title"),
                            "%"+ critiqueSearchCriteria.getTitle() +"%")
            );
        }
        if(Objects.nonNull(critiqueSearchCriteria.getRating())){
            predicates.add(
                    criteriaBuilder.equal(critiqueRoot.get("rating"),
                            critiqueSearchCriteria.getRating())
            );
        }

        if(Objects.nonNull(critiqueSearchCriteria.getUsername())){
            Join<Critique, AppUser> subqueryCritiqueAppUser = critiqueRoot.join("critiqueOwner");
            predicates.add(
                    criteriaBuilder.like(subqueryCritiqueAppUser.get("username"), "%"+ critiqueSearchCriteria.getUsername()+"%")
            );
        }

        if(Objects.nonNull(critiqueSearchCriteria.getFilm())){
            Join<Critique, Film> subqueryCritiqueFilm = critiqueRoot.join("film");
            predicates.add(
                    criteriaBuilder.like(subqueryCritiqueFilm.get("name"), "%"+ critiqueSearchCriteria.getFilm()+"%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
