package dk.bankdata.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NamedQueries({
  @NamedQuery(
      name = "OwnedShare.getOwnedShares",
      query = "SELECT ownedShare FROM OwnedShare ownedShare"),
  @NamedQuery(
      name = "OwnedShare.getOwnedShareById",
      query = "SELECT OS FROM OwnedShare OS WHERE OS.id = :id")
})
public class OwnedShare {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(cascade = CascadeType.DETACH)
  private Share share;

  private double buyPrice;

  private Date buyDate;

  public OwnedShare() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Share getShare() {
    return share;
  }

  public void setShare(Share share) {
    this.share = share;
  }

  public double getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Date getBuyDate() {
    return buyDate;
  }

  public void setBuyDate(Date buyDate) {
    this.buyDate = buyDate;
  }
}
