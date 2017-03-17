package base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by QA Lady on 3/16/2017.
 */
public class TestBase {

    public static WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void configDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/Dev_Tools/Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void quitDriver() {
        driver.quit();
    }
}
