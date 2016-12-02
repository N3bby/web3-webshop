package view.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductPage extends Page {

    public UpdateProductPage(WebDriver driver) {
        super(driver);
    }

    public UpdateProductPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("Update Product");
    }

    @Override
    public String getPageUrl() {
        throw new IllegalStateException();
    }

    private String getInputText(String textBoxId) {
        return getDriver().findElement(By.id(textBoxId)).getAttribute("value");
    }

    private void setInputText(String textBoxId, String text) {
        getDriver().findElement(By.id(textBoxId)).clear();
        getDriver().findElement(By.id(textBoxId)).sendKeys(text);
    }

    public String getDescriptionText() {
        return getInputText("description");
    }

    public String getPriceText() {
        return getInputText("price");
    }

    public void setDescriptionText(String description) {
        setInputText("description", description);
    }

    public void setPriceText(String price) {
        setInputText("price", price);
    }

    private void submit() {
        getDriver().findElement(By.id("updateProduct")).click();
    }

    public ProductOverviewPage submitNormal() {
        submit();
        return new ProductOverviewPage(getDriver(), false);
    }

    public UpdateProductPage submitExpectingErrors() {
        submit();
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
