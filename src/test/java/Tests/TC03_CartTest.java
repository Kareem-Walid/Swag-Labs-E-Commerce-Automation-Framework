package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.DataUtils.getPropertyData;

public class TC03_CartTest {
    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");

    public TC03_CartTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyData("environment", "Browser"));
        getDriver().get(getPropertyData("environment", "P01_BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        new P02_HomePage(getDriver())
                .addProducts(6, 4)
                .clickOnCartIcon();
    }


    @Test(enabled = true)
    public void checkCheckoutButton() throws IOException {
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();
        Assert.assertEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "P04_CHECK_OUT_URL"));
    }


    @Test(enabled = true)
    public void checkContinueShoppingButton() throws IOException {
        new P03_CartPage(getDriver())
                .clickOnContinueShoppingButton();
        Assert.assertEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "P02_HOME_URL"));
    }

    @AfterMethod
    public void tearDown() {

    }
}
