package gui;

import helpers.CSVExporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.TransactionsPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static io.qameta.allure.Allure.step;

@DisplayName("Тест на проверку транзакций")
public class LoginTest extends CSVExporter {
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private TransactionsPage transactionsPage;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        String hubUrl = "http://192.168.1.148:4444";

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        this.driver = new RemoteWebDriver(new URL(hubUrl), capabilities);

        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        this.loginPage = new LoginPage(this.driver);
        this.mainPage = new MainPage(this.driver);
        this.transactionsPage = new TransactionsPage(this.driver);
    }

    @Test
    public void testOpenPage() throws IOException {
        step("Открываем страницу globalsqa.com", () ->
                loginPage.openLoginPage("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login"));
        step("Выбираем Customer Loger", () ->
                loginPage.setCustomerLogin());
        step("Заходим пользователем Гарри Поттер", () -> {
            loginPage.selectCustomerName("Harry Potter");
            loginPage.loginToAccount();
        });
        step("Пополняем счет суммой, вычисленной с помощью чисел Фибоначчи", () -> {
            mainPage.fillFibonacciNumInDeposit();
            mainPage.submitDeposit();
        });
        step("Списываем со счета сумму, вычисленную с помощью чисел Фибоначчи", () -> {
            mainPage.fillFibonacciNumInWithdrawl();
            mainPage.submitWithdrawl();
        });
        step("Проверяем баланс", () ->
                mainPage.checkBalance());
        step("Переходим в транзакции", () ->
                mainPage.setTransactions());
        step("Проверяем наличие операций", () ->
                transactionsPage.checkTransactions());
        step("Записываем операции в файл", () -> {
            String filePath = "src/main/resources/transactions.csv";
            CSVExporter.exportTransactionsToCSV(driver, filePath);
        });
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}