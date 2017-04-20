import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by QA Lady on 4/12/2017.
 */
public class StylesTests extends TestBase {

    @Test
    public void productOptionsChecks() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        for (int i = 0; i < allProducts.size(); i++) {
            System.out.println("Getting product price and name for comparison");
            WebElement product = allProducts.get(i);
            String prName = product.findElement(By.xpath(".//div[@class='name']")).getText();
            String prPrice = null;
            WebElement prPriceElem = null;
            String prSalePrice = null;
            WebElement prSalePriceElem = null;
            String prBeforeSalePrice = null;
            WebElement prBeforeSalePriceElem = null;
            if (elementHelper.isElementPresent(driver, product, By.xpath(".//*[@class='campaign-price']"))) {
                prSalePriceElem = product.findElement(By.xpath(".//*[@class='campaign-price']"));
                prSalePrice = prSalePriceElem.getText();
                prBeforeSalePriceElem = product.findElement(By.xpath(".//*[@class='regular-price']"));
                prBeforeSalePrice = prBeforeSalePriceElem.getText();
                String salePriceColor = prSalePriceElem.getCssValue("color");
                String originalPriceColor = prBeforeSalePriceElem.getCssValue("color");
                System.out.println(i + 1 + ": '" + prName + "' Sale Price: " + prSalePrice + " color: " + salePriceColor + " Original Price: " + prBeforeSalePrice + " color: " + originalPriceColor);
                System.out.println("Check that sale price has red color");
                Assert.assertTrue(salePriceColor.contains("204, 0, 0"), "check red color");
                System.out.println("Check that sale price has bold style");
                Assert.assertEquals(prSalePriceElem.getCssValue("font-weight"), "bold", "check that style is bold");
                //
                System.out.println("Check that original (price before sale) has grey color");
                Assert.assertTrue(originalPriceColor.contains("119, 119, 119"), "check grey color");
                System.out.println("Check that original price has crossed out style '" + prBeforeSalePriceElem.getCssValue("text-decoration-line") + "'");
                Assert.assertTrue(prBeforeSalePriceElem.getCssValue("text-decoration-line").contains("line-through"), "check text-decoration");
                //
                System.out.println("Check that Sale price has bigger font size than regular price");
                int regSize = Integer.parseInt(prBeforeSalePriceElem.getCssValue("font-size").substring(0, 2));
                int saleSize = Integer.parseInt(prSalePriceElem.getCssValue("font-size").substring(0, 2));
                Assert.assertTrue(saleSize > regSize, "check that sale size has bigger font");
            } else {
                prPriceElem = product.findElement(By.xpath(".//*[@class='price']"));
                prPrice = prPriceElem.getText();
                System.out.println(i + 1 + ": '" + prName + "' Price: " + prPrice);
            }
            System.out.println("Click on a product to open Product Page");
            inputAndActionsHelper.clickOn(product);

            System.out.println("Check that product name on Main page and on Product page is the same");
            WebElement nameOnPrPageElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='title'][@itemprop='name']")));
            String nameOnPrPage = nameOnPrPageElem.getText();
            Assert.assertEquals(prName, nameOnPrPage, "check that product name is the same on tile and product page");
            //
            System.out.println("Check that product price on Main page and on Product page is the same");
            WebElement salePriceOnPrPageElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='campaign-price']")));
            WebElement beforeSalePriceOnPrPageElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='regular-price']")));
            WebElement priceOnPrPageElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='price']")));
            if (prSalePriceElem != null) {
                String prSalePriceOnPrPage = salePriceOnPrPageElem.getText();
                Assert.assertEquals(prSalePrice, prSalePriceOnPrPage, "check that product price is the same on tile and product page");
                String salePrColor = salePriceOnPrPageElem.getCssValue("color");
                String originalPrColor = beforeSalePriceOnPrPageElem.getCssValue("color");
                System.out.println(i + 1 + ": '" + prName + "' Sale Price: " + prSalePrice + " color: " + salePrColor + " style: " + salePriceOnPrPageElem.getCssValue("font-weight") + " Original Price: " + prBeforeSalePrice + " color: " + originalPrColor + " style: " + beforeSalePriceOnPrPageElem.getCssValue("text-decoration-line"));
                //
                System.out.println("Check that sale price has red color on Product page");
                Assert.assertTrue(salePrColor.contains("204, 0, 0"), "check red color");
                System.out.println("Check that sale price has bold style on Product page");
                Assert.assertEquals(salePriceOnPrPageElem.getCssValue("font-weight"), "bold", "check that style is bold");
                //
                System.out.println("Check that original (price before sale) has grey color on Product page");
                Assert.assertTrue(originalPrColor.contains("102, 102, 102"), "check grey color");
                System.out.println("Check that original price has crossed out style '" + beforeSalePriceOnPrPageElem.getCssValue("text-decoration-line") + "' on Product page");
                Assert.assertTrue(beforeSalePriceOnPrPageElem.getCssValue("text-decoration-line").contains("line-through"), "check text-decoration");
                //
                System.out.println("Check that Sale price has bigger font size than regular price");
                int regSize = Integer.parseInt(beforeSalePriceOnPrPageElem.getCssValue("font-size").substring(0, 2));
                int saleSize = Integer.parseInt(salePriceOnPrPageElem.getCssValue("font-size").substring(0, 2));
                Assert.assertTrue(saleSize > regSize, "check that sale size has bigger font");
            } else {
                String prPriceOnPrPage = priceOnPrPageElem.getText();
                Assert.assertEquals(prPrice, prPriceOnPrPage, "check that product price is the same on tile and product page");
            }
            //
            System.out.println("Going back to original page");
            driver.navigate().back();
            //getting all products after navigated back
            allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        }


    }

}
