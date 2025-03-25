package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class NavigationTest {

    private WebDriver driver;
    private WebDriverWait waiter;

    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/navigation1.html";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        waiter = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        //driver.quit();
    }

    @Test
    void navigationPageTest() {

        final String href1 = "navigation1.html";
        final String expectedText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua.";

        driver.findElement(By.xpath("//a[@href='" + href1 + "']")).click();

        Assertions.assertAll(
                () -> Assertions.assertEquals(BASE_URL + "/" + href1, driver.getCurrentUrl()),
                () -> Assertions.assertTrue(driver.findElement(By.
                        xpath("//a[@href='" + href1 + "']/../.")).getDomAttribute("class").contains("active")),
                () -> Assertions.assertEquals("#", driver.findElement(By.
                        xpath("//div//a[text()='Previous']")).getDomAttribute("href")),
                () -> Assertions.assertEquals(expectedText, driver.findElement(By.xpath("//p")).getText()),
                () -> Assertions.assertNotEquals("#", driver.findElement(By.
                        xpath("//div//a[text()='Next']")).getDomAttribute("href"))

        );
    }

    @Test
    void test() throws InterruptedException {

        final List<String> expectedText = List.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        Assertions.assertAll(

                () -> Assertions.assertEquals("#", driver.findElement(By.
                        xpath("//div//a[text()='Previous']")).getDomAttribute("href")),
                () -> Assertions.assertNotEquals("#", driver.findElement(By.
                        xpath("//div//a[text()='Next']")).getDomAttribute("href"))
        );


        int size = driver.findElements(By.xpath("//li/a[text()=1 or text()=2 or text()=3]")).size();

        for (int i = 1; i <= size; i++) {

            WebElement activeEl = driver.findElement(By.xpath("//li[@class='page-item active']/a"));

            int finalI = i;
            int finalI1 = i;
            Assertions.assertAll(
                    () -> Assertions.assertEquals(activeEl.getDomProperty("href"), driver.getCurrentUrl()),
                    () -> Assertions.assertTrue(driver.findElement(By.xpath(
                            "//a[@href='navigation" + finalI + ".html']/../."))
                            .getDomAttribute("class")
                            .contains("active")),
                    () -> Assertions.assertEquals(expectedText.get(finalI1 -1),
                            driver.findElement(By.xpath("//p")).getText())
            );

            if (i < size) {
                driver.findElement(By.xpath("//a[text()='Next']")).click();
            }
        }

        Assertions.assertAll(
                () -> Assertions.assertEquals("navigation" + (size - 1) + ".html", driver.findElement(By.
                        xpath("//div//a[text()='Previous']")).getDomAttribute("href")),
                () -> Assertions.assertEquals("#", driver.findElement(By.
                        xpath("//div//a[text()='Next']")).getDomAttribute("href"))
        );
    }
}
