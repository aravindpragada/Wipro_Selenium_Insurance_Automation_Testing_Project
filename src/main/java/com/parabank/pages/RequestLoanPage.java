package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RequestLoanPage {
    private WebDriver driver;
    private By amount = By.id("amount");
    private By downPayment = By.id("downPayment");
    private By applyBtn = By.cssSelector("input[value='Apply Now']");
    private By rightPanel = By.id("rightPanel");

    public RequestLoanPage(WebDriver driver){ this.driver = driver; }

    public void requestLoan(String amt, String dp){
        driver.findElement(amount).clear();
        driver.findElement(amount).sendKeys(amt);
        driver.findElement(downPayment).clear();
        driver.findElement(downPayment).sendKeys(dp);
        driver.findElement(applyBtn).click();
    }

    public String getPanelText(){
        try { return driver.findElement(rightPanel).getText(); } catch(Exception e){ return ""; }
    }
}
