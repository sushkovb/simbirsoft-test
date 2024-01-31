package helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.TransactionsPage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVExporter {
    private final TransactionsPage transactionsPage;

    public CSVExporter(WebDriver driver) {
        this.transactionsPage = new TransactionsPage(driver);
    }
    public void exportTransactionsToCSV(WebDriver driver, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Date-Time,Amount,Transaction Type\n");

            List<WebElement> transactionRows = driver.findElements(transactionsPage.transactionRowsLocate);

            for (WebElement row : transactionRows) {
                String dateTime = row.findElement(transactionsPage.dateTimeLocate).getText();
                String amount = row.findElement(transactionsPage.amountLocate).getText();
                String transactionType = row.findElement(transactionsPage.transactionTypeLocate).getText();

                String formattedDateTime = formatDateTime(dateTime);

                writer.write(formattedDateTime + "," + amount + "," + transactionType + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        File csvFile = new File("src/main/resources/transactions.csv");
        attachFileToAllure(csvFile);
    }

    private static void attachFileToAllure(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String fileContentString = new String(fileContent, StandardCharsets.UTF_8);

        Allure.addAttachment("Файл с проведенными транзакциями", fileContentString);
    }

    private static String formatDateTime(String inputDateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

            return outputFormat.format(inputFormat.parse(inputDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDateTime;
        }
    }
}
