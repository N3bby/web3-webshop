package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import db.TestCleanup;
import domain.Person;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import view.page.AddUserPage;
import view.page.HomePage;
import view.page.PersonOverviewPage;

public class RegisterTest {
	private WebDriver driver;
	
	@Before
	public void setUp() {
        System.setProperty("webdriver.chrome.driver", GlobalTestingVars.DRIVER_CHROME);
		driver = new ChromeDriver();
	}
	
	@After
	public void clean() {
		driver.quit();
        TestCleanup.cleanTestPersons(TestIdGenerator.ID_PREFIX_ESCAPED);
	}
	
	private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
		return TestIdGenerator.generateTestId(component);
	}

	@Test
	public void testRegisterCorrect() {

        String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");

        AddUserPage addUserPage = new AddUserPage(driver);
        HomePage homePage = addUserPage.submitFormNormal(useridRandom, "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

        assertTrue(homePage.isCurrentPage());

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        List<Person> accounts = personOverviewPage.getAccounts();

        boolean found = false;
        for(Person person : accounts) {
            if(person.getEmail().trim().equals("jan.janssens@hotmail.com")) found = true;
        }
        assertTrue(found);
		
	}

	@Test
	public void testRegisterUseridEmpty(){

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormExpectingErrors("", "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

        assertTrue(addUserPage.isCurrentPage());

        List<String> errors = addUserPage.getErrors();
        assertEquals(errors.size(), 1);
        assertTrue(errors.contains("No userid given"));

        assertEquals(addUserPage.getUserIdText(), "");
        assertEquals(addUserPage.getFirstNameText(), "Jan");
        assertEquals(addUserPage.getLastNameText(), "Janssens");
        assertEquals(addUserPage.getEmailText(), "jan.janssens@hotmail.com");

	}

	@Test
	public void testRegisterFirstNameEmpty(){

        AddUserPage addUserPage = new AddUserPage(driver);

        addUserPage.submitFormExpectingErrors("jakke", "", "Janssens", "jan.janssens@hotmail.com", "1234");

        assertTrue(addUserPage.isCurrentPage());

        List<String> errors = addUserPage.getErrors();
        assertEquals(errors.size(), 1);
        assertTrue(errors.contains("No firstname given"));

        assertEquals(addUserPage.getUserIdText(), "jakke");
        assertEquals(addUserPage.getFirstNameText(), "");
        assertEquals(addUserPage.getLastNameText(), "Janssens");
        assertEquals(addUserPage.getEmailText(), "jan.janssens@hotmail.com");

	}

	@Test
	public void testRegisterLastNameEmpty(){

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormExpectingErrors("jakke", "Jan", "", "jan.janssens@hotmail.com", "1234");

        assertTrue(addUserPage.isCurrentPage());

        List<String> errors = addUserPage.getErrors();
        assertEquals(errors.size(), 1);
        assertTrue(errors.contains("No last name given"));

        assertEquals(addUserPage.getUserIdText(), "jakke");
        assertEquals(addUserPage.getFirstNameText(), "Jan");
        assertEquals(addUserPage.getLastNameText(), "");
        assertEquals(addUserPage.getEmailText(), "jan.janssens@hotmail.com");

	}

	@Test
	public void testRegisterEmailEmpty() {

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormExpectingErrors("jakke", "Jan", "Janssens", "", "1234");

        assertTrue(addUserPage.isCurrentPage());

        List<String> errors = addUserPage.getErrors();
        assertEquals(errors.size(), 1);
        assertTrue(errors.contains("No email given"));

        assertEquals(addUserPage.getUserIdText(), "jakke");
        assertEquals(addUserPage.getFirstNameText(), "Jan");
        assertEquals(addUserPage.getLastNameText(), "Janssens");
        assertEquals(addUserPage.getEmailText(), "");

	}

	@Test
	public void testRegisterPasswordEmpty(){

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormExpectingErrors("jakke", "Jan", "Janssens", "jan.janssens@hotmail.com", "");

        assertTrue(addUserPage.isCurrentPage());

        List<String> errors = addUserPage.getErrors();
        assertEquals(errors.size(), 1);
        assertTrue(errors.contains("No password given"));

        assertEquals(addUserPage.getUserIdText(), "jakke");
        assertEquals(addUserPage.getFirstNameText(), "Jan");
        assertEquals(addUserPage.getLastNameText(), "Janssens");
        assertEquals(addUserPage.getEmailText(), "jan.janssens@hotmail.com");

	}

	@Test
	public void testRegisterUserAlreadyExists(){

		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("pierke");

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormNormal(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");
        addUserPage = new AddUserPage(driver);
        addUserPage.submitFormNormal(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

        List<String> errors = addUserPage.getErrors();
        assertEquals(errors.size(), 1);
        assertTrue(errors.contains("User already exists"));

        assertEquals(addUserPage.getUserIdText(), useridRandom);
        assertEquals(addUserPage.getFirstNameText(), "Pieter");
        assertEquals(addUserPage.getLastNameText(), "Pieters");
        assertEquals(addUserPage.getEmailText(), "pieter.pieters@hotmail.com");

	}

}
