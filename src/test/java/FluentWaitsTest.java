import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Arrays;


public class FluentWaitsTest {

    WebDriver driver = new ChromeDriver();
    Wait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofSeconds(2))
            .ignoreAll(Arrays.asList(NoSuchElementException.class, ElementNotInteractableException.class));

    @BeforeClass
    public void setUp() {
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/");
    }

    @Test(dataProvider = "usernamesAndPasswords", dataProviderClass = DataProviderClass.class)
    public void testLogin(String username, String password) {

        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        usernameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        passwordInput.sendKeys(password);

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginBtn.click();

        if(username.equalsIgnoreCase("locked_out_user")) {
            WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));

            Assert.assertTrue(errorMessage.isDisplayed());
        } else {
            WebElement productsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product_label")));

            Assert.assertTrue(productsTitle.isDisplayed());
            Assert.assertEquals(productsTitle.getText(), "Products");

            WebElement hamburgerIcon = driver.findElement(By.cssSelector(".bm-burger-button button"));

            hamburgerIcon.click();

            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

            logoutBtn.click();
        }
    }

    @AfterMethod
    public void clearInputs()  {
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));

        usernameInput.clear();
        passwordInput.clear();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
