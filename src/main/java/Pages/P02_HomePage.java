package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_HomePage {

    // # Locators
    private static final String ADD_TO_CART_BTN = "(//button[contains(@class,'btn_inventory')])";
    private static final By CART_ICON = By.xpath("//a[contains(@class,'shopping_cart_link')]");
    private static final By CART_BADGE = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    private static final By REMOVE_BTN = By.xpath("//button[contains(@id,'remove')]");
    private static final By SELECTED_PRODUCTS_PRICES = By.xpath(
            "//button[contains(@id,'remove')]/preceding-sibling::div[@class='inventory_item_price']");

    private final WebDriver driver;


    // # Constructors
    public P02_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // # Dynamic Locator
    private static By addToCartButton(int index) {

        return By.xpath(ADD_TO_CART_BTN + "[" + index + "]");
    }

    // # Public Methods
    public P02_HomePage addProducts(int upperBound, int totalNumbers) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumbers(upperBound, totalNumbers);
        for (int index : randomNumbers) {
            Utility.clickOnElement(driver, addToCartButton(index));
        }
        return this;
    }

    public String getNumberOfProductsInCart() {
        try {
            return Utility.getText(driver, CART_BADGE);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }


    public P03_CartPage clickOnCartIcon() {
        Utility.clickOnElement(driver, CART_ICON);
        return new P03_CartPage(driver);
    }


    public String getNumberOfSelectedProducts() {
        try {
            List<WebElement> selectedProducts = driver.findElements(REMOVE_BTN);
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public String getTotalPricesOfSelectedProducts() {
        try {
            float totalPrice = 0;
            List<WebElement> pricesOfSelectedProducts = driver.findElements(SELECTED_PRODUCTS_PRICES);
            for (WebElement price : pricesOfSelectedProducts) {
                totalPrice += Float.parseFloat(price.getText().replace("$", ""));
            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }


}
