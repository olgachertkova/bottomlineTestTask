package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PageBase;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static pages.PageBase.takeScreenShot;

public class TestBase {
    WebDriver driver;
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod(alwaysRun = true)
    public void startTest(Method m, Object[] p){
        logger.info("Start test: " + m.getName());
        if(p.length != 0) {
            logger.info(" --> With data: " + Arrays.asList(p));
        }
    }


    @BeforeMethod(alwaysRun= true)
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
    }

    @AfterMethod(alwaysRun = true)
    public void quit(ITestResult result) throws IOException {
        if(result.isSuccess()){
            logger.info("Test result: PASSED");

        }else{
            logger.error("Test result: FAILED");
            logger.info("Screenshot: " + takeScreenShot(driver));
        }
        logger.info("Stop test: " + result.getMethod().getMethodName());
        logger.info("======================================================");
        driver.quit();
    }


}
