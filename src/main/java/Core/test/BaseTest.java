package Core.test;

import com.aventstack.extentreports.Status;
import Core.model.Common;
import Core.model.Waits;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Core.reporting.ReportUtil;
import Core.web.BasePage;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver = null;
    static Properties envData = null;
    private ReportUtil report = null;
    String projectPath =System.getProperty("user.dir");
    String browserName = null;
    String browserVersion = null;

    @SuppressWarnings("deprecation")
    @BeforeSuite
    public void beforeSuite() throws IOException {
        //load environment data from file
        loadEnvironmentData(System.getProperty("env"));
        Waits.IMPLICIT_WAIT = Integer.valueOf(Common.getEnvData("implicitwait"));
        Waits.EXPLICIT_WAIT = Integer.valueOf(Common.getEnvData("explicitwait"));
        Waits.PAGE_WAIT = Integer.valueOf(Common.getEnvData("pagewait"));

        //load application static data from file
        //loadApplicationStaticData();

        report =new ReportUtil();
        report.initializeReport(System.getProperty("env")+"_Report", System.getProperty("user.dir")+"\\Reports", "");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException {
        ReportUtil.flushReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        browserName = String.valueOf(System.getProperty("browser"));
        Test test = method.getAnnotation(Test.class);
        String testName = test.testName();
        if(testName != null && !testName.isEmpty()) {
            ReportUtil.addTestToReport(this.getClass().getName(), test.testName());
        } else {
            ReportUtil.addTestToReport(this.getClass().getName(), method.getName());
        }
        openBrowser(browserName);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, Method method) {
        System.out.println(this.getClass().getName() + " After Method");
        Test test = method.getAnnotation(Test.class);

        if (result.getStatus() == ITestResult.SUCCESS) {
            ReportUtil.PASS(driver, "Test Passed");
            System.out.println(result.getMethod().getMethodName() + ": Test is PASSED");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            ReportUtil.FAIL(driver, result.getThrowable().getMessage());
            System.out.println(result.getMethod().getMethodName() + ": Test is FAILED");
            ReportUtil.FAIL("Test failed");
        } else if (result.getStatus() == ITestResult.SKIP) { // Test passed with out any interruption
            ReportUtil.FAIL(driver, result.getThrowable().getMessage());
            System.out.println(result.getMethod().getMethodName() + ": Test is SKIPPED");
            //ReportUtil.FAIL(driver, result.getThrowable().getLocalizedMessage());
            ReportUtil.FAIL("Test is Skipped");
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ex) {

            }
        }
    }

    // loading environment data from core.test/resources
    void loadEnvironmentData(String environment) {

        try {
            FileReader reader = new FileReader(projectPath + "\\src\\test\\resources\\" + environment + ".properties");
            envData = new Properties();
            envData.load(reader);
            Common.setEnvData(envData);
        } catch (Exception ex) {
            System.out.println("Error occurred while reading environment file");
        }
    }

    // loading application static data
    void loadApplicationStaticData() {

        try {
            FileReader reader = new FileReader(projectPath + "\\src\\main\\resources\\app.properties");
            Properties appData = new Properties();
            appData.load(reader);
            Common.setAppData(appData);
        } catch (Exception ex) {
            System.out.println("Error occurred while reading application static data file");
        }
    }

    // open browser with specified version in pom.xml
    // adding implicit wait to driver and maximize browser
    void openBrowser(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                //WebDriverManager.chromedriver().setup();
                //ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--remote-allow-origins=*");
                // WebDriverManager.chromedriver().setup();

                // download the file in project folder
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("plugins.always_open_pdf_externally", true);

                String downloadedFile = System.getProperty("user.dir");
                chromePrefs.put("download.default_directory", downloadedFile + "\\Downloads");
                //chromeOptions.setExperimentalOption("prefs", chromePrefs);
                // System.setProperty("webdriver.chrome.driver", driverPath);
                driver = new ChromeDriver();
                break;
            case "firefox": driver = new FirefoxDriver();
            case "safari": driver = new SafariDriver();
            case "edge": driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        // Report.PASS("Browser initialization is completetd");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Waits.IMPLICIT_WAIT));
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        browserVersion = caps.getBrowserVersion();
        System.out.println("BROWSER VERSION: " + browserVersion);
        String driverVersion = caps.getCapability("chrome").toString().split(" ")[0].split("=")[1];//
        System.out.println("DRIVER VERSION: " + driverVersion);
        ReportUtil.test.log(Status.INFO, "Opened " + browserName + ": " +browserVersion + "- browser");
    }

    // to get generic object as return type
    protected <T extends BasePage> T launchApplication(Class cls) {
        String appUrl = String.valueOf(Common.getEnvData("appurl"));
        driver.get(appUrl);
        report.PASS("Application is launched: " + appUrl + " on " + browserName + "(" + browserVersion
                + ")");
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
}
