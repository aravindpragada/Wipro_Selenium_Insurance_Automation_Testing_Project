package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.parabank.pages.BillPayPage;
import com.parabank.pages.HomePage;
import com.parabank.pages.LoginPage;
import com.parabank.utils.ConfigReader;

@Listeners(com.parabank.listeners.TestListener.class)
public class ClaimsTests extends BaseTest {

    @Test(description = "Submit a claim (simulated via Bill Pay) and validate acknowledgment")
    public void submitClaim(){
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("validUsername"), ConfigReader.get("validPassword"));
        HomePage home = new HomePage(driver);
        home.goToBillPay();
        BillPayPage bill = new BillPayPage(driver);
        bill.sendPayment("Claim Office", "123456", "123456", "10");
        Assert.assertTrue(bill.isPaymentSuccessful(), "Claim submission acknowledgment should appear");
    }
}
