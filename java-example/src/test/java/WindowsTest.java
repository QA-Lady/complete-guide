import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import utils.LoginHelper;

import java.util.List;
import java.util.Set;

/**
 * Created by QA Lady on 4/17/2017.
 */
public class WindowsTest extends TestBase {
    @Test
    public void linkAndWindowsChecks() {
        driver.get("http://localhost/litecart/admin/");
        LoginHelper.doLogin("admin", "admin");
        System.out.println("Click on main Countries menu item");
        WebElement parentMenuCountries = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='box-apps-menu-wrapper']/ul/li[@id='app-']//span[2][text()='Countries']")));
        parentMenuCountries.click();
        wait.until(ExpectedConditions.urlToBe("http://localhost/litecart/admin/?app=countries&doc=countries"));
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr[@class='row']")));
        //Open 1st Country in the List for Edit
        WebElement row = rows.get(0);
        ((Locatable) row).getCoordinates().inViewPort();
        WebElement country = row.findElement(By.xpath("./td[5]/a"));
        String countryText = country.getText();
        System.out.println("Opening '" + countryText + "' for edit");
        country.click();
        wait.until(ExpectedConditions.urlContains("edit_country&country_code"));
        List<WebElement> externalLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//i[@class='fa fa-external-link']")));
        for (int i = 0; i < externalLinks.size(); i++) {
            WebElement externalLink = externalLinks.get(i);
            String originalWindow = driver.getWindowHandle();
            Set<String> existingWindows = driver.getWindowHandles();
            System.out.println("Click on External Link #" + (i + 1) + " to Open New Window");
            externalLink.click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            //switch to new window
            driver.switchTo().window(newWindow);
            String newWindowTitle = driver.getTitle();
            System.out.println("New Window Title: '" + newWindowTitle + "'");
            System.out.println("close the new window");
            driver.close();
            System.out.println("go back to originalWindow");
            driver.switchTo().window(originalWindow);
        }
    }

    private <V> ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

}





