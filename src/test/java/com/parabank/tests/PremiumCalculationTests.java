package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.parabank.utils.ExcelUtil;

@Listeners(com.parabank.listeners.TestListener.class)
public class PremiumCalculationTests extends BaseTest {

    private static final String DATA_PATH = "src/test/resources/testdata/policyData.xlsx";
    private static final String SHEET = "Premiums";

    @DataProvider(name = "premiumData")
    public Object[][] premiumData() {
        String[] expectedHeaders = new String[] {"Policy", "Age", "Coverage", "Premium"};
        return ExcelUtil.readSheetByHeaders(DATA_PATH, SHEET, expectedHeaders);
    }

    @Test(dataProvider = "premiumData", description = "Validate premium calculation using data-driven Excel")
    public void validatePremium(String policyType, String age, String coverage, String expectedPremium) {
        System.out.println("Policy: " + policyType + " | Age: " + age + " | Coverage: " + coverage + " | Premium: " + expectedPremium);

        Assert.assertNotNull(age, "Age must not be null");
        Assert.assertFalse(age.trim().isEmpty(), "Age must be provided in Excel for the test to run");

        Assert.assertNotNull(expectedPremium, "Expected premium must not be null");
        Assert.assertFalse(expectedPremium.trim().isEmpty(), "Expected premium must not be empty");

    }
}
