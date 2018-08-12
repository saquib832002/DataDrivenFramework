package com.qtpselenium.core.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.qtpselenium.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class myTestBase {
	public WebDriver driver;
	Properties prop = null;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	//class Local {};
	
	//============================== Operation===================================
	public void openBrowser(String browserName) {
		class Local {};
		//prop=new Properties();
		if(prop==null){
			prop=new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
				prop.load(fs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reportFailure(Local.class.getEnclosingMethod().getName(),e.getMessage());
			}
		}
		
		if(browserName.equalsIgnoreCase("mozilla")) {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("firefox_exe"));
			driver=new FirefoxDriver();
			
			reportSuccess(Local.class.getEnclosingMethod().getName(), browserName + " opened successfully");
		}
		else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("iedriver_exe"));
			driver= new InternetExplorerDriver();
			reportSuccess(Local.class.getEnclosingMethod().getName(), browserName + " opened successfully");
		}
		else if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_exe"));
			driver=new ChromeDriver();
			reportSuccess(Local.class.getEnclosingMethod().getName(), browserName + " opened successfully");
		}
		else {
			reportFailure(Local.class.getEnclosingMethod().getName(), browserName + " User selected browser not found");
		}
	}

	public void navigateURL(String urlKey) {
		class Local {};
		driver.get(prop.getProperty(urlKey).toString());
		reportSuccess(Local.class.getEnclosingMethod().getName(),prop.getProperty(urlKey).toString() +" opened successfully");
		}
	public void enterData(String locatorKey, String data) {
		class Local {};
		getElement(locatorKey).sendKeys(data);
		reportSuccess(Local.class.getEnclosingMethod().getName(),data +" eneterd successfully");
		
	}
	public void click(String locatorKey) {
	
	}
	
	
	//============================== Validations===================================
	
	public WebElement getElement(String locatorKey) {
		WebElement e=null;
		if (locatorKey.endsWith("_id")) {
			e = driver.findElement(By.id(prop.getProperty(locatorKey)));
		}
		else if (locatorKey.endsWith("_xpath")) {
			e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		}
		else if (locatorKey.endsWith("_css")) {
			e = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
		}
		else if (locatorKey.endsWith("_class")) {
			e = driver.findElement(By.className(prop.getProperty(locatorKey)));
		}
		
		else if (locatorKey.endsWith("_tagename")) {
			e = driver.findElement(By.tagName(prop.getProperty(locatorKey)));
		}
		
		return e;
		
	}
	
	
	
	//============================== Reports===================================

	public void reportSuccess(String stepName, String message) {
		test.log(LogStatus.PASS,stepName,message);
		//test.log()
	}
	
	public void reportFailure(String stepName,String message) {
		test.log(LogStatus.FAIL,stepName,message);
		takeScreenShot();
		Assert.fail(message);
	}
	
	public void takeScreenShot() {
		System.out.println("Taking screemshot");
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));

	}
}
