import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoginHelper;

import java.util.List;

/**
 * Created by QA Lady on 4/18/2017.
 */
public class BrowserLogTest extends TestBase {

    @Test
    public void browserLogTest() {
        driver.get("http://localhost/litecart/admin/");
        LoginHelper.doLogin("admin", "admin");
        System.out.println("Click on main Catalog menu item");
        WebElement parentMenuCatalog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li[@id='app-']//span[2][text()='Catalog']")));
        parentMenuCatalog.click();
        wait.until(ExpectedConditions.urlContains("catalog&doc=catalog"));
        WebElement rubberRuckCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), 'Rubber Ducks')]")));
        rubberRuckCategory.click();
        WebElement subcategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), 'Subcategory')]")));
        subcategory.click();
        List<WebElement> productsDucks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), 'Duck')][not(contains(text(), 'Rubber Ducks'))]")));
        for (int i = 0; i < productsDucks.size(); i++) {
            WebElement product = productsDucks.get(i);
//            ((Locatable) product).getCoordinates().inViewPort();
            product.click();
            wait.until(ExpectedConditions.urlContains("edit_product&category_id"));
            List<String> browserLog = getBrowserLog();
            Assert.assertFalse(browserLog.isEmpty(), "browser log should not be empty");
            driver.navigate().back();
            //getting original products after navigated back
            productsDucks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), 'Duck')][not(contains(text(), 'Rubber Ducks'))]")));
        }
    }
}
