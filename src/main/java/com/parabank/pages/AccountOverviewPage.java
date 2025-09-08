package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountOverviewPage {
    private WebDriver driver;
    private By accountsTable = By.id("accountTable");

    public AccountOverviewPage(WebDriver driver){ this.driver = driver; }

    public boolean hasAccounts(){
        try { return driver.findElement(accountsTable).isDisplayed(); } catch(Exception e){ return false; }
    }
}
