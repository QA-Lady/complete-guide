import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoginHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by QA Lady on 4/11/2017.
 */
public class CountriesTests extends TestBase {

    @Test
    public void sortingCheck() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        LoginHelper.doLogin("admin", "admin");
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr[@class='row']")));
        List<String> countries = new ArrayList<>();
        List<String> zones = new ArrayList<>();
        System.out.println("Getting countries and zones from GUI");
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            ((Locatable) row).getCoordinates().inViewPort();
            WebElement country = row.findElement(By.xpath("./td[5]/a"));
            String countryText = country.getText();
            countries.add(countryText);
            WebElement zoneNumber = row.findElement(By.xpath("./td[6]"));
            if (Integer.parseInt(zoneNumber.getText()) != 0) {
                ((Locatable) country).getCoordinates().inViewPort();
                country.click();
                wait.until(ExpectedConditions.titleIs("Edit Country | My Store"));
                WebElement tableHeader = driver.findElement(By.xpath("//table[@id='table-zones']//tr[@class= 'header']"));
                ((Locatable) tableHeader).getCoordinates().inViewPort();
                List<WebElement> zoneRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@id='table-zones']//tr/td[3]/text()/..")));
                for (WebElement zone : zoneRows) {
                    ((Locatable) zone).getCoordinates().inViewPort();
                    String zoneText = zone.getText();
                    zones.add(zoneText);
                }
                List<String> expectedZones = new ArrayList<>(zones);
                expectedZones.sort(Comparator.naturalOrder());
                System.out.println("Checking Zones sorting for '" + countryText + "': " + expectedZones);
                Assert.assertEquals(zones, expectedZones);
                //
                driver.navigate().back();
                //getting original rows after navigated back
                rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr[@class='row']")));

            }
        }
        List<String> expectedCountries = new ArrayList<>(countries);
        expectedCountries.sort(Comparator.naturalOrder());
        System.out.println("Checking Countries Sorting. Expected order is: " + expectedCountries);
        Assert.assertEquals(countries, expectedCountries);
    }

    @Test
    public void sortingCheck2() {
        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        LoginHelper.doLogin("admin", "admin");
        List<WebElement> countries = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr[@class='row']/td[3]/a")));
        List<String> countriesLabels = new ArrayList<>();
        List<String> zones = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++) {
            WebElement country = countries.get(i);
            String countryLabel = country.getText();
            System.out.println("Adding country: " + countryLabel);
            countriesLabels.add(countryLabel);
            country.click();
            wait.until(ExpectedConditions.stalenessOf(country));
            longWait.until(ExpectedConditions.urlContains("edit_geo_zone&page"));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@id='table-zones']//tr[2]//select[contains(@name,'[zone_code]')]")));
            Select zonesSelect = new Select(driver.findElement(By.xpath("//table[@id='table-zones']//tr[2]//select[contains(@name,'[zone_code]')]")));
            List<WebElement> zonesElems = zonesSelect.getOptions();
            for (WebElement zoneElem : zonesElems) {
                zones.add(zoneElem.getText());
            }
            List<String> expectedZones = new ArrayList<>(zones);
            expectedZones.sort(Comparator.naturalOrder());
            System.out.println("Checking Zones sorting for '" + countryLabel + "': " + expectedZones);
            Assert.assertEquals(zones, expectedZones);
            System.out.println("Clear Zones before check for the next Country");
            zones.clear();
            //
            System.out.println("Going back to original page");
            driver.navigate().back();
            //getting original rows after navigated back
            countries = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@class='dataTable']//tr[@class='row']/td[3]/a")));
        }
        List<String> expectedCountries = new ArrayList<>(countriesLabels);
        expectedCountries.sort(Comparator.naturalOrder());
        System.out.println("Checking Countries Sorting. Expected order is: " + expectedCountries);
        Assert.assertEquals(countriesLabels, expectedCountries);

    }
}
