import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoginHelper;
import utils.dataModel.CustomerData;
import utils.dataModel.DataProviders;

import java.util.Set;


/**
 * Created by QA Lady on 4/14/2017.
 */
public class NewUserTests extends TestBase {

    @Test(dataProvider = "CustomerInfo", dataProviderClass = DataProviders.class)
    public void newUserCreation(CustomerData customerData) {
        System.out.println("Get original state of Users Table");
        Set<String> oldIds = customerHelper.getCustomerIds();
        //
        customerHelper.createAccount(customerData);
        System.out.println("Check that account has been successfully created");
        appManager.checkSuccessMessage("Your customer account has been created.");
        LoginHelper.doLogout();
        System.out.println("Check that logout was successful");
        appManager.checkSuccessMessage("You are now logged out.");
        //
        System.out.println("login as Admin and check that users table was updated with the New user");
        Set<String> newIds = customerHelper.getCustomerIds();
        Assert.assertTrue(newIds.containsAll(oldIds));
        Assert.assertTrue(newIds.size() == oldIds.size() + 1);
        getUrl("http://localhost/litecart/en/");
        LoginHelper.doLogout();
        //
        System.out.println("Login again as the same user (the one that was just created)");
        LoginHelper.doLogin(customerData.getEmail(), customerData.getPassw());
        System.out.println("Logout again");
        LoginHelper.doLogout();
    }


}
