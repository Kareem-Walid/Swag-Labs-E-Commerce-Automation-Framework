package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.*;

public final class Utility {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    // آخر locator اتعامل معاه (لكل thread) - بيستخدمه الـ screenshot عشان يعمل highlight
    private static final ThreadLocal<By> lastLocator = new ThreadLocal<>();

    // # Constructor
    private Utility() {

    }


    // # Wait Methods
    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, TIMEOUT);
    }


    // # Locator Memory
    public static By getLastLocator() {
        return lastLocator.get();
    }


    // # Locator Action Methods
    public static WebElement findWebElement(WebDriver driver, By locator) {
        lastLocator.set(locator);
        return generalWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void clickOnElement(WebDriver driver, By locator) {
        lastLocator.set(locator);
        generalWait(driver).until(ExpectedConditions.elementToBeClickable(locator)).click();
    }


    public static void sendText(WebDriver driver, By locator, String value) {
        lastLocator.set(locator);
        WebElement element = generalWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }


    public static String getText(WebDriver driver, By locator) {
        lastLocator.set(locator);
        return generalWait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }


    // # Page Methods
    public static void scrolling(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findWebElement(driver, locator));
    }


    public static void selectingFromDropDown(WebDriver driver, By locator, String option) {
        new Select(findWebElement(driver, locator)).selectByVisibleText(option);
    }


    // # Screenshot Methods

    // صورة للصفحة كاملة مع highlight لعنصر معيّن (القديمة زي ما هي)
    public static void takeScreenshot(WebDriver driver, By locator) {
        try {
            final String SCREENSHOTS_PATH = "test-outputs/Screenshots/";
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator))
                    .save(SCREENSHOTS_PATH);
        } catch (Exception e) {
            LogsUtils.error("Failed to take screenshot");
        }
    }

    // صورة للصفحة كاملة باسم الـ test، مع highlight لآخر عنصر اتعامل معاه (لو لسه موجود)
    public static void takeScreenshot(WebDriver driver, String name) {
        if (driver == null) {
            return;   // لو الـ setup فشل قبل فتح المتصفح
        }

        final String SCREENSHOTS_PATH = "test-outputs/Screenshots/";
        String fileName = name + "_" + System.currentTimeMillis();
        By locator = getLastLocator();

        // المحاولة الأولى: مع highlight
        if (locator != null) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty()) {
                    Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                            .highlight(elements.get(0))
                            .withName(fileName)
                            .save(SCREENSHOTS_PATH);
                    return;
                }
            } catch (Exception e) {
                LogsUtils.error("Highlight failed, taking plain screenshot: " + e.getMessage());
            }
        }

        // المحاولة التانية (fallback): صورة عادية من غير highlight
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .withName(fileName)
                    .save(SCREENSHOTS_PATH);
        } catch (Exception e) {
            LogsUtils.error("Failed to take screenshot: " + e.getMessage());
        }
    }


    // # Generate Method
    public static Set<Integer> generateUniqueNumbers(int upperBound, int totalNumbers) {

        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();
        while (uniqueNumbers.size() < totalNumbers) {
            uniqueNumbers.add(random.nextInt(upperBound) + 1);
        }
        return uniqueNumbers;
    }


    public static boolean verifyURL(WebDriver driver, String expectedUrl) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedUrl));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void clickAndSwitchToNewTab(WebDriver driver, By locator) {
        String originalWindow = driver.getWindowHandle();
        clickOnElement(driver, locator);
        Utility.generalWait(driver);

        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!originalWindow.equals(handle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }


    // # Cookies Methods

    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSessionCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }


    // # Files Methods
    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            return null;
        }
        Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
        return listOfFiles[0];
    }
}