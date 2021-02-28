package config;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("classpath:config.properties")//создаем интерфейс наследующий Config
public interface ServerConfig extends Config {

    @Key("url")
    String url();//в Config будет использоваться url, который будет ссылаться на параметр Key("url") из config.properties

    @Key("login")
    String login();

    @Key("password")
    String password();

}

