package utils.appManager;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.*;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class AppManager {

    protected ShoppingCartHelper shoppingCartHelper;
    protected InputAndActionsHelper inputAndActionsHelper;
    protected ElementHelper elementHelper;
    protected WebDriverHelper webDriverHelper;
    protected CustomerHelper customerHelper;
    protected ProductHelper productHelper;

    public void init() {
        productHelper = new ProductHelper(this);
        customerHelper = new CustomerHelper(this);
        webDriverHelper = new WebDriverHelper(this);
        elementHelper = new ElementHelper(this);
        inputAndActionsHelper = new InputAndActionsHelper(this);
        shoppingCartHelper = new ShoppingCartHelper(this);
    }

    public WebDriverHelper getWebDriverHelper() {
        return webDriverHelper;
    }

    public CustomerHelper getCustomerHelper() {
        return customerHelper;
    }

    public ProductHelper getProductHelper() {
        return productHelper;
    }

    public ElementHelper getElementHelper() {
        return elementHelper;
    }

    public InputAndActionsHelper getInputAndActionsHelper() {
        return inputAndActionsHelper;
    }

    public ShoppingCartHelper getShoppingCartHelper() {
        return shoppingCartHelper;
    }

    public void checkSuccessMessage(String expected) {
        WebElement successMessage = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notices']/div")));
        Assert.assertEquals(successMessage.getText(), expected, "check success message");
    }


}
