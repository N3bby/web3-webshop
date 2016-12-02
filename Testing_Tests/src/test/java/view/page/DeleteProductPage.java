package view.page;

import domain.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DeleteProductPage extends Page{

    public DeleteProductPage(WebDriver driver) {
        super(driver);
    }

    public DeleteProductPage(WebDriver driver, boolean navigateTo) {
        super(driver, navigateTo);
    }

    @Override
    public boolean isCurrentPage() {
        return getDriver().findElement(By.tagName("h2")).getText().trim().equals("Delete Product");
    }

    @Override
    public String getPageUrl() {
        throw new IllegalStateException();
    }

    public Product getProduct() {
        List<WebElement> th = getDriver().findElements(By.tagName("th"));
        return new Product(th.get(1).getText(), th.get(3).getText(), Double.parseDouble(th.get(5).getText()));
    }

    public ProductOverviewPage confirm() {
        getDriver().findElement(By.id("deleteProductConfirm")).click();
        return new ProductOverviewPage(getDriver(), false);
    }

    public ProductOverviewPage cancel() {
        getDriver().findElement(By.id("deleteProductCancel")).click();
        return new ProductOverviewPage(getDriver(), false);
    }

}
