package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPage {
    private final By deposit = By.xpath("//button[@ng-click='deposit()']");
    private final By withdrawl = By.xpath("//button[@ng-click='withdrawl()']");
    private final By inputNumberDeposit = By.xpath("//input[@ng-model='amount']");
    private final By inputNumberWithdrawl = By.xpath("//label[text()='Amount to be Withdrawn :']/following-sibling::input[@ng-model='amount']");
    private final By submitDeposit = By.xpath("//button[@type='submit' and text()='Deposit']");
    private final By submitWithdrawl = By.xpath("//button[@type='submit' and text()='Withdraw']");
    private final By accountInfo = By.xpath("//div[@ng-hide='noAccount']");
    private final By transaction = By.xpath("//button[@ng-click='transactions()']");
    private final WebDriver driver;
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFibonacciNumInDeposit() {
        LocalDate currentDate = LocalDate.now();
        int n = currentDate.getDayOfMonth() + 1;
        long fibonacciNumber = calculateFibonacci(n);
        driver.findElement(deposit).click();
        driver.findElement(inputNumberDeposit).sendKeys(String.valueOf(fibonacciNumber));
    }

    public void fillFibonacciNumInWithdrawl() {
        LocalDate currentDate = LocalDate.now();
        int n = currentDate.getDayOfMonth() + 1;
        long fibonacciNumber = calculateFibonacci(n);
        driver.findElement(withdrawl).click();
        driver.findElement(inputNumberWithdrawl).sendKeys(String.valueOf(fibonacciNumber));
    }

    public static long calculateFibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
        }
    }

    public void submitDeposit() {
        driver.findElement(submitDeposit).click();
    }

    public void submitWithdrawl() {
        driver.findElement(submitWithdrawl).click();
    }

    public void checkBalance() {
        String accountInfoText = driver.findElement(accountInfo).getText();
        assertTrue(accountInfoText.contains("Balance : 0"));
    }

    public void goToTransactions() {
        driver.findElement(transaction).click();
    }

}

