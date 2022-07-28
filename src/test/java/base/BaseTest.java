package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pageObjects.AccountServices;
import pageObjects.BillPay;
import pageObjects.LoginPage;

public abstract class BaseTest {
    // below are the specific references which the test classes are using
    private WebDriver driver;
    public LoginPage loginPage;
    public AccountServices accountServices;
    public BillPay billPayPage;

    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    // TODO
    // create a property file, get desired browser from there
    @BeforeMethod
    public void setDriver() {
        WebDriverManager.chromedriver().setup();
        webDriverThreadLocal.set(new ChromeDriver());
    }

    // use below when multiple browser needed
    //@BeforeMethod
    @Parameters("browser")
    public void setDriverThreadLocal(@Optional("Chrome") String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            driver = setUpChrome();
        } else if (browser.equals("firefox")) {
            driver = setUpFirefox();
        }
        webDriverThreadLocal.set(driver);
    }

    public WebDriver getDriver() {
        return webDriverThreadLocal.get();
    }

    private WebDriver setUpChrome() {
        WebDriverManager.chromedriver().cachePath(System.getProperty("user.dir") + "/src/test/resources/drivers").setup();
        return new ChromeDriver(OptionsManager.getChromeOptions());
    }

    private WebDriver setUpFirefox() {
        WebDriverManager.firefoxdriver().cachePath(System.getProperty("user.dir") + "/src/test/resources/drivers").setup();
        return new FirefoxDriver(OptionsManager.getFirefoxOptions());
    }

    public final static Logger logger = LogManager.getLogger();
    // Sign In Link
    By signInLink = By.xpath("//a[normalize-space()='Sign in']");

    // Sign Out link
    private final By signOut = By.xpath("//div/a[normalize-space()='Sign out']");
    private final By logOutlink = By.xpath("//a[text()='Log Out']");

    public LoginPage launchApplication() {
        getDriver().get(Constants.APP_URL);
        return new LoginPage(getDriver());
    }

    @AfterMethod
    public void tearDown() {
        getDriver().quit();
        webDriverThreadLocal.remove();
    }

}
