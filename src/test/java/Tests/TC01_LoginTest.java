package Tests;

import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.DataUtils.getPropertyData;

@Listeners({IInvokeMethodListenerClass.class, ITestResultListenerClass.class})

public class TC01_LoginTest {

    //private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    // private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    private final String USERNAME = DataUtils.getJsonData("invalidLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("invalidLogin", "password");
    private final String EXPECTED_ERROR = DataUtils.getJsonData("invalidLogin", "expectedError");

    public TC01_LoginTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyData("environment", "Browser"));
        getDriver().get(getPropertyData("environment", "P01_BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(alwaysRun = false)
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyData("environment", "P02_HOME_URL")));
    }


    // invalid pass
    @Test(alwaysRun = true)
    public void invalidLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).getErrorMessage().contains(EXPECTED_ERROR));
    }


    @Test(dataProvider = "validUsers")
    public void loginWithValidUsers(String username, String password) {
        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),
                "Login failed for user: " + username);
    }

    @AfterMethod
    public void tearDown() {
        getDriver().quit();
    }


    // =Helper Function
    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return DataUtils.getJsonArrayData("validLogin");
    }
}
