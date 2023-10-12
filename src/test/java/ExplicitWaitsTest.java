import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExplicitWaitsTest {

    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeClass
    public void setUp() {
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/");
    }

    @Test(dataProvider = "usernamesAndPasswords", dataProviderClass = DataProviderClass.class)
    public void testLogin(String username, String password) {

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.sendKeys(password);

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginBtn.click();

        if(username.equalsIgnoreCase("locked_out_user")) {
            WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));

            Assert.assertTrue(errorMessage.isDisplayed());
        } else {
            WebElement productsTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product_label")));

            Assert.assertTrue(productsTitle.isDisplayed());
            Assert.assertEquals(productsTitle.getText(), "Products");

            WebElement hamburgerIcon = driver.findElement(By.cssSelector(".bm-burger-button button"));

            hamburgerIcon.click();

            WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));

            logoutBtn.click();
        }
    }

    @AfterMethod
    public void clearInputs()  {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        usernameInput.clear();
        passwordInput.clear();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
