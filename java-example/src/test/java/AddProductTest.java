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
 * Created by QA-Lady on 4/15/2017.
 */
public class AddProductTest extends TestBase {

    @Test
    public void addProductChecks() {
        driver.get("http://localhost/litecart/admin/");
        LoginHelper.doLogin("admin", "admin");
        System.out.println("Click on main Catalog menu item");
        inputAndActionsHelper.clickOn(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li[@id='app-']//span[2][text()='Catalog']")));
        System.out.println("Click on 'Add New Product' button");
        inputAndActionsHelper.clickOn(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='content']/div[1]/a[2]")));
        System.out.println("Click on General tab");
        productHelper.goToTab("General");
        System.out.println("Select 'Enabled' radio button in the 'Status radio group");
        inputAndActionsHelper.selectRadioBtnOrCheckbox(By.xpath("//input[@type='radio']/..[contains(text(),'Enabled')]/input"));
        String productName = productHelper.enterProductName("Crapemyrtle");
        productHelper.enterProductCode(RandomStringUtils.randomAlphabetic(10));
        productHelper.selectProdGroupByValue("Unisex", "1-3");
        productHelper.enterQuantity(RandomStringUtils.randomNumeric(2));
        productHelper.selectSoldOutStatus("Temporary sold out");
        inputAndActionsHelper.uploadFile("CherryDazzle_Crapemyrtle.jpg", "Image");
        productHelper.enterDate("Date Valid From", "date_valid_from", "2017-04-15");
        productHelper.enterDate("Date Valid To", "date_valid_to", "2017-06-19");
        System.out.println("Switch to Information tab");
        productHelper.goToTab("Information");
        productHelper.selectManufacturer();
        inputAndActionsHelper.enterTextTo("Keywords", "bush, landscaping", By.xpath("//input[@name='keywords']"));
        inputAndActionsHelper.enterTextTo("Short Description", RandomStringUtils.randomAlphabetic(16), By.xpath("//input[@name='short_description[en]']"));
        inputAndActionsHelper.enterTextTo("Detailed Description", RandomStringUtils.randomAlphabetic(140), By.xpath("//div[@class='trumbowyg-editor']"));
        inputAndActionsHelper.enterTextTo("Head Title", RandomStringUtils.randomAlphabetic(8), By.xpath("//input[@name='head_title[en]']"));
        inputAndActionsHelper.enterTextTo("Meta Description", RandomStringUtils.randomAlphabetic(16), By.xpath("//input[@name='meta_description[en]']"));
        System.out.println("Switch to Prices tab");
        productHelper.goToTab("Prices");
        String price = "30";
        System.out.println("Enter Purchase Price");
        WebElement priceElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='purchase_price']")));
        inputAndActionsHelper.enterText(priceElem, price, false);
        System.out.println("Check that price was entered correctly");
        Assert.assertEquals(priceElem.getAttribute("value"), price);
        System.out.println("Select Currency");
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='purchase_price_currency_code']")))).selectByValue("USD");
        System.out.println("Enter Price with Taxes USD");
        WebElement gross_pricesUSD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='gross_prices[USD]']")));
        String priceTotalUSD = "32.10";
        inputAndActionsHelper.enterText(gross_pricesUSD, priceTotalUSD, false);
        System.out.println("Check that total USD price is displayed in the corresponding Price input too");
        WebElement priceTotUSD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='prices[USD]']")));
        Assert.assertEquals(priceTotUSD.getAttribute("value"), priceTotalUSD);
        System.out.println("Enter Price with Taxes EUR");
        WebElement gross_pricesEUR = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='gross_prices[EUR]']")));
        String priceTotalEUR = "35.00";
        inputAndActionsHelper.enterText(gross_pricesEUR, priceTotalEUR, false);
        System.out.println("Check that total EUR price is displayed in the corresponding Price input too");
        WebElement priceTotEUR = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='prices[EUR]']")));
        Assert.assertEquals(priceTotEUR.getAttribute("value"), priceTotalEUR);
        System.out.println("Click on Save button");
        WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='save']")));
        inputAndActionsHelper.clickOn(saveBtn);
        wait.until(ExpectedConditions.invisibilityOf(saveBtn));
        System.out.println("Check that product has been successfully created");
        appManager.checkSuccessMessage("Changes were successfully saved.");
        System.out.println("Check that product with correct name is added to the Catalog");
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), '" + productName + "')]/..")));
        System.out.println("Check that the product also has small image preview");
        Assert.assertTrue(product.findElement(By.tagName("img")).isDisplayed());
        System.out.println("click on product to navigate to Products page");
        inputAndActionsHelper.clickOn(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), '" + productName + "')]")));
        wait.until(ExpectedConditions.urlContains("edit_product&category_id"));
        WebElement productPageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']/h1")));
        System.out.println("Check that product page header has correct product name");
        Assert.assertEquals(productPageHeader.getText(), "Edit Product: " + productName);

    }

}
