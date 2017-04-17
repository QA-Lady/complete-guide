import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * Created by QA-Lady on 4/16/2017.
 */


public class ShoppingCartTest extends TestBase {

    @Test
    public void shoppingCartTest() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        WebElement prodQuantityCartPreview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='quantity']")));
        int orginalCartContent = Integer.parseInt(prodQuantityCartPreview.getText());
        WebElement checkoutLink = null;
        List<WebElement> items = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            System.out.println("Click on " + (i + 1) + " product to open product page");
            WebElement firstProduct = allProducts.get(i);
            firstProduct.click();
            wait.until(ExpectedConditions.invisibilityOf(firstProduct));
            try {
                System.out.println("Select small size in the pulldown if the pulldown is available");
                new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='options[Size]']")))).selectByValue("Small");
            } catch (Exception e) {
                System.out.println("Current product does not have 'Size' selection");
            }
            System.out.println("Click on 'Add To Cart' button");
            WebElement addToCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@value='Add To Cart']")));
            addToCartBtn.click();
            int expectedNumberOfItems = orginalCartContent + (i + 1);
            //getting the element again
            prodQuantityCartPreview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='quantity']")));
            wait.until(ExpectedConditions.textToBePresentInElement(prodQuantityCartPreview, String.valueOf(expectedNumberOfItems)));
            System.out.println("Click on Checkout");
            checkoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cart']/a[3]")));
            checkoutLink.click();
            wait.until(urlContains("checkout"));
            List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable rounded-corners']//tr")));
            items = longWait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@class='dataTable rounded-corners']//tr/td[@class='item']"), expectedNumberOfItems));
            System.out.println("Check that correct number of items is displayed in the cart: " + expectedNumberOfItems + " items should be displayed");
            Assert.assertEquals(items.size(), expectedNumberOfItems, "check correct number of items in the cart");
            System.out.println("Click on Home button to go back to Home page");
            WebElement homeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='fa fa-home']")));
            homeBtn.click();
            wait.until(ExpectedConditions.not(urlContains("checkout")));
            //getting all products after navigated back
            allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        }
        System.out.println("Click on Checkout link");
        checkoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cart']/a[3]")));
        checkoutLink.click();
        items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable rounded-corners']//tr/td[@class='item']")));
        System.out.println("Check that cart now has 3 items");
        int originalItemsNumber = items.size();
        Assert.assertEquals(originalItemsNumber, 3, "check that cart has 3 items");
        for (int j = 1; j <= 3; j++) {
            WebElement itemsTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='dataTable rounded-corners']")));
            System.out.println("Click on Remove button");
            WebElement removeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='remove_cart_item']")));
            removeBtn.click();
            wait.until(ExpectedConditions.stalenessOf(itemsTable));
            if (j == 3) {
                System.out.println("The last item number " + j + " was removed. Check empty cart message");
                WebElement emptyCartElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout-cart-wrapper']/p/em")));
                Assert.assertEquals(emptyCartElem.getText(), "There are no items in your cart.", "check empty cart message");
                System.out.println("Click on Back link to go back to Home Page");
                WebElement backLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout-cart-wrapper']/p[2]/a")));
                backLink.click();
                wait.until(ExpectedConditions.not(urlContains("checkout")));
                return;
            } else {
                items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable rounded-corners']//tr/td[@class='item']")));
                int expectedItemsNumber = originalItemsNumber - (j);
                System.out.println("Check that cart now has " + expectedItemsNumber + " items");
                Assert.assertEquals(items.size(), expectedItemsNumber, "check that table has updated with removed item(s)");
            }
        }
    }


}
