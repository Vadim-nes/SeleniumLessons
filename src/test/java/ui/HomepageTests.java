package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @ParameterizedTest
    @CsvFileSource(resources = "/testData.csv", numLinesToSkip = 1)
    void toCheckChaptersElementsTest(String chapterGroup, String chapterElements,String title) {

        int size = driver.findElements(By.xpath(CHAPTER_LOCATOR + chapterGroup)).size();

        for (int i = 0; i < size; i++) {

            List<WebElement> el = driver.findElements(By.xpath(CHAPTER_LOCATOR + chapterGroup));

            Assertions.assertTrue(chapterElements.contains(el.get(i).getText()),"ERROR WITH " + el.get(i).getText());
            el.get(i).click();

            Assertions.assertNotEquals(BASE_URL, driver.getCurrentUrl().toString());
            Assertions.assertEquals(title, driver.getTitle());

            driver.get(BASE_URL);
        }
    }
}