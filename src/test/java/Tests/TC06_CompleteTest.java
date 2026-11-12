package Tests;

import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtils;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.DataUtils.getPropertyData;


@Listeners({IInvokeMethodListenerClass.class, ITestResultListenerClass.class})

public class TC06_CompleteTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";
    // private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    // private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtils.getJsonData("information", "fName");
    private final String LASTNAME = DataUtils.getJsonData("information", "lName");
    private final String ZIPCODE = DataUtils.getJsonData("information", "ZipCode");

    public TC06_CompleteTest() throws FileNotFoundException {
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

        new P05_OverviewPage(getDriver())
                .clickOnFinishButton();
    }


    // TODO: want some extra effort
    @Test(enabled = false)
    public void Generate_PDF_TC() {
        new P06_CompletePage(getDriver())
                .clickOnGeneratePdfBtn();
    }

    @Test(enabled = true)
    public void check_BackHomeBtn_TC() throws IOException {
        new P06_CompletePage(getDriver())
                .clickOnBackHomeBtn();
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyData("environment", "P02_HOME_URL")));
    }

    @Test(enabled = true)
    public void check_CarEmptyAfterOrder_TC() {
        new P06_CompletePage(getDriver())
                .clickOnBackHomeBtn();
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsInCart(), "0");
    }

    @Test(enabled = true)
    public void check_Linkedin_SocialMediaTC() throws IOException {
        new P06_CompletePage(getDriver())
                .clickOnLinkedInIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyData("environment", "OFFICIAL_LINKED_IN_LINK")));
    }

    @Test(enabled = true)
    public void check_Facebook_SocialMediaTC() throws IOException {
        new P06_CompletePage(getDriver())
                .clickOnFacebookIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyData("environment", "OFFICIAL_X_LINK")));
    }

    @Test(enabled = true)
    public void check_X_SocialMediaTC() throws IOException {
        new P06_CompletePage(getDriver())
                .clickOnXIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyData("environment", "OFFICIAL_X_LINK")));
    }


    @AfterMethod
    public void tearDown() {
        getDriver().quit();
    }
}
