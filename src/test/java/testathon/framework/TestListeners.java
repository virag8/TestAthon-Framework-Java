package test.java.testathon.framework;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import test.java.testathon.selenium.BrowserFactory;
import test.java.testathon.utils.Report;

import java.util.UUID;

public class TestListeners implements ITestListener {

    Report report = Report.getInstance();
    ExtentTest logger;

    public void onTestStart(ITestResult result) {
        String testName = result.getName() + "@" + UUID.randomUUID().toString().replace("-", "");
        ITestContext context = result.getTestContext();
        String env = (String) context.getAttribute("env");
        String envParams = (String) context.getAttribute("envParams");
        logger = report.createExtent(testName + " [" + env + "]");
        Report.setTest(logger);
        Report.getTest().log(Status.INFO, "TEST STARTED: " + result.getInstance().toString());
        Report.getTest().log(Status.INFO, "Environment: " + env + "//EnvParams: " + envParams);
    }

    public void onTestSuccess(ITestResult result) {
        Report.getTest().log(Status.INFO, "TEST SUCCESSFUL:");
    }

    public void onTestFailure(ITestResult result) {
        String testName = result.getName() + "@" + UUID.randomUUID().toString().replace("-", "");
        ITestContext context = result.getTestContext();
        String env = (String) context.getAttribute("env");
        WebDriver driver = (WebDriver) context.getAttribute("webDriver");
        if (Report.getTest() == null) {
            logger = report.createExtent(testName + " Setup [" + env + "]");
            Report.setTest(logger);
        }
        Report.getTest().log(Status.FAIL, result.getThrowable().getMessage());
        Report.getTest().log(Status.FAIL, "TEST FAILED");
        if (driver != null) {
            BrowserFactory.getScreenshotOfCurrentScreenAndSaveWith(testName, driver, Report.getTest());
        }

    }

    public void onTestSkipped(ITestResult result) {
        ITestContext context = result.getTestContext();
        String env = (String) context.getAttribute("env");
        String testName = result.getName() + "@" + result.getInstance().toString().split("@")[1];

        logger = report.createExtent(testName + " [" + env + "]");
        Report.setTest(logger);
        Report.getTest().log(Status.WARNING, "TEST SKIPPED");
        Report.getTest().log(Status.INFO, "BROWSER: " + env);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Report.getTest().log(Status.WARNING, "TEST FAILED WITH Warnings");
    }

    public void onStart(ITestContext context) {
        report = Report.getInstance();
    }

    public void onFinish(ITestContext context) {
        report.finishReport();
    }

}
