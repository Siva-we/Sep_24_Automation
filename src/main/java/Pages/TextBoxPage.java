package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextBoxPage {

    WebDriver driver = null;

    private By byName = By.id("userName");


    public void submitForm(){
        driver.findElement(byName).sendKeys("akljdlfj");
    }
}
