package AdminManagementTests;

import Core.model.Common;
import Core.test.BaseTest;
import Pages.Admin.SaveUserPage;
import Pages.Admin.UserManagementPage;
import Pages.DashBoardPage;
import Pages.LoginPage;
import org.testng.annotations.Test;

public class UserTest extends BaseTest {

    @Test(testName = "Add New Admin User")
    public void AddNewAdminUser() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        SaveUserPage saveUserPage = userManagementPage.clickAdd();
    }

    @Test(testName = "Validate User Total In The Title")
    public void ValidateUserTotalInTheTitle_TC001() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateUserListCount();
    }

    @Test(testName = "Validate Column Titles")
    public void ValidateColumnTitles_TC002() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateColumnTitles("Username");
    }

    @Test(testName = "Validate Checkbox Column")
    public void ValidateCheckboxColumn_TC003() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateCheckboxColumn();
    }

    @Test(testName = "Validate Empty Values In Username Column")
    public void ValidateEmptyValuesInUsernameColumn_TC004() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateUsernameColumnDataForEmptyAndUniqueValues();
    }

    @Test(testName = "Validate Unique Values In Username Column")
    public void ValidateUniqueValuesInUsernameColumn_TC005() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateUsernameColumnDataForEmptyAndUniqueValues();
    }

    @Test(testName = "Validate Enable And Disable In Status Column")
    public void ValidateEnableAndDisableInStatusColumn_TC006() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateEnableAndDisableInStatusColumn();
    }

    @Test(testName = "Validate Empty Values In Status Column")
    public void ValidateEmptyValuesInStatusColumn_TC007() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateEmptyValuesInStatusColumn();
    }

    @Test(testName = "Validate Action Column For Delete Icon")
    public void ValidateActionColumnForDeleteIcon_TC008() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateEditDeleterIconInActionColumn();
    }

    @Test(testName = "Validate Action Column For Edit Icon")
    public void ValidateActionColumnForEditIcon_TC009() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateEditDeleterIconInActionColumn();
    }

    @Test(testName = "Validate Values Are Sorted")
    public void ValidateValuesAreSorted_TC010() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateSortForColumn("Username", "Descending");
    }

    @Test(testName = "Search By Valid Username")
    public void SearchByValidUsername_TC011() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.searchByUsername("valid");
    }

    @Test(testName = "Search By Invalid Username")
    public void SearchByInvalidUsername_TC012() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.searchByUsername("invalid");
    }

    @Test(testName = "Search By Valid User Role")
    public void SearchByValidUserRole_TC013() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.searchByUserRole("ESS");
    }

    @Test(testName = "Search By Valid Employee Name")
    public void SearchByValidEmployeeName_TC014() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.searchByEmployeeName("valid");
    }

    @Test(testName = "Search By Invalid Employee Name")
    public void SearchByInvalidEmployeeName_TC015() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.searchByEmployeeName("invalid");
    }

    @Test(testName = "Search By Status")
    public void SearchByStatus_TC016() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.searchByStatus("Enabled");
    }

    @Test(testName = "Validate Reset Filter")
    public void ValidateResetFilter_TC017() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateResetButton();
    }

    @Test(testName = "Verify Top Right Dropdown")
    public void VerifyTopRightDropdown_TC018() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.verifyDropdownToggleBehavior();
    }

    @Test(testName = "Validate Toggle Button")
    public void ValidateToggleButton_TC019() {
        LoginPage loginPage = launchApplication(LoginPage.class);
        DashBoardPage dashBoardPage = loginPage.loginToApplication(Common.getEnvData("username"), Common.getEnvData("password"));
        UserManagementPage userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
        userManagementPage.validateToggleButton();
    }







}
