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
	
	public static void deleteFileIfExists(String fileName)
	{
		File file = new File(new File("").getAbsolutePath() + "/Downloads/" + fileName);
		if (file.exists())
		    file.delete();
	}
	
	public static void validateFileIsDownloaded(String fileName)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		File file = new File(new File("").getAbsolutePath() + "/Downloads/" + fileName);
		wait.until((ExpectedCondition<Boolean>) driver -> file.exists());
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		System.out.println("Taking screenshot...");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
}
