import base.TestBase;
import net.lightbody.bmp.core.har.Har;
import org.testng.annotations.Test;
import utils.LoginHelper;

/**
 * Created by QA-Lady on 4/18/2017.
 */
public class ProxyTest extends TestBase {

    @Test
    public void proxyChecks() {
        // create a new HAR with the label "harLog"
        webDriverHelper.proxy.newHar("harLog");
        driver.get("http://localhost/litecart/admin/");
        LoginHelper.doLogin("admin", "admin");
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        // get the HAR data
        Har har = webDriverHelper.proxy.getHar();
        har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() + ":" + l.getRequest().getUrl()));
        webDriverHelper.proxy.endHar();
    }
}
