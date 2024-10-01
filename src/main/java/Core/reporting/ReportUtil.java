package Core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportUtil {

    private static ExtentReports extent;
    public static ExtentTest test;
    private String reportPath = "";
    public String reportScreenshot = "";
    private ExtentHtmlReporter htmlReporter;
    List<String> testNames = new ArrayList<>();


    public void initializeReport(String reportName, String reportPath, String projectName) {
        if(extent == null) {
            htmlReporter = new ExtentHtmlReporter(reportPath+"\\"+reportName+"_"+getDateTimeString()+".html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            htmlReporter.config().setChartVisibilityOnOpen(true);
            htmlReporter.config().setDocumentTitle("Automation Report");
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        }
    }

    public static String getDateTimeString() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static void addTestToReport(String packageName, String methodName) {
        test = extent.createTest(methodName);
    }

    public static void flushReport() {
        extent.flush();
    }

    public static void PASS(String message) {
        test.log(Status.PASS, message);
    }


    public static void PASS(WebDriver driver, String message) {
        if (Boolean.valueOf(System.getProperty("screenshots"))) {
            try {
                test.log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver)).build());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PASS(message);
    }

    public static void FAIL(String message) {
        test.log(Status.FAIL, message);
    }

    public static void FAIL(WebDriver driver, String message) {
        try {
            test.log(Status.FAIL, message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver)).build());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FAIL(message);
    }

    public static void INFO(String message) {
        test.log(Status.INFO, message);
    }

    public static void INFO(WebDriver driver, String message) {
        if (Boolean.valueOf(System.getProperty("screenshots"))) {
            try {
                test.log(Status.INFO, message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver)).build());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        INFO(message);
    }

    public  static  String takeScreenShot(WebDriver driver) {
        String file = ".\\Reports\\" + "Screenshots\\" + getDateTimeString() + ".jpg";
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);

            // Call getScreenshotAs method to create image file

            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

            // Move image file to new destination
            File DestFile = new File(file);

            // Copy file at destination

            FileUtils.copyFile(SrcFile, DestFile);

        } catch (Exception ex) {
            System.out.println("Not able to take screenshot");
            System.out.println(ex.toString());
        }
        return file.replace("Reports\\", "");
    }
}
