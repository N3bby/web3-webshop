package view;

import static org.junit.Assert.*;

import java.util.ArrayList;

import db.TestCleanup;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import domain.Person;
import view.page.AddProductPage;
import view.page.AddUserPage;
import view.page.HomePage;
import view.page.PersonOverviewPage;

public class LoginTest {

	WebDriver driver;
	private final String DEFAULT_FIRST_NAME = "Delete";
	private final String DEFAULT_LAST_NAME = "Me";
	private final String EMAIL_SUFFIX = "@shop.com";
	private final String DEFAULT_PASSWORD = "1234";

	@Before
	public void setUp() {
        System.setProperty("webdriver.chrome.driver", GlobalTestingVars.DRIVER_CHROME);
		driver = new ChromeDriver();
	}

	@After
	public void clean() {
		TestCleanup.cleanTestPersons(TestIdGenerator.ID_PREFIX_ESCAPED);
		((ChromeDriver)driver).getSessionStorage().clear(); //Dunno if this is needed, but it's a good idea
		driver.quit();
	}

	private String addContextPerson() {

		String userId = TestIdGenerator.generateTestId("userToDelete");

		AddUserPage addUserPage = new AddUserPage(driver);
		addUserPage.submitFormNormal(userId, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, userId + EMAIL_SUFFIX, DEFAULT_PASSWORD);

		return userId;

	}

	@Test
	public void testCorrectLoginHomepageWelcomeTextLogoutButton() {

		String userId = addContextPerson();

		HomePage homePage = new HomePage(driver);
		homePage.login(userId, DEFAULT_PASSWORD);

		assertTrue(homePage.isCurrentPage());

		checkIsLoggedInWithCorrectMessage(homePage);

	}

	@Test
	public void testStillLoggedInWhenVisitedOtherPage() {

		String userId = addContextPerson();

		HomePage homePage = new HomePage(driver);
		homePage.login(userId, DEFAULT_PASSWORD);

		PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
		AddProductPage addProductPage = new AddProductPage(driver);

		homePage = new HomePage(driver);

		checkIsLoggedInWithCorrectMessage(homePage);

	}

	private void checkIsLoggedInWithCorrectMessage(HomePage homePage) {

		assertTrue(homePage.isLoggedIn());

		assertNotNull(homePage.getLoggedInMessage());
		assertEquals(homePage.getLoggedInMessage(), "Welcome, " + DEFAULT_FIRST_NAME + "!");

	}

}
