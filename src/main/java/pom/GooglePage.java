/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author MarcelaLopez
 */
public class GooglePage extends Base {

    public By txtBuscar = By.xpath("//input[@name='q'][@type='text'][@title='Buscar']");

    public By link = By.xpath("//cite[text()='https://the-internet.herokuapp.com']");

    public By txtHovers = By.xpath("//a[contains(text(),'Hovers')]");

    public By imagesItem = By.xpath("//div[@class='figure']");
    public By image1Name = By.xpath("//*[contains(text(),'name: user1')]");
    public By image2Link = By.xpath("//a[@href='/users/2']");
    public By image3Link = By.xpath("//a[@href='/users/3']");

    public GooglePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void hoverClickAction(By locator) {
        hoverClick(locator);
    }

    public void hoverImages() {

        System.out.println("IMAGENES" + findElements(imagesItem).size());

        //Performing the mouse hover action on the target element.
        hover(getItemList(findElements(imagesItem), 0));
        waitisVisible(image1Name);
        String[] parts = getText(image1Name).split(":");
        System.out.println("IMAGE#01: " + parts[1]);

//      #02. For the second profile print the text from the web element “view profile” 
        hover(getItemList(findElements(imagesItem), 1));
        waitisVisible(image2Link);
        System.out.println("IMAGE#02: " + getText(image2Link));

//        #03.For the third profile click the “View Profile”
        hover(getItemList(findElements(imagesItem), 2));
        waitisVisible(image3Link);       
        System.out.println("IMAGE#03: " + getHref(image3Link));
        click(image3Link);
    }
    
    public void submit(By locator){
        submitAction(locator);
    }

}
