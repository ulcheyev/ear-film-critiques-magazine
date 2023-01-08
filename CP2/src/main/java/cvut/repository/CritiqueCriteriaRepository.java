package cvut.repository;

import cvut.model.AppUser;
import cvut.model.Critique;
import cvut.model.Film;
import cvut.model.dto.CritiqueDTO;
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

    public List<Critique> findAllByFilters(CritiqueDTO critiqueDTO) {
        CriteriaQuery<Critique> critiqueCriteriaQuery = criteriaBuilder.createQuery(Critique.class);
        Root<Critique> critiqueRoot = critiqueCriteriaQuery.from(Critique.class);
        Predicate predicate = getPredicate(critiqueDTO, critiqueRoot);

        critiqueCriteriaQuery.where(predicate);
        TypedQuery<Critique> typedQuery = entityManager.createQuery(critiqueCriteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getPredicate(CritiqueDTO critiqueDTO,
                                   Root<Critique> critiqueRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(critiqueDTO.getTitle())) {
            predicates.add(
                    criteriaBuilder.like(critiqueRoot.get("title"),
                            "%" + critiqueDTO.getTitle() + "%")
            );
        }
        if (Objects.nonNull(critiqueDTO.getRating())) {
            predicates.add(
                    criteriaBuilder.equal(critiqueRoot.get("rating"),
                            critiqueDTO.getRating())
            );
        }

        if (Objects.nonNull(critiqueDTO.getUsername())) {
            Join<Critique, AppUser> subqueryCritiqueAppUser = critiqueRoot.join("critiqueOwner");
            predicates.add(
                    criteriaBuilder.like(subqueryCritiqueAppUser.get("username"), "%" + critiqueDTO.getUsername() + "%")
            );
        }

        if (Objects.nonNull(critiqueDTO.getFilm())) {
            Join<Critique, Film> subqueryCritiqueFilm = critiqueRoot.join("film");
            predicates.add(
                    criteriaBuilder.like(subqueryCritiqueFilm.get("name"), "%" + critiqueDTO.getFilm() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
