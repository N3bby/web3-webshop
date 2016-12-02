package view.page;

import domain.Person;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DeletePersonPage extends Page {
    
    public DeletePersonPage(WebDriver driver) {
        super(driver);
    }

    public DeletePersonPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("Delete Person");
    }

    @Override
    public String getPageUrl() {
        throw new IllegalStateException();
    }

    public Person getPerson() {
        List<WebElement> th = getDriver().findElements(By.tagName("th"));
        return new Person("randind", th.get(1).getText(), "somepassword", th.get(3).getText(), th.get(5).getText());
    }

    public PersonOverviewPage confirm() {
        getDriver().findElement(By.id("deletePersonConfirm")).click();
        return new PersonOverviewPage(getDriver(), false);
    }

    public PersonOverviewPage cancel() {
        getDriver().findElement(By.id("deletePersonCancel")).click();
        return new PersonOverviewPage(getDriver(), false);
    }
    
}
