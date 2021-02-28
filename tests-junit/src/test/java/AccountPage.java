import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

public class AccountPage {
    private WebDriver driver;

    public AccountPage (WebDriver driver) {
        this.driver = driver;
    }

    private Logger logger = LogManager.getLogger(MainTest.class);

    private By inputCountry= By.xpath("//p[contains(text(),'Страна')]/parent::div/following-sibling::div//input/following-sibling::div");
    private By country= By.xpath("//button[@title='Россия']");
    private By inputCity= By.xpath("//p[contains(text(),'Город')]/parent::div/following-sibling::div//input/following-sibling::div");
    private By city= By.xpath("//button[@title='Москва']");
    private By english= By.xpath("//p[contains(text(),'Уровень английского')]/parent::div/following-sibling::div//input/following-sibling::div");
    private By englishLevel= By.xpath("//button[@title='Средний (Intermediate)']");
    private By contactType= By.xpath("//input[@name='contact-0-service']/parent::*/div");
    private By contactTypeTelegram= By.xpath("//button[@title='Тelegram']");
    private By contactTypeInput= By.xpath("//input[@name='contact-0-value']");
    private By addContact= By.xpath("//button[text()='Добавить']");
    private By contactType2= By.xpath("//input[@name='contact-1-service']/parent::*/div");
    private By contactTypeWhatsApp= By.xpath("//input[@name='contact-0-value']/following::button[@title='WhatsApp']");
    private By contactTypeInput2= By.xpath("//input[@name='contact-1-value']");
    private By saveInformation= By.xpath("//button[@title='Сохранить и заполнить позже']");


    public AccountPage typeInformation() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(inputCountry).click();
        driver.findElement(country).click();
        driver.findElement(inputCity).click();
        driver.findElement(city).click();
        driver.findElement(english).click();
        driver.findElement(englishLevel).click();
        driver.findElement(contactType).click();
        driver.findElement(contactTypeTelegram).click();
        driver.findElement(contactTypeInput).sendKeys("123");
        driver.findElement(addContact).click();
        driver.findElement(contactType2).click();
        driver.findElement(contactTypeWhatsApp).click();
        driver.findElement(contactTypeInput2).sendKeys("123");
        driver.findElement(saveInformation).click();
        logger.info("Заполнена информация");
        return this;
    }

    public AccountPage checkPersonalInf() {
        check(inputCountry,"Россия");
        check(inputCity,"Москва");
        check(english,"Средний (Intermediate)");
        checkValue(contactTypeInput,"123");
        checkValue(contactTypeInput2,"123");
        logger.info("Информация успешно проверена");
        return this;
    }

    private void check (By element, String expectedText){
        String checkText = driver.findElement(element).getText();
        Assert.assertEquals("Не указан ожидаемый текст", expectedText, checkText );
    }

    private void checkValue (By element, String expectedText){
        String checkText = driver.findElement(element).getAttribute("value");
        Assert.assertEquals("Не указан ожидаемый текст", expectedText, checkText );
    }

}
