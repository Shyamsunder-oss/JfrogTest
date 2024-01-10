package com.reusecode.extentreports;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.reusecode.iodata.Input;
import com.reusecode.utilities.StatusHTML;


public class Listener extends ExtentReportsGenerator implements ITestListener 
{	
	public static String _runtimeHTMLReports = System.getProperty("user.dir") + "\\runningstatusreport.html";
	
	public static File fRuntimeHtmlReports = new File(_runtimeHTMLReports);
	
	public static String _startTime = null;
	
	public static boolean invokeReports = true;
	
	/***********************************************************************************************************
	 * Method Name : onTestStart()
	 * 
	 * (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 * 
	 * Description : 
	 * 
	 * 		This method will run before starting the method.
	 * 
	 ***********************************************************************************************************/
	
    public void onTestStart(ITestResult result) 
    {    	
    	System.out.println("========== NEW TEST STARTED ==========");
        System.out.println(Input.timeStamp()+ " : { " + result.getName() + " }");
        _startTime = Input.getTime();
        try 
        {
        	if(invokeReports)
        	{
        		File statusHTMLFile= new File(Listener._runtimeHTMLReports);  
        		statusHTMLFile.delete();    				
        	}
        	StatusHTML.htmlCreate(StatusHTML.htmlTagUpdate(result.getName(), _startTime, "", "Orange", "Processing"), false);
        	if(invokeReports)
        	{
        		Desktop.getDesktop().browse(fRuntimeHtmlReports.toURI());
        		invokeReports = false;
        	}
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
    }
    
    /***********************************************************************************************************
     * Method Name : onTestSuccess()
     * 
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     * 
     * Description : 
     * 
     * 		This method will catch the success status of the test method.
     * 
     ***********************************************************************************************************/
	
    public void onTestSuccess(ITestResult result) 
    {    	
    	System.out.println(Input.timeStamp()+ " : { " + result.getName() + " }\n");
    	System.out.println("========== SUCCESSFULLY FINISHED ==========");
    	if(StatusHTML.getStatusOfHTML() == "Pass with warnings")
    	{
    		try 
    		{
				StatusHTML.htmlCreate(StatusHTML.htmlTagUpdate(result.getName(), _startTime, Input.getTime(), "Orange", "Pass with warnings"), Input.m_Task_Passed);
				StatusHTML.setStatusOfHTML(null);
			} 
    		catch (IOException e) 
    		{				
				e.printStackTrace();
			}
    	}
    	else
    	{
    		try 
    		{
    			StatusHTML.htmlCreate(StatusHTML.htmlTagUpdate(result.getName(), _startTime, Input.getTime(), "MediumSeaGreen", "Pass"), Input.m_Task_Passed);
    		} 
    		catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
    	}        
    }
    
    /***********************************************************************************************************
     * Method Name : onTestFailure()
     * 
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     * 
     * Description : 
     * 
     * 		This method will catch the failure status of the test method.
     * 
     ***********************************************************************************************************/
	
    public void onTestFailure(ITestResult result) 
    {    	
    	System.out.println(Input.timeStamp()+ " : { " + result.getName()+" }\n");
    	System.out.println("========== TEST FAILED ==========");
               
        try 
        {
			StatusHTML.htmlCreate(StatusHTML.htmlTagUpdate(result.getName(), _startTime, Input.getTime(), "Tomato", "Fail"), Input.m_Task_Passed);
		} 
        catch (IOException e1) 
        {
			e1.printStackTrace();
		}
    }
    
    /***********************************************************************************************************
     * Method Name : onTestSkipped()
     * 
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     * 
     * Description : 
     * 
     * 		This method will catch the skipped status of the test method.
     * 
     ***********************************************************************************************************/
	
    public void onTestSkipped(ITestResult result) 
    {    	
    	System.out.println(Input.timeStamp()+ " : { " + result.getName()+" }\n");
    	System.out.println("========== TEST SKIPPED ==========");
        try 
        {
			StatusHTML.htmlCreate(StatusHTML.htmlTagUpdate(result.getName(), _startTime, Input.getTime(), "Violet", "Skip"), Input.m_Task_Passed);
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.LIME));
            //test.skip(result.getThrowable());  
            extent.flush();			
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}        
    }
    
   /***********************************************************************************************************
    * Method Name : onTestFailedButWithinSuccessPercentage()
    * 
    * (non-Javadoc)
    * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
    * 
    * Description : 
    * 
    * 		This method will catch the success percentage of the failure test method.
    * 
    ***********************************************************************************************************/
	
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
    {    	
        System.out.println(Input.timeStamp()+" : Test Failed but within success percentage : { " + result.getName()+" }");
    }
    
    /***********************************************************************************************************
     * Method Name : onStart()
     * 
     * (non-Javadoc)
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     * 
     * Description : 
     * 
     * 		This method will run once before the suite.
     * 
     ***********************************************************************************************************/    
	
    public void onStart(ITestContext context) 
    {    	
        System.out.println(Input.timeStamp()+" : This is on Start method, Output Directory : {" + context.getOutputDirectory()+"}");
    }
    
    /***********************************************************************************************************
     * Method Name : onFinish()
     * 
     * (non-Javadoc)
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     * 
     * Description : 
     * 
     * 		This method will run once after the suite and print the status of the all test methods .
     * 
     ***********************************************************************************************************/  
    
    public void onFinish(ITestContext context) 
    {		
    	System.out.println("========== FINAL RESULTS ==========\n");
        System.out.println(Input.timeStamp() + " : The Total Tests Passed : { " + context.getPassedTests().getAllResults().size() + " }");
        System.out.println(Input.timeStamp() + " : The Total Tests Failed : { " + context.getFailedTests().getAllResults().size() + " }");
        System.out.println(Input.timeStamp() + " : The Total Tests Skipped : { " + context.getSkippedTests().getAllResults().size() + " }");  
    }
    
    /************************************************************************************************************/
}

