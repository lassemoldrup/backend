package dk.bankdata.resource;

import dk.bankdata.model.Account;
import dk.bankdata.service.AccountService;
import io.quarkus.scheduler.Scheduled;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class UpdateAccountValueResource {

  @Inject AccountService accountService;

  @Scheduled(every = "1s")
  @Transactional
  void subtractFromInvestmentAccount() {
    Account accountAktiesparekonto = accountService.getAccountAktiesparekonto();
    accountService.subtractFromBalance(accountAktiesparekonto, 1);
  }
}
