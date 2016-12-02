package view;

import db.TestCleanup;
import domain.Product;
import global.GlobalTestingVars;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import view.page.AddProductPage;
import view.page.DeleteProductPage;
import view.page.ProductOverviewPage;

public class DeleteProductTest {

    private WebDriver driver;

    private final String DEFAULT_DESCRIPTION = "Sample description";
    private final String DEFAULT_PRICE = "500";

    @Before
    public void setup() {
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

    public String addContextProduct() {

        String productId = generateRandomUseridInOrderToRunTestMoreThanOnce("productToDelete");

        AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.submitNormal(productId, DEFAULT_DESCRIPTION, DEFAULT_PRICE);

        return productId;

    }

    @Test
    public void testDeleteProductShowsInfo() {

        String productId = addContextProduct();

        ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        DeleteProductPage deleteProductPage = productOverviewPage.getDeleteProductPage(productId);

        Product product = deleteProductPage.getProduct();

        Assert.assertEquals(product.getId(), productId);
        Assert.assertEquals(product.getDescription(), DEFAULT_DESCRIPTION);
        Assert.assertEquals(product.getPrice(), Double.parseDouble(DEFAULT_PRICE), 0.00001);

    }

    @Test
    public void testDeleteProductConfirm() {

        String productId = addContextProduct();

        ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        DeleteProductPage deleteProductPage = productOverviewPage.getDeleteProductPage(productId);

        ProductOverviewPage newProductOverViewPage = deleteProductPage.confirm();

        Assert.assertTrue(newProductOverViewPage.isCurrentPage());

        //Doing my own 'expected' implementation because I want to check the message as well
        boolean threwException = false;
        try{
            newProductOverViewPage.getProduct(productId);
        } catch (NoSuchElementException e) {
            Assert.assertTrue(e.getMessage().contains("Product with id '" + productId + "' was not found!"));
            threwException = true;
        }
        Assert.assertTrue(threwException);

    }

    @Test
    public void testDeleteProductCancel() {

        String productId = addContextProduct();

        ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        DeleteProductPage deleteProductPage = productOverviewPage.getDeleteProductPage(productId);

        ProductOverviewPage newProductOverViewPage = deleteProductPage.cancel();
        Assert.assertTrue(newProductOverViewPage.isCurrentPage());

        //Also throws an exception if it doesn't work so the not null assert might not be needed
        Assert.assertNotNull(newProductOverViewPage.getProduct(productId));

    }

}
