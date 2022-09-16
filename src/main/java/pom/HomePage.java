/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author MarcelaLopez
 */
public class HomePage extends Base{
    
    By lcRadioButton = By.xpath("//input[@name='radioButton']");
    
    By lcInput = By.id("autocomplete");
    
    By lcDropdown = By.xpath("//select[@id='dropdown-class-example']");
    
    @FindBy(xpath = "//button[contains(text(),'Home')]")
    public WebElement btnHome;
    
    @FindBy(xpath = "//button[contains(text(),'Practice')]")
    public WebElement btnPractice;
    
    @FindBy(xpath = "//button[contains(text(),'Login')]")
    public WebElement btnLogin;
    
    @FindBy(xpath = "//button[contains(text(),'Signup')]")
    public WebElement btnSignup;
    
    By lcButtonTab = By.id("opentab");
    
    
    
    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void selectRadioButton(String value){
        radioButtonMethod(lcRadioButton, value);
    }
    
    public void input(String inputText, String typeText) {
        
        lcInput = By.xpath("//fieldset[legend[contains(text(),'"+inputText+"')]]/input");
        
        String result = getPlaceholder(lcInput);       
        if (result.equals("Type to Select Countries")) {
            System.out.println("The placeholder: "+result+" is the expected");
        }else{
            System.out.println("The placeholder: "+result+" is not the expected");
        }
        
        String value = getValue(typeAndSelectOption(lcInput, typeText));
        
        if (value.equals("El Salvador")) {
            System.out.println("The text value: "+value+" is the expected");
        }else{
            System.out.println("The text value: "+value+" is not the expected");
        }
        
                
    }
    
    public String dropdownGetValue(int option){       
        return getItemList(getDropdownList(lcDropdown),option).getText();        
    }    
    
    public void dropdownSelectValue(String textOption){       
        selectByText(lcDropdown,textOption);        
    }
    
    public void clickButton(String buttonName, String homeURL) {
        
        String buttonText;
        String currentURL;
        
        switch (buttonName) {
            case "Home":
                buttonText = btnHome.getText();
                btnHome.click();
                currentURL = getURL();                
                break;
            case "Practice":
                buttonText = btnPractice.getText();
                btnPractice.click();
                currentURL = getURL();                
                break;
            case "Login":
                buttonText = btnLogin.getText();
                btnLogin.click();
                currentURL = getURL();                
                break;                
            case "Signup":
                buttonText = btnSignup.getText();
                btnSignup.click();
                currentURL = getURL();                
                break;
            default:
                System.out.println("The: "+buttonName+ " is not a valid");
                throw new AssertionError();
        }          
        
        if(homeURL.equals(currentURL)){
            System.out.println("Same URL - Btn: "+buttonText  );
        }else{
            System.out.println("Different URL - Btn: "+buttonText  );
            goBack();
        } 
        
    }
    
    public void openTab(){
        click(lcButtonTab);
    }
    
}
