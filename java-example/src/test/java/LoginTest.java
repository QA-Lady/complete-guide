import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by QA Lady on 3/16/2017.
 */
public class LoginTest extends TestBase {

    @Test
    public void loginTest() {
        wait.until(ExpectedConditions.titleIs("My Store"));
        WebElement loginBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='box-login']")));
        System.out.println("Check that login box is displayed");
        Assert.assertTrue(loginBox.isEnabled(), "login box should be displayed");
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        System.out.println("Enter username");
        userNameInput.sendKeys("admin");
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        System.out.println("Enter password");
        passwordInput.sendKeys("admin");
        WebElement saveLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='remember_me']")));
        System.out.println("Check that 'Remember Me' checkbox is unchecked by default");
        Assert.assertFalse(saveLogin.isSelected(), "unchecked by default");
        System.out.println("Click on checkbox");
        saveLogin.click();
//        new Actions(driver).moveToElement(saveLogin).click().build().perform();
        System.out.println("Check that checkbox is now checked");
        Assert.assertTrue(saveLogin.isSelected(), "is checked after click");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
        System.out.println("Click on Login button");
        loginBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='box-apps-menu']")));
        System.out.println("Login successful");
    }
}
