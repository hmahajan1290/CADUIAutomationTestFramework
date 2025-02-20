package com.cadd.qa.testcases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cadd.qa.base.TestBase;
import com.cadd.qa.pages.HomePage;
import com.cadd.qa.pages.LoginPage;
import com.cadd.qa.pages.UserHomePage;
import com.cadd.qa.util.TestConstants;

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
		Assert.assertEquals(loginPage.getLoginPageTitle(), "CADdetails Login");
		log.info("[ASSERT PASSED] - User is on CADdetails Login page");
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
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
