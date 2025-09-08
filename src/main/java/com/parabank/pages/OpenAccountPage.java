package com.parabank.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OpenAccountPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By typeSelect = By.id("type");                
    private final By typeSelectName = By.name("type");
    private final By openButton = By.cssSelector("input[type='submit'][value*='Open'], button[type='submit']");
    private final By successDiv = By.xpath("//*[contains(text(),'Account Opened') or contains(.,'Account Opened') or contains(.,'Congratulations')]");

    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   
    public void openNewAccount(String type) {
        try {
            WebElement selectEl = null;
            List<WebElement> els = driver.findElements(typeSelect);
            if (!els.isEmpty()) selectEl = els.get(0);
            else {
                els = driver.findElements(typeSelectName);
                if (!els.isEmpty()) selectEl = els.get(0);
            }

            if (selectEl != null) {
                Select s = new Select(selectEl);
                try { s.selectByVisibleText(type); }
                catch (Exception e) {
                    boolean found = false;
                    for (WebElement opt : s.getOptions()) {
                        if (opt.getText().equalsIgnoreCase(type) || opt.getText().toLowerCase().contains(type.toLowerCase())) {
                            s.selectByVisibleText(opt.getText());
                            found = true;
                            break;
                        }
                    }
                    if (!found) s.selectByIndex(0); 
                }
            }

            List<WebElement> btns = driver.findElements(openButton);
            if (!btns.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(btns.get(0))).click();
            } else {
                throw new NoSuchElementException("Open button not found");
            }
        } catch (Exception e) {
        }
    }

    public boolean isSuccess() {
        try {
            return driver.findElements(successDiv).size() > 0 ||
                   driver.findElements(By.cssSelector("#newAccountId, #accountId")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
