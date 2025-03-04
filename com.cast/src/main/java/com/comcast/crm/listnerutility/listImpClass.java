package com.comcast.crm.listnerutility;

       import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import co.cast.generic.WebDriverUtitlity.UtilityClassObject;

public class listImpClass implements ITestListener, ISuiteListener {

	public ExtentSparkReporter spark;
	public  ExtentTest test;
	public  ExtentReports report;

	// I method of ITestListener
	public void onTestStart(ITestResult result) {
		System.out.println("report configuration");
		
		
		test = report.createTest(result.getMethod().getMethodName());
		UtilityClassObject.setTest(test);
		test.log(Status.INFO, result.getMethod().getMethodName() + "==>STARTED<==");

	}

	public void onTestFailure(ITestResult result) {
		// TAKES SCREEN SHOT OF FAILED TEST CASE WITH ACTUAL DATA AND TIME
		String testname = result.getMethod().getMethodName();
		
		TakesScreenshot edriver = (TakesScreenshot) UtilityClassObject.getdriver();
		String filepath = edriver.getScreenshotAs(OutputType.BASE64);
		String time = new Date().toString().replace("", "_").replace(":", "_");
		//null pointer exception if no replace on spacess
		test.addScreenCaptureFromBase64String(filepath, testname + "-" + time);
		
		test.log(Status.PASS, result.getMethod().getMethodName()+"==FAILED==>");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(Status.PASS, result.getMethod().getMethodName()+"==SKIPPED==>");

	}

	public void onStart(ISuite suite) {
		System.out.println("Report configuration");
		
		String time =new Date().toString().replace("","_").replace(":","_");
		
		// spark report config
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/report"+time+".html"); // automaticaly creat folder
		spark.config().setDocumentTitle("CRM test suite result");
		spark.config().setReportName("CRM report");
		spark.config().setTheme(Theme.DARK);

		// system and environment information and create test
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "WIndows-10");
		report.setSystemInfo("BROWSER", "CHROME-100");
	}

	public void onFinish(ISuite suite) {
		System.out.println("Report Backup");
		report.flush();
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("" + result.getMethod().getMethodName() + "===END+==>");
		test.log(Status.PASS, result.getMethod().getMethodName()+"==COMPLETED==>");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}
}
