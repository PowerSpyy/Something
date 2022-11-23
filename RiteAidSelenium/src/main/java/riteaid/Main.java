package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import static riteaid.Util.*;

public final class Main {
    private static final String FirstName = "fewa";
    private static final String LastName = "awef";
    private static String RewardsID = "";
    private static boolean ShouldUseRewardsID = false;
    private static String PhoneNumber = "8589039139";
    private static String EmailAddress = "JohnnyD123@gmail.com";
    private static final String Password = "Fewa123!";
    public static final WebDriver driver = geckoDriver(true);

    public static int seedA = 0;
    public static int seedB = 0;
    public static int AreaCode = 324;
    public static String ending = "@gmail.com";
    public static final JavascriptExecutor js = (JavascriptExecutor) driver;
    public static FileWriter file;
    public static WebElement thing;

    static {
        try {
            file = new FileWriter("./RiteAidSelenium/src/main/data/data.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        driver.get("https://www.riteaid.com/signup");
        assert driver.getTitle().contains("Signup");

        final WebElement FirstNameInputField = driver.findElement(By.xpath("//*[@id=\"fname\"]"));
        final WebElement LastNameInputField = driver.findElement(By.xpath("//*[@id=\"lname\"]"));
        final WebElement OptionalRewardsIDInputField = driver.findElement(By.xpath("//*[@id=\"rewardsId\"]"));
        final WebElement PhoneNumberInputField = driver.findElement(By.xpath("//*[@id=\"phone\"]"));
        final WebElement EmailInputField = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        final WebElement PasswordInputField = driver.findElement(By.xpath("//*[@id=\"signup-password\"]"));

        final WebElement SignUpButton = driver.findElement(By.xpath("//*[@id=\"sign-up-submit-button\"]"));

        setPhoneNumber(GeneratePhoneNumber(seedA, AreaCode));
        setEmailAddress(GenerateEmailAddress(ending, seedB));

        //https://riteaid.com/signup#accountcreated <- is the link when pressed button
        //https://www.riteaid.com/ra-dashboard
        try {
            for (int i = 0; i < 100; ++i) {
                createAccount(FirstNameInputField, LastNameInputField, PhoneNumberInputField, EmailInputField, PasswordInputField, SignUpButton, OptionalRewardsIDInputField);
            }
        } catch (ElementNotInteractableException ENIE) {
            LogMessageAsError(ENIE.getMessage());
        } finally {
            LogMessage("Finished processing Sign Up button");
        }

        //end
        LogMessage("Done!");
        //driver.quit();
    }

    public static void createAccount(WebElement FirstNameInputField, WebElement LastNameInputField, WebElement PhoneNumberInputField, WebElement EmailInputField, WebElement PasswordInputField, WebElement SignUpButton, WebElement OptionalRewardsIDInputField) throws IOException, InterruptedException {
        FirstAndLastNameFieldInput(FirstNameInputField, LastNameInputField);

        if (isShouldUseRewardsID()) OptionalRewardsIDInputField.sendKeys(getRewardsID());
        else LogMessage("No Rewards ID was used");

        PhoneNumberSendKeys(PhoneNumberInputField, getPhoneNumber());
        EmailAndPasswordFieldInput(EmailInputField, PasswordInputField);

        js.executeScript("window.scrollBy(0, 500)", "");
        Thread.sleep(500);
        SignUpButton.click();
        Thread.sleep(500);

        js.executeScript("window.scrollBy(0, -200)", "");
        Thread.sleep(500);

        while (!GoodEmail()) {
            ++seedB;
            setEmailAddress(GenerateEmailAddress(ending, seedB));
            EmailInputField.clear();
            EmailInputField.sendKeys(getEmailAddress());
            LogMessageAsInfo("Entering Email Address: \"" + getEmailAddress() + "\"");

            js.executeScript("window.scrollBy(0, 300)", "");
            Thread.sleep(500);
            SignUpButton.click();
            Thread.sleep(500);
            js.executeScript("window.scrollBy(0, -300)", "");
            Thread.sleep(2000);

            LogMessage(String.valueOf(seedA));
            LogMessage(String.valueOf(seedB));
        }

        js.executeScript("window.scrollBy(0, -800)", "");
        Thread.sleep(500);

        while (!GoodPhoneNumber()) {
            ++seedA;
            setPhoneNumber(GeneratePhoneNumber(seedA, AreaCode));
            PhoneNumberInputField.clear();
            PhoneNumberSendKeys(PhoneNumberInputField, getPhoneNumber());

            js.executeScript("window.scrollBy(0, 800)", "");
            Thread.sleep(1000);
            SignUpButton.click();
            Thread.sleep(500);
            js.executeScript("window.scrollBy(0, -800)", "");
            Thread.sleep(1000);

            LogMessage(String.valueOf(seedA));
            LogMessage(String.valueOf(seedB));
        }

        Thread.sleep(5000);

        file.write(getPhoneNumber());
        file.write(getEmailAddress());
        file.write("\n");
        file.close();
        ++seedA;
        ++seedB;
        setPhoneNumber(GeneratePhoneNumber(seedA, AreaCode));
        setEmailAddress(GenerateEmailAddress(ending, seedB));
        Thread.sleep(1000);
        thing = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div[1]/header/nav/div[2]/div[3]/button"));
        thing.click();
        Thread.sleep(1000);
        thing = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div[3]/div/div/div/div/div[8]/div/div"));
        thing.click();

        LogMessageAsInfo(driver.getTitle());
    }

    public static boolean GoodEmail() {
        return (driver.findElements(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div[2]/div[2]/div/div/div/form/div[2]/div[1]/div[1]/div[5]/div/label")).size() == 0)
                && (driver.findElements(By.xpath("//*[@id=\"emailError\"]")).size() == 0);
    }

    public static boolean GoodPhoneNumber() {
        return driver.findElements(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div[2]/div[2]/div/div/div/form/div[2]/div[1]/div[1]/div[3]/div/label")).size() == 0;
    }

    public static WebDriver geckoDriver(boolean supressLogFiles) {
        if (supressLogFiles) System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
        else LogMessage("Log Messages are enabled");

        LogMessage("Setting up Gecko Driver (Firefox)");
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(new FirefoxOptions().addPreference("geo.enabled", false)
                .addPreference("media.volume_scale", "0.0"));
    }

    public static WebDriver chromeDriver() {
        LogMessage("Setting up Chrome Driver");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(new ChromeOptions());
    }

    public static void FirstAndLastNameFieldInput(WebElement fName, WebElement lName) {
        fName.sendKeys(getFirstName());
        LogMessageAsInfo("Entering First Name field: \"" + getFirstName() + "\"");
        lName.sendKeys(getLastName());
        LogMessageAsInfo("Entering Last Name field: \"" + getLastName() + "\"");
    }

    public static void EmailAndPasswordFieldInput(WebElement EmailInputField, WebElement PasswordInputField) {
        EmailInputField.sendKeys(getEmailAddress());
        LogMessageAsInfo("Entering Email Address: \"" + getEmailAddress() + "\"");
        PasswordInputField.sendKeys(getPassword());
        LogMessageAsInfo("Entering Password: \"" + getPassword() + "\"");
    }

    public static void PhoneNumberSendKeys(WebElement PhoneNumberInputField, String message) throws InterruptedException {
        LogMessageAsInfo("Entering Phone Number: \"" + getPhoneNumber() + "\"");
        PhoneNumberInputField.click();
        PhoneNumberInputField.sendKeys("(");

        for (int i = 0; i < 10; ++i) {
            Thread.sleep(50);
            PhoneNumberInputField.sendKeys(Character.toString(message.charAt(i)));
        }
    }

    //getters and setters
    public static String getFirstName() {
        return FirstName;
    }
    public static String getLastName() {
        return LastName;
    }
    public static String getRewardsID() {
        return RewardsID;
    }
    public static boolean isShouldUseRewardsID() {
        return ShouldUseRewardsID;
    }
    public static String getPhoneNumber() {
        return PhoneNumber;
    }
    public static void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public static String getEmailAddress() {
        return EmailAddress;
    }
    public static void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }
    public static String getPassword() {
        return Password;
    }
}