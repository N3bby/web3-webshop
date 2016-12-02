package view.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AddUserPage extends Page {

    private final String userIdId = "userid";
    private final String firstNameId = "firstName";
    private final String lastNameId = "lastName";
    private final String emailId = "email";
    private final String passwordId = "password";

    public AddUserPage(WebDriver driver) {
        super(driver);
    }

    public AddUserPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);

    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("Sign Up");
    }

    @Override
    public String getPageUrl() {
        return websiteRoot + "Controller?action=requestPersonAdd";
    }

    private String getInputText(String textBoxId) {
        return getDriver().findElement(By.id(textBoxId)).getAttribute("value");
    }

    public String getUserIdText() {
        return getInputText(userIdId);
    }

    public String getFirstNameText() {
        return getInputText(firstNameId);
    }

    public String getLastNameText() {
        return getInputText(lastNameId);
    }

    public String getEmailText() {
        return getInputText(emailId);
    }

    public String getPasswordText() {
        return getInputText(passwordId);
    }



    private void clickSubmit() {
        getElementById("signUp").click();
    }

    private void submitForm(String userId, String firstName, String lastName, String email, String password) {
        setTextBoxText(userIdId, userId);
        setTextBoxText(firstNameId, firstName);
        setTextBoxText(lastNameId, lastName);
        setTextBoxText(emailId, email);
        setTextBoxText(passwordId, password);
        clickSubmit();
    }

    public HomePage submitFormNormal(String userId, String firstName, String lastName, String email, String password) {
        submitForm(userId, firstName, lastName, email, password);
        return new HomePage(getDriver(), false);
    }

    public AddUserPage submitFormExpectingErrors(String userId, String firstName, String lastName, String email, String password) {
        submitForm(userId, firstName, lastName, email, password);
        return this;
    }

    public List<String> getErrors() {

        List<String> errors = new ArrayList<>();

        List<WebElement> elements = getDriver().
                findElement(By.className("alert-danger")).
                findElements(By.cssSelector("ul li"));

        for(WebElement li : elements) {
            errors.add(li.getText());
        }

        return errors;

    }

}
