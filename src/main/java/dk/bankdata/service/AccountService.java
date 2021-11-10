package dk.bankdata.service;

import dk.bankdata.model.Account;
import dk.bankdata.model.AccountType;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
public class AccountService {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(AccountService.class));

  @Inject EntityManager entityManager;

  @Transactional
  public List<Account> getAccounts() {
    return entityManager
        .createNamedQuery("Account.getAccounts", Account.class)
        .getResultStream()
        .filter(account -> account.getType() != AccountType.HIDDEN)
        .collect(Collectors.toList());
  }

  public Account getAccountById(String accountId) {
    LOGGER.info("Fetching account: " + accountId);
    TypedQuery<Account> namedQuery =
        entityManager.createNamedQuery("Account.getAccountById", Account.class);
    namedQuery.setParameter("id", UUID.fromString(accountId));
    if (namedQuery.getResultList().isEmpty())
      throw new RuntimeException("Could not find account with id " + accountId);
    return namedQuery.getResultList().get(0);
  }

  public Account createAccount(Account account) {
    entityManager.persist(account);
    return account;
  }

  public Account getAccountBoersen() {
    return entityManager
        .createNamedQuery("Account.getAccountByName", Account.class)
        .setParameter("name", "Børsen")
        .getResultList()
        .get(0);
  }

  public Account getAccountAktiesparekonto() {
    return entityManager
        .createNamedQuery("Account.getAccountByName", Account.class)
        .setParameter("name", "Aktiesparekonto")
        .getResultList()
        .get(0);
  }

  @Transactional
  public Account subtractFromBalance(Account account, double amount) {
    account.setBalance(account.getBalance() - amount);
    entityManager.persist(account);
    return account;
  }

  @Transactional
  public Account addToBalance(Account account, double amount) {
    account.setBalance(account.getBalance() + amount);
    entityManager.persist(account);
    return account;
  }
}
