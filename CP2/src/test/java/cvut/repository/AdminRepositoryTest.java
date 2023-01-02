package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindAdminWithSpecifiedCritiqueQuantity(){
        // Set data

        Query query = em.createNamedQuery("Admin.findAdminWithSpecifiedCritiqueQuantity");

        // Set query parameters
        query.setParameter(1, 3);

        // Execute the query and get the results
        List<Admin> admins = query.getResultList();

        // Verify the results
        Assertions.assertNotNull(admins);
        Assertions.assertFalse(admins.isEmpty());
        assertEquals(4, admins.size());
        assertEquals("onie.lakin", admins.get(0).getUsername());
        assertEquals(1599, admins.get(0).getId());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void testFindAdminWithSpecifiedCritiqueQuantity() {
        // Set data
        Admin admin1 = Generator.generateAdmin();
        Critique critique = Generator.generateCritique(6);
        Critique critique2 = Generator.generateCritique(10);
        Critique critique3 = Generator.generateCritique(15);
        admin1.setCritiqueList(Arrays.asList(critique, critique2));
        Admin admin2 =Generator.generateAdmin();
        List<Critique> critiqueList = new ArrayList<>();
        List<Critique> critiqueList2 = new ArrayList<>();
        critique.setAdmin(admin1);
        critique2.setAdmin(admin1);
        critique3.setAdmin(admin2);
        critiqueList.add(critique);
        critiqueList.add(critique2);
        critiqueList2.add(critique3);
        admin2.setCritiqueList(critiqueList2);
        admin1.setCritiqueList(critiqueList);
        em.persist(admin1);
        em.persist(admin2);
        em.flush();

        // Execute the query
        Query query = em.createNamedQuery("findAdminWithSpecifiedCritiqueQuantityNNQ", Admin.class);
        query.setParameter(1, 0);
        List<Admin> resultList = query.getResultList();

        // Verify the results
        assertEquals(50, resultList.size());
        assertEquals(1614, resultList.get(0).getId());
    }

}
