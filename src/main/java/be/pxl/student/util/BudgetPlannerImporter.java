package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    public List<Account> getAccountsWithPayments() {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Payment> payments = new ArrayList<>();

        Path path = Paths.get("src/main/resources/account_payments.csv");
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new FileReader(path.toFile()));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                String [] data = line.split(",");
                for (Account acc : accounts)
                {
                    if (acc.getIBAN() == data[1])
                    {

                    }
                    else
                    {
                        Account account = new Account();
                        account.setName(data[0]);
                        account.setIBAN(data[1]);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return accounts;
    }
}
