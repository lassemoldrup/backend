package dk.bankdata.service;

import dk.bankdata.model.*;
import dk.bankdata.model.TransactionCategory;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
public class InvestmentService {

  @Inject EntityManager entityManager;
  @Inject TransactionService transactionService;
  @Inject AccountService accountService;

  @Transactional
  public List<Share> getShares() {
    return entityManager.createNamedQuery("Share.getShares", Share.class).getResultList();
  }

  @Transactional
  public List<OwnedShare> getOwnedShares() {
    return entityManager
        .createNamedQuery("OwnedShare.getOwnedShares", OwnedShare.class)
        .getResultList();
  }

  public Share getShareById(String shareId) {
    TypedQuery<Share> namedQuery =
        entityManager.createNamedQuery("Share.getShareById", Share.class);
    namedQuery.setParameter("id", UUID.fromString(shareId));
    if (namedQuery.getResultList().isEmpty())
      throw new RuntimeException("Could not find share with id " + shareId);
    return namedQuery.getResultList().get(0);
  }

  public OwnedShare getOwnedShareById(String ownedShareId) {
    TypedQuery<OwnedShare> namedQuery =
        entityManager.createNamedQuery("OwnedShare.getOwnedShareById", OwnedShare.class);
    namedQuery.setParameter("id", UUID.fromString(ownedShareId));
    if (namedQuery.getResultList().isEmpty())
      throw new RuntimeException("Could not find share with id " + ownedShareId);
    return namedQuery.getResultList().get(0);
  }

  @Transactional
  public void createShare(Share share) {
    entityManager.persist(share);
  }

  @Transactional
  public void buyShareById(String shareId) {
    Share shareEntity = entityManager.find(Share.class, UUID.fromString(shareId));

    Account accountAktiesparekonto = accountService.getAccountAktiesparekonto();
    Account accountBoersen = accountService.getAccountBoersen();

    OwnedShare ownedShare = new OwnedShare();
    ownedShare.setShare(shareEntity);
    ownedShare.setBuyPrice(shareEntity.getCurrentPrice());
    ownedShare.setBuyDate(new Date());
    entityManager.persist(ownedShare);

    Transaction transaction = new Transaction();
    transaction.setAmount(shareEntity.getCurrentPrice() * -1);
    transaction.setDate(new Date());
    transaction.setAccountFrom(accountAktiesparekonto);
    transaction.setAccountTo(accountBoersen);
    transaction.setDescription("Køb af aktie " + shareEntity.getName());
    transaction.setTransactionCategory(TransactionCategory.INVESTMENT);
    transactionService.createTransaction(transaction);
  }

  @Transactional
  public void sellOwnedShareById(String ownedShareId) {
    OwnedShare ownedShareEntity =
        entityManager.find(OwnedShare.class, UUID.fromString(ownedShareId));

    Account accountAktiesparekonto = accountService.getAccountAktiesparekonto();
    Account accountBoersen = accountService.getAccountBoersen();
    accountAktiesparekonto.setBalance(
        accountAktiesparekonto.getBalance() + ownedShareEntity.getShare().getCurrentPrice());
    entityManager.persist(accountAktiesparekonto);
    accountBoersen.setBalance(
        accountBoersen.getBalance() - ownedShareEntity.getShare().getCurrentPrice());
    entityManager.persist(accountAktiesparekonto);

    Transaction transaction = new Transaction();
    transaction.setAmount(ownedShareEntity.getShare().getCurrentPrice());
    transaction.setDate(new Date());
    transaction.setDescription("Salg af aktie " + ownedShareEntity.getShare().getName());
    transaction.setAccountFrom(accountAktiesparekonto);
    transaction.setAccountTo(accountBoersen);
    transaction.setTransactionCategory(TransactionCategory.INVESTMENT);
    transactionService.createTransaction(transaction);
    entityManager.remove(ownedShareEntity);
  }
}
