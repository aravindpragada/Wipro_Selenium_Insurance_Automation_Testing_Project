package com.parabank.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
    public static String take(WebDriver driver, String name){
        try{
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String dest = "target/screenshots/"+name+"_"+ts+".png";
            File out = new File(dest);
            out.getParentFile().mkdirs();
            FileUtils.copyFile(src, out);
            return dest;
        }catch(Exception e){ return null; }
    }
}
