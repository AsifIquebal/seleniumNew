package pageObjects;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utility.JavaScriptUtils;
import utility.MyWrapper;

public class LoginPage {
    private WebDriver driver;
    private MyWrapper myWrapper;
    By userNameField = By.name("username");
    By passwordField = By.name("password");
    By signInButton = By.xpath("//input[@value='Log In']");
    By invalidLogInErrorMessage = By.xpath("//div[@id='rightPanel']/p");
    By registerLink = By.linkText("Register");
    By adminPageLink = By.xpath("//a[text()='Admin Page']");
    By logOutButton = By.xpath("//a[text()='Log Out']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        myWrapper = new MyWrapper();
    }

    public LoginPage enterUserName(String userName) {
        //logger.info("Entering username");
        myWrapper.sendKeys(driver, userNameField, userName);
        return this;
    }

    public LoginPage enterPassword(String passWord) {
        //logger.info("Entering password");
        myWrapper.sendKeys(driver, passwordField, passWord);
        return this;
    }

    public AccountServices clickOnSignInButton() {
        //logger.info("Clicking on Sign In Button");
        myWrapper.click(driver, signInButton);
        JavaScriptUtils.waitForDOMLoad(driver);
        return new AccountServices(driver);
    }

    public void clickOnLogOut() {
        myWrapper.click(driver, logOutButton);
    }

    public String getInvalidLoginErrorMessage() {
        return MyWrapper.getText(driver, invalidLogInErrorMessage);
    }

    // use of Generic return type
    @Step("Type {username} / {password}")
    @Description("login generic page")
    public AccountServices login(String username, String password) {
        return enterUserName(username).enterPassword(password).clickOnSignInButton();
    }

}
