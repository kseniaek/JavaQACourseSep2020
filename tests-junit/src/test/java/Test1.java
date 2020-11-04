import config.ServerConfig;
import io.github.bonigarcia.wdm.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Test1 {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(Test1.class);//подключили логгер
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);//теперь можно использовать owner

    public void moveToElement(String elementText){
        String locatorByText = "//*[contains(text(),'%s')]";

        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(String.format(locatorByText, elementText)));
        actions.moveToElement(element).build().perform();
    }

    public void clickElementByText(String elementText){
        String locatorByText = "//*[contains(text(),'%s')]";
        driver.findElement(By.xpath(String.format(locatorByText, elementText))).click();
    }


    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();//используем WebDriverManager

        logger.info("Драйвер инициализирован");

        //driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);//можно задать для загрузки страницы
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://tomsk.220-volt.ru/catalog/2-0/");
        //driver.get(cfg.url());
        driver.manage().window().maximize();
        logger.info("Открыта страница 220-volt.ru");
    }

    @Test
    public void test(){

        moveToElement("Каталог товаров");
        moveToElement("Электроэнструменты");
        clickElementByText("Перфораторы");

    }

    @After
    public void setDown(){
        if (driver != null){ //если драйвер не отключен, отулючить его
            driver.quit();
            logger.info("Драйвер закрыт");
        }
    }







}
