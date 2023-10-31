package cvut.repository;

import cvut.exception.NotFoundException;
import cvut.model.AppUser;
import cvut.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public AppUserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<AppUser> getMostCommentedPosts(int page, int postsPerPage) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUser> cq = cb.createQuery(AppUser.class);
        Root<AppUser> p = cq.from(AppUser.class);
        cq.select(p);
        cq.orderBy(cb.desc(cb.size(p.<Collection<Comment>>get("comments"))));

        TypedQuery<AppUser> typedQuery = entityManager.createQuery(cq);
        ((TypedQuery<?>) typedQuery).setFirstResult((page - 1) * postsPerPage);
        typedQuery.setMaxResults(postsPerPage);
        List<AppUser> resultList = (List<AppUser>) typedQuery.getResultList();

        if (resultList.isEmpty())
            throw new NotFoundException("Result list is empty while searching most commented post");
        else
            return resultList;
    }

}
