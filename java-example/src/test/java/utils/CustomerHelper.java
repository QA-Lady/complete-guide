package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.appManager.AppManager;
import utils.dataModel.CustomerData;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by QA Lady on 4/19/2017.
 */
public class CustomerHelper {

    private AppManager appManager;

    public CustomerHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public void createAccount(CustomerData customerData) {
        openCreateAccountPage();
        enterFirstName(customerData.getFirstname());
        enterLastName(customerData.getLastname());
        enterAddress(customerData.getAddress());
        enterCity(customerData.getCity());
        enterZip(customerData.getZip());
        selectCountry(customerData.getCountry());
        selectState(customerData.getStateByIndex());
        String emailID = enterEmail(customerData.getEmail());
        enterPhone(customerData.getPhone());
        String password = enterPassword(customerData.getPassw());
        confirmPassword(password);
        System.out.println("Click on 'Create Account' button");
        appManager.getInputAndActionsHelper().clickOn(ExpectedConditions.elementToBeClickable(By.xpath("//button[@value='Create Account']")));
    }

    public void openCreateAccountPage() {
        appManager.getWebDriverHelper().getDriver().get("http://localhost/litecart/en/");
        System.out.println("Click on 'New customers click here' link");
        appManager.getInputAndActionsHelper().clickOn(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='box-account-login']//td/a")));
        TestBase.wait.until(ExpectedConditions.urlContains("create_account"));
    }

    public void confirmPassword(String password) {
        System.out.println("Confirm password");
        WebElement passwordConfirm = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='confirmed_password']")));
        appManager.getInputAndActionsHelper().enterText(passwordConfirm, password, false);
    }

    public String enterPassword(String s) {
        System.out.println("Enter password");
        WebElement passwordElem = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        String password = s;
        appManager.getInputAndActionsHelper().enterText(passwordElem, password, false);
        return password;
    }

    public void enterPhone(String text) {
        System.out.println("Enter phone");
        WebElement phone = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='phone']")));
        appManager.getInputAndActionsHelper().enterText(phone, text, true);
    }

    public String enterEmail(String e) {
        System.out.println("Enter email");
        WebElement email = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email']")));
        String emailID = e;
        appManager.getInputAndActionsHelper().enterText(email, emailID, false);
        return emailID;
    }

    public void selectState(int index) {
        System.out.println("Select random State with index from 0 - 9");
        Select stateSelect = new Select(TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='zone_code']/option/.."))));
        stateSelect.selectByIndex(index);
//        stateSelect.selectByValue("CO");
    }

    public void selectCountry(String text) {
        System.out.println("Select " + text + " in the countries pul-down");
        Select countriesSelect = new Select(TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='select2-hidden-accessible']"))));
        countriesSelect.selectByVisibleText(text);
    }

    public void enterZip(String text) {
        WebElement zip = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='postcode']")));
        System.out.println("Enter zip");
        appManager.getInputAndActionsHelper().enterText(zip, text, false);
    }

    public void enterCity(String text) {
        WebElement city = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='city']")));
        System.out.println("Enter city");
        appManager.getInputAndActionsHelper().enterText(city, text, false);
    }

    public void enterAddress(String text) {
        WebElement address = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='address1']")));
        System.out.println("Enter address");
        appManager.getInputAndActionsHelper().enterText(address, text, false);
    }

    public void enterLastName(String text) {
        WebElement lastName = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='lastname']")));
        System.out.println("Enter lastname");
        appManager.getInputAndActionsHelper().enterText(lastName, text, false);
    }

    public void enterFirstName(String text) {
        WebElement firstName = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='firstname']")));
        System.out.println("Enter firstname");
        appManager.getInputAndActionsHelper().enterText(firstName, text, false);
    }

    public Set<String> getCustomerIds() {
        if (!appManager.getWebDriverHelper().getDriver().getCurrentUrl().equals("http://localhost/litecart/admin/?app=customers&doc=customers")) {
            appManager.getWebDriverHelper().getDriver().get("http://localhost/litecart/admin/?app=customers&doc=customers");
            LoginHelper.doLogin("admin", "admin");
        }
        List<WebElement> customerRows = appManager.getWebDriverHelper().getDriver().findElements(By.cssSelector("table.dataTable tr.row"));
        return customerRows.stream()
                .map(e -> e.findElements(By.tagName("td")).get(2).getText())
                .collect(toSet());
    }
}
