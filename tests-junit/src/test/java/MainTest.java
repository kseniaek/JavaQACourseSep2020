import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class MainTest {
    public static void main(String[] args) {

    }
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(MainTest.class);//подключили логгер
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);//теперь можно использовать owner


    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();//используем WebDriverManager
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        logger.info("Драйвер инициализирован");
        driver.get(cfg.url());//открываем url из config.properties
        logger.info("Открыта страница otus");

    }

    @Test
    public void signInTest(){
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickSignIn();
        loginPage.signIn();
        AccountPage accountPage = mainPage.openPersonalPage();
        accountPage.typeInformation();
    }

    @Test
    public void checkInfTest(){
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickSignIn();
        loginPage.signIn();
        AccountPage accountPage = mainPage.openPersonalPage();
        accountPage.checkPersonalInf();
    }

    @After
    public void quit(){
        driver.quit();
    }
}
