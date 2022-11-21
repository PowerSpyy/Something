package riteaid;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class Main {
    private static String FirstName = "fewa";
    private static String LastName = "awef";
    private static String RewardsID = "";
    private static boolean ShouldUseRewardsID = false;
    private static String PhoneNumber = "8589039139";//first character is ignored for some reason
    private static String EmailAddress = "JohnnyD123@gmail.com"; //temp
    private static final String Password = "Fewa123!";

    public static  void main(String[] args) {
        //setPhoneNumber(GeneratePhoneNumber(69420, 9999991, "324"));
        //resetInfo(); //doesn't work yet
        WebDriver driver = geckoDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

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
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        LastNameInputField.sendKeys(getLastName());
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        if (isShouldUseRewardsID())
            OptionalRewardsIDInputField.sendKeys(RewardsID);
        else System.out.println("No Rewards ID was used");
        PhoneNumberInputField.click();
        PhoneNumberSendKeys(PhoneNumberInputField, PhoneNumber);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        EmailInputField.sendKeys(getEmailAddress());
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        PasswordInputField.sendKeys(getPassword());
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));

        SignUpButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        System.out.println(driver.getTitle());

        //end
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {}

        driver.quit();
        System.out.println("Done!");
    }

    public static WebDriver geckoDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(new FirefoxOptions().addPreference("geo.enabled", false));
    }

    public static void PhoneNumberSendKeys(WebElement PhoneNumberInputField, String message) {
        String uno = Character.toString(message.charAt(0));
        String dos = Character.toString(message.charAt(1));
        String tres = Character.toString(message.charAt(2));
        String cuatro = Character.toString(message.charAt(3));
        String cinco = Character.toString(message.charAt(4));
        String seis = Character.toString(message.charAt(5));
        String siete = Character.toString(message.charAt(6));
        String ocho = Character.toString(message.charAt(7));
        String neuve = Character.toString(message.charAt(8));
        String diaz = Character.toString(message.charAt(9));

        PhoneNumberInputField.click();
        PhoneNumberInputField.sendKeys("(");
        PhoneNumberInputField.sendKeys(uno);
        PhoneNumberInputField.sendKeys(dos);
        PhoneNumberInputField.sendKeys(tres);
        PhoneNumberInputField.sendKeys(cuatro);
        PhoneNumberInputField.sendKeys(cinco);
        PhoneNumberInputField.sendKeys(seis);
        PhoneNumberInputField.sendKeys(siete);
        PhoneNumberInputField.sendKeys(ocho);
        PhoneNumberInputField.sendKeys(neuve);
        PhoneNumberInputField.sendKeys(diaz);
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
                System.out.println("Cannot find " + ending + " in Enum \"EmailEndings.java\"");
                throw new Exception("Not present");
            }
        }
    }
}