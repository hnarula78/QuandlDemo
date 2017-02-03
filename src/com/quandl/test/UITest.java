package com.quandl.test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

/**
 * Created by himan on 2017-02-02.
 */
public class UITest extends Utilities {
    public WebDriver driver ;

    @BeforeTest
    public void OpenBrowser(){
        loadProperties();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities = capabilities.merge(DesiredCapabilities.chrome());
        System.setProperty("webdriver.chrome.driver", getChromeDriverLocation());
        driver = new ChromeDriver();

        driver.navigate().to("http://www.quandl.com");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    //Test 1 : Verify that only 1 Logo is visible and it points to Home Page
    @Test(priority = 1)
    public void verifyOneLogo(){
        int visible_logo_counter = 0;
        String HomeLink = null;
        List<WebElement> logos = driver.findElements(By.xpath("//*[starts-with(@class,'quandl-logo')]"));
        System.out.println("Total Logo Objects (Visible / Not Visible ) available on Opened URL are " + logos.size());
        for(WebElement logo : logos) {
            if (logo.isDisplayed()){
                HomeLink = logo.getAttribute("href");
                visible_logo_counter ++;
                System.out.println("Visible Logo Object Visible points to --> " + HomeLink);
            }
        }
        Assert.assertTrue(visible_logo_counter==1,"No. of visible Logos is not equals to 1");
        Assert.assertTrue(HomeLink.equals("https://www.quandl.com/"),"The visible logo did not point to HomePage");
        System.out.println("Total Visible Logos on Page --> [" + visible_logo_counter + "] AND Visible Logo points to -->" + HomeLink);
    }
    //Test 2 : Verify that there's  a career link in Footer Bar
    @Test(priority = 2)
    public void verifyCareerInFooter(){
        WebElement careerlink = driver.findElement(By.xpath("//ancestor::footer[@class='fat-footer']/descendant::a[contains(@class,'qa-footer-jobs')]"));
        Assert.assertTrue(careerlink.isDisplayed(),"Career Link is Not Visible in Footer");
        Assert.assertTrue(careerlink.isEnabled(),"Career Link is Not Clickable");
        System.out.println("Career Link points to " + careerlink.getAttribute("href"));
    }
    //Test 3: Verify that you are able to Sign up for a new acccount
    @Test(priority =3)
    public void signUpAccount(){
        boolean flag= false;
        List<WebElement> signInLink = driver.findElements(By.xpath("//a[contains(text(),'Sign in')]"));
        for(WebElement signUpLink :signInLink )
        {
            if(signUpLink.getText()!=null || signUpLink.getText().equals(""))
            {
                Assert.assertTrue(signUpLink.isEnabled(),"Sign In button is not enabled");
                signUpLink.click();
                flag = true;
            }
            if(flag= true)
                break;
        }
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        WebElement createButton = driver.findElement(By.cssSelector("a.qa-register.b-button.b-button--blue.b-button--small"));
        createButton.click();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        WebElement inputUser = driver.findElement(By.cssSelector("input.ember-text-field.qa-username.valid-input.ember-view"));
        WebElement inputEmail = driver.findElement(By.cssSelector("input.ember-text-field.qa-email.valid-input.ember-view"));
        WebElement inputPassword = driver.findElement(By.cssSelector("input.ember-text-field.qa-password.valid-input.ember-view"));
        WebElement inputConfirmPassword = driver.findElement(By.cssSelector("input.ember-text-field.qa-password-confirmation.valid-input.ember-view"));
        WebElement submitButton = driver.findElement(By.cssSelector("button.qa-sign-up-free.b-button--orange.b-button--large.b-button--full-width.b-button.ember-view"));
        inputUser.sendKeys(getUserName());
        inputEmail.sendKeys(getEmail());
        inputPassword.sendKeys(getPassword());
        inputConfirmPassword.sendKeys(getPassword());
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement apiKeyText = driver.findElement(By.cssSelector("h3.api-key"));
        Assert.assertTrue(apiKeyText.isDisplayed(),"Account Sign Up did not Succeeded.");
        System.out.println("Your API Key is " + apiKeyText.getText());
   }

@AfterTest
    public void quitBrowser()
    {
        driver.close();
    }
}
