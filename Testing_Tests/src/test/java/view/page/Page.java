package view.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Page {

    public final String websiteRoot = "http://localhost:8080/";

    private WebDriver driver;

    public Page(WebDriver driver) {
        this(driver, true);
    }

    public Page(WebDriver driver, boolean navigateTo) {
        if(driver == null) throw new IllegalArgumentException("WebDriver may not be null!");
        this.driver = driver;
        if(navigateTo) {
            driver.get(getPageUrl());
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public abstract boolean isCurrentPage();

    public abstract String getPageUrl();

    public WebElement getElementById(String id) {
        return getDriver().findElement(By.id(id));
    }

    public void setTextBoxText(String id, String value) {
        getElementById(id).clear();
        getElementById(id).sendKeys(value);
    }

}
