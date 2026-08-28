package lk.jiat.jta.ejb;

import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lk.jiat.jta.entity.User;

import java.math.BigDecimal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserBeanImpl implements UserBean {

    @PersistenceContext(unitName = "JTA-PU")
    private EntityManager em;

    @EJB
    private AccountBean accountBean;


    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void register(String name, String email, String password) {

        //Session session = em.unwrap(Session.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        em.persist(user);

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void transfer(Long from, Long to, BigDecimal amount) {
        EntityTransaction transaction = em.getTransaction();
        System.out.println("transfer: "+ System.identityHashCode(transaction));


        accountBean.creditAmount(to, amount);
        accountBean.debitAmount(from, amount);

    }
}
