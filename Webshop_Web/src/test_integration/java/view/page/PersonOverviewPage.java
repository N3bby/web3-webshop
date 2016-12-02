package view.page;

import domain.Person;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PersonOverviewPage extends Page {

    public PersonOverviewPage(WebDriver driver) {
        super(driver);
    }

    public PersonOverviewPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("User Overview");
    }

    @Override
    public String getPageUrl() {
        return websiteRoot + "Controller?action=personOverview";
    }

    public List<Person> getAccounts() {
        List<Person> accounts = new ArrayList<>();
        List<WebElement> table = getDriver().findElements(By.cssSelector("table tr"));
        table.remove(0); //Table headings
        for(WebElement tr : table) {
            List<WebElement> th = tr.findElements(By.tagName("th"));
            accounts.add(new Person(
                    Integer.toString(accounts.size()),
                    th.get(0).getText(),
                    "t",
                    th.get(1).getText(),
                    th.get(2).getText()
                    )
            );
        }
        return accounts;
    }

    public DeletePersonPage getDeletePersonPage(String id) {

        List<WebElement> deleteForms = getDriver().findElements(By.className("deleteForm"));

        for (WebElement deleteForm : deleteForms) {
            WebElement emailInput = deleteForm.findElement(By.name("id"));
            if(emailInput.getAttribute("value").equals(id)) {
                deleteForm.findElement(By.className("overviewFormSubmit")).click();
                return new DeletePersonPage(getDriver(), false);
            }
        }

        throw new NoSuchElementException("Person with id '" + id + "' was not found!");

    }

    public Person getPersonByEmail(String email) {

        List<Person> accounts = getAccounts();
        for(Person person : accounts) {
            if(person.getEmail().equals(email)) {
                return person;
            }
        }
        throw new NoSuchElementException("Person with email '" + email + "' was not found!");

    }

    public CheckPasswordPage getCheckPasswordPage(String id) {

        List<WebElement> checkForms = getDriver().findElements(By.className("checkForm"));

        for (WebElement checkForm : checkForms) {
            WebElement emailInput = checkForm.findElement(By.name("id"));
            if(emailInput.getAttribute("value").equals(id)) {
                checkForm.findElement(By.className("overviewFormSubmit")).click();
                return new CheckPasswordPage(getDriver(), false);
            }
        }

        throw new NoSuchElementException("Person with id '" + id + "' was not found!");

    }


}
