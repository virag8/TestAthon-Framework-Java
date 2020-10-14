package test.java.testathon.selenium;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

	String browser = "";
	Map<String, String> browserParams;

	private DriverFactory() {
		// Do-nothing..Do not allow to initialize this class from outside
		browserParams = new HashMap<String, String>();
	}

	private static DriverFactory instance = new DriverFactory();

	public static DriverFactory getInstance(String browser, Map<String, String> browserParams) {
		instance.browser = browser;
		instance.browserParams=browserParams;
		return instance;
	}

	ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() // thread local driver object for webdriver
	{
		@Override
		protected WebDriver initialValue() {
			return BrowserFactory.Launch(instance.browser, instance.browserParams); // can be replaced with other browser drivers
		}
	};

	public WebDriver getDriver() // call this method to get the driver object and launch the browser
	{
		return driver.get();
	}

	public void removeDriver() // Quits the driver and closes the browser
	{
		driver.get().quit();
		driver.remove();
	}
}
