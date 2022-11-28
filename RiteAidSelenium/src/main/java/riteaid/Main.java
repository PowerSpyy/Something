package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.*;
import java.util.Random;

import static riteaid.Util.*;

public final class Main {
    public static final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static String FirstName;
    private static String LastName;
    public static final Random rand = new Random();
    private static final String RewardsID = "";
    private static final boolean ShouldUseRewardsID = false;
    private static String PhoneNumber = "";
    private static String EmailAddress = "";
    private static final String Password = "Fewa123!";
    public static final WebDriver driver = geckoDriver(true);
    public static int AreaCode = 481;
    public static String ending = "@gmail.com";
    public static final JavascriptExecutor js = (JavascriptExecutor) driver;
    public static FileWriter file;
    public static FileReader seeds;

    static {
        try {
            seeds = new FileReader("./RiteAidSelenium/src/main/data/seeds.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int c = 0;

    public static int seedA;
    public static int seedB;
    public static WebElement thing;

    public static WebElement FirstNameInputField;
    public static WebElement LastNameInputField;
    public static WebElement OptionalRewardsIDInputField;
    public static WebElement PhoneNumberInputField;
    public static WebElement EmailInputField;
    public static WebElement PasswordInputField;
    public static WebElement SignUpButton;

    public static void main(String[] args) throws Exception {
        driver.get("https://www.riteaid.com/signup");
        assert driver.getTitle().contains("Signup");

        seedA = 0;
        seedB = 0;

        while ((c = seeds.read()) != 32) {
            seedA = seedA*10 + c - 48;
        }

        while ((c = seeds.read()) != -1) {
            seedB = seedB*10 + c - 48;
        }

        setPhoneNumber(GeneratePhoneNumber(seedA, AreaCode));
        setEmailAddress(GenerateEmailAddress(ending, seedB));

        //https://riteaid.com/signup#accountcreated <- is the link when pressed button
        //https://www.riteaid.com/ra-dashboard
        try {
            for (int i = 0; i < 100; ++i) {
                try {
                    FirstNameInputField = driver.findElement(By.xpath("//*[@id=\"fname\"]"));
                    LastNameInputField = driver.findElement(By.xpath("//*[@id=\"lname\"]"));
                    OptionalRewardsIDInputField = driver.findElement(By.xpath("//*[@id=\"rewardsId\"]"));
                    PhoneNumberInputField = driver.findElement(By.xpath("//*[@id=\"phone\"]"));
                    EmailInputField = driver.findElement(By.xpath("//*[@id=\"email\"]"));
                    PasswordInputField = driver.findElement(By.xpath("//*[@id=\"signup-password\"]"));
                    SignUpButton = driver.findElement(By.xpath("//*[@id=\"sign-up-submit-button\"]"));
                    FirstName = "";
                    LastName = "";
                    for (int j = 0; j < 6; ++j) {
                        FirstName += letters[rand.nextInt(26)];
                        LastName += letters[rand.nextInt(26)];
                    }
                    createAccount(FirstNameInputField, LastNameInputField, PhoneNumberInputField, EmailInputField, PasswordInputField, SignUpButton, OptionalRewardsIDInputField);
                    Thread.sleep(3000);
                    LogMessage("lol");
                }
                catch (Exception e) {
                    LogMessageAsError(e.getMessage());

                    ++seedB;
                    setEmailAddress(GenerateEmailAddress(ending, seedB));
                    driver.get("https://www.riteaid.com/signup");
                }
            }
        } catch (ElementNotInteractableException ENIE) {
            LogMessageAsError(ENIE.getMessage());
        }

        LogMessageAsInfo("i have died");
        file.close();


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
        thing = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div[2]/div[2]/div/div/div/form/div[2]/div[1]/div[1]/div[7]/label"));
        thing.click();
        Thread.sleep(500);
        SignUpButton.click();
        Thread.sleep(500);

        js.executeScript("window.scrollBy(0, -300)", "");
        Thread.sleep(500);

        while (!GoodEmail()) {
            ++seedB;
            setEmailAddress(GenerateEmailAddress(ending, seedB));
            EmailInputField.clear();
            EmailInputField.sendKeys(getEmailAddress());
            LogMessageAsInfo("Entering Email Address: \"" + getEmailAddress() + "\"");


            SignUpButton.sendKeys(Keys.DOWN);
            Thread.sleep(1000);
            SignUpButton.click();
            Thread.sleep(1000);

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

        EmailInputField.sendKeys(Keys.DOWN);

        while (!GoodEmail()) {
            ++seedB;
            setEmailAddress(GenerateEmailAddress(ending, seedB));
            EmailInputField.clear();
            EmailInputField.sendKeys(getEmailAddress());
            LogMessageAsInfo("Entering Email Address: \"" + getEmailAddress() + "\"");


            SignUpButton.sendKeys(Keys.DOWN);
            Thread.sleep(1000);
            SignUpButton.click();
            Thread.sleep(3000);

            LogMessage(String.valueOf(seedA));
            LogMessage(String.valueOf(seedB));
        }

        Thread.sleep(7000);

        try {
            file = new FileWriter("./RiteAidSelenium/src/main/data/accounts.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        file.write(getPhoneNumber() + getEmailAddress() + "\n");
        file.close();
        ++seedA;
        ++seedB;

        try {
            file = new FileWriter("./RiteAidSelenium/src/main/data/seeds.txt", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        file.write(seedA + " " + seedB);
        file.close();

        setPhoneNumber(GeneratePhoneNumber(seedA, AreaCode));
        setEmailAddress(GenerateEmailAddress(ending, seedB));
        Thread.sleep(1000);
        driver.get("https://www.riteaid.com/signup");
//        thing = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div[1]/header/nav/div[2]/div[3]/button"));
//        thing.click();
//        Thread.sleep(1000);
//        thing = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div[3]/div/div/div/div/div[8]/div/div/div/div[2]"));
//        thing.click();

        LogMessageAsInfo(driver.getTitle());
    }

    public static boolean GoodEmail() {
        return driver.findElement(By.xpath("//*[@id=\"emailError\"]")).getText().length() == 0;
    }

    public static boolean GoodPhoneNumber() {
        return driver.findElement(By.xpath("//*[@id=\"phone-existing\"]")).isDisplayed();
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