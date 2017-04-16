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
        WebElement parentMenuCatalog = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li[@id='app-']//span[2][text()='Catalog']")));
        parentMenuCatalog.click();
        System.out.println("Click on 'Add New Product' button");
        WebElement addNewProduct = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='content']/div[1]/a[2]")));
        addNewProduct.click();
        System.out.println("Click on General tab");
        WebElement generalTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='tabs']//li/a[text()='General']/..")));
        generalTab.click();
        wait.until(ExpectedConditions.attributeContains(generalTab, "class", "active"));
        System.out.println("Select 'Enabled' radio button in the 'Status radio group");
        selectRadioBtnOrCheckbox(By.xpath("//input[@type='radio']/..[contains(text(),'Enabled')]/input"));
        System.out.println("Enter product name");
        String productName = "Crapemyrtle";
        WebElement productNameElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name[en]']")));
        enterText(productNameElem, productName, false);
        System.out.println("Enter product code");
        WebElement productCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='code']")));
        enterText(productCode, RandomStringUtils.randomAlphabetic(10), false);
        System.out.println("Select Unisex checkbox in Product Groups Gender");
        selectRadioBtnOrCheckbox(By.xpath("//input[@name='product_groups[]'][@value='1-3']"));
        System.out.println("Enter random quantity");
        WebElement productQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='quantity']")));
        enterText(productQuantity, RandomStringUtils.randomNumeric(2), false);
        System.out.println("Select Sould Out Status");
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='sold_out_status_id']")))).selectByVisibleText("Temporary sold out");
        System.out.println("Upload Image");
        WebElement browse = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='new_images[]']")));
        String file = AddProductTest.class.getResource("/InputTestData/CherryDazzle_Crapemyrtle.jpg").getFile().substring(1);
        //selenium doesn't like "/" had to replace to "\\"
        browse.sendKeys(file.replace("/", "\\"));
        System.out.println("Check that picture was uploaded successfully");
        Assert.assertTrue(browse.getAttribute("value").contains("CherryDazzle_Crapemyrtle.jpg"));
        System.out.println("Date Valid From");
        WebElement dateValid = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='date_valid_from']")));
        enterText(dateValid, "2017-04-15", true);
        System.out.println("Date Valid To");
        WebElement dateValidTo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='date_valid_to']")));
        enterText(dateValidTo, "2017-06-19", true);
        System.out.println("Switch to Information tab");
        WebElement informationTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='tabs']//li/a[text()='Information']/..")));
        informationTab.click();
        wait.until(ExpectedConditions.attributeContains(informationTab, "class", "active"));
        System.out.println("Select Manufacturer");
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='manufacturer_id']")))).selectByVisibleText("ACME Corp.");
        System.out.println("Enter Keywords");
        WebElement keyword = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='keywords']")));
        enterText(keyword, "bush, landscaping", false);
        System.out.println("Enter Short Description");
        WebElement shortDescr = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='short_description[en]']")));
        enterText(shortDescr, RandomStringUtils.randomAlphabetic(16), false);
        System.out.println("Enter Detailed Description");
        WebElement detailDescr = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='trumbowyg-editor']")));
        enterText(detailDescr, RandomStringUtils.randomAlphabetic(140), false);
        System.out.println("Enter Head Title");
        WebElement headTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='head_title[en]']")));
        enterText(headTitle, RandomStringUtils.randomAlphabetic(8), false);
        System.out.println("Enter Meta Description");
        WebElement metaDescr = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='meta_description[en]']")));
        enterText(metaDescr, RandomStringUtils.randomAlphabetic(16), false);
        System.out.println("Switch to Prices tab");
        WebElement pricesTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='tabs']//li/a[text()='Prices']/..")));
        pricesTab.click();
        wait.until(ExpectedConditions.attributeContains(pricesTab, "class", "active"));
        System.out.println("Enter Purchase Price");
        WebElement priceElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='purchase_price']")));
        String price = "30";
        enterText(priceElem, price, false);
        System.out.println("Check that price was entered correctly");
        Assert.assertEquals(priceElem.getAttribute("value"), price);
        System.out.println("Select Currency");
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='purchase_price_currency_code']")))).selectByValue("USD");
        System.out.println("Enter Price with Taxes USD");
        WebElement gross_pricesUSD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='gross_prices[USD]']")));
        String priceTotalUSD = "32.10";
        enterText(gross_pricesUSD, priceTotalUSD, false);
        System.out.println("Check that total USD price is displayed in the corresponding Price input too");
        WebElement priceTotUSD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='prices[USD]']")));
        Assert.assertEquals(priceTotUSD.getAttribute("value"), priceTotalUSD);
        System.out.println("Enter Price with Taxes EUR");
        WebElement gross_pricesEUR = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='gross_prices[EUR]']")));
        String priceTotalEUR = "35.00";
        enterText(gross_pricesEUR, priceTotalEUR, false);
        System.out.println("Check that total EUR price is displayed in the corresponding Price input too");
        WebElement priceTotEUR = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='prices[EUR]']")));
        Assert.assertEquals(priceTotEUR.getAttribute("value"), priceTotalEUR);
        System.out.println("Click on Save button");
        WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='save']")));
        saveBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(saveBtn));
        System.out.println("Check that account has been successfully created");
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notices']/div")));
        Assert.assertEquals(successMessage.getText(), "Changes were successfully saved.", "check success message");
        System.out.println("Check that product with correct name is added to the Catalog");
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), '" + productName + "')]/..")));
        System.out.println("Check that the product also has small image preview");
        Assert.assertTrue(product.findElement(By.tagName("img")).isDisplayed());
        System.out.println("click on product to navigate to Products page");
        product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='dataTable']//tr/td[3]//a[contains(text(), '" + productName + "')]")));
        product.click();
        wait.until(ExpectedConditions.urlContains("edit_product&category_id"));
        WebElement productPageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']/h1")));
        System.out.println("Check that product page header has correct product name");
        Assert.assertEquals(productPageHeader.getText(), "Edit Product: " + productName);

    }

    public void selectRadioBtnOrCheckbox(By locator) {
        //Get the Radio Button or Checkbox as WebElement by locator
        WebElement radioBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));

        //Check if its already selected? otherwise select it by calling click() method
        if (!radioBtn.isSelected())
            radioBtn.click();

        //Verify it is selected
        Assert.assertTrue(radioBtn.isSelected());
    }
}
