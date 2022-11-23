package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileWriter;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static riteaid.Util.*;

public final class Main {
    private static final String FirstName = "fewa";
    private static final String LastName = "awef";
    private static String RewardsID = "";
    private static boolean ShouldUseRewardsID = false;
    private static String PhoneNumber = "8589039139";//first character is ignored for some reason
    private static String EmailAddress = "JohnnyD123@gmail.com"; //temp
    private static final String Password = "Fewa123!";
    public static final WebDriver driver = geckoDriver(true);

    public static void main(String[] args) throws Exception {
        //setPhoneNumber(GeneratePhoneNumber(69420, 9999991, "324"));
        resetInfo(EmailEndings.GMAIL); //doesn't work yet
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://www.riteaid.com/signup");
        assert driver.getTitle().contains("Signup");

        final WebElement FirstNameInputField = driver.findElement(By.xpath("//*[@id=\"fname\"]"));
        final WebElement LastNameInputField = driver.findElement(By.xpath("//*[@id=\"lname\"]"));
        final WebElement OptionalRewardsIDInputField = driver.findElement(By.xpath("//*[@id=\"rewardsId\"]"));
        final WebElement PhoneNumberInputField = driver.findElement(By.xpath("//*[@id=\"phone\"]"));
        final WebElement EmailInputField = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        final WebElement PasswordInputField = driver.findElement(By.xpath("//*[@id=\"signup-password\"]"));

        FirstAndLastNameFieldInput(FirstNameInputField, LastNameInputField);

        if (isShouldUseRewardsID()) OptionalRewardsIDInputField.sendKeys(getRewardsID());
        else LogMessage("No Rewards ID was used");

        //@IMPORTANT Sometimes this could fail;
        //If the browser/PC is slow, some numbers could get lossy
        PhoneNumberSendKeys(PhoneNumberInputField, PhoneNumber);
        EmailAndPasswordFieldInput(EmailInputField, PasswordInputField);

        FileWriter file = new FileWriter("./RiteAidSelenium/src/main/data/data.txt", true);

        file.write(getPhoneNumber());
        file.write(getEmailAddress());
        file.write("\n");
        file.close();

        /* error when SignUpButton.click():
        Exception in thread "main" org.openqa.selenium.ElementNotInteractableException: Element <button id="sign-up-submit-button" class="sign-up-modal__login-btns__signup
            sign-up-modal__login-btns__signup--submit btn-rounded
            background-green color-white" type="submit"> could not be scrolled into view
         */

        WebElement SignUpButton = driver.findElement(By.xpath("//*[@id=\"sign-up-submit-button\"]"));

        //https://riteaid.com/signup#accountcreated <- is the link when pressed button
        //https://www.riteaid.com/ra-dashboard
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 500)", "");
            Thread.sleep(4000);
            SignUpButton.click();
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[5]/div/div/div/div[4]/footer/div/div[1]/div/div[1]/div/div/div[1]/div/a"))).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            LogMessageAsInfo(driver.getTitle());
        } catch (ElementNotInteractableException ENIE) {
            LogMessageAsError(ENIE.getMessage());
        } finally {
            LogMessage("Finished processing Sign Up button");
        }

        //end
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
        LogMessage("Done!");
        //driver.quit();
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
        final String uno = Character.toString(message.charAt(0));
        final String dos = Character.toString(message.charAt(1));
        final String tres = Character.toString(message.charAt(2));
        final String cuatro = Character.toString(message.charAt(3));
        final String cinco = Character.toString(message.charAt(4));
        final String seis = Character.toString(message.charAt(5));
        final String siete = Character.toString(message.charAt(6));
        final String ocho = Character.toString(message.charAt(7));
        final String neuve = Character.toString(message.charAt(8));
        final String dies = Character.toString(message.charAt(9));

        LogMessageAsInfo("Entering Phone Numbed: \"" + getPhoneNumber() + "\"");
        PhoneNumberInputField.click();
        PhoneNumberInputField.sendKeys("(");

        for (int i = 0; i < 10; ++i) {
            Thread.sleep(50);
            PhoneNumberInputField.sendKeys(Character.toString(message.charAt(i)));
        }

        assert Objects.equals(PhoneNumberInputField.getText(), "("+uno+dos+tres+") "+cuatro+cinco+seis+"-"+siete+ocho+neuve+dies);
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

    //remember to change seed and area code
    public static void resetInfo(EmailEndings ending) throws Exception {
        LogMessage("Reset Email and Phone Number");
        switch (ending) {
            case MAIL -> {
                setPhoneNumber(GeneratePhoneNumber(69, 324));
                setEmailAddress(GenerateEmailAddress("@mail.com", 69));
            }
            case GMAIL -> {
                setPhoneNumber(GeneratePhoneNumber(69, 324));
                setEmailAddress(GenerateEmailAddress("@gmail.com", 69));
            }
            case HOTMAIL -> {
                setPhoneNumber(GeneratePhoneNumber(69, 324));
                setEmailAddress(GenerateEmailAddress("@hotmail.com", 69));
            }
            case YAHOO -> {
                setPhoneNumber(GeneratePhoneNumber(69, 324));
                setEmailAddress(GenerateEmailAddress("@yahoo.com", 69));
            }
            case OUTLOOK -> {
                setPhoneNumber(GeneratePhoneNumber(69, 324));
                setEmailAddress(GenerateEmailAddress("@outlook.com", 69));
            }
            default -> {
                LogMessageAsError("Cannot find " + ending + " in Enum \"EmailEndings.java\"");
                throw new Exception("Not present");
            }
        }
    }
}