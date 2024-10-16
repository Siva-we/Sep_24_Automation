package Pages.Admin;

import Pages.OrangeCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class SaveUserPage extends OrangeCommon {

    //locators
    //private By btnAdd = By.xpath("//div[@class='orangehrm-header-container']//button");

    //constructors
    public SaveUserPage(){}

    public SaveUserPage(WebDriver driver){
        super(driver);
    }

    //method override
    @Override
    protected void verifyPageLoad() {
        Assert.assertTrue(getElement(lblTitle).getText().contains("Admin"), "UserManagement page not loaded");
    }

    //methods


}
