package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

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

        driver.quit();
        System.out.println("Done!");
    }
}