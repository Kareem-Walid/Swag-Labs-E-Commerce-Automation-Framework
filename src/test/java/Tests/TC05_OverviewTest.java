package Tests;

import Pages.*;
import Utilities.DataUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.DataUtils.getPropertyData;

public class TC05_OverviewTest {


    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtils.getJsonData("information", "fName");
    private final String LASTNAME = DataUtils.getJsonData("information", "lName");
    private final String ZIPCODE = DataUtils.getJsonData("information", "ZipCode");

    public TC05_OverviewTest() throws FileNotFoundException {
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

        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();

        new P04_CheckoutPage(getDriver())
                .fillForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();
    }

    @Test
    public void checkCalculation() {
        BigDecimal items = new P05_OverviewPage(getDriver()).getItemTotal();
        BigDecimal tax = new P05_OverviewPage(getDriver()).getTax();
        BigDecimal total = new P05_OverviewPage(getDriver()).getTotal();
        Assert.assertEquals(total.compareTo(items.add(tax)), 0);
    }

    @Test
    public void clickOnFinishButton() throws IOException {
        new P05_OverviewPage(getDriver())
                .clickOnFinishButton();
        Assert.assertEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "P06_COMPLETE_URL"));
    }

    @AfterMethod
    public void tearDown() {

    }

}
