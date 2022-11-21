package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class Main {
    private static final String FirstName = "fewa";
    private static final String LastName = "awef";
    private static final String RewardsID = "";
    private static final boolean ShouldUseRewardsID = false;
    private static final String PhoneNumber = "8982903939";
    public static void main(String[] args) {
        //get the auto driver setup
        WebDriverManager.firefoxdriver().setup();

        //block geo tracking for firefox
        FirefoxOptions options = new FirefoxOptions()
                .addPreference("geo.enabled", false);

        WebDriver driver = new FirefoxDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.get("https://www.riteaid.com/signup");

        String title = driver.getTitle();
        assert title.contains("Signup");

        final WebElement FirstNameInputField = driver.findElement(By.xpath("//*[@id=\"fname\"]"));
        final WebElement LastNameInputField = driver.findElement(By.xpath("//*[@id=\"lname\"]"));
        final WebElement OptionalRewardsIDInputField = driver.findElement(By.xpath("//*[@id=\"rewardsId\"]"));
        final WebElement PhoneNumberInputField = driver.findElement(By.xpath("//*[@id=\"phone\"]"));
        final WebElement EmailInputField = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        final WebElement PasswordInputField = driver.findElement(By.xpath("//*[@id=\"signup-password\"]"));
        final WebElement SignUpButton = driver.findElement(By.xpath("//*[@id=\"sign-up-submit-button\"]"));

        FirstNameInputField.sendKeys(FirstName);
        LastNameInputField.sendKeys(LastName);
        if (ShouldUseRewardsID) OptionalRewardsIDInputField.sendKeys(RewardsID);
        else System.out.println("No Rewards ID was used");
        PhoneNumberInputField.sendKeys(PhoneNumber);

        //end
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        driver.quit();
        System.out.println("Done!");
    }
}