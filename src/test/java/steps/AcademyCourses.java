package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AcademyCourses {
    private WebDriver driver;
    private WebDriverWait wait;

    // List to store article titles
    private List<String> articleTitles = new ArrayList<>();

    @Given("I am at the Cloudwise website")
    public void iNavigateToTheCloudwiseWebsite() {
        driver = WebDriverManager.getDriver();
        driver.get("https://cloudwise.nl/");
        wait = new WebDriverWait(driver, 10);
    }

    @When("I go to the Academy tab")
    public void iGoToTheAcademyTab() {
        WebDriverManager.bypassCookiePopup();
        // Find the Academy tab by link text and click it
        WebElement academyTab = driver.findElement(By.linkText("Academy"));
        academyTab.click();
        // Wait until the page header (h1) is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pageIntro h1")));
        // Assert that the right page is visible
        String expectedTitle = "Academy";
        String actualTitle = driver.findElement(By.cssSelector(".pageIntro h1")).getText();
        assertEquals("The page title should match the expected title", expectedTitle, actualTitle);
    }

    @Then("I verify that the page header matches the title of the course on the Academy page")
    public void iCheckThatThePageHeaderMatches() {
        // Wait for the articles to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dienst-item h4")));
        // Find all course titles on the page
        List<WebElement> articles = driver.findElements(By.cssSelector(".dienst-item h4"));
        if (articles.isEmpty()) {
            System.out.println("No articles found with the specified selector.");
        } else {
            // Loop through each article and store its title
            for (WebElement article : articles) {
                // Get the course title
                String title = article.getText();
                // Add the title to the list
                articleTitles.add(title);
            }
        }

        for (String title : articleTitles) {
            // Wait until the course title is clickable
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[text()='" + title + "']")));
            // Click on the course title
            driver.findElement(By.xpath("//h4[text()='" + title + "']")).click();
            // Wait for the <h1> element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pageIntro h1")));
            // Wait until the text of the <h1> matches the expected title
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".pageIntro h1"), title));
            // Retrieve the text of the <h1>
            String pageHeader = driver.findElement(By.cssSelector(".pageIntro h1")).getText();
            // Assert that the page header matches the article title
            assertEquals("The page header should match the article title", title, pageHeader);
            // Navigate back to the previous(Academy) page
            driver.navigate().back();
            // Wait for the courses to be visible again after navigating back
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dienst-item h4")));
        }
    }

    @Then("I print the total number of courses grouped by category")
    public void iPrintTotalNumberOfCoursesGroupedByCategory() {
        // Wait for the courses to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dienst-item")));
        // Find all course elements
        List<WebElement> courses = driver.findElements(By.cssSelector(".dienst-item"));
        // Total number of courses
        int totalCourses = courses.size();
        // Map to hold the count of courses by category
        Map<String, Integer> categoryCount = new HashMap<>();
        categoryCount.put("OPLEIDING", 0);
        categoryCount.put("BOOTCAMP", 0);
        categoryCount.put("WORKSHOP", 0);
        // Loop through each course to categorize them
        for (WebElement course : courses) {
            // Find all category elements within the current course
            List<WebElement> categories = course.findElements(By.cssSelector(".kenmerken span"));
            // Check if categories are found
            for (WebElement category : categories) {
                // Get the text of the category and convert it to uppercase to match with the page
                String categoryText = category.getText().toUpperCase();
                // Increment the count for the corresponding category and
                // Check if the category exists in the map and update the count
                if (categoryCount.containsKey(categoryText)) {
                    categoryCount.put(categoryText, categoryCount.get(categoryText) + 1);
                }
            }
        }

        // Print the total number of courses
        System.out.println("Total number of courses - " + totalCourses);
        // Print the number of articles by category
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println("Number of articles that has category " + entry.getKey() + " - " + entry.getValue());
        }
    }

    @After
    public void tearDown() {
        WebDriverManager.quitDriver();
    }
}
