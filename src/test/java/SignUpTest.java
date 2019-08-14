import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SignUpTest {

    private WebDriver driver;
    private SignUpPage signUpPage;

    @Before
    public void setUp() throws MalformedURLException {
        System.setProperty("webdriver.gecko.driver", "D:\\Studing\\UDEMY.COM\\Automation_instruments\\geckodriver\\geckodriver.exe");
        driver = new FirefoxDriver();
       // DesiredCapabilities caps = DesiredCapabilities.firefox();
        //driver= new RemoteWebDriver(new URL("http:localhost:4444/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.spotify.com/int/signup");
        signUpPage = new SignUpPage(driver);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void typeInvalidYear() {
        signUpPage.setMonth("December")
                .typeDay("20")
                .typeYear("85")
                .setShare(true);

        Assert.assertTrue(signUpPage.isErrorVisible("Please enter a valid year."));
        Assert.assertFalse(signUpPage.isErrorVisible("When where you born?"));
    }

    @Test
    public void typeInvalidEmail() {
        signUpPage.typeEmail("test@gmail.test")
                .tupeConfirmEmail("wrong@gmail.test")
                .clickSignUpButton();
        Assert.assertTrue(signUpPage.isErrorVisible("Email address doesn't match."));
    }

    @Test
    public void signUpWithEmptyPassword() {
        signUpPage.typeEmail("test@gmail.com")
                .tupeConfirmEmail("test@gmail.com")
                .typeName("Testname")
                .clickSignUpButton();
        Assert.assertTrue(signUpPage.isErrorVisible("Please chose a password."));
    }

    @Test
    public void typeInvalidValues() {
        signUpPage.typeEmail("testemail")
                .tupeConfirmEmail("wrong@test.com")
                .typePassword("qwer1234")
                .typeName("Name")
                .setSex("Male")
                .setShare(false)
                .clickSignUpButton();
        Assert.assertEquals(4, signUpPage.getErrors().size());
        Assert.assertEquals("When where you born?", signUpPage.getErrorByNumber(3));
    }
}
