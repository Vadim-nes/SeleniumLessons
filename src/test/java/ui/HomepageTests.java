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
import java.util.List;

public class HomepageTests {

    WebDriver driver;

    public static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    public static final String CHAPTER_LOCATOR = "//div/h5[@class='card-title' and contains(text(),";


    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();

    }

    @Test
    void toCheckChaptersElementsTest() {

        int elementsCount = 0;

        List<WebElement> chaptersList = driver.findElements(By.xpath("//div/h5[@class= 'card-title']"));

        for (var chapter : chaptersList) {

            List<WebElement> elements = chapter.findElements(By.xpath("./ .. /a"));

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