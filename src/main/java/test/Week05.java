package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pom.BrowserDriver;
import pom.GooglePage;

import java.util.ArrayList;

public class Week05 {

    public static WebDriver driver;
    public static String url = "https://www.google.com/";
    public static GooglePage base;

    @Parameters({"browser"})
    @BeforeTest
    public void openBrowser(String browser) {

//        #01.Open the web browser
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

    @Test(priority = 0)
    public void setUpTest() {
        System.out.println("---------------------------- HOMEWORK WEEK 05 -------------------------------");

        base = new GooglePage(driver);
        base.maxiBrowser();

//        #02.Navigate to Google
        base.visit(url);

    }

    @Test(priority = 1)
    public void tabTest() {

//        #03.Open 5 new tabs (original + 5 new tabs there should be 6 in total)
        String googleTab = base.getIdWindow();

//        JavascriptExecutor js = (JavascriptExecutor)driver;
        while (base.countOpenTab() < 6) {
//            Open Tab
            base.newTab();
//            js.executeScript("window.open('about:blank','_blank');");
            base.switchTo(googleTab);
            System.out.println("The current number of tabs: " + base.countOpenTab());
        }

//        #04. Now change tabs
        //Loop through tabs
        for (String windowHandle : base.getIdWindows()) {
            base.switchTo(windowHandle);
            System.out.println("windowHandle: " + windowHandle);

        }

//        #05. Now you should close all the news tabs except the original using the window handler
        // Store all currently open tabs in tabs
        ArrayList<String> tabs = new ArrayList<String>(base.getIdWindows());

//        Loop the open tabs
        for (int i = tabs.size() - 1; i >= 0; i--) {
            System.out.println("The current number of tabs: " + base.countOpenTab());
            base.switchTo(tabs.get(i));

            if (!googleTab.contentEquals(base.getIdWindow())) {
                base.closeTab();
            }
        }

    }

    @Test(priority = 2)
    public void googleSearch(){
//        #6. In the first tab that has google open we should search the following “the internet herokuapp”
        base.typeText(base.txtBuscar, "the internet herokuapp");
        base.submit(base.txtBuscar);

//        #07. From the result we should enter the first result that match the URL https://the-internet.herokuapp.com/
        System.out.println("LINK" + base.getText(base.link));
        base.click(base.link);

    }

    @Test(priority = 3)
    public void scrollAndHover(){

//        #08. From the list of examples scroll down and had into view the option of hovers and click it
        base.scrollIntoViewJS(base.txtHovers);
        base.hoverClickAction(base.txtHovers);

//        #09. Hover of the user image profile
        base.hoverImages();

    }



}
