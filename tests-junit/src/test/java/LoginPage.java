import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
    private Logger logger = LogManager.getLogger(LoginPage.class);

    private By inputEmail= By.xpath("//*[@action='/login/']//input[@name='email']");
    private By inputPassword= By.xpath("//*[@action='/login/']//input[@name='password']");
    private By submit= By.xpath("//*[@action='/login/']//button[@type='submit']");


    public LoginPage typeLogin(){
        driver.findElement(inputEmail).sendKeys(cfg.login());
        return this;
    }

    public LoginPage typePassword(){
        driver.findElement(inputPassword).sendKeys(cfg.password());
        return this;
    }

    public LoginPage signInClick(){
        driver.findElement(submit).click();
        return this;
    }

    public MainPage signIn(){
        typeLogin();
        typePassword();
        signInClick();
        logger.info("Успешная авторизация");
        return new MainPage(driver);

    }

}

