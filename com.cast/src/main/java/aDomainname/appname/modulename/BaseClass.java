package aDomainname.appname.modulename;
/** 
 * Contains Configuration Annotation
 * 
 * @author dielan 
 */

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.listnerutility.home;

import co.cast.DataBaseUtitlity.DatabaseUtility;
import co.cast.generic.FileUtility.ExcelUtility;
import co.cast.generic.FileUtility.FileUtility;
import co.cast.generic.FileUtility.JsonUtility;
import co.cast.generic.WebDriverUtitlity.JavaUtility;
import co.cast.generic.WebDriverUtitlity.UtilityClassObject;
import co.cast.generic.WebDriverUtitlity.WebDriverUtility;

public class BaseClass {
	
	/*creating object for utility*/
public 	DatabaseUtility dlib=new DatabaseUtility();
public 	ExcelUtility elib=new ExcelUtility();
public 	FileUtility flib=new FileUtility();
public 	JsonUtility jlib=new JsonUtility();
public 	JavaUtility jvlib=new JavaUtility();
public 	WebDriverUtility wlib=new WebDriverUtility();
public WebDriver driver;
	@BeforeSuite
	public void configBs() throws Throwable {
		System.out.println("======database connection ======= b s")	;
		//dlib.getDbConnection();
		}
	
	@BeforeClass
	public void configBc() throws Throwable {
		System.out.println("=====launch browser==== b c");
	//String BROWSER=	flib.getDataFromPropertiesFile("browser");
		String BROWSER=System.getProperty("browser",flib.getDataFromPropertiesFile("url")); //if cmd is null property file
		

	if(BROWSER.equals("chrome")) {
		driver=new ChromeDriver();
	}else if(BROWSER.equals("firefox")) {
		driver=new ChromeDriver();
	}else if(BROWSER.equals("edge")) {
		driver=new EdgeDriver();
	}else {
		driver=new ChromeDriver();
	}
	UtilityClassObject.setDriver(driver);
	}
	String URL;
	@BeforeMethod
	public void configBm() throws Exception {
		System.out.println("====login to application === b m ");
		wlib.WaitForPageToLoad(driver);
		//String url=flib.getDataFromProperties("url");
	//String un=flib.getDataFromPropertiesFile("username");
	//String pwd=flib.getDataFromPropertiesFile("password");
		 URL=System.getProperty("url",flib.getDataFromPropertiesFile("url"));
	String USERNAME=System.getProperty("username",flib.getDataFromPropertiesFile("username"));
	String PASSWORD=System.getProperty("password",flib.getDataFromPropertiesFile("password"));
	
		driver.get(URL);
		Login lo=new Login(driver);
		lo.loginToApp(USERNAME, PASSWORD);
		

	}
	@AfterMethod
	public void configAm() throws SQLException {
		System.out.println("===logout== a m ");
	home ho=new home(driver);
	     ho.logout();
	}
	@AfterClass
	public void configAc() {
		System.out.println("====close browser== a c ");
	
			driver.close();

	}
	@AfterSuite
	public void configAs() throws Exception {
		System.out.println("==close database connection== a s ");
		dlib.closeDbconnection();
		

	}	
}
