package utils.dataModel;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

/**
 * Created by QA Lady on 4/19/2017.
 */
public class DataProviders {

    @DataProvider(name = "CustomerInfo")
    public static Object[][] text() {
        return new Object[][]{{new CustomerData(RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomNumeric(5), "United States", Integer.parseInt(RandomStringUtils.randomNumeric(1)), RandomStringUtils.random(6, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'x', 'y', 'z'}) + "@google.com", RandomStringUtils.randomNumeric(7), RandomStringUtils.randomAlphabetic(10))}, {new CustomerData(RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomNumeric(5), "United States", Integer.parseInt(RandomStringUtils.randomNumeric(1)), RandomStringUtils.random(6, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'x', 'y', 'z'}) + "@yandex.com", RandomStringUtils.randomNumeric(7), RandomStringUtils.randomAlphabetic(10))}, {new CustomerData(RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomNumeric(5), "United States", Integer.parseInt(RandomStringUtils.randomNumeric(1)), RandomStringUtils.random(6, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'x', 'y', 'z'}) + "@yahoo.com", RandomStringUtils.randomNumeric(7), RandomStringUtils.randomAlphabetic(10))}};
    }
}
