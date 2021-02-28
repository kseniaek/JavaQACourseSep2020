import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    private Logger logger = LogManager.getLogger(MainPage.class);
    private By signInButton = By.xpath("//button[@class='header2__auth js-open-modal']");
    private By nameButton = By.xpath("//*[@class='header2-menu__item-text header2-menu__item-text__username']");
    private By personalAccountDropdownItem = By.xpath("//a[@title='Личный кабинет']");
    private By about = By.xpath("//a[@title='О себе']");

    public LoginPage clickSignIn(){
        driver.findElement(signInButton).click();
        logger.info("Открыта страница авторизации");
        return new LoginPage(driver);
    }

    public AccountPage openPersonalPage(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(nameButton).click();
        driver.findElement(personalAccountDropdownItem).click();
        driver.findElement(about).click();
        logger.info("Открыта страница 'о себе'");
        return new AccountPage(driver);
    }
}
