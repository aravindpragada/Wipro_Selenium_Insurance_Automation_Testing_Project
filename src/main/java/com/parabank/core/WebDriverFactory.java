package com.parabank.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

    public static WebDriver createInstance(String browser, boolean headless) {
        if (browser == null) browser = "chrome";
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fopts = new FirefoxOptions();
                if (headless) fopts.addArguments("-headless");
                return new FirefoxDriver(fopts);
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions copts = new ChromeOptions();
                if (headless) copts.addArguments("--headless=new");
                copts.addArguments("--no-sandbox","--disable-dev-shm-usage");
                return new ChromeDriver(copts);
        }
    }
}
