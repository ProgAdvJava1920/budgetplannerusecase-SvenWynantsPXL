package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BudgetPlannerMapper {
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    public List<Account> mapAccounts(List<String> accountLines) {
        List<Account> accountList = new ArrayList<>();
        for (String accountLine : accountLines) {
            Account account = mapDataLineToAccount(accountLine);
            if (!accountList.contains(account)) {
                accountList.add(account);
            }
        }
        return accountList;
    }

    public Account mapDataLineToAccount(String line) {
        String[] items = line.split(",");
        String name = items[0];
        String iban = items[1];


        return new Account(name, iban);
    }

    private Payment createPayment(String[] data) throws ParseException {
        String iban = data[2];
        Date date = convertToDate(data[3]);
        float amount = Float.parseFloat(data[4]);
        String currency = data[5];
        String details = data[6];
        Payment payment = new Payment(iban, date, amount, currency, details);

        return payment;
    }

    public Date convertToDate(String dateString) throws ParseException {
        /*String datePattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(datePattern, Locale.US);
        ZonedDateTime zonedDate = ZonedDateTime.parse(dateString, myFormatter);
        return Date.from(zonedDate.toInstant());*/
        return DATE_FORMAT.parse(dateString);
    }

    public String convertDateToString(Date date){
        return DATE_FORMAT.format(date);
    }
}
