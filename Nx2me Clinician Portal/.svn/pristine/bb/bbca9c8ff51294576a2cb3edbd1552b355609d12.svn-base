
package com.cs.ns.ClinicianPortal_Automation.Qa.TP2186;

import java.awt.Desktop;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.cs.ns.ClinicianPortal_Automation.Base_class.BaseClass;
import com.cs.ns.ClinicianPortal_Automation.utilities.ReusableMethods;
import com.reusecode.extentreports.Listener;
import com.reusecode.iodata.Input;
import com.reusecode.iodata.Verify;
import com.reusecode.utilities.Excel;
import com.reusecode.utilities.StatusHTML;
import com.reusecode.utilities.TestTool;
import com.reusecode.utilities.XMLHandler;

@Listeners(com.reusecode.extentreports.Listener.class)
public class TP2186_FlowsheetStatistics extends ReusableMethods 
{
	/******************************************************************************************************
	 * Before class : Dashboard_Page_Setup()
	 * 
	 * This will kill the previous web driver instances and TestTool Data setup. 
	 * 
	 * ****************************************************************************************************/
	
	@BeforeClass
	public static void TP2186_Flowsheet_Verification_setup() throws InterruptedException, IOException
	{		
		TestTool.setTestToolDBServer(g_Testtool_DB_Server);//Connectivity.TestToolGui v2.5.1.547
		TestTool.setTestToolPDMPWEBServer(g_Testtool_PDMP_Server);
		TestTool.set_VPNCredentials(g_VPN_ID, g_VPN_Password);
		TestTool.setTestToolVersion(g_Testtool_Version);
		Input.driverInstanceKillProcess();
		testtool_DataConfig("TP2186",null,null);
		Excel.writeToExcel(cUI_FUNCTIONALITY_DATA, cVPN_USER_ID_ROW, cVPN_USER_ID_COL, strExcelPath, "example@nxstage.com");
	    Excel.writeToExcel(cUI_FUNCTIONALITY_DATA, cVPN_PASSWORD_ROW, cVPN_PASSWORD_COL, strExcelPath, "Dontknow12!");	    
	}
	
	/******************************************************************************************************
	 * Test Method Name : Flowsheet_Statistics()
	 * 
	 * Description : 
	 * 
	 * 		This method will perform the flowsheets pannel statistics sections.
	 * 
	 ******************************************************************************************************/
	
	@Test(priority=0, retryAnalyzer=BaseClass.class)
	public static void Flowsheet_Statistics_Test() throws InterruptedException, IOException
	{  		
		  g_Test_Task_Result = g_Test_Task_Passed;
		  StatusHTML.htmlTitleTag("Clinician Portal FlowsheetStatistics Verification...");
		  System.out.println("========== "+Input.getDateTime()+" ==========");
		  if(landingToPortal())
		  {
			  reportsHeaderBrowsersDetails("Nx2me Clinician Portal","TP2186");
			  if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
			  {				  				  				  
				  archieve_All_Flowsheets(_dashboard_tab, cFOURTY_DAYS);
				  test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the Flowsheet Statistics section displays 3 panels...", ExtentColor.BLUE));
				  for(int i = 0; i<Flowsheet_Statistics_Panel.length; i++)
				  {
					  test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the : '"+_Flowsheet_Statistics_Panel_Text[i]+ " '", ExtentColor.BLUE));					  
					  if(Input.isElementPresent(Flowsheet_Statistics_Panel[i]))
					  {
						  TimeUnit.SECONDS.sleep(1/2);
						  test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Flowsheet_Statistics_Panel[i]) + " : is Verified .",ExtentColor.GREEN));
					  }
					  else
					  {
						  test.log(Status.INFO, MarkupHelper.createLabel("Unable to find the : ' "+_Flowsheet_Statistics_Panel_Text[i] +" ' label.", ExtentColor.RED));
						  g_Test_Task_Result = g_Test_Task_Failed; 
					  }
				  }
				  test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Statistics sections verification completed.", ExtentColor.GREEN));
				  test.log(Status.INFO, MarkupHelper.createLabel("Going to perform the flowsheet status verification...", ExtentColor.BLUE));
				  if(!flowsheet_Status_Verification())
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet status verification is failed." , ExtentColor.RED));
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }				  
				  portal_Signout();  
			  }
			  else
			  {
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
		  }		  
		  else
		  {
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }
		  
		  if(g_Test_Task_Result)
		  {
			  Assert.assertTrue(true);
		  }
		  else
		  {
			  Assert.assertTrue(false);
		  }	
	  }
	
	/******************************************************************************************************
	 * Test Method Name : Upload_50_Flwosheets_And_Do_Flowsheet_Statistics_Test()
	 * 
	 * Description : 
	 * 
	 * 		This method will upload the 50 flowsheets to the portal app using the test tool and do the flowsheet statistics test 
	 * 		(Changing flowsheet status, archive functionality).
	 * 
	 ******************************************************************************************************/
	
	  @Test(priority=1, retryAnalyzer=BaseClass.class)
	  public static void Upload_50_Flwosheets_And_Do_Flowsheet_Statistics_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	  {		  			
		  int[] _flowsheet_Number = {3, 3, 3, 1};
		  int[] _archieve_Flowsheet_Number = {4, 3, 2, 1};
		  int i = 0;
		  int j = 0;
		  
		  testtool_DataConfig("TP2186","CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "50");
		 test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 50 flowsheets to the portal...", ExtentColor.BLUE));						  
		  
		 if(TestTool.testtoolTaskExec(g_testtoolStandardDir+ "TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
		 {
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 50 Flowsheet Task is Successful.", ExtentColor.GREEN)); 			  	  			  
			  if(landingToPortal())
			  {
				  if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
				  {
					  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFOURTY_DAYS));
					  Input.clickOn(view, click);
					  g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);					  					 					  
					  if(!flowsheet_Status_Verification())
					  {
						  g_Test_Task_Result = g_Test_Task_Failed; 
					  }

					  for( i = 0; i < FS_ACTNS.length - cONE; i++)
					  {
						  test.log(Status.INFO, MarkupHelper.createLabel("Going to change ' " + _flowsheet_Number[i]+ " ' Flowsheet's status as ' "+ _Arr_FS_STAT_Text[cZERO] +" ' to ' "+_Arr_FS_STAT_Text[i + cONE]+" '", ExtentColor.BLUE));						  
						  for(j = 0; j < _flowsheet_Number[i]; j++)
						  {
							  if(searchForFlowsheetByAction(dashboardTab_total_flowsheets, _Arr_FS_STAT_Text[cZERO]))
							  {
								  flowsheet_Actions(null, FS_status, FS_ACTNS[i + cONE]);
								  test.log(Status.INFO, MarkupHelper.createLabel("Changed the ' " + _flowsheet_Number[i]+ " ' Flowsheet's status as ' "+ _Arr_FS_STAT_Text[cZERO] +" ' to ' "+_Arr_FS_STAT_Text[i + cONE]+" '", ExtentColor.GREEN));						  								  
							  } 
						  }						  
					  }
					  if(!flowsheet_Status_Verification()){
						  test.log(Status.INFO, MarkupHelper.createLabel("Verification of the Flowsheet Status panel is failed.", ExtentColor.BLUE));	
						  g_Test_Task_Result = g_Test_Task_Failed; 
					  }					  					  
					  
					  for(i = 0; i < FS_ACTNS.length- 1; i++)
					  {
						  test.log(Status.INFO, MarkupHelper.createLabel("Going to archive ' " + _archieve_Flowsheet_Number[i]+" ' Flowsheet's status as ' "+_Arr_FS_STAT_Text[i]+" ' ", ExtentColor.BLUE));							 
						  for(j = 0; j < _archieve_Flowsheet_Number[i]; j++)
						  {
							   if(searchForFlowsheetByAction(dashboardTab_total_flowsheets, _Arr_FS_STAT_Text[i]))
							  {
								  flowsheet_Actions(FS_archive, FS_archive, FS_archive_Yes);
								  test.log(Status.INFO, MarkupHelper.createLabel("Archived the ' " + _archieve_Flowsheet_Number[i]+" ' Flowsheet's status as ' "+_Arr_FS_STAT_Text[i]+" ' ", ExtentColor.GREEN));							 									 
							  } 
							  else
							  {
								  test.log(Status.ERROR, MarkupHelper.createLabel("Archive Task Failed.", ExtentColor.RED));
								  g_Test_Task_Result = g_Test_Task_Failed; 
							  }
						  }						  
					  }
					  if(!flowsheet_Status_Verification())
					  {
						  test.log(Status.ERROR, MarkupHelper.createLabel("The flowsheet status verification is failed.", ExtentColor.RED));
						  g_Test_Task_Result = g_Test_Task_Failed; 
					  }					  					  				
					  archieve_All_Flowsheets(_dashboard_tab, cFOURTY_DAYS);
					  portal_Signout();
				  }
			  }
			  else
			  {
				  portal_Signout();
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
		  }
		  else
		  {
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 50 Flowsheet Task Failed.", ExtentColor.RED));
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }		  
		  
		  if(g_Test_Task_Result)
		  {
			  Assert.assertTrue(true);
		  }
		  else
		  {
			  Assert.assertTrue(false);
		  }
	  }
	  
	  /******************************************************************************************************
	   * Test Method Name : Upload_10_Unconfirmed_And_5_Confirmed_Flowhseets_And_Do_Verification() 
	   * 
	   * Description : 
	   * 
	   * 		This method will perform below testtool activities. 
	   * 		
	   * 		1. Upload 10 Unconfirmed Flowsheets to portal. 
	   * 		2. Upload 10 confirmed Flowsheets to portal. 
	   * 		3. Upload 5 new Flowsheets to portal.
	   * 
	   * 		Verification:
	   * 
	   * 		1. Unconfirmed, confirmed flowsheets status.
	   * 		2. Flowsheet statistics panel verifictaion. 
	   * 
	   ******************************************************************************************************/
	  
	  @Test(priority=2, retryAnalyzer=BaseClass.class)
	  public static void Upload_10_Unconfirmed_And_5_Confirmed_Flowhseets_And_Do_Verification_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	  {  
		  g_Test_Task_Result = g_Test_Task_Passed;
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to perform the uploading of 10 unconfirmed Flowsheet Task...", ExtentColor.BLUE)); 
		  testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "10");
		  
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_10_Unconfirmed_fs_Files.cfg", cTWO))
		  {			  
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 10 Unconfirmed Flowsheet Task Successful.", ExtentColor.GREEN)); 
			  System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed");
			  
			  if(landingToPortal())
			  {  
				  if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
				  {					 
					  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFOURTY_DAYS));
					  TimeUnit.SECONDS.sleep(1);
					  Input.clickOn(view, click);
					  Input.clickOn(patients_Tab, click);
					  
					  if(Input.getLabelText(patientsPage_total_Records).contains(_no_data_available))
					  {
						  System.out.println(_no_data_available);
						  //do nothing.
					  }
					  else
					  {						  
						  if(search_Patient(_patients_tab, g_refPatientName1))
						  {
							  test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Patient Status from 'Confirmed' to 'Unconfirmed'...", ExtentColor.BLUE));
							  Input.dropDown(patient_confirmed, "Unconfirmed");
							  test.log(Status.INFO, MarkupHelper.createLabel("Changed the Patient Status from 'Confirmed' to 'Unconfirmed'.", ExtentColor.GREEN));
							  Input.clickOn(patients_Tab, click);
							  test.log(Status.INFO, MarkupHelper.createLabel("Going to print Total unconfirmed flowsheets...", ExtentColor.BLUE));
							  test.log(Status.INFO, MarkupHelper.createLabel("Total unconfirmed flowsheets are ' "+ totalFlowsheetsCountS(patientsPage_total_Records) +" '", ExtentColor.GREEN));
							  Input.clickOn(dashboard_Tab, click);
							  
							  if(!flowsheet_Status_Verification())
							  {
								  test.log(Status.ERROR, MarkupHelper.createLabel("The flowsheet status verification is failed.", ExtentColor.RED));
								  g_Test_Task_Result = g_Test_Task_Failed; 
							  }
						  }
						  else
						  {
							  test.log(Status.INFO, MarkupHelper.createLabel("Unable to find  the Patient ' "+ g_refPatientName1 +" '", ExtentColor.GREEN));
						  }														
					  }					  
					  portal_Signout();					  
				  }
				  else
				  {
					  portal_Signout();	
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }			  
			  }
			  else
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Login to portal is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed;  
			  } 				
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 10 Unconfirmed Flowsheet Task failed.", ExtentColor.RED)); 
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }
		  
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 10 new Confirmed Flowsheets with Treatment date set to the date of test execution...", ExtentColor.BLUE)); 
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "10" );
		  
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
		  {	
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "50" );			
			  System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed for upload 10 Confirmed flowsheets");
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 10 new Confirmed Flowsheets with Treatment date set to the date of test execution is successful.", ExtentColor.GREEN)); 		 
			  landingToPortal();
			  Input.clickOn(dashboard_Tab, click);
			  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFOURTY_DAYS));
			  TimeUnit.SECONDS.sleep(1);
			  Input.clickOn(view, click);
			  if(!flowsheet_Statistics_Verification())
			  {
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  portal_Signout();
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 10 new Confirmed Flowsheets with Treatment date set to the date of test execution is failed.", ExtentColor.RED)); 		 				 
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }

		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 5 Flowsheets to PN1...", ExtentColor.BLUE)); 
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_centerID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN1_patientID);		  
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "5" );
		  
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
		  {	
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag,g_numberFlowsheets_Tag, "50" );			
			  System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed for upload 10 Confirmed flowsheets");
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 5 Flowsheets to PN1 is successful.", ExtentColor.GREEN)); 
			  landingToPortal();
			  
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("The flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  portal_Signout();
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 5 Flowsheets to PN1 is failed.", ExtentColor.RED)); 
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }		  
		  
		  if(g_Test_Task_Result)
		  {
			  Assert.assertTrue(true);
		  }
		  else
		  {
			  Assert.assertTrue(false);
		  }
	  }
	  
	  /******************************************************************************************************
	   * Test Method Name : Alerts_Test_For_atert0_1_3() 
	   * 
	   * Description : 
	   * 
	   * 		This method will perform below testtool activities.
	   * 		
	   * 		1. Alert 0 generation. 
	   * 		2. Alert 1 generation.  
	   * 		3. Alert 3 generation. 
	   * 		4. Upload an unconfirmed flowsheet to PN2.
	   * 		5. Upload an unconfirmed flowsheet to PN1
	   * 
	   * 		Verification:
	   * 
	   * 		1. flowsheet Statistics Verification
	   * 		2. Alerts settings and change settings.
	   * 
	   ******************************************************************************************************/
	  
	  @Test(priority=3, retryAnalyzer=BaseClass.class)
	  public static void Alerts_Test_For_atert0_1_3_Test() throws InterruptedException, TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	  {  
		  /*
		   * Set Alerts
		   */	  
		  g_Test_Task_Result = g_Test_Task_Passed;
		  if(landingToPortal())
		  {
			  if(g_refPatientName1 == null)
			  {
				  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFOURTY_DAYS));
				  Input.clickOn(view, click);				  
				  g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);
			  }
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to set the patient alerts...", ExtentColor.BLUE));
			  if(search_Patient(_patients_tab, g_refPatientName1))
			  {
				  Input.clickOn(alertsCriteria_Tab, click);
				  if(enter_Alerts_Data(_alertsArr))
				  {
					  test.log(Status.INFO, MarkupHelper.createLabel("Alerts are Updated successfully.", ExtentColor.GREEN));
				  }
				  else
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Alerts are not Updated successfully.", ExtentColor.RED));
				  }
			  }
			  portal_Signout();
		  }
		  
		  /*
		   * Upload a Flowsheet that would generate 0 alerts for PN1 by setting alert values within range.
		   */
		  
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 3 Flowsheets that would generate 0 alerts for PN1 by setting alert values within range...", ExtentColor.BLUE));
		  
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_0.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_centerID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_0.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN1_patientID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_0.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "3" );
		  
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_Alert0.cfg", cTWO))
		  {				
			  System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed for upload 3 flowsheets that would generate 0 alerts");
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 3 Flowsheets that would generate 0 alerts for PN1 by setting alert values within range is successful.", ExtentColor.BLUE));				 
			  landingToPortal();
			  
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to adjust the date filter to 10 days...", ExtentColor.BLUE)); 
			  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTEN_DAYS));
			  Input.clickOn(view, click);
			  test.log(Status.INFO, MarkupHelper.createLabel("The date filter is set to 10 days.", ExtentColor.GREEN)); 
			  portal_Signout();
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 3 Flowsheets that would generate 0 alerts for PN1 by setting alert values within range is failed.", ExtentColor.RED));				
			  g_Test_Task_Result = g_Test_Task_Failed; 	  
		  }
		  
		  /*
		   * Upload Flowsheet using file CreateFlowsheet1.xml to generate 1 alert for PN2.
		   */
		  
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 3 Flowsheets using file CreateFlowsheet_to_generate_alert_1.xml to generate 1 alert for PN1...", ExtentColor.BLUE));
		  
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_1.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_centerID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_1.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN1_patientID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_1.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "3" );
		  
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_Alert1.cfg", cTWO))
		  {				
			  System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed for upload 3 flowsheets that would generate 1 alerts");
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL :Uploading of 3 Flowsheets using file CreateFlowsheet_to_generate_alert_1.xml to generate 1 alert for PN1 is successful.", ExtentColor.GREEN));
				 
			  landingToPortal();			  			  
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  test.log(Status.INFO, MarkupHelper.createLabel("Adjust date filter to 10 days.", ExtentColor.BLUE)); 
			  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTEN_DAYS));
			  Input.clickOn(view, click);
			  portal_Signout();
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL :Uploading of 3 Flowsheets using file CreateFlowsheet_to_generate_alert_1.xml to generate 1 alert for PN1 is failed.", ExtentColor.RED));				
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }

		  /*
		   * Upload Flowsheet using file CreateFlowsheet2.xml to generate 3 alerts for PN3, with date of treatment = date of execution – 29 days.
		   */
		  
		  testtool_DataConfig("TP2186", "CreateFlowsheet_to_generate_alert_3.xml", g_createFlowsheetV2_Tag);
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 3 Flowsheets using file CreateFlowsheet_to_generate_alert_3.xml to generate 3 alerts for PN1, with date of treatment = date of execution - 29 days...", ExtentColor.BLUE)); 
		 
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_3.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_centerID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_3.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN1_patientID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_3.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "3" );
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_3.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, Input.substract_DaysFromTodaysDate(29));
		  
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_Alert3.cfg", cTWO))
		  {	
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheet_to_generate_alert_3.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, "${NowUtc}");		  		
			  System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed for upload 3 flowsheets that would generate 3 alerts");
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 3 Flowsheets using file CreateFlowsheet_to_generate_alert_3.xml to generate 3 alerts for PN1, with date of treatment = date of execution - 29 days is successful.", ExtentColor.GREEN)); 
				 
			  landingToPortal();
			  
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to adjust date filter to 40 days...", ExtentColor.BLUE)); 
			  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFOURTY_DAYS));
			  Input.clickOn(view, click);
			  test.log(Status.INFO, MarkupHelper.createLabel("The date filter is adjusted to 40 days.", ExtentColor.GREEN));
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }	
			  portal_Signout();			  
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 3 Flowsheets using file CreateFlowsheet_to_generate_alert_3.xml to generate 3 alerts for PN1, with date of treatment = date of execution - 29 days is failed.", ExtentColor.RED)); 				
			  g_Test_Task_Result = g_Test_Task_Failed; 			  
		  }
		  
		  /* 
		   * Upload an unconfirmed flowsheet to PN2 with date of treatment 
		   */
		  
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload an unconfirmed flowsheet to PN2 with date of treatment...", ExtentColor.BLUE));
		  testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag);
		  
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN2_centerID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN2_patientID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "1" );			
			
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_10_Unconfirmed_fs_Files.cfg", cTWO))
		  {
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of an unconfirmed flowsheet to PN2 with date of treatment is successful.", ExtentColor.GREEN));
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "10" );				  
			  landingToPortal();
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  portal_Signout();				 
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of an unconfirmed flowsheet to PN2 with date of treatment is failed.", ExtentColor.RED));
			  g_Test_Task_Result = g_Test_Task_Failed; 			  
		  }
		  
		  /*
		   *  Upload an unconfirmed flowsheet to PN1 with date of treatment = date of execution – 29 days 
		   */
		  
		  testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag);
		  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload an unconfirmed flowsheet to PN1 with date of treatment = date of execution - 29 days...", ExtentColor.BLUE)); 
		  
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag,g_PN1_centerID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_patientID_Tag,g_PN1_patientID);
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag,"1" );			
		  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, Input.substract_DaysFromTodaysDate(29));		 
		 
		  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_10_Unconfirmed_fs_Files.cfg", cTWO))
		  {	
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload an unconfirmed flowsheet to PN1 with date of treatment = date of execution - 29 days is successful.", ExtentColor.GREEN)); 				 
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, "${NowUtc}");
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "10" );
			  landingToPortal();
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse over on Unconfirmed flowsheets field and print tooltip...", ExtentColor.BLUE)); 
			  Input.mouseOver(FS_Statistics_Unconfirmed_Label);
			  test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(FS_Statistics_Unconfirmed_Label_Tooltip),ExtentColor.GREEN)); 
			  
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to adjust the date filter to 3 days...", ExtentColor.BLUE));
			  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTHREE));
			  Input.clickOn(view, click);		
			  test.log(Status.INFO, MarkupHelper.createLabel("The date filter is adjusted to 3 days.", ExtentColor.GREEN)); 
			  if(!flowsheet_Statistics_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }	
			  portal_Signout();
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Upload an unconfirmed flowsheet to PN1 with date of treatment = date of execution - 29 days is failed.", ExtentColor.RED)); 				 				 
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }		  		
		  
		  if(g_Test_Task_Result)
		  {
			  Assert.assertTrue(true);
		  }
		  else
		  {
			  Assert.assertTrue(false);
		  }
	  }
	  
	  /******************************************************************************************************
	   * Test Method Name : DB_Delete_And_PatientProvision_Task() 
	   * 
	   * Description : 
	   * 
	   * 		This method will perform the DB Delete task on center, and provisioning the center and as well 
	   * 		as provision the patient,
	   *
	   ******************************************************************************************************/
	  
	  @Test(priority=4, retryAnalyzer=BaseClass.class)
	  public static void DB_Delete_And_PatientProvision_Task_Test() throws TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, InterruptedException
	  {		  
		  g_Test_Task_Result = g_Test_Task_Passed; 		  
		  if(landingToPortal())
		  {
			  if(!patients_Statistics_Panel_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  portal_Signout();
		  }
		  else
		  {
			  test.log(Status.ERROR, MarkupHelper.createLabel("Login to portal is failed.", ExtentColor.RED));
			  g_Test_Task_Result = g_Test_Task_Failed;  
		  }
		  
		  if(db_DeleteTask("DbDelete", "DeleteCenterId", g_centerId))
		  {
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to Provision patient ' "+g_PN1_patientID+" ' in the center...", ExtentColor.BLUE));
			  if(provisioning_Pateint_In_Center(_updateTags_Before_Provision, _tag_Vals_PN1))
			  {				  
				  test.log(Status.INFO, MarkupHelper.createLabel("Patient ' "+g_PN1_patientID+" ' is Provisioned", ExtentColor.GREEN));
				  landingToPortal();
				  if(!patients_Statistics_Panel_Verification())
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }				  				  
				  String[] _alertsText = {"Set Dialysate volume deviation for ' "+g_PN1_patientID+" '.","Set Dry weight deviation for ' "+g_PN1_patientID+" '.", "Set # of cycler alarms for ' "+g_PN1_patientID+" '."};
				  for(int i = 0; i < Alerts_Labels.length; i++)
				  {
					  test.log(Status.INFO, MarkupHelper.createLabel("Going to change the alert : "+_alertsText[i]+" ...", ExtentColor.BLUE));
					  if(single_Alert_Entry(Alerts_Labels[i], _alertsArr[i]))
					  {
						  test.log(Status.INFO, MarkupHelper.createLabel("Alert Updated successfully.", ExtentColor.GREEN));
						  Input.clickOn(dashboard_Tab, click);
						  if(!patients_Statistics_Panel_Verification())
						  {
							  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
							  g_Test_Task_Result = g_Test_Task_Failed; 
						  }  
					  }
					  else
					  {
						  test.log(Status.INFO, MarkupHelper.createLabel("Alert Updated successfully.", ExtentColor.GREEN));
						  g_Test_Task_Result = g_Test_Task_Failed; 
					  }
				  }
	
				  portal_Signout();
			  }
			  else
			  {
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
		  }
		  else
		  {
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }
		  
		  test.log(Status.INFO, MarkupHelper.createLabel("Going to Provision patient ' "+g_PN2_patientID+" ' in the center...", ExtentColor.BLUE));
		  if(provisioning_Pateint_In_Center(_updateTags_Before_Provision, _tag_Vals_PN2))			
		  {				
			  test.log(Status.INFO, MarkupHelper.createLabel("Patient ' "+g_PN2_patientID+" ' is Provisioned", ExtentColor.GREEN));
			  landingToPortal();
			  test.log(Status.INFO, MarkupHelper.createLabel("Patient is Provisioned", ExtentColor.GREEN));
			  if(!patients_Statistics_Panel_Verification())
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  
			  portal_Signout();
			  
			  testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag);
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload a Flowsheet for ' "+g_PN1_patientID+" ' with Latest Treatment Date, D1 = DOE-3...", ExtentColor.BLUE)); 
			  
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_centerID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_patientID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "2" );			
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, Input.substract_DaysFromTodaysDate(3));
			  
			  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
			  {	
				  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Upload a Flowsheet for ' "+g_PN1_patientID+" ' with Latest Treatment Date, D1 = DOE-3 is successful.", ExtentColor.GREEN)); 				  
				  landingToPortal();
				  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, "${NowUtc}");
				  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "50" );
				  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTEN_DAYS));
				  Input.clickOn(view, click);
				  if(!patients_Statistics_Panel_Verification())
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }
				  portal_Signout();				  
			  }
			  else
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Upload a Flowsheet for ' "+g_PN1_patientID+" ' with Latest Treatment Date, D1 = DOE-3 is failed.", ExtentColor.RED)); 				  					 
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  
			  testtool_DataConfig("TP2186","CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml",g_createFlowsheetV2_Tag);
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 2 Flowsheets for ' "+g_PN2_patientID+" ' with Latest Treatment Date, D2 = DOE-4...", ExtentColor.BLUE)); 
			  
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN2_centerID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN2_patientID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "2" );			
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, Input.substract_DaysFromTodaysDate(4));
			  
			  if(TestTool.testtoolTaskExec(g_testtoolStandardDir+"TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
			  {	
				  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading 2 Flowsheets for ' "+g_PN2_patientID+" ' with Latest Treatment Date, D2 = DOE-4 is successful.", ExtentColor.GREEN)); 				  
				  landingToPortal();
				  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_treatmentDate_Tag, "${NowUtc}");
				  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "50" );
				  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTEN_DAYS));
				  Input.clickOn(view, click);
				  
				  if(!patients_Statistics_Panel_Verification())
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }
				  
				  if(!Input.getLabelText(Inactive_Pt_Count).equals("0"))
				  {
					  test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse hover on Inactive Label...", ExtentColor.BLUE)); 
					  Input.mouseOver(Inactive_Label);
					  test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Inactive_Label_Tooltip),ExtentColor.GREEN));
					  test.log(Status.INFO, MarkupHelper.createLabel("Patient order is : "+Input.getLabelText(patient_name_order),ExtentColor.GREEN));
				  }				  
				  portal_Signout();				  				  
			  }
			  else
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 2 Flowsheets for ' "+g_PN2_patientID+" ' with Latest Treatment Date, D2 = DOE-4 is failed.", ExtentColor.RED)); 				  					 
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  
			  testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag);
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 2 Flowsheets for ' "+g_PN1_patientID+" ' with Latest Treatment Date= DOE...", ExtentColor.BLUE)); 
			  
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN1_centerID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN1_patientID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "2" );	
			  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
			  {	
				  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 2 Flowsheets for ' "+g_PN1_patientID+" ' with Latest Treatment Date= DOE is successful.", ExtentColor.GREEN)); 					 
				  landingToPortal();
				  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "50" );
				  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTEN_DAYS));
				  Input.clickOn(view, click);
				  
				  if(!patients_Statistics_Panel_Verification())
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }
				  
				  if(!Input.getLabelText(Inactive_Pt_Count).equals("0"))
				  {
					  test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse hover on Inactive Label...", ExtentColor.BLUE)); 
					  Input.mouseOver(Inactive_Label);
					  test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Inactive_Label_Tooltip),ExtentColor.GREEN));
					  test.log(Status.INFO, MarkupHelper.createLabel("Patient order is : "+Input.getLabelText(patient_name_order),ExtentColor.GREEN));
				  }	
				  portal_Signout();				  				  
			  }
			  else
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 2 Flowsheets for ' "+g_PN1_patientID+" ' with Latest Treatment Date= DOE is failed.", ExtentColor.RED)); 
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
			  
			  test.log(Status.INFO, MarkupHelper.createLabel("Going to Provision patient ' "+g_PN3_patientID+" ' in the center...", ExtentColor.BLUE));
			  if(provisioning_Pateint_In_Center(_updateTags_Before_Provision, _tag_Vals_PN3))			
			  {				
				  test.log(Status.INFO, MarkupHelper.createLabel("Patient ' "+g_PN3_patientID+" ' is Provisioned", ExtentColor.GREEN));
			  }
			  
			  testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag);
			  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 2 unconfirmed Flowsheets for ' "+g_PN3_patientID+" ' with Latest Treatment Date= DOE...", ExtentColor.BLUE)); 
			  
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_centerID_Tag, g_PN3_centerID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_patientID_Tag, g_PN3_patientID);
			  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "2" );	
			  if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_10_Unconfirmed_fs_Files.cfg", cTWO))
			  {	
				  test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 2 unconfirmed Flowsheets for ' "+g_PN3_patientID+" ' with Latest Treatment Date= DOE is successful.", ExtentColor.GREEN)); 				  
				  landingToPortal();
				  XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag, "50" );
				  Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cTEN_DAYS));
				  Input.clickOn(view, click);
				  if(!patients_Statistics_Panel_Verification())
				  {
					  test.log(Status.ERROR, MarkupHelper.createLabel("Patient statistics verification is failed.", ExtentColor.RED));
					  g_Test_Task_Result = g_Test_Task_Failed; 
				  }
				  
				  if(Input.getLabelText(Inactive_Pt_Count).equals("0"))
				  {
					  test.log(Status.INFO, "Mouse hover text is not displayed."); 					  
				  }
				  else
				  {
					  test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse hover on Inactive Label...", ExtentColor.BLUE)); 
					  Input.mouseOver(Inactive_Label);
					  test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Inactive_Label_Tooltip),ExtentColor.GREEN));
					  test.log(Status.INFO, MarkupHelper.createLabel("Patient order is : "+Input.getLabelText(patient_name_order),ExtentColor.GREEN));
				  }		 
				  portal_Signout();				  				  
			  }	
			  else
			  {
				  test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 2 unconfirmed Flowsheets for ' "+g_PN3_patientID+" ' with Latest Treatment Date= DOE is failed.", ExtentColor.RED)); 				  
				  g_Test_Task_Result = g_Test_Task_Failed; 
			  }
		  }
		  else
		  {
			  g_Test_Task_Result = g_Test_Task_Failed; 
		  }
		  
		 /* test.log(Status.INFO, MarkupHelper.createLabel("Going to Provision patient ' "+g_PN3_patientID+" ' in the center...", ExtentColor.BLUE));
		  if(provisioning_Pateint_In_Center(_updateTags_Before_Provision, _tag_Vals_PN3))			
		  {				
			  test.log(Status.INFO, MarkupHelper.createLabel("Patient is Provisioned", ExtentColor.GREEN));
		  }*/
		  		  
		  if(g_Test_Task_Result)
		  {
			  Assert.assertTrue(true);
		  }
		  else
		  {
			  Assert.assertTrue(false);
		  }
	  }
	  
	  /******************************************************************************************************
	   * 
	   ******************************************************************************************************/
	  
	  @AfterClass
	  public void tearDown()
	  {		
		  try {
				Desktop.getDesktop().browse(Listener.fRuntimeHtmlReports.toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		  Input.getDriver().quit();	
		  Input.alertMessage("Execution is completed.....");
	  }
}
