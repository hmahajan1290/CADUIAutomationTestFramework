package com.cadd.qa.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
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
	TestUtil ut = new TestUtil();
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
		loginPage.validateLoginPageTitle();
		userHomePage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		userHomePage.validateLoggedInUserName();
	}
	
	@Test
	public void verifyLoggedInUserIsAbleToDownloadCADSampleCollectionFromHomePage()
	{
		log.info("------------- Test execution verifyLoggedInUserIsAbleToDownloadCADSampleCollectionFromHomePage START -------------");
		ut.deleteFileIfExists(TestConstants.CAD_SAMPLE_COLLECTION_FILE_NAME);
		userHomePage.clickCADLink();
		ut.validateFileIsDownloaded(TestConstants.CAD_SAMPLE_COLLECTION_FILE_NAME);
		log.info("File '" + TestConstants.CAD_SAMPLE_COLLECTION_FILE_NAME + "' is downloaded at - " + new File("").getAbsolutePath() + "/Downloads/");
		log.info("------------- Test execution verifyLoggedInUserIsAbleToDownloadCADSampleCollectionFromHomePage END -------------");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		if(testFailed)
		{
			try 
			{
				TestUtil.takeScreenshotAtEndOfTest(result.getMethod().getMethodName());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		driver.quit();
	}
}
