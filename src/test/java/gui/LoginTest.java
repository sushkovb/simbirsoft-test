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

import java.net.MalformedURLException;
import java.net.URL;

import static data.Constants.HUB_URL;
import static data.Constants.LOGIN_PAGE_URL;
import static io.qameta.allure.Allure.step;

@DisplayName("Тест на проверку транзакций")
public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private TransactionsPage transactionsPage;
    private CSVExporter csvExporter;
    @BeforeEach
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        this.driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);

        this.loginPage = new LoginPage(this.driver);
        this.mainPage = new MainPage(this.driver);
        this.transactionsPage = new TransactionsPage(this.driver);
        this.csvExporter = new CSVExporter(this.driver);
    }
    @Test
    public void testOpenPage() {
        step("Открываем страницу globalsqa.com", () ->
                loginPage.openLoginPage(LOGIN_PAGE_URL));
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
                mainPage.goToTransactions());
        step("Проверяем наличие операций", () ->
                transactionsPage.checkTransactions());
        step("Записываем операции в файл", () -> {
            String filePath = "src/main/resources/transactions.csv";
            csvExporter.exportTransactionsToCSV(driver, filePath);
        });
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}