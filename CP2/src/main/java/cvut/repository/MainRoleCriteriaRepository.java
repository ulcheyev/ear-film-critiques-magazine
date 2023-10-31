package cvut.repository;

import cvut.model.FilmRole;
import cvut.model.MainRole;
import cvut.model.dto.creation.MainRoleCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MainRoleCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;


    @Autowired
    public MainRoleCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<MainRole> findAllByFilters(MainRoleCreationDTO mainRoleCreationDTO) {
        CriteriaQuery<MainRole> critiqueCriteriaQuery = criteriaBuilder.createQuery(MainRole.class);
        Root<MainRole> mainRoleRoot = critiqueCriteriaQuery.from(MainRole.class);
        Predicate predicate = getPredicate(mainRoleCreationDTO, mainRoleRoot);

        critiqueCriteriaQuery.where(predicate);
        TypedQuery<MainRole> typedQuery = entityManager.createQuery(critiqueCriteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getPredicate(MainRoleCreationDTO mainRoleCreationDTO,
                                   Root<MainRole> mainRoleRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(mainRoleCreationDTO.getFilmRole())) {
            predicates.add(
                    criteriaBuilder.equal(mainRoleRoot.get("filmRole"),
                            FilmRole.fromDBName(mainRoleCreationDTO.getFilmRole()))
            );
        }
        if (Objects.nonNull(mainRoleCreationDTO.getFirstname())) {
            predicates.add(
                    criteriaBuilder.like(mainRoleRoot.get("firstname"),
                            "%" + mainRoleCreationDTO.getFirstname() + "%")
            );
        }

        if (Objects.nonNull(mainRoleCreationDTO.getLastname())) {
            predicates.add(
                    criteriaBuilder.like(mainRoleRoot.get("lastname"),
                            "%" + mainRoleCreationDTO.getLastname() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
