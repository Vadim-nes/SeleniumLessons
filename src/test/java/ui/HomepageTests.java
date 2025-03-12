package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class HomepageTests {

    WebDriver driver;
    WebDriverWait waiter;

    public static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    public static final String CHAPTER_LOCATOR = "//div/h5[@class='card-title' and contains(text(),";


    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        waiter = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get(BASE_URL);

    }

    @AfterEach
    void tearDown() {
        //System.out.println(driver.getPageSource());
        driver.quit();

    }

    @Test
    void toCheckChaptersElementsTest() {

        int elementsCount = 0;

            List <WebElement> chaptersList = driver.findElements(By.xpath("//div/h5[@class= 'card-title']"));

            for (var chapter : chaptersList) {

                List <WebElement> elements = chapter.findElements(By.xpath("./ .. /a"));

                elementsCount += elements.size();

                for (var element : elements) {

                    String elementHrefLink = BASE_URL + element.getDomAttribute("href");
                    System.out.println(element.getText());
                    element.click();
                    String elementLink = driver.getCurrentUrl();

                    driver.navigate().back();
                    Assertions.assertEquals(elementHrefLink, elementLink);
                }

            }

            Assertions.assertEquals(27, elementsCount);

    }
}