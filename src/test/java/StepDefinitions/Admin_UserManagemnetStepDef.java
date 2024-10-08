package StepDefinitions;

import Core.reporting.ReportUtil;
import Core.test.BaseTest;
import Pages.Admin.UserManagementPage;
import Pages.DashBoardPage;
import Pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.io.IOException;

public class Admin_UserManagemnetStepDef {

    LoginPage loginPage = null;
    DashBoardPage dashBoardPage = null;
    UserManagementPage userManagementPage = null;

    @Given("user launch application")
    public void userLaunchApplication() throws IOException {
        System.out.println("Launching application...");
        System.setProperty("env","QA");
        BaseTest baseTest = new BaseTest();
        baseTest.beforeSuite();
        ReportUtil.addTestToReport(this.getClass().getName(), "User management");
        baseTest.openBrowser("chrome");
        loginPage = baseTest.launchApplication(LoginPage.class);
    }

    @When("Login to application as an admin")
    public void loginToApplicationAsAnAdmin() {
        dashBoardPage= loginPage.loginToApplication("Admin", "admin123");
    }

    @When("Login to application as an admin {string} and {string}")
    public void loginToApplicationAsAnAdminAnd(String username, String password) {
        dashBoardPage= loginPage.loginToApplication(username, password);
    }

    @And("Select Admin from left menu")
    public void selectAdminFromLeftMenu() {
        userManagementPage = dashBoardPage.selectMenuOption("Admin", UserManagementPage.class);
    }
}
