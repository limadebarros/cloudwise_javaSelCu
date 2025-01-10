package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverManager {
    private static WebDriver driver;
    private static WebDriverWait wait;

    private WebDriverManager() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "chromedriver-win64/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void bypassCookiePopup() {
        String cookiePopupSelector = "//*[contains(text(), 'Wij gebruiken cookies')]";
        String acceptButtonSelector = "//*[contains(text(), 'Accepteren')]";

        // Locate the cookie consent popup
        WebElement cookiePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cookiePopupSelector)));

        // Check if the cookie popup is displayed
        if (cookiePopup.isDisplayed()) {
            // Click the accept button on the cookie popup
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(acceptButtonSelector)));
            acceptButton.click();

            // Wait for the popup to disappear
            wait.until(ExpectedConditions.stalenessOf(cookiePopup));
        } else {
            System.out.println("Cookie consent popup is not visible. Continuing with the test.");
        }
    }
}