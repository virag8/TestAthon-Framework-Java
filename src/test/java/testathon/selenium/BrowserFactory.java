package test.java.testathon.selenium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import test.java.testathon.utils.Report;

public class BrowserFactory {
	public static WebDriver Launch(String env) {
		WebDriver driver = null;
		// TODO Auto-generated constructor stub
		switch (env.toLowerCase()) {
		case "chrome":
			driver = BrowserFactory.chromelaunch();
			break;
		case "chrome-emulator":
			driver = BrowserFactory.chromeEmulatorlaunch();
			break;
		case "chrome-remote":
			driver = BrowserFactory.chromeRemotelaunch();
			break;
		default:
			break;
		}
		return driver;
	}

	public static WebDriver chromeEmulatorlaunch() {

		WebDriverManager.chromedriver().setup();

		Map<String, String> mobileEmulation = new HashMap<String, String>();

		mobileEmulation.put("deviceName", "Nexus 5");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		WebDriver driver = new ChromeDriver(chromeOptions);
		System.out.println("driver launched: " + driver);

		return driver;
	}

	public static WebDriver chromelaunch() {

		// System.setProperty("webdriver.chrome.driver", "resources/chromedriver_mac");
		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();
		System.out.println("driver launched: " + driver);

		return driver;
	}

	public static WebDriver chromeRemotelaunch() {

		final String USERNAME = "virag1";
		final String AUTOMATE_KEY = "UzxmJ8R6k7dUmiY3gYT7";
		final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("os", "Windows");
		caps.setCapability("os_version", "10");
		caps.setCapability("browser", "Chrome");
		caps.setCapability("browser_version", "80");
		caps.setCapability("name", "virag1's First Test");

		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(URL), caps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("driver launched: " + driver);

		return driver;
	}

	public static boolean getScreenshotOfCurrentScreenAndSaveWith(String name, WebDriver driver, ExtentTest logger) {
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Path screenshotFilePath = Paths.get(System.getProperty("user.dir"), "test-output", name + ".png");

		try {
			File screenshot = new File(screenshotFilePath.toString());
			FileHandler.copy(screenshotFile, screenshot);
			String path = "<img src=\"" + screenshot.toString() + "\"/>";
			Report report = Report.getInstance();
			report.addScreenCapture(screenshot.toString(), logger);
			Reporter.log(path);

			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
