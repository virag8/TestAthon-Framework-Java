package test.java.testathon.framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.util.Strings;
import test.java.testathon.selenium.DriverFactory;
import test.java.testathon.utils.IO;

import java.util.HashMap;
import java.util.Map;

public class BaseUITest {
    protected DriverFactory driverInstance = null;
    protected String env = null;
    protected Map<String, String> envParams = null;

    @BeforeTest
    @Parameters({"env", "env_params"})
    public void beforeTest(@Optional("chrome") String Env, @Optional("") String EnvParams) {
        try {
            jsonDataProvider();
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
        if (Strings.isNotNullAndNotEmpty(envParam)) {
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

    @AfterMethod
    public void afterMethod() {
        try {
            driverInstance.removeDriver();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }

    @DataProvider(name = "jsonDataProvider")
    public Object[][] jsonDataProvider() {
        String testName = "testWordPressLogin";
        JSONObject testDataList = IO.loadJSON("src/main/resources/testdata.json");
        JSONArray testDataArray = (JSONArray) testDataList.get(testName);
        Object[][] returnValue = new Object[testDataArray.size()][1];
        int index = 0;
        for (Object o : testDataArray) {
            JSONObject data = (JSONObject) o;
            returnValue[index++][0] = data;
        }
        return returnValue;
    }
}
