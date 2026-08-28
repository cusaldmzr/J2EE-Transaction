package lk.jiat.jta.ejb;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.*;
import lk.jiat.jta.entity.Account;

import java.math.BigDecimal;

@Stateless
public class AccountBeanImpl implements AccountBean{

    @PersistenceContext(unitName = "JTA-PU")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void creditAmount(Long accountNo, BigDecimal amount) {
        EntityTransaction transaction = em.getTransaction();
        System.out.println("creditAmount: "+ System.identityHashCode(transaction));
        try {
            Account account = em.createNamedQuery("Account.findByAccountNo", Account.class)
                    .setParameter("accountNumber", accountNo)
                    .getSingleResult();

            account.setBalance(account.getBalance().add(amount));

            em.merge(account);

        }catch(NoResultException e){
            e.printStackTrace();
        }

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void debitAmount(Long accountNo, BigDecimal amount) {
        EntityTransaction transaction = em.getTransaction();
        System.out.println("debitAmount: "+ System.identityHashCode(transaction));
        try {
            Account account = em.createNamedQuery("Account.findByAccountNo", Account.class)
                    .setParameter("accountNumber", accountNo)
                    .getSingleResult();

            account.setBalance(account.getBalance().subtract(amount));

            em.merge(account);
        }catch(NoResultException e){
            e.printStackTrace();
        }
    }
}
