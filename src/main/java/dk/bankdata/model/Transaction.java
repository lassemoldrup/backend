package dk.bankdata.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NamedQueries({
  @NamedQuery(
      name = "Transaction.getTransactionsForAccount",
      query =
          "SELECT t FROM Transaction t WHERE t.accountFrom.id = :accountId OR t.accountTo.id = :accountId"),
  @NamedQuery(name = "Transaction.getTransactions", query = "SELECT t FROM Transaction t")
})
public class Transaction {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(cascade = CascadeType.ALL)
  private Account accountFrom;

  @ManyToOne(cascade = CascadeType.ALL)
  private Account accountTo;

  private double amount;

  private Date date;

  private String description;

  private TransactionCategory transactionCategory;

  public Transaction() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Account getAccountFrom() {
    return accountFrom;
  }

  public Transaction setAccountFrom(Account accountFrom) {
    this.accountFrom = accountFrom;
    return this;
  }

  public Account getAccountTo() {
    return accountTo;
  }

  public Transaction setAccountTo(Account accountTo) {
    this.accountTo = accountTo;
    return this;
  }

  public double getAmount() {
    return amount;
  }

  public Transaction setAmount(double amount) {
    this.amount = amount;
    return this;
  }

  public Date getDate() {
    return date;
  }

  public Transaction setDate(Date date) {
    this.date = date;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Transaction setDescription(String description) {
    this.description = description;
    return this;
  }

  public TransactionCategory getTransactionCategory() {
    return transactionCategory;
  }

  public Transaction setTransactionCategory(TransactionCategory transactionCategory) {
    this.transactionCategory = transactionCategory;
    return this;
  }
}
