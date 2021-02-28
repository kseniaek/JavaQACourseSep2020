import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

    static WebDriver create(String webDriverName, String ...options) {
        String webDriverNameLow = webDriverName.toLowerCase();
        if (webDriverNameLow.equals("chrome")){
            return createChromeDriver.createNewDriver();
        }
        if (webDriverNameLow.equals("firefox")){
            return createFirefoxDriver.createNewDriver();
        }
        else return null;
    }
}
class createChromeDriver extends createDriver{
    public static WebDriver createNewDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

}
class createFirefoxDriver extends createDriver{
    public static WebDriver createNewDriver(){
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }
}

class createDriver{
    public static void createNewOption(String ...options) {
    }

    public static WebDriver createNewDriver() {
        return null;
    }
}
















