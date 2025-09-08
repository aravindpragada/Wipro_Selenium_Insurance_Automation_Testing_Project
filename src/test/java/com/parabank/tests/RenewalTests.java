package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.parabank.pages.AccountOverviewPage;
import com.parabank.pages.HomePage;
import com.parabank.pages.LoginPage;
import com.parabank.pages.OpenAccountPage;
import com.parabank.utils.ConfigReader;

@Listeners(com.parabank.listeners.TestListener.class)
public class RenewalTests extends BaseTest {

    @Test(description = "Simulate renewal by opening another account (policy) and verifying overview")
    public void renewPolicy(){
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("validUsername"), ConfigReader.get("validPassword"));
        HomePage home = new HomePage(driver);
        home.goToOpenAccount();
        OpenAccountPage open = new OpenAccountPage(driver);
        open.openNewAccount("SAVINGS");
        Assert.assertTrue(open.isSuccess(), "Renewal (new policy) should succeed");
        home.goToAccountsOverview();
        AccountOverviewPage overview = new AccountOverviewPage(driver);
        Assert.assertTrue(overview.hasAccounts(), "Accounts overview should list accounts (policies)");
    }
}
