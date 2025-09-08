package com.parabank.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BillPayPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    //locators 
    private final By payeeNameId1 = By.id("payee.name");
    private final By payeeNameId2 = By.name("payee.name");
    private final By payeeAcctId1 = By.id("payee.accountNumber");
    private final By payeeAcctId2 = By.name("payee.accountNumber");
    private final By verifyAcctId  = By.id("verifyAccount");
    private final By verifyAcctName = By.name("verifyAccount");
    private final By amountId     = By.id("amount");
    private final By amountName   = By.name("amount");
    private final By sendPaymentBtn = By.cssSelector("input[type='submit'][value*='Send Payment'], input[value='Send Payment'], button[type='submit']");

    // success/failure outputs
    private final By successMsg = By.xpath("//*[contains(text(),'Bill Payment Complete') or contains(text(),'successfully sent') or contains(.,'was successfully scheduled')]");
    private final By errorMsg = By.cssSelector(".error, #rightPanel .error, .err");

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   
    public void sendPayment(String payeeName, String account, String verifyAccount, String amount) {
        setField(payeeName, payeeNameId1, payeeNameId2);
        setField(account, payeeAcctId1, payeeAcctId2);
        setField(verifyAccount, verifyAcctId, verifyAcctName);
        setField(amount, amountId, amountName);

        List<WebElement> btns = driver.findElements(sendPaymentBtn);
        if (!btns.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(btns.get(0))).click();
        } else {
            List<WebElement> submits = driver.findElements(By.cssSelector("input[type='submit'], button[type='submit']"));
            if (!submits.isEmpty()) submits.get(0).click();
            else throw new NoSuchElementException("No submit button found on Bill Pay page");
        }

        try { Thread.sleep(700); } catch (InterruptedException ignored) {}
    }

    
    private void setField(String value, By... locators) {
        for (By loc : locators) {
            List<WebElement> els = driver.findElements(loc);
            if (els.isEmpty()) continue;

            for (WebElement el : els) {
                try {
                    String tag = el.getTagName().toLowerCase();

                    if ("input".equals(tag) || "textarea".equals(tag) || "select".equals(tag)) {
                        try {
                            el.clear();
                        } catch (Exception ignored) {}
                        el.sendKeys(value);
                        return;
                    }

                    List<WebElement> descendants = el.findElements(By.cssSelector("input, textarea, select"));
                    if (!descendants.isEmpty()) {
                        WebElement input = descendants.get(0);
                        try { input.clear(); } catch (Exception ignored) {}
                        input.sendKeys(value);
                        return;
                    }

                    String token = extractLocatorToken(loc);
                    if (token != null && !token.isEmpty()) {
                        List<WebElement> inputs = driver.findElements(By.cssSelector(
                                "input[id='" + token + "'], input[name='" + token + "'], input[id*='" + token + "'], input[name*='" + token + "']"));
                        if (!inputs.isEmpty()) {
                            WebElement in = inputs.get(0);
                            try { in.clear(); } catch (Exception ignored) {}
                            in.sendKeys(value);
                            return;
                        }
                    }

                    Object editable = ((JavascriptExecutor) driver).executeScript(
                            "return arguments[0].isContentEditable || (arguments[0].tagName.toLowerCase()==='span' && arguments[0].hasAttribute('contenteditable'))",
                            el);
                    boolean isEditable = editable instanceof Boolean && (Boolean) editable;
                    if (isEditable) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].innerText = arguments[1];", el, value);
                        return;
                    }

                    ((JavascriptExecutor) driver).executeScript(
                            "var p = arguments[0].parentElement; if(!p) return false; var i = p.querySelector('input,textarea,select'); if(i){ i.value=arguments[1]; i.dispatchEvent(new Event('input')); return true;} return false;",
                            el, value);

                    return;
                } catch (Exception e) {
                }
            }
        }
    }

    private String extractLocatorToken(By loc) {
        try {
            String s = loc.toString(); 
            int idx = s.indexOf(":");
            if (idx >= 0 && idx + 1 < s.length()) {
                return s.substring(idx + 1).trim();
            }
            return s.trim();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isPaymentSuccessful() {
        try { return driver.findElements(successMsg).size() > 0; } catch (Exception e) { return false; }
    }

    public boolean isPaymentFailed() {
        try { return driver.findElements(errorMsg).size() > 0; } catch (Exception e) { return false; }
    }
}
