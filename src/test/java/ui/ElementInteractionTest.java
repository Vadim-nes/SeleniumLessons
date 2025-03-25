package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class ElementInteractionTest {

    private WebDriver driver;
    private WebDriverWait waiter;

    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

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
    void testTitles() {
        WebElement title = driver.findElement(By.className("display-4"));
        WebElement h5 = driver.findElement(By.tagName("h5"));

        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", title.getText());
        Assertions.assertEquals("Practice site", h5.getText());
    }

    @Test
    void testTextInput() {
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys("selenium test");
        String expectedTextInput = textInput.getDomProperty("value");
        Assertions.assertEquals("selenium test", expectedTextInput);
    }

    @Test
    void testPasswordInput() {
        WebElement passwordInput = driver.findElement(By.name("my-password"));
        passwordInput.sendKeys("password1");
        String expectedPasswordInput = passwordInput.getDomProperty("value");
        Assertions.assertEquals("password1", expectedPasswordInput);
    }

    @Test
    void testTextarea() {
        WebElement textarea = driver.findElement(By.cssSelector(".form-label.w-100> textarea[class =form-control]"));
        textarea.sendKeys("test test test text!@!!");
        String expectedTextareaText = textarea.getDomProperty("value");
        Assertions.assertEquals("test test test text!@!!", expectedTextareaText);
    }

    @Test
    void testDisabledInput() {
        WebElement disabledInput = driver.findElement(By.xpath("//div//input[@disabled]"));
        Assertions.assertFalse(disabledInput.isEnabled());
        if (disabledInput.isEnabled()) {
            System.out.println("disabledInput element is enabled");
        } else {
            System.out.println("disabledInput element is disabled");
        }
    }

    @Test
    void testReadonlyInput() {
        WebElement readonlyInput = driver.findElement(By.xpath("//div//input[@readonly]"));
        Assertions.assertNotNull(readonlyInput.getDomAttribute("readonly"));
        if (readonlyInput.isEnabled()) {
            System.out.println("readonlyInput element is enabled");
        } else {
            System.out.println("readonlyInput element is disabled");
        }
    }

    @Test
    void testDropdownSelect() {
        WebElement dropdownSelect = driver.findElement(By.className("form-select"));
        Select select = new Select(dropdownSelect);
        String actualFistOption = select.getFirstSelectedOption().getText();
        Assertions.assertEquals("Open this select menu", actualFistOption);
        select.selectByIndex(0);
        select.selectByValue("1");
        select.selectByVisibleText("Two");
        select.selectByIndex(3);
        List<WebElement> selected = select.getAllSelectedOptions();
        List<WebElement> allOptions = select.getOptions();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, selected.size()),
                () -> Assertions.assertEquals(4, allOptions.size())
        );
    }

    @Test
    void imageDownload() throws InterruptedException {
        URL url = HomepageTests.class.getClassLoader().getResource("111.png");
        String absolutePath;
        if (url != null) {
            absolutePath = new File(url.getPath()).getAbsolutePath();
        } else {
            throw new NullPointerException("absolute path is null!");
        }

        driver.findElement(By.name("my-file")).sendKeys(absolutePath);

        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assertions.assertAll(
                () -> Assertions.assertTrue(driver.getCurrentUrl().contains("111.png")),
                () -> Assertions.assertEquals("Form submitted", driver.findElement(By.
                        cssSelector("h1.display-6")).getText())
        );
    }

    @Test
    void testImageDownloadSecondApproach() throws InterruptedException {
        File file = new File("src/test/resources/111.png");

        driver.findElement(By.name("my-file")).sendKeys(file.getAbsolutePath());
        Thread.sleep(500);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assertions.assertAll(
                () -> Assertions.assertTrue(driver.getCurrentUrl().contains("111.png")),
                () -> Assertions.assertEquals("Form submitted", driver.findElement(By.
                        cssSelector("h1.display-6")).getText())
        );
    }

    @Test
    void testCheckBox() {
        WebElement checkboxOne = driver.findElement(By.id("my-check-1"));
        checkboxOne.click();
        Assertions.assertFalse(checkboxOne.isSelected());

        WebElement checkboxTwo = driver.findElement(By.id("my-check-2"));
        checkboxTwo.click();
        Assertions.assertTrue(checkboxTwo.isSelected());
        checkboxTwo.click();
        Assertions.assertFalse(checkboxTwo.isSelected());
    }

    @Test
    void testRadioButton() {
        WebElement radioTwo = driver.findElement(By.id("my-radio-2"));
        radioTwo.click();
        Assertions.assertTrue(radioTwo.isSelected());
    }

    @Test
    void testColorPicker() throws InterruptedException {
        WebElement rgb = driver.findElement((By.name("my-colors")));
        rgb.sendKeys("#ff0000");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);
        System.out.println(driver.getCurrentUrl());
        Assertions.assertTrue(driver.getCurrentUrl().contains(URLEncoder.encode("#ff0000", StandardCharsets.UTF_8)));
    }

    @Test
    void testDatePicker() throws InterruptedException, UnsupportedEncodingException {
        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.sendKeys("03/27/2025");
        String actualDate = datePicker.getDomProperty("value");
        Thread.sleep(1000);

        Assertions.assertEquals("03/27/2025", actualDate);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);
        System.out.println(URLEncoder.encode("03/27/2025", "UTF-8"));
        System.out.println(driver.getCurrentUrl());

        Assertions.assertTrue(driver.getCurrentUrl().contains(URLEncoder.encode("03/27/2025", StandardCharsets.UTF_8)));
    }

    @Test
    void testFormRange() {
        WebElement exampleRange = driver.findElement(By.xpath("//input[@class= 'form-range']"));
        Assertions.assertAll(
                () -> Assertions.assertEquals("5", exampleRange.getDomAttribute("value")),
                () -> Assertions.assertEquals("0", exampleRange.getDomAttribute("min")),
                () -> Assertions.assertEquals("10", exampleRange.getDomAttribute("max")),
                () -> Assertions.assertEquals("1", exampleRange.getDomAttribute("step"))
        );
    }

    @Test
    void testFormRangeAction() throws InterruptedException {
        WebElement exampleRange = driver.findElement(By.xpath("//input[@class= 'form-range']"));
        for (int i = 5; i <= 10; i++) {
            Thread.sleep(1000);
            Assertions.assertEquals(String.valueOf(i), exampleRange.getDomProperty("value"));
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }

        for (int i = 10; i >= 0; i--) {
            Thread.sleep(1000);
            Assertions.assertEquals(String.valueOf(i), exampleRange.getDomProperty("value"));
            exampleRange.sendKeys(Keys.ARROW_LEFT);
        }
    }

    @Test
    void testDropdownDatalistInput() throws InterruptedException {
        WebElement dropdownDatalistInput = driver.findElement(By.xpath("//input[@name='my-datalist']"));
        dropdownDatalistInput.sendKeys("Seattle");
// how to test it?>
    }
}
