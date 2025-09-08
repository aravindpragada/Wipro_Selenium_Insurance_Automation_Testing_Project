package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginBtn = By.xpath("//input[@value='Log In']");
    private By logoutLink = By.linkText("Log Out");

    // Success indicator
    private By accountsOverviewHeader = By.xpath("//h1[contains(text(),'Accounts Overview')]");
    // Error indicator
    private By loginError = By.xpath("//p[@class='error']");

    public enum LoginStatus {
        SUCCESS, ERROR, UNKNOWN
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String username) {
        WebElement userEl = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userEl.clear();
        userEl.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passEl = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passEl.clear();
        passEl.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public LoginStatus performLoginAndWait(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();

        try {
            if (wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(accountsOverviewHeader),
                    ExpectedConditions.visibilityOfElementLocated(loginError)
            )) != null) {
                if (!driver.findElements(accountsOverviewHeader).isEmpty()) {
                    return LoginStatus.SUCCESS;
                } else if (!driver.findElements(loginError).isEmpty()) {
                    return LoginStatus.ERROR;
                }
            }
        } catch (Exception e) {
            return LoginStatus.UNKNOWN;
        }
        return LoginStatus.UNKNOWN;
    }

    public boolean isLoggedIn() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public LoginStatus login(String username, String password) {
        return performLoginAndWait(username, password);
    }

    public void logout() {
        try {
            driver.findElement(logoutLink).click();
        } catch (Exception ignored) {}
    }
}
