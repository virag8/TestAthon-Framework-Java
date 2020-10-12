package test.java.testathon.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WordPressHomePage {

	protected WebDriver driver;

	@FindBy(how = How.ID, using = "wp-admin-bar-my-account")
	private WebElement lblMyAccount;

	public WordPressHomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		if (!driver.getTitle().contains("Dashboard")) {
			throw new IllegalStateException(
					"This is not Home Page of logged in user," + " current page is: " + driver.getTitle());
		}
		PageFactory.initElements(driver, this);
	}

	public void VerifyUser() {
		System.out.println(lblMyAccount.getText());

	}
}
