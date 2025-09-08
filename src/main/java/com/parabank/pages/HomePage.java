package com.parabank.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By leftPanel = By.id("leftPanel");

    // Accounts Overview locators
    private final By accountsOverviewLinkText  = By.linkText("Accounts Overview");
    private final By accountsOverviewCss       = By.cssSelector("#leftPanel a[href*='overview.htm']");
    private final By accountsOverviewXPath     = By.xpath("//a[contains(normalize-space(.),'Accounts Overview')]");

    // Bill Pay locators
    private final By billPayLinkText           = By.linkText("Bill Pay");
    private final By billPaymentLinkText       = By.linkText("Bill Payment");
    private final By billPayCss                = By.cssSelector("#leftPanel a[href*='billpay.htm']");

    // Open account locators
    private final By openNewAccountLinkText    = By.linkText("Open New Account");
    private final By openAccountAltLinkText    = By.linkText("Open Account");
    private final By openAccountCss            = By.cssSelector("#leftPanel a[href*='openaccount.htm']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void goToAccountsOverview() {
        clickFirst(accountsOverviewLinkText, accountsOverviewCss, accountsOverviewXPath);
    }

    public void goToBillPay() {
        clickFirst(billPayLinkText, billPayCss, billPaymentLinkText);
    }

    public void goToOpenAccount() {
        clickFirst(openNewAccountLinkText, openAccountAltLinkText, openAccountCss);
    }

    private void clickFirst(By... locators) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(leftPanel));
        } catch (Exception ignored) {}

        for (By loc : locators) {
            List<WebElement> found = driver.findElements(loc);
            if (!found.isEmpty()) {
                WebElement el = found.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(el)).click();
                return;
            }
        }
        throw new NoSuchElementException("Could not find any of the provided locators: " + java.util.Arrays.toString(locators));
    }
}
