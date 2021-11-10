package dk.bankdata.resource;

import dk.bankdata.model.Account;
import dk.bankdata.service.AccountService;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountResource {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(AccountService.class));

  @Inject AccountService accountService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Account> getAccounts() {
    LOGGER.info("Fetching accounts");
    return Stream.concat(
            accountService.getAccounts().stream(), accountService.getAccounts().stream())
        .sorted(Comparator.comparing(Account::getName))
        .collect(Collectors.toList());
  }

  @GET
  @Path("/{accountId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Account getAccountById(@PathParam("accountId") String accountId) {
    LOGGER.info("Fetching account " + accountId);
    return accountService.getAccountById(accountId);
  }
}
