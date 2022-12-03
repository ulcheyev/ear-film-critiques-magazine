package cvut.model;

import cvut.exception.ValidationException;
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

        Subquery<Long> sq = critiqueCriteriaQuery.subquery(Long.class);
        Join<Critique, AppUser> sqEmp = critiqueRoot.join("critiqueOwner");

        Predicate predicate = getPredicate(critiqueSearchCriteria, critiqueRoot, sqEmp);

        critiqueCriteriaQuery.where(predicate);
        TypedQuery<Critique> typedQuery = entityManager.createQuery(critiqueCriteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getPredicate(CritiqueSearchCriteria critiqueSearchCriteria,
                                   Root<Critique> critiqueRoot, Join<Critique, AppUser> join) {
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
            predicates.add(
                    criteriaBuilder.like(join.get("username"), "%"+ critiqueSearchCriteria.getUsername()+"%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
