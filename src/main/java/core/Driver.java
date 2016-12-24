package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.propertyfinder.utils.WebDriverListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners({com.propertyfinder.utils.WebDriverListener.class})
public class Driver {

	public static WebDriver driver;
	public static EventFiringWebDriver eventFiringWebDriver ;
	private static String chromeDriverPath = null;;
	private static String internetExplorerDriverPath = "D:\\iedriver\\";
	public final static Logger logger = Logger.getLogger(Driver.class.getName());
	public static ExtentReports extent  = null;
	public static ExtentTest test = null;
	public static String description = null;

	/**
	 * @param browserType
	 * @param appURL
	 * @throws UnknownHostException
	 */
	private static void setDriver(String browserType, String appURL) throws UnknownHostException {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		case "ie":
			driver = initInternetExplorerDriver(appURL);
			break;
		default:
			logger.info("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			logger.info("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
		eventFiringWebDriver = new EventFiringWebDriver(driver);
		eventFiringWebDriver.register(new WebDriverListener());
		extent =  new ExtentReports(System.getProperty("user.dir")+"\\ExtentReport.html", true);
		extent.config().documentTitle("Automation Report").reportName("Regression").reportHeadline("");
		extent.addSystemInfo("Selenium Version", "2.46");
		extent.addSystemInfo("Environment", "QA");
		extent.addSystemInfo("User Name", System.getProperty("user.name"));
		extent.addSystemInfo("OS", System.getProperty("os.name"));
		extent.addSystemInfo("Java Version", System.getProperty("java.version"));
		extent.addSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
		String css = "body, .test .right span, .collapsible-header { background: #333; color: #fff; }" +
                "nav, .tab, .card-panel { background: #000 !important; }" +
                "table { border: 1px solid #555 !important; }" +
                "pre { background: #333; border: 1px solid #777 !important; color: #eee !important; }" +
                ".select-dropdown { background: #333; border-bottom: 1px solid #777 !important; }" +
                ".select-dropdown li:hover, .select-dropdown li.active { background: #555; }" +
                "table.bordered > thead > tr, table.bordered > tbody > tr, th, td { border-bottom: 1px solid #555 !important; }" +
                "th, td, .test-name, .test-desc, .test .right span { color: #fff !important; }" +
                ".test-body .collapsible > li { border: 1px solid #777; }";

		extent.config().insertCustomStyles(css);

	}

	/**
	 * @param appURL
	 * @return
	 */
	private static WebDriver initChromeDriver(String appURL) {
		logger.info("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", chromeDriverPath
				+ "\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		logger.info("Launching Firefox browser..");
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setAcceptUntrustedCertificates(true); 
		driver = new FirefoxDriver(firefoxProfile);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	/**
	 * @param appURL
	 * @return
	 */
	private static WebDriver initInternetExplorerDriver(String appURL) {	
		logger.info("Launching internet explorer browser..");
		System.setProperty("webdriver.ie.driver", internetExplorerDriverPath +"IEDriverServer.exe");
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		driver = new InternetExplorerDriver(ieCapabilities);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}
	
	public static void main(String[] args) throws IOException, BiffException{
		Properties prop = new Properties();
		final String dir = System.getProperty("user.dir");
		prop.load(Driver.class.getClassLoader().getResourceAsStream("propertyfinder.properties"));
		String browserType = prop.getProperty("browserType");
		System.out.println("browserType====="+browserType);
		String appURL  = prop.getProperty("appURL");
		System.out.println("appURL====="+appURL);
		Path path = Paths.get(dir,"src\\main\\java\\core");
		chromeDriverPath  = path.toString();
		setDriver(browserType.trim(), appURL.trim());
		executeTestSuite();
	}
	

	private static void executeTestSuite() throws BiffException, IOException{
		TestNG myTestng = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		final String dir = System.getProperty("user.dir");
		Path path = Paths.get(dir, "src\\main\\resources\\", "controller.xlsx");
		FileInputStream fs = new FileInputStream(path.toString());
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fs);
		XSSFSheet sheet = myWorkBook.getSheet("Controller");
		for (int i = 1; i <=sheet.getLastRowNum(); i++) {
			String testSuiteName = sheet.getRow(i).getCell(1).toString();
			String runMode = sheet.getRow(i).getCell(2).toString();
			if(runMode.equalsIgnoreCase("Y")){
				XmlSuite suite  = createXMLSuite(myWorkBook,testSuiteName);
				if(suite != null){
					suites.add(suite);
				}			
			}
		}
		myTestng.setXmlSuites(suites);
		myTestng.run();
	}
	
	private static XmlSuite createXMLSuite(XSSFWorkbook workbook, String sheetName){
		XSSFSheet sheet = workbook.getSheet(sheetName);
		XmlSuite xmlSuite = new XmlSuite();
		xmlSuite.setName(sheetName);
		XmlTest xmltest= new XmlTest();
		xmltest.setName(sheetName+"_Test");
		xmltest.setSuite(xmlSuite);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			String testCaseName  = sheet.getRow(i).getCell(1).toString();
			String runMode = sheet.getRow(i).getCell(2).toString();
			if(runMode.equalsIgnoreCase("Y")){
				classes.add(new XmlClass("com.propertyfinder.testcases."+"findproperty"));
			}
		}
		xmltest.setXmlClasses(classes);
		xmlSuite.addTest(xmltest);
		return xmlSuite;
		
	}



	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String takeScreenShot(String fileName) throws IOException{
		File scrFile = ((TakesScreenshot)eventFiringWebDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("ScreenShots/"+fileName+".png"));
		return fileName+".png";
	}
}