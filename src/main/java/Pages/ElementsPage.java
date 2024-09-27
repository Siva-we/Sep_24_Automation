package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementsPage {

    WebDriver driver = null;

    By byTextBox = By.id("");

    public void selectTextBox(){
        driver.findElement(byTextBox).click();
    }

}
