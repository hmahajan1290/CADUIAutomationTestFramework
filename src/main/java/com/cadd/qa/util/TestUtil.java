package com.cadd.qa.util;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cadd.qa.base.TestBase;

public class TestUtil extends TestBase{
	
	Logger log = Logger.getLogger(TestUtil.class);
	
	public void deleteFileIfExists(String fileName)
	{
		try
		{
			File file = new File(new File("").getAbsolutePath() + "/Downloads/" + fileName);
			if (file.exists())
			    file.delete();
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in deleteFileIfExists method", e);
			e.printStackTrace();
		}
	}
	
	public void validateFileIsDownloaded(String fileName)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			File file = new File(new File("").getAbsolutePath() + "/Downloads/" + fileName);
			wait.until((ExpectedCondition<Boolean>) driver -> file.exists());
		}
		catch (Exception e)
		{
			testFailed = true;
			log.error("ERROR in validateFileIsDownloaded method", e);
			e.printStackTrace();
		}
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		System.out.println("Taking screenshot...");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
}
