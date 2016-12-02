package view.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Page {

    private final String welcomeMessageId = "welcomeMessage";
    private final String userId = "userid";
    private final String passwordId = "password";
    private final String loginButtonId = "login";
    private final String logoutButtonId = "logout";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().getTitle().trim().equals("Home");
    }

    @Override
    public String getPageUrl() {
        return websiteRoot + "Controller";
    }

    public String getLoggedInMessage() {
        WebElement welcomeMessage;
        try {
            welcomeMessage = getElementById(welcomeMessageId);
        } catch (Exception e) {
            return null;
        }
        return welcomeMessage.getText();
    }

    public HomePage login(String user, String password) {
        setTextBoxText(userId, user);
        setTextBoxText(passwordId, password);
        getElementById(loginButtonId).click();
        return this;
    }

    public boolean isLoggedIn() {
        boolean foundLogoutButton = false;
        try {
            getElementById(logoutButtonId);
            foundLogoutButton = true;
        } catch (Exception e) { }
        return foundLogoutButton && getLoggedInMessage() != null;
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
