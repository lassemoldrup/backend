package dk.bankdata.model;

import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
  @NamedQuery(name = "Account.getAccounts", query = "SELECT A FROM Account A ORDER BY A.type DESC"),
  @NamedQuery(
      name = "Account.getFirstInvestmentAccount",
      query = "SELECT A FROM Account A WHERE A.type = :type"),
  @NamedQuery(
      name = "Account.getAccountByName",
      query = "SELECT A FROM Account A WHERE A.name = :name"),
  @NamedQuery(name = "Account.getAccountById", query = "SELECT A FROM Account A WHERE A.id = :id")
})
@Entity
public class Account {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  private AccountType type;

  private String number;

  private String name;

  private double balance;

  public Account() {}

  public UUID getId() {
    return id;
  }

  public Account setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getNumber() {
    return number;
  }

  public Account setNumber(String number) {
    this.number = number;
    return this;
  }

  public String getName() {
    return name;
  }

  public Account setName(String name) {
    this.name = name;
    return this;
  }

  public AccountType getType() {
    return type;
  }

  public Account setType(AccountType type) {
    this.type = type;
    return this;
  }

  public double getBalance() {
    return balance;
  }

  public Account setBalance(double balance) {
    this.balance = balance;
    return this;
  }
}
