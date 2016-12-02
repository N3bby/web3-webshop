package view;

import db.TestCleanup;
import domain.Product;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import view.page.AddProductPage;
import view.page.ProductOverviewPage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddProductTest {

    private WebDriver driver;

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

    @Test
    public void testAddProductCorrect() {

        String productIdRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("product");
        AddProductPage addProductPage = new AddProductPage(driver);

        ProductOverviewPage overviewPage = addProductPage.submitNormal(productIdRandom, "Sample description", "500");
        assertTrue(overviewPage.isCurrentPage());

        List<Product> products = overviewPage.getProducts();

        boolean found = false;
        for (Product product : products) {
            if (product.getId().equals(productIdRandom) &&
                    product.getDescription().equals("Sample description") &&
                    product.getPrice() == 500) {
                found = true;
            }
        }
        assertTrue(found);

    }

    @Test
    public void testAddProductIdEmpty() {

        AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.submitExpectingErrors("", "Sample description", "500");

        assertTrue(addProductPage.isCurrentPage());

        List<String> errors = addProductPage.getErrors();

        assertTrue(errors.contains("Product id cannot be empty."));
        assertEquals(addProductPage.getProductIdText(), "");
        assertEquals(addProductPage.getDescriptionText(), "Sample description");
        assertEquals(addProductPage.getPriceText(), "500");

    }

    @Test
    public void testAddProductDescriptionEmpty() {

        String randId = generateRandomUseridInOrderToRunTestMoreThanOnce("product");

        AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.submitExpectingErrors(randId, "", "500");

        assertTrue(addProductPage.isCurrentPage());

        List<String> errors = addProductPage.getErrors();

        assertTrue(errors.contains("Product description cannot be empty."));
        assertEquals(addProductPage.getProductIdText(), randId);
        assertEquals(addProductPage.getDescriptionText(), "");
        assertEquals(addProductPage.getPriceText(), "500");

    }

    @Test
    public void testAddProductPriceEmpty() {

        String randId = generateRandomUseridInOrderToRunTestMoreThanOnce("product");

        AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.submitExpectingErrors(randId, "Sample description", "");

        assertTrue(addProductPage.isCurrentPage());

        List<String> errors = addProductPage.getErrors();

        assertTrue(errors.contains("Price must be a number."));
        assertEquals(addProductPage.getProductIdText(), randId);
        assertEquals(addProductPage.getDescriptionText(), "Sample description");
        assertEquals(addProductPage.getPriceText(), "");

    }

    @Test
    public void testAddProductPriceNegative() {

        String randId = generateRandomUseridInOrderToRunTestMoreThanOnce("product");

        AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.submitExpectingErrors(randId, "Sample description", "-100");

        assertTrue(addProductPage.isCurrentPage());

        List<String> errors = addProductPage.getErrors();

        assertTrue(errors.contains("Product price cannot be negative."));
        assertEquals(addProductPage.getProductIdText(), randId);
        assertEquals(addProductPage.getDescriptionText(), "Sample description");
        assertEquals(addProductPage.getPriceText(), "-100");

    }

    @Test
    public void testAddProductProductAlreadyExists() {

        String randId = generateRandomUseridInOrderToRunTestMoreThanOnce("product");

        AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.submitNormal(randId, "Sample description", "500");

        addProductPage = new AddProductPage(driver);
        addProductPage.submitNormal(randId, "Sample description", "500");

        assertTrue(addProductPage.isCurrentPage());

        List<String> errors = addProductPage.getErrors();

        assertTrue(errors.contains("Product already exists"));
        assertEquals(addProductPage.getProductIdText(), randId);
        assertEquals(addProductPage.getDescriptionText(), "Sample description");
        assertEquals(addProductPage.getPriceText(), "500");

    }

}
