package com.cadd.qa.testcases;

import java.io.File;

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
import com.cadd.qa.util.TestUtil;

public class UserHomePageTests extends TestBase{

	HomePage homePage;
	LoginPage loginPage;
	UserHomePage userHomePage;
	Logger log = Logger.getLogger(UserHomePageTests.class);
	
	public UserHomePageTests()
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
		userHomePage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		userHomePage.validateLoggedInUserName();
	}
	
	@Test
	public void verifyLoggedInUserIsAbleToDownloadCADSampleCollectionFromHomePage()
	{
		log.info("------------- Test execution verifyLoggedInUserIsAbleToDownloadCADSampleCollectionFromHomePage START -------------");
		TestUtil.deleteFileIfExists(TestConstants.CAD_SAMPLE_COLLECTION_FILE_NAME);
		userHomePage.clickCADLink();
		TestUtil.validateFileIsDownloaded(TestConstants.CAD_SAMPLE_COLLECTION_FILE_NAME);
		log.info("File '" + TestConstants.CAD_SAMPLE_COLLECTION_FILE_NAME + "' is downloaded at - " + new File("").getAbsolutePath() + "/Downloads/");
		log.info("------------- Test execution verifyLoggedInUserIsAbleToDownloadCADSampleCollectionFromHomePage END -------------");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
