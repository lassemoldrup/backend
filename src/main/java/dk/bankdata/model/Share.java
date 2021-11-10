package dk.bankdata.model;

import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NamedQueries({
  @NamedQuery(name = "Share.getShares", query = "SELECT S FROM Share S ORDER BY S.name"),
  @NamedQuery(name = "Share.getShareByName", query = "SELECT S FROM Share S WHERE S.name = :name"),
  @NamedQuery(name = "Share.getShareById", query = "SELECT S FROM Share S WHERE S.id = :id"),
  @NamedQuery(
      name = "Share.sellShare",
      query = "DELETE FROM Share s WHERE s.name = :name AND s.id = :id")
})
public class Share {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  private double currentPrice;

  private String name;

  public Share() {}

  public String getName() {
    return name;
  }

  public Share setName(String name) {
    this.name = name;
    return this;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public Share setCurrentPrice(double currentPrice) {
    this.currentPrice = currentPrice;
    return this;
  }

  public UUID getId() {
    return id;
  }

  public Share setId(UUID id) {
    this.id = id;
    return this;
  }
}
