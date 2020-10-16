package test.java.testathon.tests;

import com.aventstack.extentreports.Status;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.WordPressHomePage;
import test.java.testathon.pagefactory.WordPressLoginPage;
import test.java.testathon.utils.Report;


public class UITestWordPressApp extends BaseUITest {

    @Test(enabled = true, dataProvider = "jsonDataProvider")
    public void testWordPressLogin(JSONObject testData) {
        try {
            Report.getTest().log(Status.INFO, "TestData: " + testData.toJSONString());
            String user = testData.get("user").toString();
            String password = testData.get("password").toString();

            WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());

            WordPressHomePage homePage = loginPage.Login(user, password);

            homePage.VerifyUser();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }

    @Test(enabled = true, dataProvider = "jsonDataProvider")
    public void testWordPressInvalidLogin(JSONObject testData) {
        try {
            Report.getTest().log(Status.INFO, "TestData: " + testData.toJSONString());
            String user = testData.get("user").toString();
            String password = testData.get("password").toString();

            WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());

            WordPressHomePage homePage = loginPage.Login(user, password);

            homePage.VerifyUser();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }

}
