package com.cadd.qa.testcases;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cadd.qa.base.TestBase;
import com.cadd.qa.pages.HomePage;
import com.cadd.qa.pages.LoginPage;
import com.cadd.qa.pages.UserHomePage;
import com.cadd.qa.util.TestConstants;
import com.cadd.qa.util.TestUtil;

public class LoginPageTests extends TestBase{

	HomePage homePage;
	LoginPage loginPage;
	Logger log = Logger.getLogger(LoginPageTests.class);
	
	public LoginPageTests()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp()
	{
		initialization();
		log.info("Navigating to url: " + prop.getProperty("url"));
		homePage = new HomePage();
		loginPage = homePage.clickSignIn();
		loginPage.validateLoginPageTitle();
	}
	
	@Test
	public void verifyUserIsAbleToLoginWithValidCredentials()
	{
		log.info("------------- Test execution verifyUserIsAbleToLoginWithValidCredentials START -------------");
		UserHomePage userHomePage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		userHomePage.validateLoggedInUserName();
		log.info("------------- Test execution verifyUserIsAbleToLoginWithValidCredentials END -------------");
	}
	
	@Test
	public void verifyErrorMessageIsDisplayedWhenInvalidEmailIsEnteredForLogin()
	{
		log.info("------------- Test execution verifyErrorMessageIsDisplayedWhenInvalidEmailIsEnteredForLogin START -------------");
		loginPage.login("test@abc.com", prop.getProperty("password"));
		loginPage.validateValidationErrorText(TestConstants.INVALID_EMAIL_ERROR_TEXT);
		log.info("------------- Test execution verifyErrorMessageIsDisplayedWhenInvalidEmailIsEnteredForLogin END -------------");
	}
	
	@Test
	public void verifyErrorMessageIsDisplayedWhenValidEmailButInvalidPaswordIsEnteredForLogin()
	{
		log.info("------------- Test execution verifyErrorMessageIsDisplayedWhenValidEmailButInvalidPaswordIsEnteredForLogin START -------------");
		loginPage.login(prop.getProperty("email"), "abc");
		loginPage.validateValidationErrorText(TestConstants.INVALID_PASSWORD_ERROR_TEXT);
		log.info("------------- Test execution verifyErrorMessageIsDisplayedWhenValidEmailButInvalidPaswordIsEnteredForLogin END -------------");
	}
	
	@Test
	public void verifyErrorMessageIsDisplayedWhenUserTriesToLoginWithoutEnteringEmailAndPassword()
	{
		log.info("------------- Test execution verifyErrorMessageIsDisplayedWhenUserTriesToLoginWithoutEnteringEmailAndPassword START -------------");
		loginPage.login("", "");
		loginPage.validateValidationErrorText(TestConstants.EMAIL_REQUIRED_ERROR_TEXT);
		loginPage.validateValidationErrorText(TestConstants.PASSWORD_REQUIRED_ERROR_TEXT);
		log.info("------------- Test execution verifyErrorMessageIsDisplayedWhenUserTriesToLoginWithoutEnteringEmailAndPassword END -------------");
	}
	
	@AfterMethod
	public void tearDown()
	{
		if(testFailed)
		{
			try 
			{
				TestUtil.takeScreenshotAtEndOfTest();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		driver.quit();
	}
}
