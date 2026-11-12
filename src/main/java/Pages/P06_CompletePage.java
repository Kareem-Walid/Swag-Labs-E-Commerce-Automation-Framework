package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.clickAndSwitchToNewTab;

public class P06_CompletePage {

    // # Locators
    private static final By GENERATE_PDF_BTN = By.id("generate-pdf-order");
    private static final By BACK_HOME_BTN = By.id("back-to-products");
    private static final By LINKED_IN_ICON = By.cssSelector("[data-test=\"social-linkedin\"]");
    private static final By FACEBOOK_ICON = By.cssSelector("[data-test=\"social-facebook\"]");
    private static final By X_ICON = By.cssSelector("[data-test=\"social-x\"]");
    private static final By COMPLETION_TXT = By.cssSelector("data-test=\"complete-header\"");
    private final WebDriver driver;


    // # Constructors
    public P06_CompletePage(WebDriver driver) {

        this.driver = driver;
    }


    // # Methods
    public P06_CompletePage clickOnGeneratePdfBtn() {
        Utility.clickOnElement(driver, GENERATE_PDF_BTN);
        return new P06_CompletePage(driver);
    }

    public P02_HomePage clickOnBackHomeBtn() {
        Utility.clickOnElement(driver, BACK_HOME_BTN);
        return new P02_HomePage(driver);
    }

    public void clickOnLinkedInIcon() {
        clickAndSwitchToNewTab(driver, LINKED_IN_ICON);
    }

    public void clickOnFacebookIcon() {
        clickAndSwitchToNewTab(driver, FACEBOOK_ICON);
    }

    public void clickOnXIcon() {
        clickAndSwitchToNewTab(driver, X_ICON);
    }

}
