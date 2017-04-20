package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.appManager.AppManager;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * Created by QA Lady on 4/19/2017.
 */
public class ProductHelper {

    private AppManager appManager;

    public ProductHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public void selectManufacturer() {
        System.out.println("Select Manufacturer");
        new Select(TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='manufacturer_id']")))).selectByVisibleText("ACME Corp.");
    }

    public void selectSoldOutStatus(String text) {
        System.out.println("Select Sold Out Status");
        new Select(TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='sold_out_status_id']")))).selectByVisibleText(text);
    }

    public void selectProdGroupByValue(String checkboxName4Log, final String value4Locator) {
        System.out.println("Select " + checkboxName4Log + " checkbox in Product Groups Gender");
        appManager.getInputAndActionsHelper().selectRadioBtnOrCheckbox(By.xpath("//input[@name='product_groups[]'][@value='" + value4Locator + "']"));
    }

    public void enterDate(String text4Log, String inputName4Locator, String text) {
        System.out.println("Enter " + text4Log);
        WebElement dateValid = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='" + inputName4Locator + "']")));
        appManager.getInputAndActionsHelper().enterText(dateValid, text, true);
    }

    public void goToTab(String tabName) {
        WebElement tab = TestBase.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='tabs']//li/a[text()='" + tabName + "']/..")));
        appManager.getInputAndActionsHelper().clickOn(tab);
        TestBase.wait.until(ExpectedConditions.attributeContains(tab, "class", "active"));
    }

    public void enterQuantity(String quantity) {
        System.out.println("Enter quantity: " + quantity);
        WebElement productQuantity = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='quantity']")));
        appManager.getInputAndActionsHelper().enterText(productQuantity, quantity, false);
    }

    public void enterProductCode(String code) {
        System.out.println("Enter product code");
        WebElement productCode = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='code']")));
        appManager.getInputAndActionsHelper().enterText(productCode, code, false);
    }

    public String enterProductName(String name) {
        System.out.println("Enter product name");
        String productName = name;
        WebElement productNameElem = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name[en]']")));
        appManager.getInputAndActionsHelper().enterText(productNameElem, productName, false);
        return productName;
    }

    //
    public void addProductsToCartAndCheckItsContent() {
        appManager.getWebDriverHelper().getDriver().get("http://localhost/litecart/en/");
        List<WebElement> allProducts = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        WebElement prodQuantityCartPreview = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='quantity']")));
        int orginalCartContent = Integer.parseInt(prodQuantityCartPreview.getText());
        List<WebElement> items = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            selectProductAndOpenItsPage(allProducts, i);
            selectProductSize("Small");
            int expectedNumberOfItems = addToCart(orginalCartContent, i);
            initCheckout();
            appManager.getShoppingCartHelper().checkShoppingCartContent(expectedNumberOfItems);
            appManager.getShoppingCartHelper().goBackToHomePage();
            //getting all products after navigated back
            allProducts = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        }
    }


    public void initCheckout() {
        System.out.println("Click on Checkout");
        WebElement checkoutLink = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cart']/a[3]")));
        appManager.getInputAndActionsHelper().clickOn(checkoutLink);
        TestBase.wait.until(urlContains("checkout"));
    }

    public int addToCart(int orginalCartContent, int i) {
        WebElement prodQuantityCartPreview;
        System.out.println("Click on 'Add To Cart' button");
        appManager.getInputAndActionsHelper().clickOn(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@value='Add To Cart']")));
        int expectedNumberOfItems = orginalCartContent + (i + 1);
        //getting the element again
        prodQuantityCartPreview = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='quantity']")));
        TestBase.wait.until(ExpectedConditions.textToBePresentInElement(prodQuantityCartPreview, String.valueOf(expectedNumberOfItems)));
        return expectedNumberOfItems;
    }

    public void selectProductSize(String small) {
        try {
            System.out.println("Select " + small + " size in the pulldown if the pulldown is available");
            new Select(TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='options[Size]']")))).selectByValue(small);
        } catch (Exception e) {
            System.out.println("Current product does not have 'Size' selection");
        }
    }

    public void selectProductAndOpenItsPage(List<WebElement> allProducts, int i) {
        System.out.println("Click on " + (i + 1) + " product to open product page");
        WebElement firstProduct = allProducts.get(i);
        appManager.getInputAndActionsHelper().clickOn(firstProduct);
        TestBase.wait.until(ExpectedConditions.invisibilityOf(firstProduct));
    }

}
