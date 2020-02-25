package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    public static void main(String[] args) {
        BufferedReader reader = null;
        ArrayList<Payment> payments = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("D:/Documenten/2tinJ/Programming Advanced/Java/BudgetPlanner/src/main/resources/account_payments.csv"));
            String line = reader.readLine();
            line = reader.readLine();
            Account account = new Account();
            account.setName(line.split(",")[0]);
            account.setIBAN(line.split(",")[1]);

            while (line != null) {
                String[] data = line.split(",");
                DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                ZonedDateTime zonedDate = ZonedDateTime.parse(data[3], myFormatter);
                float amount = Float.parseFloat(data[4]);
                String currency = data[5];
                String details = data[6];
                Payment payment = new Payment(Date.from(zonedDate.toInstant()), amount, currency, details);
                payments.add(payment);
                line = reader.readLine();
            }

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

        for (Payment pay: payments) {
            System.out.println(pay.toString());
        }
    }
}
