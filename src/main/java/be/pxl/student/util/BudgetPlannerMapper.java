package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BudgetPlannerMapper {

    public List<Account> mapAccounts(List<String> accountLines) {
        List<Account> accountList = new ArrayList<>();
        for (String accountLine : accountLines) {
            Account account = mapDataLineToAccount(accountLine);
            if (!accountList.contains(account) || accountList.size() == 0) {
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

    private Payment createPayment(String[] data) {
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        ZonedDateTime zonedDate = ZonedDateTime.parse(data[3], myFormatter);

        float amount = Float.parseFloat(data[4]);

        String currency = data[5];

        String details = data[6];

        Payment payment = new Payment(Date.from(zonedDate.toInstant()), amount, currency, details);

        return payment;
    }
}
