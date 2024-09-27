package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver = null;

    private By byElmentLocator = By.id("");

    public void clickOnElements(){
        driver.findElement(byElmentLocator).click();
    }
}
