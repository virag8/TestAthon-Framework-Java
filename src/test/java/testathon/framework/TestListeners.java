package test.java.testathon.framework;

import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import test.java.testathon.selenium.BrowserFactory;
import test.java.testathon.utils.Report;

public class TestListeners implements ITestListener {

	Report report = Report.getInstance();
	ExtentTest logger;

	public void onTestStart(ITestResult result) {
		String testName = result.getName() + "@" + UUID.randomUUID().toString().replace("-", "");
		ITestContext context = result.getTestContext();
		String env = (String) context.getAttribute("env");
		logger = report.createExtent(testName + " [" + env + "]");
		Report.setTest(logger);
		Report.getTest().log(Status.INFO, "TEST STARTED: " + result.getInstance().toString());
		Report.getTest().log(Status.INFO, "BROWSER: " + env);
	}

	public void onTestSuccess(ITestResult result) {
		Report.getTest().log(Status.INFO, "TEST SUCCESSFUL:");
	}

	public void onTestFailure(ITestResult result) {
		String testName = result.getName() + "@" + UUID.randomUUID().toString().replace("-", "");
		Report.getTest().log(Status.FAIL, result.getThrowable().getMessage());
		Report.getTest().log(Status.FAIL, "TEST FAILED");
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("webDriver");
		BrowserFactory.getScreenshotOfCurrentScreenAndSaveWith(testName, driver, Report.getTest());
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
