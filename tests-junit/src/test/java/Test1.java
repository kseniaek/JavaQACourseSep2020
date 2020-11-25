import config.ServerConfig;
import io.github.bonigarcia.wdm.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import javafx.scene.control.Tab;
import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Test1 {

    protected static WebDriver driver;

    private Logger logger = LogManager.getLogger(Test1.class);//подключила логгер
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);//теперь можно использовать owner

    private String smthByText = "//*[contains(text(),'%s')]";

    public void moveToElement(String elementText){

        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(String.format(smthByText, elementText)));
        actions.moveToElement(element).build().perform();
        logger.info("Курсор наведен на элемент с текстом: "+elementText);
    }

    public void clickElementByText(String elementText){

        WebElement element = driver.findElement(By.xpath(String.format(smthByText, elementText)));
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        logger.info("Клик по элементу с текстом: "+elementText);
    }

    public void clickElementByLabelText(String elementText){
        String locatorByText = "//label[contains(text(),'%s')]";
        driver.findElement(By.xpath(String.format(locatorByText, elementText))).click();
        logger.info("Клик по элементу с текстом: "+elementText);
    }

    public void showItemsByFilter(){

        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()=' Показать: ']/a")));
        WebElement element = driver.findElement(By.xpath("//div[text()=' Показать: ']/a"));
        new Actions(driver).moveToElement(element).perform();
        element.click();
        logger.info("Показаны выбранные позиции");
    }


    public void sortBy(String sortType){
        driver.findElement(By.xpath("//span[text()='Сортировать по:']/parent::p/following-sibling::span//span[@role='combobox']")).click();
        driver.findElement(By.xpath(String.format("//span[@class='select2-listing']//option[text()='%s']", sortType))).click();
        logger.info("Отсортировано по "+sortType);
    }

    public void addToComparison(String itemTitle){
        //String locatorByText = "//a[@rel='box' and @data-product-vendor='%s']/parent::div/following-sibling::div";
        String locatorByText ="//a[@rel='box' and @data-product-vendor='%s']/parent::div/following-sibling::div[2]//i[@title='Добавить к сравнению']";
        //WebDriverWait wait = new WebDriverWait(driver,30);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(locatorByText))));
        WebElement element = driver.findElement(By.xpath(String.format(locatorByText, itemTitle)));
        new Actions(driver).moveToElement(element).perform();
        element.click();
        logger.info("Добавлен к сравнению товар: "+itemTitle);
    }

    public void continueShoppingOrToComparison (String itemTitle){
        String locatorByText = "//a[contains(text(),'%s')]";
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(locatorByText, itemTitle))));
        WebElement element = driver.findElement(By.xpath(String.format(locatorByText, itemTitle)));
        //new Actions(driver).moveToElement(element).perform();
        element.click();
        logger.info("Выполнено действие "+itemTitle);
    }

    public void checkPageTitle (String itemTitleText1, String itemTitleText2){

        //переключение на другую вкладку
        String Tab = driver.getWindowHandle();
        ArrayList<String> availableWindows = new ArrayList<String>(driver.getWindowHandles());
        if (!availableWindows.isEmpty()) {
            driver.switchTo().window(availableWindows.get(1));
        }

        //проверка, что открыта верная вкладка, по заголовку страницы
        String pageName = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals("Открыта не та страница", "Сравнение товаров", pageName);

        List<WebElement> items = driver.findElements(By.xpath("//div[@class='title']/a"));

        ArrayList itemsTitle = new ArrayList();
        for (int i=0; i<items.size(); i++) {
            String text = items.get(i).getText();
            itemsTitle.add(text);
            Assert.assertTrue((text.contains(itemTitleText1)) || (text.contains(itemTitleText2)));
            //сравнение текста элементов (товаров в сравнении) с ожидаемым
        }

        logger.info("Проверено наличие товаров в списке для сравнения");
    }











    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();//используем WebDriverManager

        //capabilities ниже позволят автоматически работать с алертом во время теста
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
        driver = new ChromeDriver(capabilities);

        ChromeOptions options = new ChromeOptions();
        options.getCapability("UnexpectedAlertBehaviour");//используем getCapability и options

        logger.info("Драйвер инициализирован");

        //driver.get("https://tomsk.220-volt.ru/catalog/2-0/");
        driver.get(cfg.url());
        logger.info("Открыта страница 220-volt.ru");

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);//можно задать для загрузки страницы
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }


    @Test
    public void test(){

        moveToElement("Каталог товаров");
        moveToElement("Электроинструменты");
        clickElementByText("Перфораторы");
        clickElementByLabelText("MAKITA");
        clickElementByLabelText("ЗУБР");
        showItemsByFilter();
        sortBy("Цене");
        addToComparison("ЗУБР");
        continueShoppingOrToComparison("Продолжить просмотр");
        addToComparison("MAKITA");
        continueShoppingOrToComparison("Перейти к сравнению");
        checkPageTitle("ЗУБР", "MAKITA");


    }

    @After
    public void setDown(){
        if (driver != null){ //если драйвер не отключен, отулючить его
            driver.manage().deleteAllCookies();
            driver.quit();
            logger.info("Драйвер закрыт");
        }
    }







}
