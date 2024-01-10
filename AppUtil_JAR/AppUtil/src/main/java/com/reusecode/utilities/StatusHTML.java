package com.reusecode.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.reusecode.extentreports.Listener;
import com.reusecode.iodata.Input;

public class StatusHTML{
	
	public static FileWriter m_fsWriter = null;
	
	public static String m_pass_With_Warn = null;
	
	public static String m_htmlText = "<!DOCTYPE html>\n<html>\n<head><meta http-equiv=\"refresh\" content=\"3\"></head>\n<style>\ntable \n{\nfont-family: arial, sans-serif;border-collapse: collapse;width: 100%;\n}\n" +
									
									"td, th \n{\nborder: 1px solid #dddddd;text-align: left;padding: 8px;\n}\n" + "tr:nth-child(even) \n{\nbackground-color: #dddddd;\n}\n</style>\n</head>\n<body>\n" +
									
									"<h2 style=\"background-color: pink;\">Automation Test Results</h2>\n<table>\n" + "\n<tr>\n" +
									
									"<th style=\"background-color: steelblue;\">Test Case Name</th>\n" +
									
									"<th style=\"background-color: steelblue;\">Start Time</th>\n" +
									
									"<th style=\"background-color: steelblue;\">End Time</th>\n" +
									
									"<th style=\"background-color: steelblue;\">Test Status</th>\n</tr>\n";
	
	/***************************************************************************************************************
	 * Method Name : htmlCreate()
	 * 
	 * @param DataFromListener
	 * @param testFlag
	 * @throws IOException
	 * @return void
	 * 
	 * Description : 
	 * 
	 * 		This method is used to create the run time HTML status report.
	 * 
	 ***************************************************************************************************************/
	
	public static void htmlCreate(String DataFromListener, boolean testFlag) throws IOException
	{		
		int iCount = 0;
		if(iCount == 0)
		{
			File f= new File(Listener._runtimeHTMLReports);  
			if(f.delete())
			{
				//do nothing.
			}  			
			m_fsWriter = new FileWriter(Listener._runtimeHTMLReports);
		}
		if(testFlag == Input.m_Task_Passed)
		{
			m_htmlText = m_htmlText.concat(DataFromListener);
			try 
			{
				m_fsWriter.write(m_htmlText);
			    m_fsWriter.close();
			    //System.out.println(TimeStamp()+ " : Successfully wrote to the HTML file.\n");
			    iCount++;
			} 
			catch (IOException e) 
			{
			    System.out.println(Input.timeStamp() + " : While writing to an HTML Reports an error occurred.\n");
			    e.printStackTrace();
			}
		}
		else
		{
			try 
			{
			    m_fsWriter.write(m_htmlText + DataFromListener);
			    m_fsWriter.close();
			    //System.out.println(TimeStamp()+ " : "+"Successfully wrote to the HTML file.");
			    iCount++;
			} 
			catch (IOException e) 
			{
				System.out.println(Input.timeStamp() + " : " + "While writing to HTML Reports an error occurred.");
			    e.printStackTrace();
			}
		}
	}
	
	/***************************************************************************************************************
	 * Method Name : htmlTagUpdate()
	 * 
	 * @param testcasename
	 * @param starttime
	 * @param endtime
	 * @param color
	 * @param status
	 * @return string
	 * 
	 * Description : 
	 * 
	 * 		This method will used to update the tag value of the HTML file and return the updated html.
	 * 
	 ***************************************************************************************************************/
	
	 public static String htmlTagUpdate(String testcasename, String starttime, String endtime, String color, String status)
	 {
		 return "<tr>\n" +
				 	"<td>" + testcasename + "</td>\n" +
	        		"<td>" + starttime + "</td>\n" +
	    			"<td>" + endtime + "</td>\n" +
	    			"<td style=\"background-color: " + color+";\">\n" +
	    				"<p>\n" +
	        				"<a href=" + Input.m_curDir+"\\reports\\" + Listener._testReport_FileName+">" + status + "</a>\n" +
	        			"</p>\n</td>\n" +
	        		"</tr>\n";
	 }
	 
	 /***************************************************************************************************************
	  * Method Name : setStatusOfHTML()
	  * 
	  * @param pass_with_warn
	  * @return void
	  * 
	  * Description : 
	  * 
	  * 	This is used to customize the status of the test method whether it is passed or passed with warnings. 
	  * 
	  ***************************************************************************************************************/
	
	 public static void setStatusOfHTML(String pass_with_warn)
	 {
		 m_pass_With_Warn = pass_with_warn;
	 }
	 
	 /***************************************************************************************************************
	  * Method Name : getStatusOfHTML()
	  * 
	  * @return string
	  * 
	  * Description : 
	  * 
	  * 	This is used to get the customized status of the test method whether it is passed or passed with warnings. 
	  * 
	  ***************************************************************************************************************/
	 public static String getStatusOfHTML()
	 {	
		 return m_pass_With_Warn;
	 }
	 
	 /***************************************************************************************************************
	  * 
	  * @param infoTitle
	  * @return
	 * @throws IOException 
	  * 
	  ***************************************************************************************************************/
	 
	 public static void htmlTitleTag(String infoTitle) throws IOException
	 {		 
		 htmlCreate("<tr><th style=\"background-color: MediumSeaGreen;\">" + infoTitle + "</th>\n", Input.m_Task_Passed); 
	 }
	 
	/***************************************************************************************************************/
}
