package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
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

public class TC04_CheckoutTest {


    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtils.getJsonData("information", "fName");
    private final String LASTNAME = DataUtils.getJsonData("information", "lName");
    private final String ZIPCODE = DataUtils.getJsonData("information", "ZipCode");

    public TC04_CheckoutTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyData("environment", "Browser"));
        getDriver().get(getPropertyData("environment", "BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        new P02_HomePage(getDriver())
                .addProducts(6, 4)
                .clickOnCartIcon();

        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();

    }

    @Test(enabled = true)
    public void fillFormWithValidData() throws IOException {
        new P04_CheckoutPage(getDriver())
                .fillForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();
        Assert.assertEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "P05_OVER_VIEW_URL"));
    }

    @AfterMethod
    public void tearDown() {

    }
}

