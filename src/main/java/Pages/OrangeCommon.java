package Pages;

import Core.web.BasePage;
import Pages.Admin.UserManagementPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class OrangeCommon extends BasePage {

    //locators
    protected static By lblTitle = By.tagName("h6");

    //dynamic locator
    private String menuOption = "//span[text()='%s']/parent::a";

    //constructors
    public OrangeCommon() {

    }
    public OrangeCommon(WebDriver driver){
        super(driver);
    }

    //methods
    public UserManagementPage selectMenuOption(String title, Class cls){
        By locator = By.xpath(String.format(menuOption, title));
        clickElement(locator, title + "Menu Option");
        return getGenericObject(cls);
    }
}
