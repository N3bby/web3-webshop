package view;

import db.TestCleanup;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import view.page.AddUserPage;
import view.page.CheckPasswordPage;
import view.page.PersonOverviewPage;

public class CheckPasswordTest {

    private WebDriver driver;

    private final String DEFAULT_FIRST_NAME = "Check";
    private final String DEFAULT_LAST_NAME = "Me";
    private final String DEFAULT_PASSWORD = "test";
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

        String userId = generateRandomUseridInOrderToRunTestMoreThanOnce("userToCheckPassword");

        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.submitFormNormal(userId, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, userId + EMAIL_SUFFIX, DEFAULT_PASSWORD);

        return userId;

    }

    @Test
    public void testCheckPasswordShowsEmail() {

        String userId = addContextPerson();

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        CheckPasswordPage checkPasswordPage = personOverviewPage.getCheckPasswordPage(userId);

        Assert.assertTrue(checkPasswordPage.isCurrentPage());
        Assert.assertEquals(checkPasswordPage.getEmail(), userId + EMAIL_SUFFIX);

    }

    @Test
    public void testCheckPasswordCorrect() {

        String userId = addContextPerson();

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        CheckPasswordPage checkPasswordPage = personOverviewPage.getCheckPasswordPage(userId);

        checkPasswordPage = checkPasswordPage.submitNormal(DEFAULT_PASSWORD);

        Assert.assertEquals(checkPasswordPage.getResultMessage(), "The password is correct");

    }

    @Test
    public void testCheckPasswordIncorrect() {

        String userId = addContextPerson();

        PersonOverviewPage personOverviewPage = new PersonOverviewPage(driver);
        CheckPasswordPage checkPasswordPage = personOverviewPage.getCheckPasswordPage(userId);

        checkPasswordPage = checkPasswordPage.submitNormal("dkjfdskljfsdl");

        Assert.assertEquals(checkPasswordPage.getResultMessage(), "The password is NOT correct");

    }

}
