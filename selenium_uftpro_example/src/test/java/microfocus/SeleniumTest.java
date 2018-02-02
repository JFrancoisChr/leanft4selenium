package microfocus;

import com.hpe.leanft.selenium.By;
import com.hpe.leanft.selenium.ByEach;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static javax.swing.UIManager.put;

/**
 * This test purpose is to demonstrate the value of LeanFT4Selenium by code
 * The extended set of locators will be used.
 * visible text, type, attributes and styles, usage of regular expression and combination of locators
 */

public class SeleniumTest  {

    public static WebDriver driver;

    public SeleniumTest() {}

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Program Files/selenium/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://the-internet.herokuapp.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }

    /**
     * The goal of this test is to find a specific cell in a table and display its content
     */
    @Test
    public void byVisibleTextTest() throws Exception {
        /* Navigate to the Challenging Dom section */
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[4]/a")).click();

        /* Using the standard devtools */
        String byXpath = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div[2]/table/tbody/tr[6]/td[4]")).getText();
        String bySelector = driver.findElement(By.cssSelector("#content > div > div > div > div.large-10.columns > table > tbody > tr:nth-child(6) > td:nth-child(4)")).getText();

        /* Using the selenium OIC and the by visible text locator */
        String byVisibleText = driver.findElement(By.visibleText("Definiebas5")).getText();

        /* Display object content */
        System.out.println("by Xpath : " + byXpath);
        System.out.println("by Selector : " + bySelector);
        System.out.println("by VisibleText : " + byVisibleText);

        /* This test demonstrates the byVisibleText locator power :
        * It obviously add another way to locate elements
        * Enhance maintainability of code : xpath and cssSelector usage is discouraged as any change in the Dom will cause the test to fail and cssSelector may be different depending on browser
        * Enhance readability of code : hard to see what web element is tested when using xpath or cssSelector !
        * */
    }

    /**
     * The goal of this test is to determine whether checkboxes are checked or not in a web page
     */
    @Test
    public void byTypeTest() throws Exception {
         /* Navigate to the Checkboxes section */
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[5]/a")).click();

        /* Using the standard devtools */
        ArrayList<WebElement> checkboxesByTagName = new ArrayList<>();
        checkboxesByTagName.addAll(driver.findElements(By.tagName("input")));


        /* Using the selenium OIC and the by type locator */
        ArrayList<WebElement> checkboxesByType = new ArrayList<>();
        checkboxesByType.addAll(driver.findElements(By.type("checkbox")));


        /* Display check list */
        checkboxesByTagName.forEach(c -> System.out.println(" is checked : " + c.getAttribute("checked")));
        checkboxesByType.forEach(c -> System.out.println(" is checked : " + c.getAttribute("checked")));

        /* This test demonstrates the byType locator power :
        * It obviously add another way to locate elements
        * Enhance maintainability of code : What if a username field is added <input type="text"> ? --> the test will be broken !
        * */
    }

    /**
     * The goal of this test is to find the checked checkbox among several checkboxes in a web page
     */
    @Test
    public void byAttributesAndStylesTest() throws Exception {
         /* Navigate to the Checkboxes section */
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[5]/a")).click();

        /* Using the standard devtools */
        WebElement checkedCheckboxByTagName;
        ArrayList<WebElement> checkboxesByTagName = new ArrayList<>();
        checkboxesByTagName.addAll(driver.findElements(By.tagName("input")));
        Iterator<WebElement> it = checkboxesByTagName.iterator();
        while (it.hasNext()){
            checkedCheckboxByTagName = it.next();
            if(checkedCheckboxByTagName.getAttribute("checked") == null){
               checkedCheckboxByTagName = null;
            }
        }

        /* Using the selenium OIC and the by attribute locator */
        WebElement checkedCheckboxByAttributes = driver.findElement(By.attribute("checked", ""));

        /* This test demonstrates the byAttribute locator power :
        * It obviously add another way to locate elements
        * Enhance readability of code
        * Enhance dev-tester efficiency : find directly the item that has the correct attribute VS find all item and then implement a treatment to find the good one
        * */
    }

    /**
     * The goal of this test is to check that elements in a the same column of a datable begin the same way
     */
    @Test
    public void regularExpressionTest() throws Exception {
        /* Navigate to the Challenging Dom section */
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[4]/a")).click();

        /* Using the standard devtools */
        ArrayList<WebElement> ItemsListStandard = new ArrayList<>();
        for(int i=1; i<11 ; i++){
            ItemsListStandard.add(driver.findElement(By.xpath("//*[@id='content']/div/div/div/div[2]/table/tbody/tr["+i+"]/td[4]")));
        }

        /* Using the selenium OIC and the regular expressions */
        ArrayList<WebElement> ItemsListWithRegEx = new ArrayList<>();
        ItemsListWithRegEx.addAll(driver.findElements(By.visibleText(Pattern.compile("^Definiebas*"))));

        /* Check results */
        System.out.println("size of ItemsListStandard " + ItemsListStandard.size());
        System.out.println("size of ItemsListWithRegEx " + ItemsListWithRegEx.size());

        /* This test demonstrates the by visible text value combined with regular expression power :
        * Enhance Readability (easy to see what is tested)
        * Enhance code writing ease
        * Enhance Maintainability (the xpath is not used n times as it is in the for loop)
        * */

    }

    /**
     * The goal of this test is to check that the word used in a google search query is present on the right
     */
    @Test
    public void locatorsCombinationTest() throws Exception {
        /* Search the word "test" on Google */
        driver.get("https://www.google.co.uk/search?dcr=0&ei=qTR0WquJOojosAXBu5KoBA&q=octane&oq=octane&gs_l=psy-ab.3..0i71k1l4.861.861.0.1157.1.1.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..1.0.0....0.rHW5osH_iok");

        /* Using the standard devtools */
        driver.findElement(By.xpath("//*[@id='rhs_block']/div[1]/div[1]/div/div[1]/div[2]/div[1]/div/div[1]/div/div/div[2]/div[1]/span"));

        /* Using the selenium OIC and combination of locators */
        driver.findElement(new ByEach(
                By.tagName("span"),
                By.visibleText("Octane")
        ));

        /* This test demonstrates the power of combinated locators :
        * Provide new ways to locate Web elements
        * Give more flexibility, accuracy and robustness
        * */
    }
}