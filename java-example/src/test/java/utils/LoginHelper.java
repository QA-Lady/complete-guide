package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

/**
 * Created by QA Lady on 4/11/2017.
 */
public class LoginHelper {


    public static void doLogin() {
        try {
            //check if we already logged in
            TestBase.shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='box-apps-menu']")));
            System.out.println("We already logged in no need to login again");
        } catch (Exception e) {
            //do login
            TestBase.wait.until(ExpectedConditions.titleIs("My Store"));
            WebElement loginBox = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='box-login']")));
            System.out.println("Check that login box is displayed");
            Assert.assertTrue(loginBox.isEnabled(), "login box should be displayed");
            WebElement userNameInput = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
            System.out.println("Enter username");
            userNameInput.sendKeys("admin");
            WebElement passwordInput = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
            System.out.println("Enter password");
            passwordInput.sendKeys("admin");
            WebElement loginBtn = TestBase.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
            System.out.println("Click on Login button");
            loginBtn.click();
            TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='box-apps-menu']")));
            System.out.println("Login successful");
        }
    }

}
