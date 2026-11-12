package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class P05_OverviewPage {


    private final WebDriver driver;


    private final By FINISH_BTN = By.id("finish");
    private final By CANCEL_BTN = By.id("cancel");
    private final By ITEMS_TOTAL_PRICE = By.className("summary_subtotal_label");
    private final By TAX_PRICE = By.className("summary_tax_label");
    private final By TOTAL_PRICE = By.className("summary_total_label");


    // # Constructors
    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }


    // # Public Methods

    public P06_CompletePage clickOnFinishButton() {
        Utility.clickOnElement(driver, FINISH_BTN);
        return new P06_CompletePage(driver);
    }

    public P02_HomePage clickOnCancelButton() {
        Utility.clickOnElement(driver, CANCEL_BTN);
        return new P02_HomePage(driver);
    }

    public BigDecimal getItemTotal() {
        return getAmount(ITEMS_TOTAL_PRICE);
    }

    public BigDecimal getTax() {
        return getAmount(TAX_PRICE);
    }

    public BigDecimal getTotal() {
        return getAmount(TOTAL_PRICE);
    }


    // # Helper Function
    private BigDecimal getAmount(By locator) {
        String text = Utility.getText(driver, locator);   // "Item total: $39.98"
        String number = text.substring(text.indexOf("$") + 1).trim();
        return new BigDecimal(number);
    }


}
