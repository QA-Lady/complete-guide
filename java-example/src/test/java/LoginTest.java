import base.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by QA Lady on 3/16/2017.
 */
public class LoginTest extends TestBase {

    @Test
    public void loginTest() {
        driver.get("http://localhost/litecart/admin/");
        wait.until(ExpectedConditions.titleIs("My Store"));
        WebElement loginBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='box-login']")));
        System.out.println("Check that login box is displayed");
        Assert.assertTrue("login box should be displayed", loginBox.isDisplayed());
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        System.out.println("Enter username");
        userNameInput.sendKeys("admin");
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        System.out.println("Enter password");
        passwordInput.sendKeys("admin");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
        System.out.println("Click on Login button");
        loginBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='box-apps-menu']")));
        System.out.println("Login successful");
    }
}
