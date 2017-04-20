package base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.*;
import utils.appManager.AppManager;

import java.net.MalformedURLException;

/**
 * Created by QA Lady on 3/16/2017.
 */
public class TestBase {

    public static WebDriverWait wait;
    public static WebDriverWait shortWait;
    public static WebDriverWait longWait;

    protected final AppManager appManager = new AppManager();
    protected WebDriverHelper webDriverHelper;
    protected WebDriver driver;
    protected InputAndActionsHelper inputAndActionsHelper;
    protected CustomerHelper customerHelper;
    protected ProductHelper productHelper;
    protected ElementHelper elementHelper;
    protected ShoppingCartHelper shoppingCartHelper;


    @BeforeSuite
    public void initAppmabager() {
        appManager.init();
        //
        webDriverHelper = appManager.getWebDriverHelper();
        inputAndActionsHelper = appManager.getInputAndActionsHelper();
        customerHelper = appManager.getCustomerHelper();
        productHelper = appManager.getProductHelper();
        elementHelper = appManager.getElementHelper();
        shoppingCartHelper = appManager.getShoppingCartHelper();
    }

    @Parameters({"browser", "platform", "version", "url"})
    @BeforeTest(alwaysRun = true)
    public void setup(String browser, @Optional String platform, @Optional String version, @Optional String url) throws MalformedURLException {
        webDriverHelper.setupDriver(browser, platform, version, url);
        driver = webDriverHelper.getDriver();
        //assigning wait values to be used in explicit waits
        wait = new WebDriverWait(driver, 10);
        shortWait = new WebDriverWait(driver, 2);
        longWait = new WebDriverWait(driver, 20);
        //maximize browser window
        driver.manage().window().maximize();
        getUrl(url);
        //implicit wait
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public void getUrl(@Optional String url) {
        if (url == null) {
            driver.get("http://localhost/litecart/admin/");
        } else {
            driver.get(url);
        }
    }

    @AfterSuite
    public void tearDownDriver() {
        webDriverHelper.quitDriver();
    }


}
