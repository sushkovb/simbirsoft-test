package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionsPage {

    private final WebDriver driver;

    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
    }
    public void checkTransactions() {
        driver.findElement(transaction0).isDisplayed();
        driver.findElement(transaction1).isDisplayed();
    }

    private final By transaction0 = By.xpath("//tbody/tr[@id='anchor0']");
    private final By transaction1 = By.xpath("//tbody/tr[@id='anchor1']");
}
