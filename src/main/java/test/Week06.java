package test;


import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pom.Base;
import pom.BrowserDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Week06 {

    public WebDriver driver;
    public String url = "https://demoqa.com/";
    public Base base;

    @Parameters({"browser"})
    @BeforeTest
    public void openBrowser(String browser) {
        System.out.println("BeforeTest :: Open Browser: " + browser);
        switch (browser) {
            case "chrome":
                // Chrome WebDriver
                driver = BrowserDriver.chromeDriverConnection();
                break;
            case "firefox":
                // Firefox WebDriver
                driver = BrowserDriver.firefoxDriverConnection();
                break;
            case "edge":
                // Edge WebDriver
                driver = BrowserDriver.edgeDriverConnection();
                break;
            default:
                System.out.println("The: " + browser + " is not a valid action");
                throw new AssertionError();
        }
    }

    @AfterTest(enabled = true)
    public void closeBrowser() {
        base.closeBrowser();
    }

    @Test(priority = 1)
    public void firstTest() {
        System.out.println("---------------------------- HOMEWORK WEEK 06 -------------------------------");

        base = new Base(driver);
        base.maxiBrowser();

        base.visit(url);

        WebElement btnForm = base.findElement(By.xpath("//*[contains(text(),'Forms')]"));
        base.scrollToItem(btnForm);
        btnForm.click();

//        Move to https://demoqa.com/forms
        WebElement btnPracticeForm = base.findElement(By.xpath("//*[contains(text(),'Practice Form')]"));
        base.scrollToItem(btnPracticeForm);
        btnPracticeForm.click();
//        Move to https://demoqa.com/automation-practice-form
        System.out.println(base.getURL());

    }

    @Test(priority = 2, enabled = true)
    public void radioButtons() {
        System.out.println("--- Scenario: Radio Buttons Check Buttons ---");
        SoftAssert sa = new SoftAssert();

        List<WebElement> listElement = base.findElements(By.xpath("//input[@name='gender']"));
        int num = randomNum(1, listElement.size());

        System.out.println("Random number is: " + num);

        WebElement rdBtn = base.findElement(By.xpath("//*[@for='gender-radio-" + num + "']"));
        rdBtn.click();

        if (!listElement.isEmpty()) {
            //Print each status element
            for (WebElement webElement : listElement) {
                if (webElement.isSelected()) {
//                if(webElement.getAttribute("id").equals("gender-radio-"+num)){
//                        webElement.click();
                    System.out.println("Selected radioButton: " + webElement.getAttribute("value"));
                    // Soft Assert
                    sa.assertTrue(webElement.isSelected(), "Verify radioButtons: " + webElement.getAttribute("value") + " status");
                } else {
                    System.out.println("NOT Selected radioButton: " + webElement.getAttribute("value"));
                    // Soft Assert
                    sa.assertFalse(webElement.isSelected(), "Verify radioButtons: " + webElement.getAttribute("value") + " status");
                }

            }
        }

        sa.assertAll("radioButtons method, asserts");

    }


    @Test(priority = 3, enabled = true)
    public void checkButtons() {

        System.out.println("--- Scenario: Check Buttons  ---");
        base.scrollToItem(base.findElement(By.xpath("//input[@id='uploadPicture']")));

//        List<WebElement> listCheck = base.findElements(By.xpath("//div[contains(@class, 'custom-checkbox')]/label"));
        List<WebElement> listCheck = base.findElements(By.xpath("//input[@type='checkbox']"));
        int num2 = randomNum(1, listCheck.size());

        System.out.println("Random number is: " + num2);

        for(int i=1; i<=listCheck.size(); i++){
            if(!(i ==num2)){
                JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",driver.findElement(By.xpath("//*[@id='hobbies-checkbox-" + i + "']")), "checked",true);
            }
        }

        if (!listCheck.isEmpty()) {
            //Print each status element
            for (WebElement webElem : listCheck) {
//                base.waitisVisible(webElem);
                System.out.println("Label: " + base.findElement(By.xpath("//label[@for='"+webElem.getAttribute("id")+"']")).getText());
                if (webElem.isSelected()) {
//                if (!webElem.getAttribute("for").equalsIgnoreCase("hobbies-checkbox-" + num2)) {
//                    webElem.click();
                    System.out.println("Selected checkbox: " + webElem.getAttribute("id"));
//                    // Hard Assert
////                    Assert.assertTrue(webElem.isSelected(),"Verify checkbox status");
                } else {
                    System.out.println("NOT Selected checkbox: " + webElem.getAttribute("id"));
//                    // Hard Assert
////                    Assert.assertFalse(webElem.isSelected(),"Verify checkbox status");
                }


            }
        }

//        ***********************************************************
        base.visit(url);
        System.out.println(base.getURL());
        //Hard assert, expected: https://demoqa.com/
        Assert.assertEquals(base.getURL(), url, "The urls is not the expected");

        WebElement btnAlertFW = base.findElement(By.xpath("//*[contains(text(),'Alerts, Frame & Windows')]"));
        base.waitisVisible(btnAlertFW);
        base.scrollToItem(btnAlertFW);
        btnAlertFW.click();

        System.out.println(base.getURL());
        //Hard assert, expected: https://demoqa.com/alertsWindows
        Assert.assertEquals(base.getURL(), "https://demoqa.com/alertsWindows", "The urls is not the expected");

        WebElement btnBrowserWind = base.findElement(By.xpath("//*[contains(text(),'Browser Windows')]"));
        base.waitisVisible(btnBrowserWind);
        btnBrowserWind.click();

        System.out.println(base.getURL());
        //Hard assert, expected: https://demoqa.com/browser-windows
        Assert.assertEquals(base.getURL(), "https://demoqa.com/browser-windows", "The urls is not the expected");
    }

    @Test(priority = 4, enabled = true)
    public void tab() {
        System.out.println("--- Scenario: Tab  ---");
        SoftAssert sa = new SoftAssert();

        WebElement tabBtn = base.findElement(By.xpath("//button[@id='tabButton']"));
        tabBtn.click();
        base.numberOfWindowsToBe(2);
        System.out.println("The current number of tabs: " + base.countOpenTab());
        // Hard Assert :: Verify the numbers of tabs in browser is: 2
        Assert.assertTrue(base.countOpenTab() == 2);

        // Store all currently open tabs in tabs
        ArrayList<String> tabs = new ArrayList<String>(base.getIdWindows());
//        base.switchTo(tabs.get(tabs.size()-1));
        base.switchTo(tabs.get(1));

//        Verify title of the new open tab contains "page" on it
        System.out.println("The title is: " + base.getTitle());
//        sa.assertTrue(base.getTitle().contains("page"), "Verify title of the new open tab contains \"page\" on it");

        base.closeTab();
        base.switchTo(tabs.get(0));

        System.out.println("The current number of tabs: " + base.countOpenTab());
        // Hard Assert :: Verify the numbers of tabs in browser is: 1
        Assert.assertTrue(base.countOpenTab() == 1);

        WebElement btnAlerts = base.findElement(By.xpath("//span[contains(text(),'Alerts')]"));
        base.scrollToItem(btnAlerts);
        btnAlerts.click();
//        Move to https://demoqa.com/alerts
        System.out.println(base.getURL());

        sa.assertAll("tab method, asserts");
    }

    @Test(priority = 5, enabled = true)
    public void alertsSC() {
        System.out.println("--- Scenario: Alerts - Alert  ---");

        WebElement clickBtn = base.findElement(By.xpath("//span[contains(text(),'On button click, alert will appear after 5 seconds')]/../..//button[contains(text(),'Click me')]"));
        clickBtn.click();

//        base.waitalertIsPresent(5);

        try {
            Alert a = base.waitalerVerify(5);

            // Hard Assert :: Confirm that the alert appears after 5 seconds
            Assert.assertTrue(a != null, "The alert does not appears after 5 seconds");

            if (a != null) {
                System.out.println("Alert is present");

                Alert timeAlert = base.switchToAlert();
                System.out.println(timeAlert.getText());
                timeAlert.dismiss();

            } else {
                throw new Throwable();
            }
        } catch (Throwable e) {
            System.err.println("Alert isn't present!!");
        }

    }

    public int randomNum(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }


}
