package dk.bankdata.resource;

import dk.bankdata.model.OwnedShare;
import dk.bankdata.model.Share;
import dk.bankdata.service.AccountService;
import dk.bankdata.service.InvestmentService;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/investment")
public class InvestmentResource {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(AccountService.class));

  @Inject InvestmentService investmentService;

  @GET
  @Path("/share")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Share> getShares() {
    LOGGER.info("Fetching shares");
    return investmentService.getShares();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/share/buy")
  public Response buyShare(String shareId) {
    Share shareById = investmentService.getShareById(shareId);
    LOGGER.info("Buying share " + shareById.getName());
    investmentService.buyShareById(shareId);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/share/sell")
  public Response sellShare(String ownedShareId) {
    OwnedShare ownedShareById = investmentService.getOwnedShareById(ownedShareId);
    LOGGER.info("Selling share " + ownedShareById.getShare().getName());
    investmentService.sellOwnedShareById(ownedShareId);
    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/share/owned")
  public List<OwnedShare> getOwnedShares() {
    LOGGER.info("Fetching ownedShares");
    return investmentService.getOwnedShares();
  }
}
