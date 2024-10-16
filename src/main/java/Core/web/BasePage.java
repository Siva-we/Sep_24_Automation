package Core.web;

import Core.model.Common;
import Core.model.Waits;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Core.reporting.ReportUtil;

import java.lang.reflect.Constructor;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePage {

    public ReportUtil ReportLog = null;
    private WebDriverWait wait = null;

    // objects
    protected WebDriver driver = null;

    // constructors
    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        verifyPageLoad();
    }

    public BasePage(WebDriver driver, ReportUtil ReportLog) {
        this.driver = driver;
        this.ReportLog = ReportLog;
        verifyPageLoad();
    }

    // abstract methods
    protected abstract void verifyPageLoad();

    // properties
    public WebDriver getDriver() {
        return driver;
    }

    // generic method
    public <T extends BasePage> T getGenericObject(Class cls) {
        T t = null;
        try {
            Constructor<?> con = cls.getDeclaredConstructor(WebDriver.class);
            Object obj = con.newInstance(driver);
            t = (T) obj;

        } catch (Exception ex) {
            System.out.println("Unable to initialize Object " + cls.getTypeName());
        }
        return t;
    }

    // action methods
    // Highlight element
    public void highLightElement(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
    }

    // get element by locator
    public WebElement getElement(By locator, String controlName) {
        waitForElementByFluentWait(locator);
        WebElement element = getDriver().findElement(locator);
        highLightElement(locator);
        //ReportUtil.core.test.info("Element found: " + controlName);
        return element;
    }

    public WebElement getElement(By locator) {
        waitForElementByFluentWait(locator);
        WebElement element = getDriver().findElement(locator);
        highLightElement(locator);
        return element;
    }

    protected List<WebElement> getElements(By locator) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Waits.EXPLICIT_WAIT));
        waitForElementByFluentWait(locator);
        List<WebElement> elements = getDriver().findElements(locator);
        return elements;
    }

    // get element by fluent wait
    public void waitForElementByFluentWait(By locator) {
        FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(Waits.EXPLICIT_WAIT))
                .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementByFluentWait(By locator, int timeout) {
        FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // get elements by locator
    public List<WebElement> getElements(By locator, String controlName) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Waits.EXPLICIT_WAIT));
        waitForElementByFluentWait(locator);
        List<WebElement> elements = getDriver().findElements(locator);
        return elements;
    }

    // click element
    public void clickElement(By locator, String controlName) {
        try {
            getElement(locator, controlName).click();
            ReportUtil.INFO("Clicked element:  + controlName");
        } catch (ElementClickInterceptedException ex) {
            hardWait(1);
            jsClickElement(locator, controlName);
        }
        ReportUtil.INFO("Clicked on "+ controlName);
    }

    // click element with wait
    public void clickElement(By locator, String controlName, int waitTimeOut) {
        hardWait(waitTimeOut);
        try {
            getElement(locator, controlName).click();
        } catch (ElementClickInterceptedException ex) {
            hardWait(1);
            jsClickElement(locator, controlName);
        }
        ReportLog.INFO(driver, "User clicked on " + controlName);
    }

    // click element using java script
    public void jsClickElement(By locator, String controlName) {
        try {
            WebElement element = getElement(locator, controlName);
            wait = new WebDriverWait(driver, Duration.ofSeconds(Waits.EXPLICIT_WAIT));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // wait.until(ExpectedConditions.elementToBeClickable(element));
            js.executeScript("arguments[0].click()", element);
        } catch (StaleElementReferenceException ex) {
            hardWait(1);
            clickElement(locator, controlName);
        }
        ReportUtil.INFO("Clicked on "+ controlName);
    }

    // click on element with wait
    public void jsClickElement(By locator, String controlName, int waitTimeOut) {
        try {
            hardWait(waitTimeOut);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Waits.EXPLICIT_WAIT));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", getElement(locator, controlName));
        } catch (StaleElementReferenceException ex) {
            hardWait(1);
            clickElement(locator, controlName);
        }
        ReportUtil.INFO("Clicked on "+ controlName);
    }

    // click element using actions
    public void actionsClickElement(By locator, String controlName) {
        Actions actions = new Actions(driver);
        actions.click(getElement(locator, controlName));
        ReportUtil.INFO("Clicked on :" + controlName);
    }

    public void actionsClickElement(By locator, String controlName, int waitTimeOut) {
        waitForElementByFluentWait(locator);
        Actions actions = new Actions(driver);
        actions.click(getElement(locator, controlName));
        ReportUtil.INFO("clicked on " + controlName);
    }

    public void rightClickElement(By locator, String controlName) {
        Actions actions = new Actions(driver);
        actions.contextClick(getElement(locator, controlName)).perform();
        ReportUtil.INFO("Right clicked on " + controlName);
    }

    // move mouse and click
    public void moveMouseAndClick(By locator, String controlName) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(locator, controlName)).click().perform();
        ReportUtil.INFO("Move mouse and click on "+ controlName);
    }

    // enter text into text box
    public void enterText(By locator, String content, String controlName) {
        Assert.assertTrue(getElement(locator, controlName).isEnabled(), controlName + " is not enabled");
        getElement(locator, controlName).sendKeys(content);
        ReportUtil.INFO("Entering text in "+ controlName +" as: " + content);
    }

    // verify staleness of element
    protected void verifyStalenessOfElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Waits.EXPLICIT_WAIT));
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
    }

    // clear text in tex tbox
    public void clearText(By locator, String controlName) {
        Assert.assertTrue(getElement(locator, controlName).isEnabled(), controlName + " is not enabled");
        getElement(locator, controlName).clear();
    }

    // select dropdown option
    public void selectDropDownOption(By locator, String option, Common.DROPDOWN mode, String controlName) {
        try {
            Select select = new Select(getElement(locator, controlName));
            switch (mode.toString().toUpperCase())
            {
                case "BYTEXT":
                    select.selectByVisibleText(option);
                    break;
                case "BYVALUE":
                    select.selectByValue(option);
                    break;
                case "BYINDEX":
                    select.selectByIndex(Integer.valueOf(option));
                    break;
                case "RANDOMOPTION":
                    int options = select.getOptions().size() - 1;
                    select.selectByIndex(options);
                    break;
            }
        } catch (StaleElementReferenceException ex) {
            clickElement(locator, controlName);
        }
    }

    // select dropdown option by partial text
    public void selectDropDownOptionByPartialText(By locator, String partialText, String controlName) {
        try {
            WebElement dropdown = getElement(locator, controlName);
            Select select = new Select(dropdown);
            List<WebElement> options = dropdown
                    .findElements(By.xpath("//option[contains(text(),'" + partialText + "')]"));
            int randomNumber = options.size() - 1;
            options.get(randomNumber).click();
        } catch (StaleElementReferenceException ex) {
            clickElement(locator, controlName);
        }
        // ReportLog.INFO(driver, controlName + " is: " + randomNumer);
    }

    // verify element present or not on the page
    protected boolean IsElementPresent(By by, String controlName) {
        boolean elementPresent = true;
        WebElement ele;
        // ReportLog.INFO(driver, "Verifying element availability: " + controlName);
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(Waits.EXPLICIT_WAIT));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception ex) {
            elementPresent = false;
        }
        // ReportLog.PASS(controlName + " element is available");
        return elementPresent;
    }

    // Get random string length
    protected String getRandomString(int length) {
        String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    protected String getRandomString(int length, String collection) {
        String AB = null;

        if(collection.equals(Common.RANDOMTEXT.Alpha)) {
            AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        } else if(collection.equals(Common.RANDOMTEXT.Numeric)) {
            AB = "1234567890";
        }  else if(collection.equals(Common.RANDOMTEXT.AlphaNumeric)) {
            AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        }
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    // Get random number length, value less than 50
    protected String getRandomNumber(int length) {
        String AB = "1234567890";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    // switch to window by Title / index
    public void switchToWindow(String content, String mode) {

        Object[] handles = driver.getWindowHandles().toArray();
        switch (mode.toString().toUpperCase()) {
            case "INDEX":
                driver.switchTo().window(handles[Integer.valueOf(content)].toString());
                // driver.switchTo().frame(locater);
                break;
            case "TITLE":
                for (Object each : handles) {
                    String title = driver.switchTo().window(each.toString()).getTitle();
                    if (title.equals(content)) {
                        break;
                    }
                }
                break;
        }
    }

    // switch to frame by locator
    public void switchToFrame(WebElement locator, Common.BROWSER_WINDOW mode) {
        Object[] handles = driver.getWindowHandles().toArray();
        switch (mode.toString().toUpperCase()) {
            case "INDEX":
                driver.switchTo().frame(locator);
                break;
        }
    }

    //switch to alert
    public Alert switchToAlert() {
       return driver.switchTo().alert();
    }

    //alert handling
    // verify alert availability
    public boolean isAlertPresent(boolean alertPresent) {
        return driver.switchTo().alert() == null ? false : true;
    }

    // get alert text
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    // switch accept alert and accept/OK
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    // cancel alert
    public void cancelAlert() {
        driver.switchTo().alert().dismiss();
    }

    // enter text into alert
    public void enterTextInToAlert(String text) {
        driver.switchTo().alert().sendKeys(text);
    }

    // hard wait
    public void hardWait(int seconds) {
        try {
            for (int iteration = 0; iteration < seconds; iteration++)
                if (((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                        .equals("comeplete")) {
                    break;
                }
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
        }
    }

    // wait for element visibility
    protected void waitForElementVisibility(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Waits.EXPLICIT_WAIT));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    // scroll to element
    protected void scrollToElement(By locator, String controlName) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // check element present
    protected boolean isElementVisible(By locator) {
        boolean found = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Waits.EXPLICIT_WAIT));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            found = true;
        } catch (Exception ex) {
            // skip this statement
        }
        return found;
    }

    // Uploading Files
    public void UploadingFile(By locatorName, String fileName, String filePath) {
        WebElement upload_file = getElement(locatorName, "File uploading");
        String fileLocation = System.getProperty("user.dir") + "//TestData//" + fileName + ".xlsx";
        upload_file.sendKeys(fileLocation);
    }

    //Drag i notification
    public void dragAndDropElement(By from, By to) {

        // Element which needs to drag.
        WebElement From = getElement(from);

        // Element on which need to drop.
        WebElement To = getElement(to);

        // Using Action class for drag and drop.
        Actions act = new Actions(driver);

        // Dragged and dropped.
        act.dragAndDrop(From, To).build().perform();

    }

    protected List<String> getTextFromList(List<WebElement> list){
        List<String> listString = new ArrayList<>();
        list.stream().forEach(x -> listString.add(x.getText()));
        return listString;
    }
}
