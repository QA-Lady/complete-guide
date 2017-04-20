package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.appManager.AppManager;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * Created by QA Lady on 4/20/2017.
 */
public class ShoppingCartHelper {
    private AppManager appManager;

    public ShoppingCartHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public void deleteItemFromShoppingCart() {
        WebElement itemsTable = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='dataTable rounded-corners']")));
        System.out.println("Click on Remove button");
        appManager.getInputAndActionsHelper().clickOn(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='remove_cart_item']")));
        TestBase.wait.until(ExpectedConditions.stalenessOf(itemsTable));
    }

    public void checkThatRemovedItemIsNotInShoppingCartTable(int originalItemsNumber, int j) {
        List<WebElement> items = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable rounded-corners']//tr/td[@class='item']")));
        int expectedItemsNumber = originalItemsNumber - (j);
        System.out.println("Check that cart now has " + expectedItemsNumber + " items");
        Assert.assertEquals(items.size(), expectedItemsNumber, "check that table has updated with removed item(s)");
    }

    public void checkEmptyCartMessage(int j) {
        System.out.println("The last item number " + j + " was removed. Check empty cart message");
        WebElement emptyCartElem = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout-cart-wrapper']/p/em")));
        Assert.assertEquals(emptyCartElem.getText(), "There are no items in your cart.", "check empty cart message");
    }

    public int checkShoppingCartTable(int expectedNumberOfItems) {
        List<WebElement> items = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable rounded-corners']//tr/td[@class='item']")));
        System.out.println("Check that cart now has 3 items");
        int originalItemsNumber = items.size();
        Assert.assertEquals(originalItemsNumber, expectedNumberOfItems, "check that cart has 3 items");
        return originalItemsNumber;
    }

    public void goBackToHomePage() {
        System.out.println("Go back to Home page");
        try {
            appManager.getInputAndActionsHelper().clickOn(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout-cart-wrapper']/p[2]/a")));
        } catch (Exception ignored) {
            appManager.getInputAndActionsHelper().clickOn(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='fa fa-home']")));
        }
        TestBase.wait.until(ExpectedConditions.not(urlContains("checkout")));
    }

    public void checkShoppingCartContent(int expectedNumberOfItems) {
        List<WebElement> items;
        List<WebElement> rows = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable rounded-corners']//tr")));
        items = TestBase.longWait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@class='dataTable rounded-corners']//tr/td[@class='item']"), expectedNumberOfItems));
        System.out.println("Check that correct number of items is displayed in the cart: " + expectedNumberOfItems + " items should be displayed");
        Assert.assertEquals(items.size(), expectedNumberOfItems, "check correct number of items in the cart");
    }

}
