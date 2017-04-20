package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.appManager.AppManager;

/**
 * Created by QA Lady on 4/18/2017.
 */


public class InputAndActionsHelper {

    private AppManager appManager;

    public InputAndActionsHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public void enterText(WebElement element, String text, boolean masked) {
        element.clear();
        if (masked == true) {
            // put cursor to the beginning before the mask
            element.sendKeys(Keys.HOME + text);
        } else {
            element.sendKeys(text);

        }
    }

    public void clickOn(ExpectedCondition<WebElement> isTrue) {
        WebElement element = TestBase.wait.until(isTrue);
        element.click();
    }

    public void clickOn(WebElement element) {
        element.click();
    }

    public void enterTextTo(String text4Log, String text2Enter, By locator) {
        System.out.println("Enter " + text4Log);
        WebElement element = TestBase.wait.until(ExpectedConditions.elementToBeClickable(locator));
        enterText(element, text2Enter, false);
    }

    public void uploadFile(String f, final String text4Log) {
        System.out.println("Upload " + text4Log);
        WebElement browse = TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='new_images[]']")));
        String file = TestBase.class.getResource("/InputTestData/" + f).getFile().substring(1);
        //selenium doesn't like "/" had to replace to "\\"
        browse.sendKeys(file.replace("/", "\\"));
        System.out.println("Check that picture was uploaded successfully");
        Assert.assertTrue(browse.getAttribute("value").contains(f));
    }

    public void selectRadioBtnOrCheckbox(By locator) {
        //Get the Radio Button or Checkbox as WebElement by locator
        WebElement radioBtn = TestBase.wait.until(ExpectedConditions.elementToBeClickable(locator));

        //Check if its already selected? otherwise select it by calling click() method
        if (!radioBtn.isSelected())
            clickOn(radioBtn);

        //Verify it is selected
        Assert.assertTrue(radioBtn.isSelected());
    }
}
