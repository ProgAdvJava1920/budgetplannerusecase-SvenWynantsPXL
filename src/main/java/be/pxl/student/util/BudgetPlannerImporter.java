package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    public static void main(String[] args) {
        BufferedReader reader = null;
        ArrayList<Payment> payments = new ArrayList<>();
        Account account = new Account();

        try {
            reader = new BufferedReader(new FileReader(Paths.get("src/main/resources/account_payments.csv").toFile()));
            String line = reader.readLine();
            line = reader.readLine();
            account.setName(line.split(",")[0]);
            account.setIBAN(line.split(",")[1]);

            account.setPayments(payments);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(account);
    }

    public static List<String> readCsvFile(Path path) throws BudgetPlannerException {
        List<String> fileLines = new ArrayList<>();
        BufferedReader reader = null;

        try
        {
            reader = Files.newBufferedReader(path);
            String line = reader.readLine(); // ignore first line

            while ((line = reader.readLine()) != null)
            {
                fileLines.add(line);
            }
        }
        catch (IOException | NullPointerException ex)
        {
            throw new BudgetPlannerException("Something went wrong reading csv file", ex);
        }

        return fileLines;
    }

    public List<Account> createAccounts(List<String> csvFile)
    {
        List<Account> accounts = new ArrayList<>();
        List<Payment> payments;

        for (String line: csvFile)
        {
            String [] data = line.split(",");
            boolean exists = false;

            for (Account acc: accounts) {
                if (acc.getName() == data[0] && acc.getIBAN() == data[1])
                {
                    exists = true;
                    List<Payment> accpay = acc.getPayments();

                    Payment payment = createPayment(data);
                    accpay.add(payment);
                    acc.setPayments(accpay);
                }
            }

            if (!exists)
            {
                Account account = new Account();
                account.setName(data[0]);
                account.setIBAN(data[1]);
                accounts.add(account);
            }
        }

        return accounts;
    }

    public Payment createPayment(String [] data)
    {
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        ZonedDateTime zonedDate = ZonedDateTime.parse(data[3], myFormatter);

        float amount = Float.parseFloat(data[4]);

        String currency = data[5];

        String details = data[6];

        Payment payment = new Payment(Date.from(zonedDate.toInstant()), amount, currency, details);

        return payment;
    }
}
