package Pages;

import Core.web.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class DashBoardPage extends OrangeCommon {

    //variable

    //locators
    //private static By lblTitle = By.tagName("h6");

    //constructor
    public DashBoardPage() { }

    public DashBoardPage(WebDriver driver){
        super(driver);
    }

    //override methods
    @Override
    protected void verifyPageLoad() {
        Assert.assertTrue(getElement(lblTitle).getText().equalsIgnoreCase("Dashboard"), "Dashboard page not loaded");
    }

    //methods
}
