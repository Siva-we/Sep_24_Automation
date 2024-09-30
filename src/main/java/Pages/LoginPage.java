package Pages;

import Core.web.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends BasePage {

    //variable

    //locators
    private static By btnLogin = By.xpath("//button[text()=' Login ']");
    private By txtUserName = By.name("username");
    private By txtPassword = By.name("password");

    //constructor
    public LoginPage() { }

    public LoginPage(WebDriver driver){
        super(driver);
    }

    //override methods
    @Override
    protected void verifyPageLoad() {
        Assert.assertNotNull(getElement(btnLogin), "Login page is not loaded" );
    }

    //methods
    public void loginToApplication(String username, String password){
        enterText(txtUserName, username, "Username Textbox");
        enterText(txtPassword, password, "Password Textbox");
        clickElement(btnLogin, "Login Button");
    }

}
