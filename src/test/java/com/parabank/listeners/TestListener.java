package com.parabank.listeners;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.parabank.utils.ConfigReader;
import com.parabank.utils.ScreenshotUtil;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String path = ConfigReader.get("reportPath");
        ExtentSparkReporter spark = new ExtentSparkReporter(path == null ? "target/extent-report.html" : path);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        try{
            Object instance = result.getInstance();
            Field f = instance.getClass().getDeclaredField("driver");
            f.setAccessible(true);
            WebDriver driver = (WebDriver) f.get(instance);
            String screenshot = ScreenshotUtil.take(driver, result.getMethod().getMethodName());
            if (screenshot != null) test.get().addScreenCaptureFromPath(screenshot);
        }catch(Exception ignored){}
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) extent.flush();
    }
}
