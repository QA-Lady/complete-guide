import base.TestBase;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoginHelper;


/**
 * Created by QA Lady on 4/14/2017.
 */
public class NewUserTests extends TestBase {

    @Test
    public void newUserCreation() {
        driver.get("http://localhost/litecart/en/");
        WebElement createUserlink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='box-account-login']//td/a")));
        System.out.println("Click on 'New customers click here' link");
        createUserlink.click();
        wait.until(ExpectedConditions.urlContains("create_account"));
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='firstname']")));
        System.out.println("Enter firstname");
        enterText(firstName, RandomStringUtils.randomAlphabetic(7), false);
        WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='lastname']")));
        System.out.println("Enter lastname");
        enterText(lastName, RandomStringUtils.randomAlphabetic(10), false);
        WebElement address = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='address1']")));
        System.out.println("Enter address");
        enterText(address, RandomStringUtils.randomAlphabetic(10), false);
        WebElement city = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='city']")));
        System.out.println("Enter city");
        enterText(city, RandomStringUtils.randomAlphabetic(8), false);
        WebElement zip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='postcode']")));
        System.out.println("Enter zip");
        enterText(zip, RandomStringUtils.randomNumeric(5), false);
        System.out.println("Select United States in the countries pul-down");
        Select countriesSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='select2-hidden-accessible']"))));
        countriesSelect.selectByVisibleText("United States");
        System.out.println("Select random State with index from 0 - 9");
        Select stateSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='zone_code']/option/.."))));
        stateSelect.selectByIndex(Integer.parseInt(RandomStringUtils.randomNumeric(1)));
//        stateSelect.selectByValue("CO");
        System.out.println("Enter email");
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email']")));
        String emailID = RandomStringUtils.random(6, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'x', 'y', 'z'}) + "@google.com";
        enterText(email, emailID, false);
        System.out.println("Enter phone");
        WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='phone']")));
        enterText(phone, RandomStringUtils.randomNumeric(7), true);
        System.out.println("Enter password");
        WebElement passwordElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        String password = RandomStringUtils.randomAlphabetic(10);
        enterText(passwordElem, password, false);
        System.out.println("Confirm password");
        WebElement passwordConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='confirmed_password']")));
        enterText(passwordConfirm, password, false);
        System.out.println("Click on 'Create Account' button");
        WebElement createAccountBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@value='Create Account']")));
        createAccountBtn.click();
        System.out.println("Check that account has been successfully created");
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notices']/div")));
        Assert.assertEquals(successMessage.getText(), "Your customer account has been created.", "check success message");
        LoginHelper.doLogout();
        System.out.println("Check that logout was successful");
        //take NEW successMessage elem
        successMessage = driver.findElement(By.xpath("//*[@id='notices']/div"));
        Assert.assertEquals(successMessage.getText(), "You are now logged out.", "check success message");
        System.out.println("Login again as the same user (the one that was just created)");
        LoginHelper.doLogin(emailID, password);
        System.out.println("Logout again");
        LoginHelper.doLogout();
    }

}
