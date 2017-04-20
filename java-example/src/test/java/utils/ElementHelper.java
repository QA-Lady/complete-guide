package utils;

import org.openqa.selenium.*;
import utils.appManager.AppManager;

/**
 * Created by QA Lady on 4/19/2017.
 */
public class ElementHelper {
    private AppManager appManager;

    public ElementHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public boolean isElementPresent(WebDriver driver, WebElement parentElement, By locator) {
        try {
            parentElement.findElement(locator);
            return true;
        } catch (InvalidSelectorException ex) {
            throw ex;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (InvalidSelectorException ex) {
            throw ex;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }
}
