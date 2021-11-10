package dk.bankdata.resource;

import dk.bankdata.model.Transaction;
import dk.bankdata.service.TransactionService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("transaction")
public class TransactionResource {

  @Inject TransactionService transactionService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{accountId}")
  @Transactional
  public List<Transaction> getTransactionsForAccount(@PathParam("accountId") String accountId) {
    return transactionService.getTransactionsForAccount(accountId);
  }
}
