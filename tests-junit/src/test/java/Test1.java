import config.ServerConfig;
import io.github.bonigarcia.wdm.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.Set;

public class Test1 {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(Test1.class);//подключили логгер
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);//теперь можно использовать owner
    //теперь сможем использовать например cfg.url и будет подставляться значение из config.properties

    @Feature(value = "Other actions")
    @Test
    @Step("Проверка логирования")
    public void log(){
        logger.info("Здесь просто информация...");
    }

    @Feature(value = "Other actions")
    @Before
    public void setUp(){
    WebDriverManager.chromedriver().setup();//используем WebDriverManager
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    logger.info("Драйвер инициализирован");
}

    @Test
    @Feature(value = "Open Otus")
    @Step("Проверка заголовка")
    @Description(value = "Тест проверяет заголовок главной страницы")
    public void openPage(){

        //driver.get("https://otus.ru/");
        driver.get(cfg.url());//открываем url из config.properties
        logger.info("Открыта страница otus");
        String expected = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        Assert.assertEquals(expected, driver.getTitle());
        System.out.println("title: "+driver.getTitle());
        Allure.addAttachment("openPage", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Test
    @Feature(value = "Open Otus")
    @Step("Проверка заголовка")
    @Description(value = "Тест проверяет заголовок главной страницы")
    public void failAssert(){
        //специально падает, для отчета Allure
        //driver.get("https://otus.ru/");
        driver.get(cfg.url());//открываем url из config.properties
        logger.info("Открыта страница otus");
        Assert.assertEquals("la-la-la",driver.getTitle());
        System.out.println("title: "+driver.getTitle());
        Allure.addAttachment("openPageFailAssert", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Feature(value = "Other actions")
    @After
    public void setDown(){
        if (driver != null){ //если драйвер не отключен, отулючить его
            driver.quit();
            logger.info("Драйвер закрыт");
        }
    }


}
