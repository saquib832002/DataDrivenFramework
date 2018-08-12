package com.qtpselenium.core.ddf.testcases;


import java.util.HashMap;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.core.ddf.base.BaseTest;
import com.qtpselenium.core.ddf.util.DataUtil;
import com.qtpselenium.core.ddf.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class DummyTestB extends BaseTest{
	String testCaseName="TestB";
	SoftAssert softAssert;
	Xls_Reader xls;
	
	@Test(dataProvider="getData")
	public void testB(HashMap<String, String> data){
		test = rep.startTest("DummyTestB");
		test.log(LogStatus.INFO, "Starting the test test B");
		test.log(LogStatus.INFO,"NNNN");
		if(!DataUtil.isRunnable(testCaseName, xls) ||  data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser("Mozilla");
		test.log(LogStatus.INFO, "Opened the browser");
		navigate("appurl");
		
		// verify signin Text
		/*
		if(!verifyText("signintext_xpath","signinText"))
			reportFailure("Text Did not Match");//critical
		*/
		softAssert.assertTrue(verifyText("signintext_xpath","signinText"), "Text did not match");
		softAssert.assertTrue(false, "Err 2");
		softAssert.assertTrue(true, "Err 3");
		softAssert.assertTrue(false, "Err 4");
		
		// check if email field is present
		if(!isElementPresent("email_xpath"))
			reportFailure("Email field not present");//critical
		
		type("email_xpath","seleniumtraining10@gmail.com");
		click("button_css");
		
		// id, name
		// not hardcode things
		// data from xls
		
		verifyTitle();
		
		test.log(LogStatus.PASS, "Test B Passed");
		// screenshots
		takeScreenShot();

	}
	
	@BeforeMethod
	public void beforeMethod(){
		super.init();
		softAssert = new SoftAssert();
	}
	
	
	@AfterMethod
	public void quit(){
		try{
			softAssert.assertAll();
		}catch(Error e){
			test.log(LogStatus.FAIL, e.getMessage());
		}
		
		rep.endTest(test);
		rep.flush();
	}
	
	@DataProvider
	public Object[][] getData(){
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, testCaseName);
		
	}
	
	
	
}
