package testathon.selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Stopwatch;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Selenium {
	public static int timeoutSec = 5;

	public static WebDriver driver = null;

	public static WebDriver chromeEmulatorlaunch() {

		// System.setProperty("webdriver.chrome.driver", "resources/chromedriver_mac");
		WebDriverManager.chromedriver().setup();

		Map<String, String> mobileEmulation = new HashMap<String, String>();

		mobileEmulation.put("deviceName", "Nexus 5");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		// WebDriver driver = new ChromeDriver(chromeOptions);

		driver = new ChromeDriver(chromeOptions);
		System.out.println("driver launched: " + driver);

		return driver;
	}

	public static WebDriver chromelaunch() {

		// System.setProperty("webdriver.chrome.driver", "resources/chromedriver_mac");
		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		System.out.println("driver launched: " + driver);

		return driver;
	}

	public static void quit() {
		driver.quit();
	}

	@SuppressWarnings("finally")
	public static WebElement getElement(By by) {
		Stopwatch sw = Stopwatch.createStarted();
		WebElement element = null;
		try {
			while (driver.findElements(by).isEmpty()) {
				try {
					Thread.sleep(1000);
					if (sw.elapsed(TimeUnit.SECONDS) > timeoutSec)
						return null;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
			element = wait.until(ExpectedConditions.elementToBeClickable(by));

		} finally {
			sw.stop();

			return element;
		}

	}

	public static File screenshot() {
		File destFile = null;
		String pattern = "dd-MMM-yyyy-H-m-s";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				destFile = new File("test-output//SeleniumDriver_screenshot_" + date + ".png");
				Files.move(screenshotFile, destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (WebDriverException w) {
		}

		return destFile;
	}
}
