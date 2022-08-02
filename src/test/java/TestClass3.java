import base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.LoginPage;

import java.lang.reflect.Method;

@Feature("Bill Payment Test Cases")
public class TestClass3 extends BaseTest {

    @BeforeMethod
    public void setUp(){
        getDriver().get("https://parabank.parasoft.com");
        loginPage = new LoginPage(getDriver());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void test01_verifyBillPaymentSuccessCase() {
        /*WebElement element = driver.findElement(By.linkText("Examples"));
        Actions my_action = new Actions(driver);
        Thread.sleep(3000);
        my_action.moveToElement(element).perform();
        OR
        WebElement element = driver.findElement(By.linkText("About"));
        Locatable hoverItem = (Locatable) element;
        Mouse mouse = ((HasInputDevices) driver).getMouse();
        mouse.mouseMove(hoverItem.getCoordinates());*/

        System.out.println("method 1 Thread ID: " + Thread.currentThread().getId());
        accountServices = loginPage.login("john", "demo");
        billPayPage = accountServices.navigateToBillPay();
        billPayPage.enterPayeeName("Simon Black")
                .enterPayeeAddress("9569 Sugar St.")
                .enterPayeeCity("San Jose")
                .enterPayeeState("CA")
                .enterPayeeZip("95123")
                .enterPayeePhone("876459876")
                .enterPayeeAccountNumber("43214")
                .enterPayeeVerifyAccountNumber("43214")
                .enterAmount("105")
                .clickSendPayment();
        Assert.assertTrue(billPayPage.getResultText().contains("successful"));
    }

    // this will fail
    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void test02_verifyBillPaymentFailCase() {
        System.out.println("method 1 Thread ID: " + Thread.currentThread().getId());
        accountServices = loginPage.login("john", "demo");
        billPayPage = accountServices.navigateToBillPay();
        billPayPage.enterPayeeName("Simon Black")
                .enterPayeeAddress("9569 Sugar St.")
                .enterPayeeCity("San Jose")
                .enterPayeeState("CA")
                .enterPayeeZip("95123")
                .enterPayeePhone("876459876")
                .enterPayeeAccountNumber("43214")
                .enterPayeeVerifyAccountNumber("43214")
                .enterAmount("-501")
                .clickSendPayment();
        Assert.assertTrue(billPayPage.getResultText().contains("not successful"));
    }

}
