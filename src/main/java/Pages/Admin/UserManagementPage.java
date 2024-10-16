package Pages.Admin;

import Pages.OrangeCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class UserManagementPage extends OrangeCommon {

    // Locators
    private By btnAdd = By.xpath("//div[@class='orangehrm-header-container']//button");
    private static final By tableElements = By.xpath("//div[@class='oxd-table-body']/div");
    private static final By recordsCount = By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']/span");
    private static final By paginationButtons = By.xpath("//nav[@aria-label='Pagination Navigation']//li/button");
    private static final String columnTitleXpath = "//div[contains(@class, 'oxd-table-header-cell') and contains(., '%s')]";
    private static final String sortIconXpath = "//div[contains(@class, 'oxd-table-header-cell') and contains(., '%s')]//div[@class='oxd-table-header-sort']";
    private static final By visibleCheckBox = By.xpath("//div[contains(@class, 'oxd-table-card-cell-checkbox')]//input[@type='checkbox']");
    private static final By hiddenCheckBox = By.xpath("//div[contains(@class, 'oxd-table-card-cell-hidden')]//input[@type='checkbox']");
    private static final By usernameColumn = By.xpath("//div[@class='oxd-table-card']/child::div/div[2]");
    private static final By userRoleColumn = By.xpath("//div[@class='oxd-table-card']/child::div/div[3]");
    private static final By employeeNameColumn = By.xpath("//div[@class='oxd-table-card']/child::div/div[4]");
    private static final By statusColumn = By.xpath("//div[@class='oxd-table-card']/child::div/div[5]");
    private static final By actionColumn = By.xpath("//div[@class='oxd-table-card']/child::div/div[6]");
    private static final By deleteButton = By.xpath("//div[@class='oxd-table-cell-actions']/button[1]");
    private static final By editButton = By.xpath("//div[@class='oxd-table-cell-actions']/button[2]");
    private static final By ascendingIcon = By.xpath("//div[@class='--active oxd-table-header-sort-dropdown']//span[contains(.,'Ascending')]");
    private static final By descendingIcon = By.xpath("//div[@class='--active oxd-table-header-sort-dropdown']//span[contains(.,'Descending')]");
    private static final String columnTitle = "//div[@class='oxd-table-card']/child::div/div[@@]/div";

    private static final By usernameTextBox = By.xpath("//div[@class='oxd-input-group__label-wrapper' and contains(.,'Username')]/following-sibling::div/input");
    private static final By submitButton = By.xpath("//button[@type='submit']");
    private static final By userRoleEntry = By.xpath("//div[@class='oxd-input-group__label-wrapper' and contains(.,'User Role')]/following-sibling::div/div/div");
    private static final By adminRoleDropdown = By.xpath("//div[@role='option']/span[contains(.,'Admin')]");
    private static final By essRoleDropdown = By.xpath("//div[@role='option']/span[contains(.,'ESS')]");
    private static final By employeeNameTextBox = By.xpath("//div[@class='oxd-input-group__label-wrapper' and contains(.,'Employee Name')]/following-sibling::div/div/div/input");
    private static final By employeeNameHints = By.xpath("//div[@class='oxd-autocomplete-option']//span");
    private static final By statusEntry = By.xpath("//div[@class='oxd-input-group__label-wrapper' and contains(.,'Status')]/following-sibling::div/div/div");
    private static final By enableDropdown = By.xpath("//div[@class='oxd-select-option' and contains(.,'Enabled')]");
    private static final By disableDropDown = By.xpath("//div[@class='oxd-select-option' and contains(.,'Disabled')]");
    private static final By invalidUserPopup = By.id("oxd-toaster_1");
    private static final By invalidEmployeeName = By.xpath("//div[@class='oxd-input-group__label-wrapper' and contains(.,'Employee Name')]/following-sibling::span");
    private static final By restButton = By.xpath("//button[contains(.,'Reset')]");
    private static final By topRightDropdown = By.xpath("//button[@class='oxd-icon-button']//parent::div[@class='--toggle']");
    private static final By filterArea = By.xpath("//div[@class='oxd-table-filter-area']");

    SoftAssert softAssert = new SoftAssert();
    public UserManagementPage() {}

    public UserManagementPage(WebDriver driver) {
        super(driver);
    }

    // Method override to verify page load
    @Override
    protected void verifyPageLoad() {
        Assert.assertTrue(getElement(lblTitle).getText().contains("User Management"), "UserManagement page not loaded");
    }

    // Method to click the Add button and return the SaveUserPage
    public SaveUserPage clickAdd() {
        clickElement(btnAdd, "Add Button");
        return new SaveUserPage(driver);
    }

    // Validate the user list count in User Management List
    public void validateUserListCount() {
        String userCountText = getElement(recordsCount).getText();
        String numberOfUsers = userCountText.replaceAll("\\D+", "");
        int actualCount = Integer.parseInt(numberOfUsers);  // Extract actual count from UI

        int totalRecordsCount;
        int recordsOnFirstPage = getUserRecordsCount();

        if (recordsOnFirstPage < actualCount) {
            int totalPages = getTotalPages();
            int recordsOnLastPage = 0;

            if (totalPages > 1) {
                goToPage(totalPages);
                recordsOnLastPage = getUserRecordsCount();
            }

            totalRecordsCount = (recordsOnFirstPage * (totalPages - 1)) + recordsOnLastPage;
        } else {
            totalRecordsCount = recordsOnFirstPage;
        }

        Assert.assertEquals(totalRecordsCount, actualCount,
                "Calculated total user count does not match actual count from UI.");
    }

    // Retrieve the number of records on the current page
    private int getUserRecordsCount() {
        List<WebElement> userList = getElements(tableElements);
        return userList.size();
    }

    // Retrieve the total number of pages present
    private int getTotalPages() {
        List<WebElement> paginationItems = getElements(paginationButtons);
        int pageCount = 0;
        for (WebElement item : paginationItems) {
            if (!item.getText().equals("Previous") && !item.getText().equals("Next")) {
                pageCount++;
            }
        }
        return pageCount == 0 ? 1 : pageCount;
    }

    // Navigate to the specified page number
    private void goToPage(int pageNumber) {
        List<WebElement> paginationItems = getElements(paginationButtons);
        for (WebElement item : paginationItems) {
            if (item.getText().equals(String.valueOf(pageNumber))) {
                item.click();
                getElements(tableElements); // Refresh the user list after clicking
                break;
            }
        }
    }

    // Validate column titles in the User Management List
    public void validateColumnTitles(String expectedTitle) {
        String dynamicXpath = String.format(columnTitleXpath, expectedTitle);
        String actualColumnTitle = driver.findElement(By.xpath(dynamicXpath)).getText();
        Assert.assertEquals(actualColumnTitle, expectedTitle,
                "Column title mismatch: Expected - " + expectedTitle + " Found - " + actualColumnTitle);
    }

    // Validate the checkbox column
    public void validateCheckboxColumn() {
        List<WebElement> userList = getElements(tableElements);


        for (WebElement row : userList) {
            boolean isHiddenCheckboxPresent = !row.findElements(hiddenCheckBox).isEmpty();

            if (isHiddenCheckboxPresent) {
                softAssert.assertTrue(!row.findElement(hiddenCheckBox).isDisplayed(),
                        "Hidden checkbox should not be displayed for Admin user");
            } else {
                softAssert.assertTrue(row.findElement(visibleCheckBox).isDisplayed(),
                        "Visible checkbox is not displayed for a regular user");
            }
        }

        softAssert.assertAll();
    }



    // Validate that the username column contains unique values and is not empty
    public void validateUsernameColumnDataForEmptyAndUniqueValues() {
        List<WebElement> userList = getElements(usernameColumn);
        HashSet<String> uniqueUsernames = new HashSet<>();
        for (WebElement name : userList) {
            String userText = name.getText();
            softAssert.assertTrue(!userText.isEmpty(), "Empty username found!");
            softAssert.assertTrue(uniqueUsernames.add(userText), "Duplicate username found: " + userText);
        }
        softAssert.assertAll();

    }

    // Validate the status column for valid values
    public void validateEnableAndDisableInStatusColumn() {
        List<WebElement> statusList = getElements(statusColumn);
        for (WebElement status : statusList) {
            String statusText = status.getText();
            Assert.assertTrue(statusText.equals("Enabled") || statusText.equals("Disabled"), "Invalid status: " + statusText);
        }
    }

    // Validate that there are no empty values in the status column
    public void validateEmptyValuesInStatusColumn() {
        List<WebElement> statusList = getElements(statusColumn);
        for (WebElement status : statusList) {
            String statusText = status.getText();
            Assert.assertTrue(!statusText.isEmpty(), "Empty status found!");
        }
    }

    // Validate the action column to verify the presence of edit and delete buttons
    public void validateEditDeleterIconInActionColumn() {
        List<WebElement> actionList = getElements(actionColumn);
        for (WebElement action : actionList) {
            softAssert.assertTrue(action.findElement(deleteButton).isDisplayed(), "Delete icon is not displayed");
            softAssert.assertTrue(action.findElement(editButton).isDisplayed(), "Edit icon is not displayed");
        }
        softAssert.assertAll();

    }

    // Validate sorting of the specified column in the given order
    public void validateSortForColumn(String column, String order) {
        int index = getColumnIndex(column);
        List<String> columnDataBeforeSort = getColData(index);
        if (order.equals("Ascending")) {
            Collections.sort(columnDataBeforeSort);
            String dynamicXpathForSort = String.format(sortIconXpath, column);
            By sortIcon = By.xpath(dynamicXpathForSort);
            moveMouseAndClick(sortIcon, "");
            moveMouseAndClick(ascendingIcon, "");
        } else if (order.equals("Descending")) {
            Collections.sort(columnDataBeforeSort, Collections.reverseOrder());
            String dynamicXpathForSort = String.format(sortIconXpath, column);
            By sortIcon = By.xpath(dynamicXpathForSort);
            moveMouseAndClick(sortIcon, "");
            moveMouseAndClick(descendingIcon, "");
        }

        List<String> columnDataAfterSort = getColData(index);
        Assert.assertEquals(columnDataBeforeSort, columnDataAfterSort, "Names are not sorted");
    }

    // Retrieve column names from the table
    private List<String> getColumnNames() {
        List<WebElement> columns = getElements(By.xpath("//div[@role='columnheader']"), "column title");
        List<String> col = new ArrayList<>();
        columns.stream().forEach(c -> col.add(c.getText()));
        return col;
    }

    // Get the index of a specified column
    private int getColumnIndex(String columnName) {
        List<WebElement> columns = getElements(By.xpath("//div[@role='columnheader']"), "column title");
        List<String> col = new ArrayList<>();
        columns.stream().forEach(c -> col.add(c.getText()));
        int colIndex = 0;
        for (int index = 0; index < col.size(); index++) {
            if (col.get(index).equalsIgnoreCase(columnName)) {
                colIndex = index;
                break;
            }
        }
        return colIndex+1;
    }

    // Retrieve data from a specified column
    private List<String> getColData(int colIndex) {
        List<WebElement> columnData = getElements(By.xpath(columnTitle.replace("@@", String.valueOf(colIndex))));
        List<String> col = new ArrayList<>();
        columnData.stream().forEach(c -> col.add(c.getText()));
        return col;
    }


    //Search filter

    // Retrieve a random data value from the specified column
    public String getRandomColData(String colName) {
        int colIndex = getColumnIndex(colName);
        List<String> colData = getColData(colIndex);

        if (colData.isEmpty()) {
            throw new IllegalStateException("No data found in column: " + colName);
        }

        Random random = new Random();
        return colData.get(random.nextInt(colData.size()));
    }

    // Search by valid or invalid username
    public void searchByUsername( String value) {
        String text = value.equalsIgnoreCase("valid")
                ? getRandomColData("Username")
                : getRandomString(5);

        enterText(usernameTextBox, text, "Username textbox");
        moveMouseAndClick(submitButton, "Submit Button");

        if (value.equalsIgnoreCase("valid")) {
            List<WebElement> userList = getElements(usernameColumn);

            for (WebElement name : userList) {
                String userText = name.getText();
                softAssert.assertEquals(text, userText,
                        "Invalid user is displayed in search result: " + userText);
            }
        } else if (value.equalsIgnoreCase("invalid")) {
            softAssert.assertTrue(getElement(invalidUserPopup).isDisplayed(),
                    "No records popup is not displayed");
            softAssert.assertEquals(getElement(recordsCount).getText(),
                    "No Records Found");
        }

        softAssert.assertAll();
    }

    public void searchByEmployeeName(String value){
        String text = value.equalsIgnoreCase("valid")
                ? getRandomColData("Employee Name")
                : getRandomString(5);
        getElement(employeeNameTextBox).sendKeys(text);
        if (value.equalsIgnoreCase("valid")) {
            List<WebElement> hints = getElements(employeeNameHints);
            if (!hints.isEmpty()) {
                hints.get(0).click();
            }
            moveMouseAndClick(submitButton, "Submit Button");
            List<WebElement> employeeNameList = getElements(employeeNameColumn);
            for (WebElement name : employeeNameList) {
                String employeeNameText = name.getText();
                Assert.assertEquals(employeeNameText, text, "invalid employeeName is displayed in search result" + employeeNameText);
            }
        }
        else if (value.equalsIgnoreCase("invalid")) {
            getElement(employeeNameTextBox).sendKeys(text);
            moveMouseAndClick(submitButton,"Submit Button");
            Assert.assertEquals(getElement(invalidEmployeeName).getText(),"Invalid");
        }


    }



    public void searchByValidUsername(String username){
        enterText(usernameTextBox,username, "Username textbox");
        moveMouseAndClick(submitButton,"Submit Button");
        List<WebElement> userList = getElements(usernameColumn);
        for (WebElement name : userList) {
            String userText = name.getText();
            softAssert.assertEquals(username, userText, "invalid user is displayed in search result" + userText);
        }
    }


    public void searchByInvalidValidUsername(String username){
        getElement(usernameTextBox).sendKeys(username);
        moveMouseAndClick(submitButton,"Submit Button");
        softAssert.assertTrue(getElement(invalidUserPopup).isDisplayed(),"No records pop is not Displayed");
        softAssert.assertEquals(getElement(recordsCount).getText(),"No Records Found");
        softAssert.assertAll();

    }

    //search by userRole
    public void searchByUserRole(String role){
        moveMouseAndClick(userRoleEntry,"");
        if(role.equalsIgnoreCase("Admin")){
            moveMouseAndClick(adminRoleDropdown,"userRole Admin");

        } else if (role.equalsIgnoreCase("ESS")) {
            moveMouseAndClick(essRoleDropdown,"userRole ESS");
        }
        moveMouseAndClick(submitButton,"Submit Button");

        List<WebElement> userRoleList = getElements(userRoleColumn);
        for (WebElement name : userRoleList) {
            String userRoleText = name.getText();
            Assert.assertEquals(userRoleText,role,"invalid userRole is displayed"+userRoleText);
        }

    }



    public void searchByValidEmployeeName(String employeeName){
        getElement(employeeNameTextBox).sendKeys(employeeName);
        List<WebElement> hints = getElements(employeeNameHints);
        if (!hints.isEmpty()) {
            hints.get(0).click();
        }
        moveMouseAndClick(submitButton,"Submit Button");
        List<WebElement> employeeNameList = getElements(employeeNameColumn);
        for (WebElement name : employeeNameList) {
            String employeeNameText = name.getText();
            Assert.assertEquals(employeeNameText, employeeName,"invalid employeeName is displayed in search result"+employeeNameText);
        }

    }

    public void searchByInvalidEmployeeName(String employeeName){
        getElement(employeeNameTextBox).sendKeys(employeeName);
        moveMouseAndClick(submitButton,"Submit Button");
        Assert.assertEquals(getElement(invalidEmployeeName).getText(),"Invalid");
    }


    public void searchByStatus(String value){
        moveMouseAndClick(statusEntry,"");
        if(value.equalsIgnoreCase("Enabled")){
            moveMouseAndClick(enableDropdown,"userRole Admin");

        } else if (value.equalsIgnoreCase("Disabled")) {
            moveMouseAndClick(disableDropDown,"userRole ESS");
        }
        moveMouseAndClick(submitButton,"Submit Button");

        String records = getElement(recordsCount).getText();
        if(records.contains("No Records Found")){
            Assert.assertTrue(getElement(invalidUserPopup).isDisplayed(),"no records pop is not Displayed");
            return;
        }

        List<WebElement> statusList = getElements(statusColumn);
        for (WebElement name : statusList) {
            String status = name.getText();
            Assert.assertEquals(status,value,"invalid status is displayed"+status);
        }

    }


    public void validateResetButton(){
        int beforeResetCount = getUserRecordsCount();
        clickElement(restButton,"Reset Button");
        int afterResetCount = getUserRecordsCount();
        softAssert.assertEquals(beforeResetCount,afterResetCount,"search results are not reset");
        softAssert.assertEquals(getElement(usernameTextBox).getAttribute("value"),"","username textbox is not empty");
        softAssert.assertEquals(getElement(userRoleEntry).getText(),"-- Select --","userRole Entry is not reset");
        softAssert.assertEquals(getElement(employeeNameTextBox).getAttribute("value"),"","employee Name is not reset");
        softAssert.assertEquals(getElement(statusEntry).getText(),"-- Select --","status  Entry is not reset");

        softAssert.assertAll();

    }






    public void verifyDropdownToggleBehavior() {
        clickElement(topRightDropdown,"Search filter collapse icon");

        softAssert.assertTrue(!getElement(usernameTextBox).isDisplayed(), "Username field should be hidden");
        softAssert.assertTrue(!getElement(userRoleEntry).isDisplayed(), "User Role field should be hidden");
        softAssert.assertTrue(!getElement(employeeNameTextBox).isDisplayed(), "Employee Name field should be hidden");
        softAssert.assertTrue(!getElement(statusEntry).isDisplayed(), "Status field should be hidden");

        clickElement(topRightDropdown,"Search filter collapse icon");

        softAssert.assertTrue(getElement(usernameTextBox).isDisplayed(), "Username field should be visible");
        softAssert.assertTrue(getElement(userRoleEntry).isDisplayed(), "User Role field should be visible");
        softAssert.assertTrue(getElement(employeeNameTextBox).isDisplayed(), "Employee Name field should be visible");
        softAssert.assertTrue(getElement(statusEntry).isDisplayed(), "Status field should be visible");

        softAssert.assertAll();

    }


    public void validateToggleButton(){

        clickElement(topRightDropdown, "Toggle Button");

        softAssert.assertTrue(getElement(filterArea).getAttribute("style").contains("display: none"),
                "Filter area should be hidden, but it is still visible.");


        clickElement(topRightDropdown, "Toggle Button");

        softAssert.assertTrue(!getElement(filterArea).getAttribute("style").isEmpty(),
                "Filter area should be visible, but it is not.");

        softAssert.assertAll();


    }



}