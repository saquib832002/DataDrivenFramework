package com.qtpselenium.core.ddf.testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.qtpselenium.core.ddf.base.myTestBase;

public class myDummyTestA extends myTestBase{
	
	@Test
	public void testA() {
		test = rep.startTest("myDummyTestA");
		openBrowser("Mozilla");
		navigateURL("appurl");
		enterData("email_id", "saquib832002@gmail.com");
		click("button_css");
	}
	
	@AfterTest
	public void afterTest() {
		rep.endTest(test);
		rep.flush();
	}

}
