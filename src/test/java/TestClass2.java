import base.BaseTest;
import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestClass2 extends BaseTest {
    @BeforeMethod
    public void setUp() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("");
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();

    }


    private static final AtomicBoolean runningRemotely = new AtomicBoolean(false);

    private boolean isRemoteLinuxExecution(){
        if(OS.isFamilyWindows()){
            runningRemotely.set(false);
            return false;
        }else {
            runningRemotely.set(true);
            return true;
        }
    }


}
