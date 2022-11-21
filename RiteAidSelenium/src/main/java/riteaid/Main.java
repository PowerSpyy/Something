package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

import static riteaid.Util.GenerateEmailAddress;
import static riteaid.Util.GeneratePhoneNumber;

public class Main {
    private static String FirstName = "fewa";
    private static String LastName = "awef";
    private static String RewardsID = "";
    private static boolean ShouldUseRewardsID = false;
    private static String PhoneNumber = "8589039139";
    private static String EmailAddress;
    private static final String Password = "Fewa123!";

    public static  void main(String[] args) {
        //resetInfo(); //doesn't work yet
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

        FirstNameInputField.sendKeys(getFirstName());
        LastNameInputField.sendKeys(getLastName());
        if (isShouldUseRewardsID())
            OptionalRewardsIDInputField.sendKeys(RewardsID);
        else System.out.println("No Rewards ID was used");
        PhoneNumberInputField.sendKeys(getPhoneNumber());
        EmailInputField.sendKeys(getEmailAddress());
        PasswordInputField.sendKeys(getPassword());

        //end
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {System.out.println(e);}

        driver.quit();
        System.out.println("Done!");
    }

    //getters and setters
    public static String getFirstName() {
        return FirstName;
    }
    public static void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public static String getLastName() {
        return LastName;
    }
    public static void setLastName(String lastName) {
        LastName = lastName;
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
                setPhoneNumber(GeneratePhoneNumber());
                setEmailAddress(GenerateEmailAddress("@mail.com"));
            }
            case GMAIL -> {
                setPhoneNumber(GeneratePhoneNumber());
                setEmailAddress(GenerateEmailAddress("@gmail.com"));
            }
            case HOTMAIL -> {
                setPhoneNumber(GeneratePhoneNumber());
                setEmailAddress(GenerateEmailAddress("@hotmail.com"));
            }
            case YAHOO -> {
                setPhoneNumber(GeneratePhoneNumber());
                setEmailAddress(GenerateEmailAddress("@yahoo.com"));
            }
            case OUTLOOK -> {
                setPhoneNumber(GeneratePhoneNumber());
                setEmailAddress(GenerateEmailAddress("@outlook.com"));
            }
            default -> {
                throw new Exception("Not present");
            }
        }
    }
}