package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;

public class Frameswindowswaiters {

    private WebDriver driver;
    private WebDriverWait waiter;

    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

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
    void testLongPage() throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='long-page.html']")).click();
        Thread.sleep(500);

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        WebElement copyright = driver.findElement(By.className("text-muted"));

        Assertions.assertEquals("Copyright © 2021-2025 Boni García",copyright.getText());
    }

    @Test
    void testInfiniteScrollPage() throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='infinite-scroll.html']")).click();
        Thread.sleep(500);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0, 500);");
        Thread.sleep(500);
        js.executeScript("window.scrollBy(0, 500);");
        Thread.sleep(500);
        js.executeScript("window.scrollBy(0, 500);");
        Thread.sleep(500);
    }

    @Test
    void testShadowDOMPage() throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='shadow-dom.html']")).click();

        WebElement element = driver.findElement(By.id("content"));

        SearchContext shadowRoot = element.getShadowRoot();

        WebElement content = shadowRoot.findElement(By.cssSelector("p"));

        Assertions.assertEquals("Hello Shadow DOM",content.getText());
    }

    @Test
    void testCookies() {
        driver.findElement(By.xpath("//a[@href='cookies.html']")).click();

        driver.manage().deleteCookieNamed("username");
        driver.manage().addCookie(new Cookie("username","VADIM"));
        driver.manage().addCookie(new Cookie("TEST","TEST2"));
        driver.findElement(By.id("refresh-cookies")).click();

        Assertions.assertEquals("VADIM", driver.manage().getCookieNamed("username").getValue());
        Assertions.assertEquals("TEST2", driver.manage().getCookieNamed("TEST").getValue());
    }

    @Test
    void testFrames() {
        driver.findElement(By.xpath("//a[@href='frames.html']")).click();

        Exception ex = Assertions.assertThrows(NoSuchElementException.class,() -> {driver.findElement(By.className("display-4")).getText();});
        Assertions.assertTrue(ex.getMessage().contains("no such element: Unable to locate element"));

        driver.switchTo().frame("frame-header");
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java",driver.findElement(By.className("display-4")).getText());

        driver.switchTo().defaultContent();
        Assertions.assertThrows(NoSuchElementException.class,() -> {driver.findElement(By.className("display-4")).getText();});
    }

    @Test
    void testIFrames() {

        driver.findElement(By.xpath("//a[@href='iframes.html']")).click();

        Exception ex = Assertions.assertThrows(NoSuchElementException.class,() -> {driver.findElement(By.className("lead")).getText();});
        Assertions.assertTrue(ex.getMessage().contains("no such element: Unable to locate element"));

        driver.switchTo().frame("my-iframe");
        System.out.println(driver.findElement(By.className("lead")).getText());
        Assertions.assertTrue(driver.findElement(By.className("lead")).getText().contains("Lorem ipsum dolor sit amet consectetur adipiscing elit habitant metus,"));

        driver.switchTo().defaultContent();
        Assertions.assertThrows(NoSuchElementException.class,() -> {driver.findElement(By.className("lead")).getText();});
    }

    @Test
    void testDialogBoxes() throws InterruptedException {

        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-alert")).click();
        waiter.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();


        Assertions.assertEquals("Hello world!", alert.getText());
        alert.accept();
        waiter.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        Thread.sleep(1000);

        WebElement confirmAlert = driver.findElement(By.id("my-confirm"));
        confirmAlert.click();
        driver.switchTo().alert().dismiss();
        Thread.sleep(1000);
        Assertions.assertEquals("You chose: false",driver.findElement(By.id("confirm-text")).getText());

        confirmAlert.click();
        driver.switchTo().alert().accept();
        Thread.sleep(1000);
        Assertions.assertEquals("You chose: true",driver.findElement(By.id("confirm-text")).getText());

        WebElement promtAlert = driver.findElement(By.id("my-prompt"));
        promtAlert.click();
        Alert al = driver.switchTo().alert();
        al.sendKeys("selenium");
        al.dismiss();
        Thread.sleep(1000);
        Assertions.assertEquals("You typed: null",driver.findElement(By.id("prompt-text")).getText());

        promtAlert.click();
        al.sendKeys("selenium");
        al.accept();
        Thread.sleep(1000);
        Assertions.assertEquals("You typed: selenium",driver.findElement(By.id("prompt-text")).getText());


        driver.findElement(By.id("my-modal")).click();
        WebElement saveButton = driver.findElement(By.xpath("//button[normalize-space() = 'Save changes']"));
        waiter.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        Thread.sleep(1000);
        Assertions.assertEquals("You chose: Save changes",driver.findElement(By.id("modal-text")).getText());
    }
}
