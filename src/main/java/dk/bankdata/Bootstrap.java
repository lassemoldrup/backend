package dk.bankdata;

import dk.bankdata.model.*;
import dk.bankdata.service.AccountService;
import dk.bankdata.service.InvestmentService;
import dk.bankdata.service.TransactionService;
import io.quarkus.runtime.StartupEvent;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class Bootstrap {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(Bootstrap.class));

  @Inject TransactionService transactionService;

  @Inject AccountService accountService;

  @Inject InvestmentService investmentService;

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    if (accountService.getAccounts().isEmpty()) {
      LOGGER.info("Bootstrapping data...");

      // Create accounts
      createInvestmentAccount();
      createBorsen();
      Account bankdata = createSU();
      Account nem = createNem();

      // Generate data
      generateRandomPurchasesFromAccount(nem);
      generatePaychecks(bankdata, nem);

      // Create shares
      createShares();

      LOGGER.info("Done Bootstrapping data!");
    }
  }

  private void createShares() {
    investmentService.createShare(new Share().setName("TESLA").setCurrentPrice(730));
    investmentService.createShare(new Share().setName("Microsoft").setCurrentPrice(300));
    investmentService.createShare(new Share().setName("Amazon").setCurrentPrice(3421));
    investmentService.createShare(new Share().setName("Apple").setCurrentPrice(153));
    investmentService.createShare(new Share().setName("Google").setCurrentPrice(100));
    investmentService.createShare(new Share().setName("Gamestop").setCurrentPrice(209));
    investmentService.createShare(new Share().setName("Adobe").setCurrentPrice(665));
    investmentService.createShare(new Share().setName("Novo Nordisk").setCurrentPrice(640));
    investmentService.createShare(new Share().setName("Vestas").setCurrentPrice(260));
    investmentService.createShare(new Share().setName("Carlsberg").setCurrentPrice(1100));
  }

  private Account createNem() {
    return accountService.createAccount(
        new Account()
            .setNumber("7450-3800000")
            .setName("NemKonto")
            .setBalance(0.0)
            .setType(AccountType.NORMAL));
  }

  private Account createSU() {
    return accountService.createAccount(
        new Account().setName("SU").setBalance(1000000).setType(AccountType.HIDDEN));
  }

  private Account createBorsen() {
    return accountService.createAccount(
        new Account().setName("Børsen").setBalance(0).setType(AccountType.HIDDEN));
  }

  private Account createInvestmentAccount() {
    return accountService.createAccount(
        new Account()
            .setName("Aktiesparekonto")
            .setNumber("7450-10000000")
            .setBalance(10000)
            .setType(AccountType.INVESTMENT));
  }

  private void generateRandomPurchasesFromAccount(Account account) {
    Random random = new Random();
    var categories = Arrays.stream(TransactionCategory.values()).collect(Collectors.toList());
    var descriptions =
        List.of(
            "Wolt",
            "Just-Eat",
            "Matas",
            "FøTeX",
            "McDonalds",
            "Oister",
            "Rema 1000",
            "Netto",
            "Spotify",
            "7-Eleven");
    for (int i = 0; i < 220; i++) {
      Calendar calendar = getRandomDateThisYear();
      int amount = random.nextInt(500) + 10;
      transactionService.createTransaction(
          new Transaction()
              .setAccountFrom(account)
              .setAccountTo(null)
              .setAmount(amount)
              .setDate(calendar.getTime())
              .setDescription(descriptions.get(random.nextInt(descriptions.size() - 1)))
              .setTransactionCategory(categories.get(random.nextInt(categories.size() - 1))));
      accountService.subtractFromBalance(account, amount);
    }
  }

  private void generatePaychecks(Account fromAccount, Account toAccount) {
    Calendar calendar = Calendar.getInstance();
    int amount = 5000;
    int january = 0;
    int december = 11;
    for (int month = january; month <= december; month++) {
      calendar.set(2021, month, 1);
      transactionService.createTransaction(
          new Transaction()
              .setAccountFrom(null)
              .setAccountTo(toAccount)
              .setTransactionCategory(TransactionCategory.PAYCHECK)
              .setAmount(amount)
              .setDate(calendar.getTime())
              .setDescription("Statens Uddannelsesstøtte"));
      accountService.addToBalance(toAccount, amount);
      accountService.subtractFromBalance(fromAccount, amount);
    }
  }

  private Calendar getRandomDateThisYear() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2021, Calendar.JANUARY, 0);
    calendar.set(Calendar.DAY_OF_YEAR, new Random().nextInt() % 365);
    return calendar;
  }
}
