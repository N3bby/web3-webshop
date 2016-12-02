package view.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckPasswordPage extends Page {

    public CheckPasswordPage(WebDriver driver) {
        super(driver);
    }

    public CheckPasswordPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().equals("Check Password");
    }

    @Override
    public String getPageUrl() {
        throw new IllegalStateException();
    }

    public String getEmail() {
        return getDriver().findElement(By.id("personEmail")).getText();
    }

    private void setPassword(String password) {
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("password")).sendKeys(password);
    }

    private void submit() {
        getDriver().findElement(By.id("submitCheck")).click();
    }

    public CheckPasswordPage submitNormal(String password) {
        setPassword(password);
        submit();
        return this;
    }

    public String getResultMessage() {
        return getDriver().findElement(By.id("resultMessage")).getText();
    }

}
