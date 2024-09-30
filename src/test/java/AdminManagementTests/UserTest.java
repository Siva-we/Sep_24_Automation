package AdminManagementTests;

import Core.test.BaseTest;
import Pages.LoginPage;
import org.testng.annotations.Test;

public class UserTest  extends BaseTest {

    @Test
    public void AddNewAdminUser(){
        LoginPage loginPage = launchApplication(LoginPage.class);
        loginPage.loginToApplication("Admin", "admin123");
    }
}
