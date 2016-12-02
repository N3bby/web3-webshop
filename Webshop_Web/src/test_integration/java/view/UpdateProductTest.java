package view;

import db.TestCleanup;
import domain.Product;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import view.page.AddProductPage;
import view.page.ProductOverviewPage;
import view.page.UpdateProductPage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdateProductTest {

    private WebDriver driver;

    private final String DEFAULT_DESCRIPTION = "Sample description";
    private final String DEFAULT_PRICE = "500";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", GlobalTestingVars.DRIVER_CHROME);
        driver = new ChromeDriver();

    }

    @After
    public void clean() {
        driver.quit();
        TestCleanup.cleanTestProducts(TestIdGenerator.ID_PREFIX_ESCAPED);
    }

    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        return TestIdGenerator.generateTestId(component);
    }

    private String addContextProduct() {
        AddProductPage addProductPage = new AddProductPage(driver);
        String productId = generateRandomUseridInOrderToRunTestMoreThanOnce("productToUpdate");
        addProductPage.submitNormal(
                productId,
                DEFAULT_DESCRIPTION,
                DEFAULT_PRICE);
        return productId;
    }

    private boolean verifyInitialState(String productId) {
        ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        Product product = productOverviewPage.getProduct(productId);
        boolean initialState = true;
        if(!product.getDescription().equals(DEFAULT_DESCRIPTION)) initialState = false;
        if(product.getPrice() != Double.parseDouble(DEFAULT_PRICE)) initialState = false;
        return initialState;
    }

    private UpdateProductPage getUpdatePage(String productId) {
        return new ProductOverviewPage(driver).getUpdateProductPage(productId);
    }

    @Test
    public void testUpdateProductPageShowsInitialValues() {

        String productId = addContextProduct();

        UpdateProductPage updatePage = getUpdatePage(productId);

        Assert.assertEquals(updatePage.getDescriptionText(), DEFAULT_DESCRIPTION);
        //Parsing these to doubles because some weird text formatting issues might fail the test (e.g. browser displaying empty decimal places)
        //Also, this behaviour can be browser dependant I would imagine
        Assert.assertEquals(Double.parseDouble(updatePage.getPriceText()), Double.parseDouble(DEFAULT_PRICE), 0.00001);

    }

    @Test
    public void testUpdateProductCorrect() {

        String productId = addContextProduct();

        UpdateProductPage updatePage = getUpdatePage(productId);
        updatePage.setDescriptionText("New sample description");
        updatePage.setPriceText("300");

        ProductOverviewPage productOverviewPage = updatePage.submitNormal();

        Product product = productOverviewPage.getProduct(productId);
        Assert.assertEquals(product.getDescription(), "New sample description");
        Assert.assertEquals(product.getPrice(), 300.0, 0.00001);

    }

    @Test
    public void testUpdateProductDescriptionEmpty() {

        String productId = addContextProduct();

        UpdateProductPage updatePage = getUpdatePage(productId);
        updatePage.setDescriptionText("");

        updatePage = updatePage.submitExpectingErrors();
        List<String> errors = updatePage.getErrors();
        Assert.assertTrue(errors.contains("Product description cannot be empty."));

        Assert.assertEquals(updatePage.getDescriptionText(), "");
        Assert.assertEquals(Double.parseDouble(updatePage.getPriceText()), Double.parseDouble(DEFAULT_PRICE), 0.00001);

        //Make sure that no changes went through
        Assert.assertTrue(verifyInitialState(productId));

    }

    @Test
    public void testUpdateProductPriceEmpty() {

        String productId = addContextProduct();

        UpdateProductPage updatePage = getUpdatePage(productId);
        updatePage.setPriceText("");

        updatePage = updatePage.submitExpectingErrors();
        List<String> errors = updatePage.getErrors();

        Assert.assertTrue(errors.contains("Price must be a number."));

        Assert.assertEquals(updatePage.getDescriptionText(), DEFAULT_DESCRIPTION);
        Assert.assertEquals(updatePage.getPriceText(), "");

        //Make sure that no changes went through
        Assert.assertTrue(verifyInitialState(productId));

    }

    @Test
    public void testUpdateProductPriceNegative() {

        String productId = addContextProduct();

        UpdateProductPage updatePage = getUpdatePage(productId);
        updatePage.setPriceText("-100");

        updatePage = updatePage.submitExpectingErrors();
        List<String> errors = updatePage.getErrors();

        Assert.assertTrue(errors.contains("Product price cannot be negative."));

        Assert.assertEquals(updatePage.getDescriptionText(), DEFAULT_DESCRIPTION);
        Assert.assertEquals(Double.parseDouble(updatePage.getPriceText()), -100, 0.00001);

        //Make sure that no changes went through
        Assert.assertTrue(verifyInitialState(productId));

    }

}
