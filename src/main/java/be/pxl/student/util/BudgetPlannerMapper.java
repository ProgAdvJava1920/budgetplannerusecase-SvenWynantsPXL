package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BudgetPlannerMapper {
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);
    public static final int CSV_ITEM_COUNT = 7;

    private Map<String, Account> accountMap = new HashMap<>();

    public List<Account> mapAccounts(List<String> accountLines) {
        List<Account> accountList = new ArrayList<>();

        for (String accountLine : accountLines) {
            try {
                Account account = mapDataLineToAccount(accountLine);
                accountMap.putIfAbsent(account.getIBAN(), account);
            } catch (ParseException | BudgetPlannerException e) {
                System.err.printf("Could not parse line [%s]", accountLine);
            }
        }
        return new ArrayList<Account>(accountMap.values());
    }

    public Account mapDataLineToAccount(String line) throws ParseException, BudgetPlannerException {
        String[] items = line.split(",");
        if (items.length != CSV_ITEM_COUNT) {
            throw new BudgetPlannerException(String.format("Invalid line. Expected length of %d items. Found %s", CSV_ITEM_COUNT, line));
        }

        String name = items[0];
        String iban = items[1];
        Account account = accountMap.getOrDefault(iban, new Account(name, iban));
        Payment pay = mapItemsToPayment(items);

        account.getPayments().add(pay);

        return account;
    }

    public Payment mapItemsToPayment(String[] items) throws ParseException {
        return new Payment(
                items[2],                       // IBAN
                convertToDate(items[3]),        // Transaction date
                Float.parseFloat(items[4]),     // amount
                items[5],                       // currency
                items[6]                        // detail
        );
    }

    public Date convertToDate(String dateString) throws ParseException {
        /*String datePattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(datePattern, Locale.US);
        ZonedDateTime zonedDate = ZonedDateTime.parse(dateString, myFormatter);
        return Date.from(zonedDate.toInstant());*/
        return DATE_FORMAT.parse(dateString);
    }

    public String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

}
