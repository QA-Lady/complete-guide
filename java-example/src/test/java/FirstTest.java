import base.TestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by QA Lady on 3/17/2017.
 */
public class FirstTest extends TestBase {

    @Test
    public void myFirstTest() {
        driver.navigate().to("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("WebDriver 3.0");
        driver.findElement(By.name("btnG")).click();
        wait.until(titleIs("WebDriver 3.0 - Google Search"));
    }

}
