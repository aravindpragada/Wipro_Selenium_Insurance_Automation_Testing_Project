package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.parabank.pages.HomePage;
import com.parabank.pages.LoginPage;
import com.parabank.pages.OpenAccountPage;
import com.parabank.utils.ConfigReader;

@Listeners(com.parabank.listeners.TestListener.class)
public class PolicyCreationTests extends BaseTest {

    @DataProvider(name = "policyTypes")
    public Object[][] policies(){
        return new Object[][]{
                {"SAVINGS"}, {"CHECKING"}
        };
    }

    @Test(dataProvider = "policyTypes", description = "Simulate policy creation via Open New Account")
    public void createPolicy(String type){
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("validUsername"), ConfigReader.get("validPassword"));
        HomePage home = new HomePage(driver);
        home.goToOpenAccount();
        OpenAccountPage open = new OpenAccountPage(driver);
        open.openNewAccount(type);
        Assert.assertTrue(open.isSuccess(), "Policy (account) should be opened for type: " + type);
    }
}
