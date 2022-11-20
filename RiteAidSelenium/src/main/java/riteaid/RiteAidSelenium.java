package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class RiteAidSelenium {
    private final static RiteAidSelenium INSTANCE =
            new RiteAidSelenium();
    private WebDriver driver;
    public RiteAidSelenium() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.get("https://www.riteaid.com/signup");
        String title = driver.getTitle();

        assert title.contains("Signup");

        driver.quit();
    }

    public RiteAidSelenium getInstance() {
        return INSTANCE;
    }
}
