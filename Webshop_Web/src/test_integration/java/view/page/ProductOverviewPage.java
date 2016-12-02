package view.page;

import domain.Person;
import domain.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductOverviewPage extends Page {

    public ProductOverviewPage(WebDriver driver) {
        super(driver);
    }

    public ProductOverviewPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("Product Overview");
    }

    @Override
    public String getPageUrl() {
        return websiteRoot + "Controller?action=productOverview";
    }

    public Product getProduct(String productId) {
        List<Product> products = getProducts();
        for (Product product : products) {
            if (product.getId().equals(productId)) return product;
        }
        throw new NoSuchElementException("Product with id '" + productId + "' was not found!");
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        List<WebElement> table = getDriver().findElements(By.cssSelector("table tr"));
        table.remove(0); //Table headings
        for (WebElement tr : table) {
            List<WebElement> th = tr.findElements(By.tagName("th"));
            products.add(new Product(
                            th.get(0).getText(),
                            th.get(1).getText(),
                            Double.parseDouble(th.get(2).getText())
                    )
            );
        }
        return products;
    }

    public UpdateProductPage getUpdateProductPage(String id) {
        List<WebElement> table = getDriver().findElements(By.cssSelector("table tr"));
        table.remove(0); //Table headings
        for (WebElement tr : table) {
            List<WebElement> th = tr.findElements(By.tagName("th"));
            if (th.get(0).getText().trim().equals(id)) {
                th.get(0).findElement(By.tagName("a")).click();
                return new UpdateProductPage(getDriver(), false);
            }
        }
        throw new NoSuchElementException("Product with id '" + id + "' was not found!");
    }

    public DeleteProductPage getDeleteProductPage(String id) {

        List<WebElement> deleteForms = getDriver().findElements(By.className("deleteForm"));

        for (WebElement deleteForm : deleteForms) {
            WebElement idInput = deleteForm.findElement(By.name("id"));
            if(idInput.getAttribute("value").equals(id)) {
                deleteForm.findElement(By.className("overviewFormSubmit")).click();
                return new DeleteProductPage(getDriver(), false);
            }
        }

        throw new NoSuchElementException("Product with id '" + id + "' was not found!");

    }

}
