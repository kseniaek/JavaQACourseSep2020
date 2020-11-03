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
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

        ChromeOptions options = new ChromeOptions();//позволяет добавлять различные опции браузера
        //System.out.println(((HasCapabilities) driver).getCapabilities());//получить список всех capabilities?
        options.addArguments("headless");//например, выполнение тестов без открытия окна браузера

        DesiredCapabilities capabilities = new DesiredCapabilities();//DesiredCapabilities позволяют добавлять настройки драйвера
        capabilities.setAcceptInsecureCerts(false);//позволяет игнорировать сертификаты
        capabilities.setCapability("unexpectAlertBehaviour", "dismiss");//позволяет работать с алертами
        capabilities.setCapability("unexpectAlertBehaviour", "accept");//подтвердить алерт
        capabilities.setCapability("loadstrategy", PageLoadStrategy.NORMAL);//определяет, что считать загруженной страницей,можно указать различные значения

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);//используется, чтобы использовать комбинацию capabilities и options
        driver = new ChromeDriver(capabilities);//передаем capabilities
      //driver = new ChromeDriver(options);//вызываем option, чтоб они сработали

        /*сейчас настроено так, что capabilities и options работают как одно и то же, за исключением того, что
        options только для браузера Chrome, а capabilities общие


        можно добавить и спользовать свою прокси
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("myproxy:8080");
        options.setCapability("proxy", proxy);
        */

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);//можно задать для загрузки страницы
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);//для скрипта
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//неявное ожидание, если драйвер не нашел элемент, он попытается найти снова

    driver.manage().window().maximize();
    logger.info("Драйвер инициализирован");

}

    @Test
    public void openPage(){

               //driver.get("https://otus.ru/");
        driver.get(cfg.url());//открываем url из config.properties
        logger.info("Открыта страница otus");
        Assert.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям",driver.getTitle());
        System.out.println("title: "+driver.getTitle());

        Cookie myCookie = new Cookie("Otus1", "Value1");//создаем куки
        driver.manage().addCookie(myCookie);
        System.out.println("проверяем куки 1: "+driver.manage().getCookies());
        driver.manage().addCookie(new Cookie("Otus2", "Value2"));
        System.out.println("проверяем куки 2: "+driver.manage().getCookieNamed("Otus2"));//получить куки по имени
        System.out.println("проверяем куки 1-2: "+driver.manage().getCookies());
        driver.manage().deleteCookieNamed("Otus2");//удалит куки по имени
        System.out.println("проверяем куки 3: "+driver.manage().getCookies());
        driver.manage().deleteAllCookies();//удалит все куки
        System.out.println("проверяем куки 4: "+driver.manage().getCookies());//убеждаемся, что все куки удалены
        //assert driver.manage().getCookies().isEmpty();//убеждаемся, что все куки удалены

    }

    @After
    public void setDown(){
        if (driver != null){ //если драйвер не отключен, отулючить его
            driver.quit();
            logger.info("Драйвер закрыт");
        }
    }







}
