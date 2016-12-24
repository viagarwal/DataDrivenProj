package com.propertyfinder.utils;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

import com.relevantcodes.extentreports.LogStatus;

import core.Driver;

public class WebDriverListener extends Driver implements WebDriverEventListener, IResultListener {

//	private final static Logger Log = Logger.getLogger(WebDriverListener.class.getName());

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		System.out.println("Before Click on" +element.toString().split("->",2)[1]);
		logger.info("Before Click on" + element.toString().split("->",2)[1]);
		test.log(LogStatus.INFO,"Before Click on", element.toString().split("->",2)[1]);
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		logger.info("After Click on" + element.toString().split("->",2)[1]);
		test.log(LogStatus.INFO,"After Click on",element.toString().split("->",2)[1]);

	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
//		test.log(LogStatus.SKIP, result.getMethod().getDescription());
//		extent.endTest(test);
//		extent.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
//		+"::"+result.getMethod().getInvocationNumbers()
		test = extent.startTest(result.getTestClass().getName()+ "::"+result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		logger.info("description====="+result.getMethod().getDescription());		
		test.log(LogStatus.PASS, result.getMethod().getDescription());
		extent.endTest(test);
		extent.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {

		test.log(LogStatus.FAIL, result.getMethod().getDescription(), result.getThrowable().getMessage());
		try {
			test.log(LogStatus.INFO, "Screencast below: "+ test.addScreenCapture(takeScreenShot(result.getTestClass().getName()+"_"+result.getMethod().getMethodName())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extent.endTest(test);
		extent.flush();
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}



}
