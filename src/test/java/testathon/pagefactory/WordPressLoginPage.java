package test.java.testathon.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WordPressLoginPage {

	protected WebDriver driver;

	@FindBy(how = How.ID, using = "wp-submit")
	private WebElement btnSubmit;

	@FindBy(how = How.ID, using = "user_login")
	private WebElement tbUserLogin;

	@FindBy(how = How.ID, using = "user_pass")
	private WebElement tbUserPassword;

	public WordPressLoginPage(WebDriver driver) {
		this.driver = driver;
		// TODO Auto-generated constructor stub
		driver.get("https://s1.demo.opensourcecms.com/wordpress/wp-login.php");
		PageFactory.initElements(driver, this);
	}

	public WordPressHomePage Login(String userId, String password) {

		tbUserLogin.sendKeys(userId);

		tbUserPassword.sendKeys(password);

		btnSubmit.click();

		return new WordPressHomePage(driver);
	}
}
