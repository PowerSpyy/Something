package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

import static riteaid.Util.*;

public final class Main {
    private static final String FirstName = "fewa";
    private static final String LastName = "awef";
    private static String RewardsID = "";
    private static boolean ShouldUseRewardsID = false;
    private static String PhoneNumber = "8589039139";//first character is ignored for some reason
    private static String EmailAddress = "JohnnyD123@gmail.com"; //temp
    private static final String Password = "Fewa123!";
    public static final WebDriver driver = geckoDriver();

    public static void main(String[] args) {
        //setPhoneNumber(GeneratePhoneNumber(69420, 9999991, "324"));
        //resetInfo(); //doesn't work yet
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://www.riteaid.com/signup");
        assert driver.getTitle().contains("Signup");

        final WebElement FirstNameInputField = driver.findElement(By.xpath("//*[@id=\"fname\"]"));
        final WebElement LastNameInputField = driver.findElement(By.xpath("//*[@id=\"lname\"]"));
        final WebElement OptionalRewardsIDInputField = driver.findElement(By.xpath("//*[@id=\"rewardsId\"]"));
        final WebElement PhoneNumberInputField = driver.findElement(By.xpath("//*[@id=\"phone\"]"));
        final WebElement EmailInputField = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        final WebElement PasswordInputField = driver.findElement(By.xpath("//*[@id=\"signup-password\"]"));
        final WebElement SignUpButton = driver.findElement(By.xpath("//*[@id=\"sign-up-submit-button\"]"));

        FirstAndLastNameFieldInput(FirstNameInputField, LastNameInputField);

        if (isShouldUseRewardsID()) OptionalRewardsIDInputField.sendKeys(RewardsID);
        else LogMessage("No Rewards ID was used");

        //@IMPORTANT Sometimes this could fail;
        PhoneNumberSendKeys(PhoneNumberInputField, PhoneNumber);
        EmailAndPasswordFieldInput(EmailInputField, PasswordInputField);

        /* error when SignUpButton.click():
        Exception in thread "main" org.openqa.selenium.ElementNotInteractableException: Element <button id="sign-up-submit-button" class="sign-up-modal__login-btns__signup
            sign-up-modal__login-btns__signup--submit btn-rounded
            background-green color-white" type="submit"> could not be scrolled into view
         */

        //https://riteaid.com/signup#accountcreated <- is the link when pressed button
        try {
            SignUpButton.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            LogMessageAsInfo(driver.getTitle());
        } catch (ElementNotInteractableException ENIE) {
            LogMessageAsError(ENIE.getMessage());
        } finally {
            LogMessage("Finished processing Sign Up button");
        }

        //end
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
        driver.quit();
        LogMessage("Done!");
    }

    public static WebDriver geckoDriver() {
        LogMessage("Setting up Gecko Driver (Firefox)");
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(new FirefoxOptions().addPreference("geo.enabled", false)
                                .addPreference("media.volume_scale", "0.0"));
    }

    public static void FirstAndLastNameFieldInput(WebElement fName, WebElement lName) {
        fName.sendKeys(getFirstName());
        LogMessageAsInfo("Entering First Name field: \"" + getFirstName() + "\"");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        lName.sendKeys(getLastName());
        LogMessageAsInfo("Entering Last Name field: \"" + getLastName() + "\"");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
    }

    public static void EmailAndPasswordFieldInput(WebElement EmailInputField, WebElement PasswordInputField) {
        EmailInputField.sendKeys(getEmailAddress());
        LogMessageAsInfo("Entering Email Address: \"" + getEmailAddress() + "\"");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        PasswordInputField.sendKeys(getPassword());
        LogMessageAsInfo("Entering Password: \"" + getPassword() + "\"");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
    }

    public static void PhoneNumberSendKeys(WebElement PhoneNumberInputField, String message) {
        final String uno = Character.toString(message.charAt(0));
        final String dos = Character.toString(message.charAt(1));
        final String tres = Character.toString(message.charAt(2));
        final String cuatro = Character.toString(message.charAt(3));
        final String cinco = Character.toString(message.charAt(4));
        final String seis = Character.toString(message.charAt(5));
        final String siete = Character.toString(message.charAt(6));
        final String ocho = Character.toString(message.charAt(7));
        final String neuve = Character.toString(message.charAt(8));
        final String diaz = Character.toString(message.charAt(9));

        LogMessageAsInfo("Entering Phone Numbed: \"" + getPhoneNumber() + "\"");
        PhoneNumberInputField.click();
        PhoneNumberInputField.sendKeys("(");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(uno);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(dos);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(tres);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(cuatro);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(cinco);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(seis);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(siete);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(ocho);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(neuve);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        PhoneNumberInputField.sendKeys(diaz);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
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
    public static void setRewardsID(String rewardsID) {
        RewardsID = rewardsID;
    }
    public static boolean isShouldUseRewardsID() {
        return ShouldUseRewardsID;
    }
    public static void setShouldUseRewardsID(boolean shouldUseRewardsID) {
        ShouldUseRewardsID = shouldUseRewardsID;
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

    public static void resetInfo(EmailEndings ending) throws Exception {
        switch (ending) {
            case MAIL -> {
                //setPhoneNumber(GeneratePhoneNumber());
                //setEmailAddress(GenerateEmailAddress("@mail.com"));
            }
            case GMAIL -> {
                //setPhoneNumber(GeneratePhoneNumber());
                //setEmailAddress(GenerateEmailAddress("@gmail.com"));
            }
            case HOTMAIL -> {
                //setPhoneNumber(GeneratePhoneNumber());
                //setEmailAddress(GenerateEmailAddress("@hotmail.com"));
            }
            case YAHOO -> {
                //setPhoneNumber(GeneratePhoneNumber());
                //setEmailAddress(GenerateEmailAddress("@yahoo.com"));
            }
            case OUTLOOK -> {
                //setPhoneNumber(GeneratePhoneNumber());
                //setEmailAddress(GenerateEmailAddress("@outlook.com"));
            }
            default -> {
                LogMessageAsError("Cannot find " + ending + " in Enum \"EmailEndings.java\"");
                throw new Exception("Not present");
            }
        }
    }
}