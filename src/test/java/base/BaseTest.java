package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pageObjects.AccountServices;
import pageObjects.BillPay;
import pageObjects.LoginPage;
import utility.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public abstract class BaseTest {

    boolean isRemote = false;
    String remoteURL = "http://localhost:4444/wd/hub";

    private WebDriver driver;
    public LoginPage loginPage;
    public AccountServices accountServices;
    public BillPay billPayPage;

    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    @BeforeMethod
    public void setDriver() throws MalformedURLException {
        ConfigReader configReader = new ConfigReader();
        Properties properties = configReader.getProperties();
        isRemote = Boolean.parseBoolean(properties.getProperty("isRemote"));
        String browserName = properties.getProperty("browser");
        /*driverFactory = new DriverFactory();
        driver = driverFactory.initDriver(browserName);*/
        if (browserName.equalsIgnoreCase("Chrome")) {
            driver = setUpChrome();
        } else if (browserName.equals("firefox")) {
            driver = setUpFirefox();
        }
        webDriverThreadLocal.set(driver);
    }

    // use below when multiple browser needed
    //@BeforeMethod
    @Parameters("browser")
    public void setDriverThreadLocal(@Optional("Chrome") String browser) throws MalformedURLException {
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

    private WebDriver setUpChrome() throws MalformedURLException {
        if (isRemote) {
            System.out.println("Running remotely...");
            /*DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");
            return new RemoteWebDriver(new URL(remoteURL), capabilities);*/
            return WebDriverManager.chromedriver()
                    .capabilities(OptionsManager.getChromeOptions())
                    .remoteAddress(remoteURL)
                    .create();
        } else {
            System.out.println("Running locally...");
            WebDriverManager.chromedriver().cachePath(System.getProperty("user.dir") + "/src/test/resources/drivers").setup();
            return new ChromeDriver(OptionsManager.getChromeOptions());
        }
    }

    private WebDriver setUpFirefox() {
        WebDriverManager.firefoxdriver().cachePath(System.getProperty("user.dir") + "/src/test/resources/drivers").setup();
        return new FirefoxDriver(OptionsManager.getFirefoxOptions());
    }

    public final static Logger logger = LogManager.getLogger();
    By signInLink = By.xpath("//a[normalize-space()='Sign in']");
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
