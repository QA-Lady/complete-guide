import base.TestBase;
import org.testng.annotations.Test;

/**
 * Created by QA-Lady on 4/16/2017.
 */


public class ShoppingCartTest extends TestBase {

    @Test
    public void shoppingCartTest() {
        productHelper.addProductsToCartAndCheckItsContent();
        productHelper.initCheckout();
        int originalItemsNumber = shoppingCartHelper.checkShoppingCartTable(3);
        for (int j = 1; j <= 3; j++) {
            shoppingCartHelper.deleteItemFromShoppingCart();
            if (j == 3) {
                shoppingCartHelper.checkEmptyCartMessage(j);
                shoppingCartHelper.goBackToHomePage();
                return;
            } else {
                shoppingCartHelper.checkThatRemovedItemIsNotInShoppingCartTable(originalItemsNumber, j);
            }
        }
    }


}
