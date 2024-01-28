package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public void openLoginPage(String url) {
        driver.get(url);
    }

    public void setCustomerLogin() {
        driver.findElement(customerLogin).click();
    }

    public void selectCustomerName(String value) {
        driver.findElement(customerName).click();
        switch (value) {
            case "Hermoine Granger":
                driver.findElement(granger).click();
                break;
            case "Harry Potter":
                driver.findElement(potter).click();
                break;
            case "Ron Weasly":
                driver.findElement(weasly).click();
                break;
            case "Albus Dumbledore":
                driver.findElement(dumbledore).click();
                break;
            case "Neville Longbottom":
                driver.findElement(longbottom).click();
                break;
        }
    }

    public void loginToAccount() {
        driver.findElement(login).click();
    }

    private final By customerLogin = By.xpath("//button[text()='Customer Login']");
    private final By customerName = By.xpath("//select[@id='userSelect']");
    private final By granger = By.xpath("//select[@id='userSelect']/option[text()='Hermoine Granger']");
    private final By potter = By.xpath("//select[@id='userSelect']/option[text()='Harry Potter']");
    private final By weasly = By.xpath("//select[@id='userSelect']/option[text()='Ron Weasly']");
    private final By dumbledore = By.xpath("//select[@id='userSelect']/option[text()='Albus Dumbledore']");
    private final By longbottom = By.xpath("//select[@id='userSelect']/option[text()='Neville Longbottom']");
    private final By login = By.xpath("//button[text()='Login']");
}
