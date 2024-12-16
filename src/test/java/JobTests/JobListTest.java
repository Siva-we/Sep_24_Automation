package JobTests;

import Core.model.Common;
import Core.test.BaseTest;
import Pages.Admin.SaveUserPage;
import Pages.Admin.UserManagementPage;
import Pages.DashBoardPage;
import Pages.LoginPage;
import org.testng.annotations.Test;

public class JobListTest extends BaseTest {

    @Test(testName = "Job list column title validation")
    public void ValidateColumnTitlesInJobListTable() {
        LoginPage loginPage = launchApplication(LoginPage.class);

    }

    @Test(testName = "Job list column title are unique")
    public void ValidateColumnTitlesAreunique() {
        LoginPage loginPage = launchApplication(LoginPage.class);

    }
}