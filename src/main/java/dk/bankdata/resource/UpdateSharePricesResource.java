package dk.bankdata.resource;

import dk.bankdata.model.Share;
import dk.bankdata.service.InvestmentService;
import io.quarkus.scheduler.Scheduled;
import java.util.Random;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UpdateSharePricesResource {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(InvestmentService.class));

  @Inject EntityManager entityManager;

  @Scheduled(every = "15s")
  @Transactional
  void updateStockPrices() {
    LOGGER.info("Updating stock prices");
    entityManager
        .createNamedQuery("Share.getShares", Share.class)
        .getResultStream()
        .forEach(
            share -> {
              double newPrice =
                  share.getCurrentPrice() * 1 + ((new Random().nextInt(50) - 25) * 0.5);
              if (newPrice > 0) {
                share.setCurrentPrice(newPrice);
              }
              entityManager.persist(share);
            });
  }
}
