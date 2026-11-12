package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {

    private final WebDriver driver;

    // # Locators
    private static final By USERNAME_INPUT = By.cssSelector("#user-name[placeholder=\"Username\"]");
    private static final By PASSWORD_INPUT = By.cssSelector("#password[placeholder=\"Password\"]");
    private static final By LOGIN_BTN = By.cssSelector("#login-button[data-test=\"login-button\"]");
    private static final By ERROR_MSG = By.cssSelector("[data-test=\"error\"]");


    // # Constructors
    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    // # Methods
    public P01_LoginPage enterUsername(String usernameText) {
        Utility.sendText(driver, USERNAME_INPUT, usernameText);
        return this;
    }

    public P01_LoginPage enterPassword(String passwordText) {
        Utility.sendText(driver, PASSWORD_INPUT, passwordText);
        return this;
    }

    public P02_HomePage clickLoginButton() {
        Utility.clickOnElement(driver, LOGIN_BTN);
        return new P02_HomePage(driver);
    }

    public String getErrorMessage() {
        return Utility.getText(driver, ERROR_MSG);
    }
}
