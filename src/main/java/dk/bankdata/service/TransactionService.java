package dk.bankdata.service;

import dk.bankdata.model.Transaction;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class TransactionService {

  @Inject EntityManager entityManager;

  public List<Transaction> getTransactionsForAccount(String accountId) {
    TypedQuery<Transaction> namedQuery =
        entityManager.createNamedQuery("Transaction.getTransactionsForAccount", Transaction.class);
    namedQuery.setParameter("accountId", UUID.fromString(accountId));
    return namedQuery.getResultList();
  }

  public List<Transaction> getTransactions() {
    TypedQuery<Transaction> namedQuery =
        entityManager.createNamedQuery("Transaction.getTransactions", Transaction.class);
    return namedQuery.getResultList();
  }

  public Transaction createTransaction(Transaction transaction) {
    entityManager.persist(transaction);
    return transaction;
  }
}
