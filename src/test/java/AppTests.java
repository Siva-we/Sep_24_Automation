import Pages.ElementsPage;
import Pages.HomePage;
import Pages.TextBoxPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class AppTests {

    WebDriver driver = null;

    @BeforeSuite
    public void beforeSuite(){ System.out.println("beforeSuite"); }

    @BeforeTest
    public void beforeTest(){  System.out.println("beforeTest");  }

    @BeforeClass
    public void beforeClass(){ System.out.println("beforeClass"); }

    @BeforeMethod
    //@Parameters({"env"})
    public void beforeMethod( ){
        String environment = System.getProperty("env");
        System.out.println("beforeMethod");
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod");
        driver.quit();
    }

    @AfterClass
    public void afterClass(){ System.out.println("afterClass"); }

    @AfterTest
    public void afterTest(){ System.out.println("afterTest"); }

    @AfterSuite
    public void afterSuite(){ System.out.println("afterSuite"); }

    @Test(priority = 0, invocationCount = 5, groups = {"smoke", "orders"} )
    public static void validateionMenuOptions(){
        System.out.println("validateionMenuOptions");
    }

    @Test(priority =1)
    public static void validateSuccessMessage(){
        System.out.println("validateSuccessMessage");
    }

    @Test(priority = 2)
    public static void validateAppHomePage(){
        System.out.println("validateAppHomePage");
        Integer.valueOf("sadkjf");
    }

    @Test
    public void ValidateFormSubmissionFromTextboxPage(){
        HomePage homePage = new HomePage();
        homePage.clickOnElements();

        ElementsPage elementsPage = new ElementsPage();
        elementsPage.selectTextBox();

        TextBoxPage textBoxPage = new TextBoxPage();
        textBoxPage.submitForm();
    }



}
