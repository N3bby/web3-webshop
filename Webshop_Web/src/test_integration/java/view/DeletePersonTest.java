package view;

import db.TestCleanup;
import domain.Person;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import view.page.*;

public class DeletePersonTest {

    private WebDriver driver;

    private final String DEFAULT_FIRST_NAME = "Delete";
    private final String DEFAULT_LAST_NAME = "Me";
    private final String EMAIL_SUFFIX = "@shop.com";

    @Before
    public void setup() {
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

    private String addContextPerson() {

        String userId = generateRandomUseridInOrderToRunTestMoreThanOnce("userToDelete");

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormNormal(userId, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, userId + EMAIL_SUFFIX, "123");

        return userId;

    }

    @Test
    public void testDeletePersonShowsInfo() {

        String userId = addContextPerson();

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        DeletePersonPage deletePersonPage = personOverviewPage.getDeletePersonPage(userId);

        Person person = deletePersonPage.getPerson();

        Assert.assertEquals(person.getEmail(), userId + EMAIL_SUFFIX);
        Assert.assertEquals(person.getFirstName(), DEFAULT_FIRST_NAME);
        Assert.assertEquals(person.getLastName(), DEFAULT_LAST_NAME);

    }

    @Test
    public void testDeletePersonConfirm() {

        String userId = addContextPerson();

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        DeletePersonPage deletePersonPage = personOverviewPage.getDeletePersonPage(userId);

        PersonOverviewPage newPersonOverviewPage = deletePersonPage.confirm();
        Assert.assertTrue(newPersonOverviewPage.isCurrentPage());

        //Doing my own 'expected' implementation because I want to check the message as well
        boolean threwException = false;
        try{
            newPersonOverviewPage.getPersonByEmail(userId + EMAIL_SUFFIX);
        } catch (NoSuchElementException e) {
            Assert.assertTrue(e.getMessage().contains("Person with email '" + userId + EMAIL_SUFFIX + "' was not found!"));
            threwException = true;
        }
        Assert.assertTrue(threwException);

    }

    @Test
    public void testDeletePersonCancel() {

        String userId = addContextPerson();

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        DeletePersonPage deletePersonPage = personOverviewPage.getDeletePersonPage(userId);

        PersonOverviewPage newPersonOverviewPage = deletePersonPage.cancel();
        Assert.assertTrue(newPersonOverviewPage.isCurrentPage());

        //Also throws an exception if it doesn't work so the not null assert might not be needed
        Assert.assertNotNull(newPersonOverviewPage.getPersonByEmail(userId + EMAIL_SUFFIX));

    }


}
