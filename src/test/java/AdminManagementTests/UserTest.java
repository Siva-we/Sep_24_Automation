package AdminManagementTests;

import Core.test.BaseTest;
import Pages.Admin.SaveUserPage;
import Pages.Admin.UserManagementPage;
import Pages.DashBoardPage;
import Pages.LoginPage;
import org.testng.annotations.Test;

public class UserTest  extends BaseTest {

    @Test
    public void AddNewAdminUser(){
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication("Admin", "admin123");
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        SaveUserPage saveUserPage = userManagementPage.clickAdd();
    }
}
