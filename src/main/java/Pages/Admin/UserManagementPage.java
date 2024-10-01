package Pages.Admin;

import Pages.OrangeCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UserManagementPage extends OrangeCommon {

    //locators
    private By btnAdd = By.xpath("//div[@class='orangehrm-header-container']//button");

    //constructors
    public UserManagementPage(){}

    public UserManagementPage(WebDriver driver){
        super(driver);
    }

    //method override
    @Override
    protected void verifyPageLoad() {
        Assert.assertTrue(getElement(lblTitle).getText().contains("User Management"), "UserManagement page not loaded");
    }

    //methods
    public SaveUserPage clickAdd(){
        clickElement(btnAdd, "Add Button");
        return new SaveUserPage(driver);
    }
}
