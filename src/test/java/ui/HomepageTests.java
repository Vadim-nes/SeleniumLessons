package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class HomepageTests {

    WebDriver driver;
    WebDriverWait waiter;

    public static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";


    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        waiter = new WebDriverWait(driver, Duration.ofSeconds(2));
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

    @Test
    void testLocators() throws InterruptedException {

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement title = driver.findElement(By.className("display-4"));
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", title.getText());

        WebElement h5 = driver.findElement(By.tagName("h5"));
        Assertions.assertEquals("Practice site", h5.getText());

        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys("selenium test");

        WebElement passwordInput = driver.findElement(By.name("my-password"));
        passwordInput.sendKeys("password1");

        WebElement textarea = driver.findElement(By.cssSelector(".form-label.w-100> textarea[class =form-control]"));
        textarea.sendKeys("test test test text!@!!");

        WebElement disabledInput = driver.findElement(By.xpath("//div//input[@disabled]"));
        if (disabledInput.isEnabled()) {
            System.out.println("disabledInput element is enabled");
        } else {
            System.out.println("disabledInput element is disabled");
        }

        WebElement readonlyInput = driver.findElement(By.xpath("//div//input[@readonly]"));
        if (readonlyInput.isEnabled()) {
            System.out.println("readonlyInput element is enabled");
        } else {
            System.out.println("readonlyInput element is disabled");
        }

        WebElement dropdownSelect = driver.findElement(By.className("form-select"));

        Select select = new Select(dropdownSelect);
        select.selectByIndex(1);

        WebElement dropdownDatalistInput = driver.findElement(By.xpath("//input[@name='my-datalist']"));
        dropdownDatalistInput.sendKeys("SeAttLe");


        WebElement fileInput = driver.findElement(By.name("my-file"));
        fileInput.sendKeys("C:/Users/user/IdeaProjects/SeleniumLessons/src/test/resources/111.png");

        WebElement checkboxOne = driver.findElement(By.id("my-check-1"));
        checkboxOne.click();
        Assertions.assertFalse(checkboxOne.isSelected());


        WebElement radioTwo = driver.findElement(By.id("my-radio-2"));
        radioTwo.click();
        Assertions.assertTrue(radioTwo.isSelected());

        Actions act = new Actions(driver);

        WebElement rgb = driver.findElement((By.name("my-colors")));
        rgb.sendKeys("#FF0000");


        WebElement returnToIndex = driver.findElement(By.linkText("Return to index"));
        returnToIndex.click();
        driver.navigate().back();

        WebElement boniGarsiaLink = driver.findElement(By.partialLinkText("Boni"));
        boniGarsiaLink.click();
        driver.navigate().back();

        WebElement exampleRange = driver.findElement(By.xpath("//input[@class= 'form-range']"));

        act.clickAndHold(exampleRange).moveByOffset(80, 0).release().perform();

        Thread.sleep(3000);
        act.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
    }
}