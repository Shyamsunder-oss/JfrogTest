
package com.reusecode.extentreports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Date;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.reusecode.iodata.Input;
import com.reusecode.iodata.Verify;
import com.reusecode.utilities.FileHandler;

public class ExtentReportsGenerator implements IRetryAnalyzer{

	public static ExtentHtmlReporter htmlReporter;
	 
    public static ExtentReports extent;
    
    public static ExtentTest test;
    
    public static String _testReport_FileName = null;
    
    public static int retryCounter = 0;
    
	public static int retryLimit = 4;
    
    /*******************************************************************************************************
     * Method Name : setUp()
     * 
     * @throws IOException
     * 
     * Description : 
     * 
     * 		This method will create an extent HTML file for generating the final reports of automation tests.
     * 
     *******************************************************************************************************/
    
    @BeforeSuite
    public void setUp() throws IOException
    {    	
    	Date d = new Date();
    	_testReport_FileName = "Extent_" + d.toString().replace(":", "_") + ".html";
    	_testReport_FileName = _testReport_FileName.toString().replace(" ", "_");
        htmlReporter = new ExtentHtmlReporter(FileHandler.checkIfFolderExists_IfNotCreate(System.getProperty("user.dir") + "/reports/") + _testReport_FileName);
        System.out.println(Input.timeStamp() + " : TEST_STATUS_HTML : " + _testReport_FileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        InetAddress addr;
	    addr = InetAddress.getLocalHost();
        
        extent.setSystemInfo("OS", System.getProperty("os.name").toLowerCase());
        extent.setSystemInfo("Host Name", addr.getHostName());
        extent.setSystemInfo("Environment", "QA Test Automation");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));        
        
        htmlReporter.config().setDocumentTitle("Automation Test Reports");
        htmlReporter.config().setReportName("AUTOMATION TEST REPORTS");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setEncoding("utf-8");
    }
    
    /*******************************************************************************************************
     * Method Name : startBefore()
     * 
     * @param method
     * 
     * Description :
     *  
     *  	This method will run before the testMethod, and get the method name to run next as per the priority.
     * 
     *******************************************************************************************************/
    
    @BeforeMethod
    public void startBefore(Method method)
    {
    	test = extent.createTest(method.getName());	
    }
    
    /*******************************************************************************************************
     * Method Name : getResult()
     * 
     * @param result
     * @throws Exception
     * 
     * Description : 
     * 
     * 		This method will catch the test status after completion of test method execution.		
     * 
     *******************************************************************************************************/
    
    @AfterMethod
    public void getResult(ITestResult result) throws Exception
    {    	
    	if(result.getStatus() == ITestResult.FAILURE)
        {
        	String screenShotPath = Verify.takeScreenshot(result.getName());
        	test.fail("Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
        	test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED", ExtentColor.RED));
        	/*Debug*/
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
            extent.flush();
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
        	test.log(Status.PASS, MarkupHelper.createLabel(result.getMethod().getMethodName() + " Test Case PASSED", ExtentColor.GREEN));
        	extent.flush();
        }
        else
        {
        	test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.YELLOW));
            test.skip(result.getThrowable());  
            //test.skip("Test Skipped");
            extent.flush();        	
        }
    }
    
    /*******************************************************************************************************
     * Method Name : tearDown()
     * 
     * Description : 
     * 		
     * 		This method will flush and save the reports after the execution of test suite.
     * 
     *******************************************************************************************************/
    
    @AfterSuite
    public void tearDown()
    {
        extent.flush();
    }
    
    /**********************************************************************************************
	 * Method Name : reportsHeaderBrowsersDetails()
	 * 
	 * Description : 
	 * 
	 * 		This method is used to print the browser and driver details to extent reports.
	 * 
	 **********************************************************************************************/
	 
    public static void reportsHeaderBrowsersDetails(String projectName, String TP_Number)
    {
    	if(Input.m_browserName.equalsIgnoreCase("Chrome") || Input.m_browserName.equalsIgnoreCase("Google Chrome"))
		{
    		htmlReporter.config().setReportName(projectName+" "+TP_Number+" Test Automation. ====== Browser Name : "+Input.m_browserName+" ====== Browser Version : "+
						Input.m_browser_Version+" ====== Driver Version : "+Input.driverVersion+" ======");
		
		}
		else if(Input.m_browserName.equalsIgnoreCase("Microsoft Edge") || Input.m_browserName.equalsIgnoreCase("Edge"))
		{
			htmlReporter.config().setReportName(projectName+" "+TP_Number+" Test Automation. ====== Browser Name : "+Input.m_browserName+" ======");
		}
		else if(Input.m_browserName.equalsIgnoreCase("Internet Explorer") || Input.m_browserName.equalsIgnoreCase("IE"))
		{
			htmlReporter.config().setReportName(projectName+" "+TP_Number+" Test Automation. ====== Browser Name : "+Input.m_browserName+" ======");
		}
		else if(Input.m_browserName.equalsIgnoreCase("Mozilla Fire Fox") || Input.m_browserName.equalsIgnoreCase("Fire Fox"))
		{
			htmlReporter.config().setReportName(projectName+" "+TP_Number+" Test Automation. ====== Browser Name : "+Input.m_browserName+" ======");
		}
		else
		{
			/*Do Nothing.*/
		}
    }
    
    /**********************************************************************************************
     * (non-Javadoc)
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 * 
	 * This method decides how many times a test needs to be rerun.
	 * TestNg will call this method every time a test fails. So we 
	 * can put some code in here to decide when to rerun the test.
	 * 
	 * Note: This method will return true if a tests needs to be retried
	 * and false it not.
	 *
     **********************************************************************************************/

	public boolean retry(ITestResult result) 
	{
		if(retryCounter < retryLimit)
		{
			retryCounter++;
			return true;
		}
		return false;
	}
    
    /*******************************************************************************************************/
}