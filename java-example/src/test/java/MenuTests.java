import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import utils.LoginHelper;

import java.util.List;

/**
 * Created by QA Lady on 4/11/2017.
 */
public class MenuTests extends TestBase {

    @Test
    public void menuClickTest() {
        driver.get("http://localhost/litecart/admin/");
        LoginHelper.doLogin("admin", "admin");
        List<WebElement> parentMenuItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li")));
        for (int i = 0; i < parentMenuItems.size(); i++) {
            WebElement parentMenu = parentMenuItems.get(i);
            String parentMenuLabel = parentMenu.getText();
            inputAndActionsHelper.clickOn(parentMenu);
            WebElement header = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1")));
            System.out.println("Parent Menu: '" + parentMenuLabel + "' is selected and page header is '" + header.getText() + "'");
            //re-take parent menu item as it is no longer attached to DOM
            parentMenuItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li")));
            parentMenu = parentMenuItems.get(i);
            List<WebElement> childMenuItems = parentMenu.findElements(By.xpath("./ul/li"));
            if (childMenuItems.size() > 0) {
                wait.until(ExpectedConditions.visibilityOfAllElements(parentMenu.findElements(By.xpath("./ul/li"))));
                for (int j = 0; j < childMenuItems.size(); j++) {
                    WebElement childMenu = childMenuItems.get(j);
                    String childMenuLabel = childMenu.getText();
                    inputAndActionsHelper.clickOn(childMenu);
                    header = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1")));
                    System.out.println("Child Menu: '" + childMenuLabel + "' is selected and page header is '" + header.getText() + "'");
                    //re-take children items
                    parentMenuItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li")));
                    parentMenu = parentMenuItems.get(i);
                    childMenuItems = wait.until(ExpectedConditions.visibilityOfAllElements(parentMenu.findElements(By.xpath("./ul/li"))));
                }
            }
        }
    }


}
