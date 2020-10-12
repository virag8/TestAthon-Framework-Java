package test.java.testathon.tests;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.java.testathon.pagefactory.WordPressHomePage;
import test.java.testathon.pagefactory.WordPressLoginPage;
import test.java.testathon.selenium.DriverFactory;


public class TestWordPressApp {

	DriverFactory driverInstance = null;
	String env = null;

	@BeforeTest
	@Parameters({ "env" })
	public void beforeTest(String Env) {
		try {
			env = Env;
			Reporter.log("Running tests for env " + env);

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	@BeforeMethod
	public void beforeMethod(ITestContext context) {
		try {
			driverInstance = DriverFactory.getInstance(env);
			context.setAttribute("env", env);
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
