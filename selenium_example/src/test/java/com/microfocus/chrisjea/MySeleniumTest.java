package com.microfocus.chrisjea;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MySeleniumTest {

    public static WebDriver driver;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Program Files/selenium/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.advantageonlineshopping.com/#/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement titleElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.className("logo")));
    }

    @Test
    public void myFirstSeleniumTest(){

        Actions action = new Actions(driver);

        /**
         * Make sure that the correct page is being displayed
         */
        Assert.assertTrue("title should contain with Advantage Shopping",
                driver.getTitle().contains("Advantage Shopping"));

        /**
         * Wait for articles to be loaded
         */
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement articles = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("our_products")));

        /**
         * Find the laptops image and the laptops link
         */
        WebElement laptopsImage = driver.findElement(By.id("laptopsImg"));
        WebElement laptopLink = driver.findElement(By.id("laptopsLink"));

        /**
         * Simulate mouse over the laptops image
         */
        while(!laptopLink.isDisplayed()){
            action.moveToElement(laptopsImage).perform();
        }

        /**
         * Click on laptops link
         */
        laptopLink.click();

        /**
         * Set a specific filter
         */
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("accordionAttrib2"))));
        driver.findElement(By.id("accordionAttrib2")).click();
        WebElement wantedProcessorType =  driver.findElement(By.name("processor_1"));
        wait.until(ExpectedConditions.elementToBeClickable(wantedProcessorType));
        wantedProcessorType.click();

        /**
         * Check that the price of a particular item that respects the filter
         */
        String itemPrice = driver.findElement(By.cssSelector(".productPrice")).getText();
        Assert.assertTrue("The item price is incorrect", itemPrice.equals("$299.99"));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
        driver.quit();
    }
}



