package view.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import view.AddProductTest;

import java.util.ArrayList;
import java.util.List;

public class AddProductPage extends Page {

    public AddProductPage(WebDriver driver) {
        super(driver);
    }

    public AddProductPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("Add Product");
    }

    @Override
    public String getPageUrl() {
        return websiteRoot + "Controller?action=requestProductAdd";
    }

    private String getInputText(String textBoxId) {
        return getDriver().findElement(By.id(textBoxId)).getAttribute("value");
    }

    public String getProductIdText() {
        return getInputText("id");
    }

    public String getDescriptionText() {
        return getInputText("description");
    }

    public String getPriceText() {
        return getInputText("price");
    }

    private void clickSubmit() {
        getElementById("addProduct").click();
    }

    private void submit(String id, String description, String price) {
        setTextBoxText("id", id);
        setTextBoxText("description", description);
        setTextBoxText("price", price);
        clickSubmit();
    }

    public ProductOverviewPage submitNormal(String id, String description, String price) {
        submit(id, description, price);
        return new ProductOverviewPage(getDriver(), false);
    }

    public AddProductPage submitExpectingErrors(String id, String description, String price) {
        submit(id, description, price);
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
