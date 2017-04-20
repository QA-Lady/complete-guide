package utils;

import base.TestBase;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.annotations.Optional;
import utils.appManager.AppManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by QA Lady on 4/19/2017.
 */
public class WebDriverHelper {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();//EventFiringWebDriver can also be added here it implements WebDriver
    private static List<WebDriver> drivers = new ArrayList<>();
    private WebDriver driver;
    public BrowserMobProxy proxy;
    DesiredCapabilities capabilities = new DesiredCapabilities();
    LoggingPreferences logPrefs = new LoggingPreferences();

    protected AppManager appManager;

    public WebDriverHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public void setupDriver(String browser, @Optional String platform, @Optional String version, @Optional String url) throws MalformedURLException {
        if (threadLocalDriver.get() != null) {
            driver = threadLocalDriver.get();
        }
        if (platform == null) {
            System.out.println("Starting beforeTest for " + browser + " on thread:  " + Thread.currentThread().hashCode());
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
//            where is driver (when not added to system path but is available locally)
                System.setProperty("webdriver.chrome.driver", "C:/Dev_Tools/Drivers/chromedriver.exe");
                options.addArguments("start-maximized");
//                driver = new ChromeDriver(options);
                //when is built with the project from resources folder
//          System.setProperty("webdriver.chrome.driver", TestBase.class.getResource("/drivers/chromedriver.exe").getFile());

                //using regular WebDriver
                logPrefs.enable(LogType.BROWSER, Level.ALL);
                capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                driver = new ChromeDriver(capabilities);
                System.out.println(((HasCapabilities) driver).getCapabilities());

//               //using EventFiringWebDriver
//                driver = new EventFiringWebDriver(new ChromeDriver(options));
//                //cast driver to EventFiringWebDriver
//                ((EventFiringWebDriver) driver).register(new MyListener());
            } else if (browser.equalsIgnoreCase("Proxy")) {
                // start the proxy
                proxy = new BrowserMobProxyServer();
                proxy.start(0);
                int port = proxy.getPort();

                // get the Selenium proxy object
                Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

                // configure it as a desired capability
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

                // start the browser up
                driver = new ChromeDriver(capabilities);

                // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
                proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
            } else if (browser.equalsIgnoreCase("firefox")) {
//        where is driver (when not added to system path but is available locally)
                System.setProperty("webdriver.gecko.driver", "C:/Dev_Tools/Drivers/geckodriver.exe");
//        when is built with the project from resources folder
//            System.setProperty("webdriver.gecko.driver", TestBase.class.getResource("/drivers/geckodriver.exe").getFile());
//            where is browser
                System.out.println("Starting ff from C:/Program Files/Mozilla Firefox/firefox.exe");
                capabilities.setCapability(FirefoxDriver.BINARY, "C:/Program Files/Mozilla Firefox/firefox.exe");

                //we may skip next capability when starting geckodriver for new FF just use new FirefoxDriver();
//        capabilities.setCapability(FirefoxDriver.MARIONETTE, true);

                capabilities.setCapability("unexpectedAlertBehaviour", "dismiss");
                driver = new FirefoxDriver(capabilities);
                System.out.println(((HasCapabilities) driver).getCapabilities());
            } else if (browser.equalsIgnoreCase("firefox_old")) {
//        where is driver
                System.setProperty("webdriver.gecko.driver", "C:/Dev_Tools/Drivers/geckodriver.exe");
                // where is browser
//            capabilities.setCapability(FirefoxDriver.BINARY, "C:/Dev_Tools/Drivers/ESR/firefox.exe");
                //deprecated way to say where is browser
//            driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Dev_Tools\\Drivers\\ESR\\firefox.exe")), new FirefoxProfile(), capabilities);

                // the way to start old Firefox til version 47 inclusive (Selenium 3.3.1 has issue with this approach)
//          capabilities.setCapability(FirefoxDriver.MARIONETTE, false);

//            workaround 2 for Selenium bug with old FireFox launch
                capabilities.setCapability("raisesAccessibilityExceptions", false);
                capabilities.setCapability("acceptSslCerts", true);
//            driver = new FirefoxDriver(new FirefoxOptions().setLegacy(true).addDesiredCapabilities(capabilities));
                //or set binary location during driver initialization using regular WebDriver
                driver = new FirefoxDriver(
                        new FirefoxOptions()
                                .setLegacy(true)
                                .setBinary(new FirefoxBinary(new File("C:/Dev_Tools/Drivers/ESR/firefox.exe"))).addDesiredCapabilities(capabilities));
                System.out.println(((HasCapabilities) driver).getCapabilities());
                logPrefs.enable(LogType.BROWSER, Level.ALL);

                //using EventFiringWebDriver
//                driver = new EventFiringWebDriver(new FirefoxDriver(
//                        new FirefoxOptions()
//                                .setLegacy(true)
//                                .setBinary(new FirefoxBinary(new File("C:/Dev_Tools/Drivers/ESR/firefox.exe"))).addDesiredCapabilities(capabilities)));
//                //cast driver to EventFiringWebDriver
//                ((EventFiringWebDriver) driver).register(new MyListener());
            } else if (browser.equalsIgnoreCase("ff_n")) {
//        where is driver
                System.setProperty("webdriver.gecko.driver", TestBase.class.getResource("/drivers/geckodriver.exe").getFile());
//            deprecated approach
//            driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Dev_Tools\\Drivers\\Nightly\\firefox.exe")));
//            new approach to let selenium know where is browser
                capabilities.setCapability(FirefoxDriver.BINARY, "C:/Dev_Tools/Drivers/Nightly/firefox.exe");
                driver = new FirefoxDriver(capabilities);
            } else if (browser.contains("ie")) {
                System.setProperty("webdriver.ie.driver", TestBase.class.getResource("/drivers/IEDriverServer.exe").getFile());
                capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                driver = new InternetExplorerDriver(capabilities);
                System.out.println(((HasCapabilities) driver).getCapabilities());
                //start IE with detailed log
//            InternetExplorerDriverService service = new InternetExplorerDriverService.Builder()
//                    .withLogLevel(InternetExplorerDriverLogLevel.TRACE)
//                    .withLogFile(new File("iedriver.log"))
//                    .build();
//            InternetExplorerDriver driver = new InternetExplorerDriver(service);
            }
        } else if (browser.contentEquals("remote.ie.windowsXP")) {
            DesiredCapabilities capabillities = DesiredCapabilities.internetExplorer();
            capabillities.setCapability("platform", "Windows XP");
            capabillities.setCapability("version", "8");
            driver = new RemoteWebDriver(
                    new URL("http://QA_Lady:f6224eba-1654-4f62-9023-4d72fbf04c21@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } else if (browser.contentEquals("remote.firefox.windows7")) {
            DesiredCapabilities capabillities = DesiredCapabilities.firefox();
            capabillities.setCapability("platform", "Windows 7");
            capabillities.setCapability("version", "Firefox 52");
            driver = new RemoteWebDriver(
                    new URL("http://QA_Lady:f6224eba-1654-4f62-9023-4d72fbf04c21@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } else if (browser.contentEquals("remote.chrome.windows8")) {
            DesiredCapabilities capabillities = DesiredCapabilities.chrome();
            capabillities.setCapability("platform", "Windows 8");
            capabillities.setCapability("version", "");
            driver = new RemoteWebDriver(
                    new URL("http://QA_Lady:f6224eba-1654-4f62-9023-4d72fbf04c21@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } else if (browser.contentEquals("remote.chrome.linux")) {
            DesiredCapabilities capabillities = DesiredCapabilities.chrome();
            capabillities.setCapability("platform", "Linux");
            capabillities.setCapability("version", "");
            driver = new RemoteWebDriver(
                    new URL("http://QA_Lady:f6224eba-1654-4f62-9023-4d72fbf04c21@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } else if (browser.contentEquals("remote.OSX10.8.ipad")) {
            DesiredCapabilities capabillities = DesiredCapabilities.ipad();
            capabillities.setCapability("platform", "OS X 10.8");
            capabillities.setCapability("version", "6");
            driver = new RemoteWebDriver(
                    new URL("http://QA_Lady:f6224eba-1654-4f62-9023-4d72fbf04c21@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } else if (browser.contentEquals("remote.mobile.android")) {
            DesiredCapabilities capabillities = DesiredCapabilities.android();
            capabillities.setCapability("platform", "Linux");
            capabillities.setCapability("version", "4.0");
            driver = new RemoteWebDriver(
                    new URL("http://QA_Lady:f6224eba-1654-4f62-9023-4d72fbf04c21@ondemand.saucelabs.com:80/wd/hub"),
                    capabillities);
        } else if (platform != null) {
            //Platforms
            if (platform.equalsIgnoreCase("Windows"))
                capabilities.setPlatform(Platform.WINDOWS);

            if (platform.equalsIgnoreCase("MAC"))
                capabilities.setPlatform(Platform.MAC);

            if (platform.equalsIgnoreCase("Andorid"))
                capabilities.setPlatform(Platform.ANDROID);

            //Browsers
            if (browser.equalsIgnoreCase("Internet Explorer"))
                capabilities = DesiredCapabilities.internetExplorer();

            if (browser.equalsIgnoreCase("Firefox"))
                capabilities = DesiredCapabilities.firefox();

            if (browser.equalsIgnoreCase("Chrome"))
                capabilities = DesiredCapabilities.chrome();

            if (browser.equalsIgnoreCase("iPad"))
                capabilities = DesiredCapabilities.ipad();

            if (browser.equalsIgnoreCase("Android"))
                capabilities = DesiredCapabilities.android();

            //Version
            capabilities.setVersion(version);

            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }
        //set thread local driver
        threadLocalDriver.set(driver);

        drivers.add(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        for (WebDriver webDriver : drivers) {
            webDriver.close();
            webDriver.quit();
        }
    }

    public List<String> getBrowserLog() {
        System.out.println("Browser supports these types of logs: " + driver.manage().logs().getAvailableLogTypes());
        List<String> browserLog = new ArrayList<>();
        for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
            browserLog.add(l.getMessage());
            System.out.println(l);
//            driver.manage().logs().get("browser").forEach(logEntry -> System.out.println(l));
        }
        return browserLog;
    }

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            // too many logs commenting out this one
//            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
            driver.close();
            driver.quit();
        }
    }


}
