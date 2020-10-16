package test.java.testathon.selenium;

import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import test.java.testathon.utils.PropertiesUtils;
import test.java.testathon.utils.Report;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BrowserFactory {
    static Properties prop = null;

    public static WebDriver Launch(String env, Map<String, String> browserParams) {
        WebDriver driver = null;
        prop = PropertiesUtils.load("src/main/resources/env.properties");
        // TODO Auto-generated constructor stub
        switch (env.toLowerCase()) {
            case "local-chrome":
                driver = BrowserFactory.chromeLaunch(browserParams);
                break;
            case "local-chrome-emulator":
                driver = BrowserFactory.chromeEmulatorLaunch(browserParams);
                break;
            case "remote-cloud":
                driver = BrowserFactory.RemoteBSLaunch(browserParams);
                break;
            case "remote-grid":
                driver = BrowserFactory.RemoteGridLaunch(browserParams);
                break;
            default:
                try {
                    throw new Exception("UNSUPPORTED ENVIRONMENT: " + env);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return driver;
    }

    public static WebDriver chromeEmulatorLaunch(Map<String, String> browserParams) {

        WebDriverManager.chromedriver().setup();

        Map<String, String> mobileEmulation = new HashMap<>();

        mobileEmulation.put("deviceName", browserParams.get("deviceName"));

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        WebDriver driver = new ChromeDriver(chromeOptions);
        System.out.println("driver launched: " + driver);

        return driver;
    }

    public static WebDriver chromeLaunch(Map<String, String> browserParams) {

        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        System.out.println("driver launched: " + driver);

        return driver;
    }

    public static WebDriver RemoteBSLaunch(Map<String, String> browserParams) {

        final String USERNAME = prop.getProperty("bs.username");
        final String AUTOMATE_KEY = prop.getProperty("bs.automation_key");
        final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("os", browserParams.get("os"));
        caps.setCapability("os_version", browserParams.get("os_version"));
        caps.setCapability("browser", browserParams.get("browser"));
        caps.setCapability("browser_version", browserParams.get("browser_version"));

        caps.setCapability("name", browserParams.get("name"));

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

    public static WebDriver RemoteGridLaunch(Map<String, String> browserParams) {

        final String URL = prop.getProperty("se.grid_url");

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setBrowserName(browserParams.get("browser"));


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
