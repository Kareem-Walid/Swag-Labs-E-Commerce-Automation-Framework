package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutPage {


    private final WebDriver driver;


    private final By FIRST_NAME_INPUT = By.id("first-name");
    private final By LAST_NAME_INPUT = By.id("last-name");
    private final By ZIP_CODE_INPUT = By.id("postal-code");
    private final By CONTINUE_BTN = By.id("continue");
    private final By CANCEL_BTN = By.id("cancel");


    // # Constructors
    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }


    // # Public Methods
    public P04_CheckoutPage fillForm(String fName, String lName, String zipcode) {
        Utility.sendText(driver, FIRST_NAME_INPUT, lName);
        Utility.sendText(driver, LAST_NAME_INPUT, lName);
        Utility.sendText(driver, ZIP_CODE_INPUT, zipcode);
        return this;
    }


    public P05_OverviewPage clickOnContinueButton() {
        Utility.clickOnElement(driver, CONTINUE_BTN);
        return new P05_OverviewPage(driver);
    }

    public P03_CartPage clickOnCancelButton() {
        Utility.clickOnElement(driver, CANCEL_BTN);
        return new P03_CartPage(driver);
    }
}
