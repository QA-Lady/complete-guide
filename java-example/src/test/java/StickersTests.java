import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by QA Lady on 4/11/2017.
 */
public class StickersTests extends TestBase {

    @Test
    public void stickersCheck() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id, 'box-')]//ul[@class='listing-wrapper products']/li")));
        for (int i = 0; i < allProducts.size(); i++) {
            WebElement product = allProducts.get(i);
            String productTitle = product.findElement(By.xpath("./a")).getAttribute("title");
            List<WebElement> stickers = product.findElements(By.xpath(".//div[starts-with(@class, 'sticker')]"));
            //each product should contain only 1 sticker
            Assert.assertEquals(stickers.size(), 1);
            String stickerText = stickers.get(0).getAttribute("title");
            System.out.println(i + 1 + ": Product: '" + productTitle + "' has a sticker with text: '" + stickerText + "'");
        }
    }
}
