package view;

import static org.junit.Assert.*;

import db.TestCleanup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Person;
import service.ShopService;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ShopServiceTest {

    private static ShopService service;
    private Person person;
    private String personId;
    private String PASSWORD = "1234";

    private final String DB_INFO_PATH = "C:\\Users\\leviv\\Google Drive\\School\\Fase 2\\Webontwikkeling 3\\Properties\\dbinfo.properties";

    @Before
    public void setUp() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(DB_INFO_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        service = new ShopService(properties);
        personId = TestIdGenerator.generateTestId("sinterklaas");
        person = new Person(personId, "klaas@klaas.be", PASSWORD, "Klaas", "Claesens");
    }

    @After
    public void after() {
        service.close();
        TestCleanup.cleanTestPersons(TestIdGenerator.ID_PREFIX_ESCAPED);
    }

    @Test
    public void getPerson_should_return_the_person_if_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getPerson(personId);

        assertNotNull(personRetrieved);
        assertTrue(personRetrieved.isCorrectPassword(PASSWORD));
        assertEquals(personId, personRetrieved.getUserid());
        assertEquals(person.getEmail(), personRetrieved.getEmail());
        assertEquals(person.getFirstName(), personRetrieved.getFirstName());
        assertEquals(person.getLastName(), personRetrieved.getLastName());
    }

    @Test
    public void getPerson_should_return_null_if_person_not_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getPerson("Unknown");

        assertNull(personRetrieved);
    }

    @Test
    public void getUserIfAuthenticated_should_return_the_person_if_registered_and_correct_password() {
        service.addPerson(person);

        Person personRetrieved = service.getUserIfAuthenticated(personId, PASSWORD);

        assertNotNull(personRetrieved);
        assertTrue(personRetrieved.isCorrectPassword(PASSWORD));
        assertEquals(personId, personRetrieved.getUserid());
    }

    @Test
    public void getUserIfAuthenticated_should_return_null_if_person_not_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getUserIfAuthenticated("Unknown", PASSWORD);

        assertNull(personRetrieved);
    }

    @Test
    public void getUserIfAuthenticated_should_return_null_if_person_is_registered_but_incorrect_password() {
        service.addPerson(person);

        Person personRetrieved = service.getUserIfAuthenticated(personId, "WrongPassword");

        assertNull(personRetrieved);
    }

}
