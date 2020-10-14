package test.java.testathon.tests;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.util.Strings;
import test.java.testathon.pagefactory.WordPressHomePage;
import test.java.testathon.pagefactory.WordPressLoginPage;
import test.java.testathon.selenium.DriverFactory;

import java.util.HashMap;
import java.util.Map;


public class TestWordPressApp {

    DriverFactory driverInstance = null;
    String env = null;
    Map<String, String> envParams = null;

    @BeforeTest
    @Parameters({"env", "env_params"})
    public void beforeTest(@Optional("chrome") String Env, @Optional("") String EnvParams) {
        try {
            env = Env;
            envParams = convertToMap(EnvParams);
            Reporter.log("Running tests for env " + env);

        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

    }

    private static Map<String, String> convertToMap(String envParam) {
        Map<String, String> envMapParams = new HashMap<>();
        if (Strings.isNotNullAndNotEmpty(envParam)){
			String[] envParams = envParam.split(",");
			for (String param : envParams) {
				String[] params = param.split("=");
				envMapParams.put(params[0], params[1]);
			}
		}
        return envMapParams;
    }

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        try {
            driverInstance = DriverFactory.getInstance(env, envParams);
            context.setAttribute("env", env);
            context.setAttribute("envParams", envParams.toString());
            context.setAttribute("webDriver", driverInstance.getDriver());
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

    }

    @Test(enabled = true)
    public void testWordPressLogin() {
        try {
            System.out.println("testWordPressLogin: " + env);

            WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());

            WordPressHomePage homePage = loginPage.Login("opensourcecms", "opensourcecms");

            homePage.VerifyUser();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }

    @Test(enabled = true)
    public void testWordPressInvalidLogin() {
        try {
            System.out.println("testWordPressInvalidLogin");

            WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());

            WordPressHomePage homePage = loginPage.Login("opensourcecms", "opensourcecms1");

            homePage.VerifyUser();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }

    @AfterMethod
    public void afterMethod() {
        try {
            driverInstance.removeDriver();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }
}
