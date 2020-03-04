package be.pxl.student.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
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
}
