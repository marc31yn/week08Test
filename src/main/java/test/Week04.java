package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pom.BrowserDriver;
import pom.HomePage;

import java.util.Random;

public class Week04 {

    public static WebDriver driver;
    public static String url = "https://rahulshettyacademy.com/AutomationPractice/";
    public static HomePage homePage;

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
//        Close the browser at the end of the test
        homePage.closeBrowser();
    }

    @Test(priority = 0)
    public void setUpTest() {
        System.out.println("---------------------------- HOMEWORK WEEK 04 -------------------------------");

        homePage = new HomePage(driver);

        homePage.visit(url);
        homePage.maxiBrowser();

    }

    @Test(priority = 1)
    public void raddioButtons(){
//        1- Radio Buttons:
        System.out.println("*** 1- Radio Buttons: ***");

        Random rand = new Random();
        int max = 3;
        int min = 1;
        int randNum = rand.nextInt((max - min) + 1) + min;

        System.out.println("Random number is: "+randNum);
        homePage.selectRadioButton("radio"+randNum);
    }

    @Test(priority = 2)
    public void input(){
//        2- Input
        System.out.println("*** 2- Input: ***");
        homePage.input("Suggession Class Example","El Sal");

    }

    @Test(priority = 3)
    public void selectDropdown(){
//        3- Select: "Dropdown Example"
        System.out.println("*** 3- Select: \"Dropdown Example\"  ***");

        String option2 = homePage.dropdownGetValue(2);
        if (option2.equals("Option2")) {
            System.out.println("The value: "+option2+" is the expected");
        }else{
            System.out.println("The value: "+option2+" is not the expected");
        }

        homePage.dropdownSelectValue("Option3");
        String option3 = homePage.dropdownGetValue(3);
        if (option3.equals("Option3")) {
            System.out.println("The value: "+option3+" is the expected");
        }else{
            System.out.println("The value: "+option3+" is not the expected");
        }
    }

    @Test(priority = 4)
    public void buttons(){
//        4- Buttons: [Home | Practice | Login | Signup]
        System.out.println("*** 4- Buttons: [Home | Practice | Login | Signup]  ***");

        homePage.clickButton("Home",url);
        homePage.clickButton("Practice",url);
        homePage.clickButton("Login",url);
        homePage.clickButton("Signup",url);

    }

    @Test(priority = 5)
    public void tabsHandle(){
//        5- Tabs : Repeat below instructions until you have 9 open Tabs in browser
        System.out.println("*** 5- Tabs : Repeat below instructions until you have 9 open Tabs in browser   ***");

        String parentTab = homePage.getIdWindow();

        while (homePage.countOpenTab() < 9) {
            homePage.openTab();
            homePage.switchTo(parentTab);
            System.out.println("The current number of tabs: "+homePage.countOpenTab());
        }
    }


}
