package Pages;

import Core.web.BasePage;
import Pages.Admin.UserManagementPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class OrangeCommon extends BasePage {

    //locators
    protected static By lblTitle = By.xpath("//h6/parent::span");
    private String ddControl = "//label[text()='%s']/parent::div/following-sibling::div/div[@class='oxd-select-wrapper']";
    private String ddControlOption = "//span[text()='%s']/parent::div[@class='oxd-select-option']";

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

    public void selectDropDownControl(String control, String option){
        By byDropDown = By.xpath(String.format(ddControl, control));
        By byOption = By.xpath(String.format(ddControlOption, option));

        clickElement(byDropDown, control + " dropdown");
        hardWait(1);
        clickElement(byOption, option + " dropdown option");
    }
}
