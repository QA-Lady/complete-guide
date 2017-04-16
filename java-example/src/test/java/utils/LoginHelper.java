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


    public static void doLogin(String login, String password) {
        try {
            //check if we already logged in
            TestBase.shortWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='box-login']|//*[@id='box-account-login']/div/form")));
            System.out.println("We already logged in no need to login again");
        } catch (Exception e) {
            //do login
            TestBase.wait.until(ExpectedConditions.titleContains("My Store"));
            WebElement loginBox = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='box-login']|//*[@id='box-account-login']/div/form")));
            System.out.println("Check that login box is displayed");
            Assert.assertTrue(loginBox.isEnabled(), "login box should be displayed");
            WebElement userNameInput = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']|//*[@id='box-account-login']//input[@name='email']")));
            System.out.println("Enter username '" + login + "'");
            userNameInput.sendKeys(login);
            WebElement passwordInput = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
            System.out.println("Enter password '" + password + "'");
            passwordInput.sendKeys(password);
            WebElement loginBtn = TestBase.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
            System.out.println("Click on Login button");
            loginBtn.click();
            TestBase.wait.until(ExpectedConditions.invisibilityOf(loginBtn));
        }
    }

    public static void doLogout() {
        try {
            //check if we already logged out
            TestBase.shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='box-login']|//*[@id='box-account-login']/div/form")));
            System.out.println("We already logged out no need to logout again");
        } catch (Exception e) {
            System.out.println("Do logout");
            WebElement logoutLink = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='box-account']/div/ul/li[4]/a")));
            logoutLink.click();
            TestBase.wait.until(ExpectedConditions.invisibilityOf(logoutLink));
        }
    }

}
