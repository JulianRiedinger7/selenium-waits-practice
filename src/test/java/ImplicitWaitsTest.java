import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class ImplicitWaitsTest {

    WebDriver driver = new ChromeDriver();

    @BeforeClass
    public void setUp() {
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/");
    }

    @Test(dataProvider = "usernamesAndPasswords", dataProviderClass = DataProviderClass.class)
    public void testLogin(String username, String password) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        loginBtn.click();

        if(username.equalsIgnoreCase("locked_out_user")) {
            WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));

            Assert.assertTrue(errorMessage.isDisplayed());
        } else {
            WebElement productsTitle = driver.findElement(By.className("product_label"));

            Assert.assertTrue(productsTitle.isDisplayed());
            Assert.assertEquals(productsTitle.getText(), "Products");

            WebElement hamburgerIcon = driver.findElement(By.cssSelector(".bm-burger-button button"));

            hamburgerIcon.click();

            WebElement logoutBtn = driver.findElement(By.id("logout_sidebar_link"));

            logoutBtn.click();
        }
    }

    @AfterMethod
    public void clearInputs() {
        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.clear();
        passwordInput.clear();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
