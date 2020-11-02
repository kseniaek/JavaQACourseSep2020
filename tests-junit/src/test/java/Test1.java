import config.ServerConfig;
import io.github.bonigarcia.wdm.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class Test1 {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(Test1.class);//подключили логгер
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);//теперь можно использовать owner
    //теперь сможем использовать например cfg.url и будет подставляться значение из config.properties

    @Test
    public void log(){
        logger.info("Здесь просто информация..");
    }

    @Before
    public void setUp(){
    WebDriverManager.chromedriver().setup();//используем WebDriverManager
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    logger.info("Драйвер инициализирован");
}

    @Test
    public void openPage(){

        Cookie myCookie = new Cookie("test", "test");//создаем куки
        //driver.manage().addCookie(myCookie);
        Set<Cookie> cookies = driver.manage().getCookies();//получаем все куки
        driver.manage().getCookieNamed("test");//получить куки по имени
        driver.manage().deleteCookieNamed("test");//удалит куки по имени
        driver.manage().deleteAllCookies();//удалит все куки

        //driver.get("https://otus.ru/");
        driver.get(cfg.url());//открываем url из config.properties
        logger.info("Открыта страница otus");
        Assert.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям",driver.getTitle());
        System.out.println("title: "+driver.getTitle());
    }

    @After
    public void setDown(){
        if (driver != null){ //если драйвер не отключен, отулючить его
            driver.quit();
            logger.info("Драйвер закрыт");
        }
    }







}
