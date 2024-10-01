package Core.test;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    public static int retryLimit ;

    static {
        retryLimit = Integer.valueOf(System.getProperty("retryLimit"));
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (counter < retryLimit) {
                counter++;
                return true;
            }
        }
        return false;
    }

}