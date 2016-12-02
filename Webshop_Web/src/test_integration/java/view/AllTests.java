package view;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        RegisterTest.class,
        AddProductTest.class,
        UpdateProductTest.class,
        DeleteProductTest.class,
        DeletePersonTest.class,
        CheckPasswordTest.class,
        LoginTest.class,
        ShopServiceTest.class
})

public class AllTests {
}
