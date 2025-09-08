package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.parabank.pages.BillPayPage;
import com.parabank.pages.HomePage;
import com.parabank.pages.LoginPage;
import com.parabank.utils.ConfigReader;

@Listeners(com.parabank.listeners.TestListener.class)
public class PaymentGatewayTests extends BaseTest {

    @Test(description = "Validate failed transaction when account numbers do not match")
    public void failedPayment(){
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("validUsername"), ConfigReader.get("validPassword"));
        HomePage home = new HomePage(driver);
        home.goToBillPay();
        BillPayPage bill = new BillPayPage(driver);
        bill.sendPayment("Wrong Payee", "111222", "333444", "5");
        Assert.assertTrue(bill.isPaymentFailed() || !bill.isPaymentSuccessful(),
                "Payment should fail or show validation error for mismatched accounts");
    }
}
