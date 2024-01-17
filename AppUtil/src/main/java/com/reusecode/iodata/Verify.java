package com.reusecode.iodata;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.reusecode.utilities.FileHandler;

public class Verify {
	
	/**********************************************************************************************
	 * Method Name : checkIfLabelDisplayed()
	 * 
	 * @param webelement
	 *            : Object Locator.
	 * @return boolean : returns true if web element is displayed, and false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This method will check if the web element is displayed or not.
	 * 
	 **********************************************************************************************/

	public static Boolean checkIfLabelDisplayed(By webelement) 
	{
		Input.m_Task_Result = Input.m_Task_Failed;
		if (webelement != null) 
		{
			Input.m_Task_Result = Input.getDriver().findElement(webelement).isDisplayed();
		}
		return Input.m_Task_Result;
	}

	/**********************************************************************************************
	 * Method Name : checkIfLabelEnabled()
	 * 
	 * @param webelement
	 *            : Object Locator.
	 * @return boolean : return true if web element is displayed, otherwise false.
	 * 
	 * Description : 
	 * 
	 * 		This Method will check if the web element is displayed or not.
	 * 
	 **********************************************************************************************/

	public static Boolean checkIfLabelEnabled(By webelement) 
	{
		Input.m_Task_Result = Input.m_Task_Failed;
		if (webelement != null) 
		{
			Input.m_Task_Result = Input.getDriver().findElement(webelement).isEnabled();
		}
		return Input.m_Task_Result;
	}

	/**********************************************************************************************
	 * Method Name : compareText()
	 * 
	 * @param webelement
	 *            : Object Locator.
	 * @param compareString
	 *            : Message/Text to compare.
	 * @return boolean : returns true if two strings are Equal, otherwise False.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to compare the two strings.
	 * 
	 **********************************************************************************************/
	public static Boolean compareText(By webelement, String compareString) 
	{
		Input.m_Task_Result = Input.m_Task_Failed;
		if (webelement != null) 
		{
			Input.m_Task_Result = Input.getLabelText(webelement).equalsIgnoreCase(compareString);
		}
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : compareText()
	 * 
	 * @param Actual
	 *            : Actual message text
	 * @param Expected
	 *            : expected Message/Text to compare.
	 * @return boolean : returns true if two strings are Equal, otherwise False.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to compare the two strings.
	 * 
	 **********************************************************************************************/
	public static Boolean compareText(String Actual, String Expected) 
	{
		return Actual.equalsIgnoreCase(Expected);
	}

	/**********************************************************************************************
	 * Method Name : TakeScreenshot()
	 * 
	 * @param method_name : Method name to take screenshot.
	 * @throws Exception
	 * 
	 * Description : 
	 * 
	 * 		This Method is used to take the screenshot.
	 * 
	 **********************************************************************************************/
	
	public static String takeScreenshot(String method_name)
	{
		File scrFile = ((TakesScreenshot) Input.getDriver()).getScreenshotAs(OutputType.FILE);
		String _path = FileHandler.checkIfFolderExists_IfNotCreate(Input.m_curDir + "/screenshots/") + method_name + "_" + Input.timeStamp() +
																  "_" + System.currentTimeMillis() + ".png";
		File destination=new File(_path);
		try 
		{
			FileUtils.copyFile(scrFile, destination);
		} 
		catch (IOException e) 
		{
			System.out.println("Capture Failed " + e.getMessage());
		}
		return _path;
	}
	
	/**********************************************************************************************
	 * Method Name : ErrorLabelText()
	 * 
	 * @param label
	 * @param errortext
	 * @param Input.getDriver()
	 * @return boolean, 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to check the error lables.
	 * 
	 **********************************************************************************************/

	public static Boolean errorLabelText(By label,String errortext)
	{
		Input.m_Task_Result = Input.m_Task_Failed;
		if(label != null && errortext != null && Input.getDriver() != null)
		{
			if(checkIfLabelDisplayed(label))
			{	
				Input.m_Task_Result = compareText(label,errortext);
			}
		}
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : checkIfLabelPresent()
	 * 
	 * @param locatorKey
	 * @return boolean, returns true if web element is present, false otherwise.
	 * 
	 * Description: 
	 * 
	 * 		This method is used to check whether the web element is present or not.
	 * 
	 **********************************************************************************************/
	
	public static Boolean checkIfLabelPresent(By locatorKey)
	{
		Input.m_Task_Result = Input.m_Task_Passed;
        if (Input.getDriver().findElements(locatorKey).isEmpty()) 
        {
        	Input.m_Task_Result = Input.m_Task_Failed;
        }
       return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
     * Method Name : checkIfChkBoxSelected()
     * 
     * @param webelement
     *            : Object Locator.
     * @return boolean : return true if Check Box is checked by default, and false otherwise.
     * 
     * Description : 
     * 
     * 		This Method will return the status of the Check Box whether it is selected or not.
     * 
     **********************************************************************************************/

    public static Boolean checkIfChkBoxRdoBtnSelected(By webelement) 
    {
    	Input.m_Task_Result = Input.m_Task_Failed;
        if (webelement != null) 
        {
        	Input.m_Task_Result = Input.getDriver().findElement(webelement).isSelected();
        }
        return Input.m_Task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : checkIfChkBoxEnabled()
     * 
     * @param webelement
     *            : Object Locator.
     * @return boolean : return true if Check Box is enabled by default, and false otherwise.
     * 
     * Description : 
     * 
     *         This Method will return the status of the Check Box whether it is enabled or not.
     * 
     **********************************************************************************************/

    public static Boolean checkIfChkBoxEnabled(By webelement) 
    {
        Input.m_Task_Result = Input.m_Task_Failed;
        if (webelement != null) 
        {
            Input.m_Task_Result = Input.getDriver().findElement(webelement).isEnabled();
        }
        return Input.m_Task_Result;
    }
    /**********************************************************************************************/
}
