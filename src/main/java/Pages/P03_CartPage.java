package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {


    private static final By ITEM_PRICES = By.className("inventory_item_price");
    private static final By CHECK_OUT_BTN = By.id("checkout");
    private static final By CONTINUE_BTN = By.id("continue-shopping");
    private final WebDriver driver;


    // # Constructors
    public P03_CartPage(WebDriver driver) {

        this.driver = driver;
    }


    // # Public Methods
    public String getTotalPrice() {
        try {
            float totalPrice = 0;
            List<WebElement> prices = driver.findElements(ITEM_PRICES);
            for (WebElement price : prices) {
                totalPrice += Float.parseFloat(price.getText().replace("$", ""));
            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

//    // TODO
//    public P03_CartPage removeItemFromCart(String itemName) {
//        Utility.clickOnElement(driver, CHECK_OUT_BTN);
//        return this;
//    }


    public P04_CheckoutPage clickOnCheckoutButton() {
        Utility.clickOnElement(driver, CHECK_OUT_BTN);
        return new P04_CheckoutPage(driver);
    }

    public P02_HomePage clickOnContinueShoppingButton() {
        Utility.clickOnElement(driver, CONTINUE_BTN);
        return new P02_HomePage(driver);
    }


}
