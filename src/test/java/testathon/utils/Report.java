package test.java.testathon.utils;

import java.io.IOException;
import java.time.LocalDateTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report {

	private Report() {

	}

	private ExtentReports extent;
	static ThreadLocal<ExtentTest> extentTestThreadLocal;
	ExtentTest logger;
	static Report report;

	static {
		report = new Report();
		report.init();
	}

	public void init() {
		ExtentSparkReporter htmlReporter;
		htmlReporter = new ExtentSparkReporter("test-output/testreport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle("Testing report");
		htmlReporter.config().setReportName("Report " + LocalDateTime.now());
		htmlReporter.config().setTheme(Theme.DARK);
		extentTestThreadLocal = new ThreadLocal<ExtentTest>();

	}

	public static void setTest(ExtentTest test) {

		extentTestThreadLocal.set(test);
	}

	public static synchronized ExtentTest getTest() {
		return extentTestThreadLocal.get();
	}

	public void finishReport() {
		extent.flush();
	}

	public static synchronized Report getInstance() {
		return report;
	}

	public ExtentTest createExtent(String testName) {
		return extent.createTest(testName);
	}

	public void log(Status status, String message, ExtentTest logger) {
		logger.log(status, message);
	}

	public void addScreenCapture(String path, ExtentTest logger) throws IOException {
		logger.addScreenCaptureFromPath(path);
	}
}
