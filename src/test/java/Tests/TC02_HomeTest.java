package Tests;

import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Utilities.DataUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.DataUtils.getPropertyData;

@Listeners({IInvokeMethodListenerClass.class, ITestResultListenerClass.class})

public class TC02_HomeTest {

    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    // private Set<Cookie> cookies;

    public TC02_HomeTest() throws FileNotFoundException {
    }

    @BeforeClass
    public void loginCookies() throws IOException {
        setupDriver(getPropertyData("environment", "Browser"));
        getDriver().get(getPropertyData("environment", "P01_BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        // cookies = getAllCookies(getDriver());
    }

    @BeforeMethod
    public void setup() throws IOException {
        getDriver().get(getPropertyData("environment", "P01_BASE_URL"));
        getDriver().get(getPropertyData("environment", "P01_BASE_URL"));
        // restoreSessionCookies(getDriver(), cookies);
        getDriver().navigate().refresh();

    }


    @Test(alwaysRun = true)
    public void addingProductsToCartTC() {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        new P02_HomePage(getDriver()).addProducts(6, 6);
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsInCart(), "6");
    }

    @Test(alwaysRun = true)
    public void clickOnCartIcconTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton()
                .clickOnCartIcon();
        //Assert.assertTrue(new P02_HomePage(getDriver()).verifyCartPageURL(getPropertyData("environment", "P03_CART_URL")));
    }


    @AfterMethod
    public void tearDown() {
        //getDriver().quit();
    }

    @AfterClass
    public void deleteSession() {
        // cookies.clear();
    }


}
