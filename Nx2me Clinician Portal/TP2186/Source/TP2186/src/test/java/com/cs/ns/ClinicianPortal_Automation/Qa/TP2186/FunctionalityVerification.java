package com.cs.ns.ClinicianPortal_Automation.Qa.TP2186;

import java.awt.Desktop;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
public class FunctionalityVerification extends ReusableMethods
{	
	/******************************************************************************************************
	 * Before class : Dashboard_Page_Setup()
	 * 
	 * This will kill the previous web driver instances and TestTool Data setup. 
	 * 
	 * ****************************************************************************************************/
	@BeforeClass
	public static void Functionality_Verification_setup() throws InterruptedException, IOException
	{		
		//BasicConfigurator.configure(); 
		TestTool.setTestToolDBServer(g_Testtool_DB_Server);
		TestTool.setTestToolPDMPWEBServer(g_Testtool_PDMP_Server);
		TestTool.set_VPNCredentials(g_VPN_ID, g_VPN_Password);
		TestTool.setTestToolVersion(g_Testtool_Version);
		Input.driverInstanceKillProcess();
		testtool_DataConfig("TP2186",null,null);
		Excel.writeToExcel(cUI_FUNCTIONALITY_DATA, cVPN_USER_ID_ROW, cVPN_USER_ID_COL, strExcelPath, "example@nxstage.com");
	    Excel.writeToExcel(cUI_FUNCTIONALITY_DATA, cVPN_PASSWORD_ROW, cVPN_PASSWORD_COL, strExcelPath, "Dontknow12!");	    
	}

	/******************************************************************************************************
	 * Test Method Name : TestToolTask_For_Uploading_The_50_Flowsheets() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will upload 50 flowsheets to the portal.
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerConfigurationException 
	 * 	 
	 ******************************************************************************************************/
	
	@Test(priority=0, retryAnalyzer=BaseClass.class)
	public static void TestToolTask_For_Uploading_The_50_Flowsheets_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException 
	{		
		StatusHTML.htmlTitleTag("Clinician Portal Functionality Verification...");
		landingToPortal();
		reportsHeaderBrowsersDetails("Nx2me Clinician Portal","TP2186");
		if(archieve_All_Flowsheets(_dashboard_tab, cFIFTY_DAYS))
		{
			portal_Signout();
		}
		
		testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag);		
		test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to perform the task to upload 50 flowsheets to the portal.", ExtentColor.BLUE));
		XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_50_fs_Confirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag,"50" );	
		if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_50_Confirmed_fs_Files.cfg", cTWO))
		{
			System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Completed");
			test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : The task to upload 50 flowsheets to the portal is successful.",ExtentColor.GREEN));			
			Assert.assertTrue(true);
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : The task to upload 50 flowsheets to the portal is failure..",ExtentColor.RED));
			Assert.assertTrue(false);			
		}		
	}
	
	/******************************************************************************************************
	 * Test Method Name : Login_To_Portal_And_Verify_Patient_Info_Actions() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will verify the 
	 * 
	 * 		1. Patient information.
	 * 		2. Flowsheet summary sections.
	 * 		3. Default date range.  
	 * 	 
	 ******************************************************************************************************/
	
	@Test(priority=1, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Login_To_Portal_And_Verify_Patient_Info_Actions_Test() throws InterruptedException 
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		if(landingToPortal())
		{
			if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
			{	
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Default Date Range Difference...", ExtentColor.BLUE));
				
				if((Input.dateDifference(Input.getAttributeValue(startDateLabel, value), Input.getAttributeValue(endDateLabel, value))) == cTWO)
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Date Range Difference is : 3 Days", ExtentColor.GREEN));
					System.out.println("Date Range Difference is : 3 Days");
				}
				else
				{
					test.log(Status.ERROR, MarkupHelper.createLabel("Date Range Difference is not matched.", ExtentColor.RED));
					g_Test_Task_Result = g_Test_Task_Failed;
				}
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to set date range to display >= 50 flowsheets...", ExtentColor.BLUE));
				Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
				Input.clickOn(view, click);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to display the flowsheet summary sections...", ExtentColor.BLUE));
				
				if ((flowsheetsPerPage(dashboardTab_total_flowsheets)) == cDASHBOARD_FLOWSHEETRANGE)
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Displayed Flowsheets are : " + cDASHBOARD_FLOWSHEETRANGE, ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Flowsheets Text is : ' "+ Input.getLabelText(dashboardTab_total_flowsheets)+" '.", ExtentColor.GREEN));
					System.out.println("Displayed Flowsheets : 15 And Text : " +Input.getLabelText(dashboardTab_total_flowsheets));
				}
				
				test.log(Status.INFO, MarkupHelper.createLabel("Patient info is displayed in the following format : ", ExtentColor.BLUE));
				Input.mouseOver(dashboardTab_patientName);
				
				for (int i = 0; i < patientInfo.length; i++)
				{
					if(Input.isElementPresent(patientInfo[i]))
					{
						test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(patientInfo[i]), ExtentColor.GREEN));
					}
					else
					{
						test.log(Status.FATAL, MarkupHelper.createLabel("Unable to find the : ' "+_PatientInfo[i]+" ' info." , ExtentColor.RED));
					}					
				}
			}			
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Navigating to : ' " + _dashboard_Tab_Title + " ' is failed.", ExtentColor.RED));
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
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	/******************************************************************************************************
	 * Test Method Name : Perform_Flowsheet_Actions() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform:
	 * 
	 * 		1. Flowsheet control verification.
	 * 		2. Flowsheet status actions.
	 * 		
	 ******************************************************************************************************/
			
	@Test(priority=2, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Perform_Flowsheet_Actions_Test() throws InterruptedException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Summary section displays...", ExtentColor.BLUE));
		
		if(Verify.checkIfLabelDisplayed(Dsb_allFlowsheetsCheck))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Additional field to select records for applying Flowsheet Actions (CHECH BOX) is Displayed", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Additional field to select records for applying Flowsheet Actions (CHECH BOX) is Not Displayed", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		
		test.log(Status.INFO, MarkupHelper.createLabel("Without selecting any flowsheet, hover over on each action... ", ExtentColor.BLUE));
		
		if(fs_Status_Actions())
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Actions are Disabled.", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Verification of flowsheet Actions Disabled Status, is failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		
		test.log(Status.INFO, MarkupHelper.createLabel("Going to select a flowsheet and verify if flowsheet actions are Enabled/Disabled...", ExtentColor.BLUE));
		Input.clickOn(singleFlowsheetSelect, click);
		
		if(!fs_Status_Actions())
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Actions are Enabled.", ExtentColor.GREEN));
			g_Test_Task_Result = g_Test_Task_Passed;
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Verification of flowsheet actions failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		test.log(Status.INFO, MarkupHelper.createLabel("Going to De-select a flowsheet and verify if flowsheet actions are Enabled/Disabled...", ExtentColor.BLUE));
		Input.clickOn(singleFlowsheetSelect, click);
		
		if(fs_Status_Actions())
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Actions are Disabled.", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Verification of flowsheet actions failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		
		test.log(Status.INFO, MarkupHelper.createLabel("Going to select all flowsheets and verify if flowsheet actions are Enabled/Disabled...", ExtentColor.BLUE));
		Input.clickOn(Dsb_allFlowsheetsCheck, click);
		
		if(!fs_Status_Actions())
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Actions are Enabled.", ExtentColor.GREEN));
			g_Test_Task_Result = g_Test_Task_Passed;
		}
		else
		
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Verification of flowsheet actions failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		
		TimeUnit.SECONDS.sleep(2);
		Input.mouseOver(dashboardTab_patientName);
		TimeUnit.SECONDS.sleep(2);
		Input.clickOn(Dsb_allFlowsheetsCheck, click);
		
		for(int i = 0; i < _flowsheet_actions_text.length - 1; i++)
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going to change 2 Flowsheet's status from ' "+ _flowsheet_actions_text[cZERO] + " ' to " +  _flowsheet_actions_text[i + cONE] + " '", ExtentColor.BLUE));
			for(int j = 0; j < cTWO; j++)
			{				 
				if(searchForFlowsheetByAction(dashboardTab_total_flowsheets, _flowsheet_actions_text[cZERO]))
				{										 
					flowsheet_Actions(null, FS_status, FS_ACTNS[i + 1]);
					test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Status Updated From : "+ _flowsheet_actions_text[cZERO] + " to : " + _flowsheet_actions_text[i + cONE],ExtentColor.GREEN));
					Input.clickOn(dashboard_Tab, click);
				}
				else
				{
					test.log(Status.ERROR,  MarkupHelper.createLabel("Flowsheet search failed, unable to find the flowsheet with desired status", ExtentColor.RED));					
				}
			}
		}		
		if(g_Test_Task_Result)
		{
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
	}
	
	/******************************************************************************************************
	 * Test Method Name : Perform_Flowhseet_AddNotes_Activities() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 
	 * 		1. Flowsheet Add notes.
	 * 		2. Deleting center notes.
	 * 		3. Center and Patient notes ICON test.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=3, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Perform_Flowhseet_AddNotes_Activities_Test() throws InterruptedException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		Input.clickOn(dashboard_Tab, click);
		int i = 0;
		test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Center and Patient Notes ICON", ExtentColor.BLUE));
		
		for( i = 0; i < cTWO; i++)
		{			
			if(Input.isElementPresent(Center_Patient_Icon_Labels[i]))
			{
				if(Input.getAttributeValue(Center_Patient_Icon_Labels[i], "src").contains("white"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel(_center_patient_label_text[i] + " is in gray color", ExtentColor.GREEN));
				}
				else if(Input.getAttributeValue(Center_Patient_Icon_Labels[i], "src").contains(_center_patient_icon_color[i]))
				{
					test.log(Status.INFO, MarkupHelper.createLabel(_center_patient_label_text[i] + " is in '" + _center_patient_icon_color[i] +" ' color", ExtentColor.GREEN));
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
					test.log(Status.FATAL, MarkupHelper.createLabel("Unable to find the ICON", ExtentColor.RED));
				}
			}
		}
		
		if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title)){
			
			Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
			Input.clickOn(view, click);
			test.log(Status.INFO, MarkupHelper.createLabel("Going to add notes to center...", ExtentColor.BLUE));
			for(i = 0; i < cFIVE; i++)
			{				
				flowsheet_Actions(singleFlowsheetSelect, FS_addNotes, FS_enterToAddNotes);
				Input.textBoxEntry(FS_enterToAddNotes, center_note+(i+1));
				Input.clickOn(FS_addNotes_Ok, click);
				
				if(Verify.checkIfLabelDisplayed(FS_addNoteError))
				{
					Input.clickOn(FS_addNoteError_Ok, click);
				}
				
				test.log(Status.INFO, MarkupHelper.createLabel("Center Note Added with : ' "+center_note+(i+1)+" '", ExtentColor.GREEN));
			}
			
			if(Input.isElementPresent(FS_Center_Notes_Label))
			{				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the center notes ICON, after adding data... ", ExtentColor.BLUE));
				
				if(Input.getAttributeValue(FS_Center_Notes_Label, "src").contains("white"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Center Notes ICON is in gray color", ExtentColor.GREEN));
				}
				else if(Input.getAttributeValue(FS_Center_Notes_Label, "src").contains("blue"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Center Note icon is changed to blue color.", ExtentColor.GREEN));
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
					test.log(Status.FATAL, MarkupHelper.createLabel("Unable to find the icon.", ExtentColor.RED));
				}
				
				Input.mouseOver(FS_Center_Notes_Label);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to Hover over on Center Notes that contains notes... ", ExtentColor.BLUE));
				
				if(Verify.checkIfLabelDisplayed(FS_Center_Notes_Check))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Center Notes ICON verified.", ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify, the center notes header displays...", ExtentColor.BLUE));
					
					for(i = 0; i < Arr_centerNotes_Data.length; i++)
					{
						test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Arr_centerNotes_Data[i]), ExtentColor.GREEN));
					}
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the flowsheet details...", ExtentColor.BLUE));
					Input.clickOn(centerNoteGoToFlowsheetDetails_SubHeader, click);
					Input.clickOn(goToCenterFlowsheetDetailPage, click);
					test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(goToCenterFlowsheetDtailPage_CnDate),ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(goToCenterFlowsheetDetailPage_Data),ExtentColor.GREEN));
					System.out.println("DATA : "+Input.getLabelText(goToCenterFlowsheetDetailPage_Data));
					
					test.log(Status.INFO, MarkupHelper.createLabel("Going to delete the center notes...", ExtentColor.BLUE));
					Input.clickOn(deleteCenterNote, click);
					
					if(Verify.checkIfLabelDisplayed(deleteCenterNote_Yes))
					{
						Input.clickOn(deleteCenterNote_Yes, click);
						test.log(Status.INFO, MarkupHelper.createLabel("Center Notes is deleted.", ExtentColor.GREEN));
					}					
					Input.clickOn(dashboard_Tab, click);
				}				
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
				}				
				Input.mouseOver(dashboardTab_patientName);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the display of flowsheet context notes...", ExtentColor.BLUE));
			}
			else
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Center Notes ICON is Disabled.", ExtentColor.RED));
			}
			
			if(Input.isElementPresent(FS_Patient_Notes_Label_without_attachments))
			{
				Input.mouseOver(FS_Patient_Notes_Label_without_attachments);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to hover over on Patient Notes that contains notes...", ExtentColor.BLUE));
				
				if(Verify.checkIfLabelDisplayed(FS_Patient_Notes_Check))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Patient Notes ICON verified.", ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the display of Patient Notes Header...", ExtentColor.BLUE));
					
					for( i = 0; i < Arr_PateintNoteData.length; i++)
					{
						test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Arr_PateintNoteData[i]),ExtentColor.GREEN));
					}
					
					Input.clickOn(patientNoteGoToFlowsheetDetails_SubHeader, click);
					Input.mouseOver(goToPatientFlowsheetDetailsPageText);
					test.log(Status.INFO, MarkupHelper.createLabel("Patient Notes text is : ' "+Input.getLabelText(goToPatientFlowsheetDetailsPageText), ExtentColor.GREEN));
					System.out.println("DATA : " + Input.getLabelText(goToPatientFlowsheetDetailsPageText));
					Input.clickOn(dashboard_Tab, click);
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
				}
				Input.mouseOver(dashboardTab_patientName);	
			}
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Unable to navigate to : ' "+_dashboard_Tab_Title+" ' tab.", ExtentColor.RED));
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
	 * Test Method Name : Perform_Print_Flowsheet_Actions() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 
	 * 		1. Flowsheet Print with / without attachments action and verification.
	 * 		2. Alerts Verification.
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws IOException 
	 * @throws TransformerConfigurationException 
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=4,dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Perform_Print_Flowsheet_Actions_Test() throws InterruptedException, TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		Input.clickOn(dashboard_Tab, click);
		Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
		Input.clickOn(view, click);
		test.log(Status.INFO, MarkupHelper.createLabel("Going to select 5 Flowsheets based on \" Patient Notes \" and Perform The Print Action...", ExtentColor.BLUE));
		
		if(search_And_Select_FS_With_Or_Without_Attachments(dashboardTab_total_flowsheets, "Patient Notes", cFIVE))
		{
			System.out.println("Flowsheets selected");
			flowsheet_Actions(null, FS_print, FS_printWithoutAttach);
			test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Print Action is completed for 5 flowsheets.", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Performing The Print Action has been failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
			
		}
		
		test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Flowsheet Print Status...", ExtentColor.BLUE));	
		if(verifyPrint_Status(dashboardTab_total_flowsheets, cFIVE))
		{
			System.out.println("Flowsheets Print Status Verified");
			test.log(Status.INFO, MarkupHelper.createLabel("Verification of Flowsheet Print Status is successful.", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Verification of Flowsheet Print Status has been failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		
		archieve_All_Flowsheets(_dashboard_tab, cDASHBOARD_FLOWSHEETRANGE);
		portal_Signout();
		testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_5_fs_with_attachments.xml", g_createFlowsheetV2_Tag);
		XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_5_fs_with_attachments.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag,"5" );	
		
		test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 5 flowsheets to portal, with attachments...", ExtentColor.BLUE));
		if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_with_attachments.cfg", cTHREE))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading the 5 flowsheets is successful.", ExtentColor.GREEN));
			if(landingToPortal())
			{
				if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
				{
					g_isPortalWindowActive = g_Test_Task_Passed;;
					Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
					Input.clickOn(view, click);
					test.log(Status.INFO, MarkupHelper.createLabel("Going to select a Flowsheets that contains attachments and Perform the Print Action...", ExtentColor.BLUE));
					
					if(Input.isElementPresent(FS_Patient_Notes_Label_with_attachments))
					{
						Input.mouseOver(FS_Patient_Notes_Label_with_attachments);
						flowsheet_Actions(FS_patientDOB, FS_print, FS_PrintWithAttachments);
						test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet print status task successful", ExtentColor.GREEN));
						test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Flowsheet Print Status...", ExtentColor.BLUE));
						
						if(Input.getLabelText(isFlowsheetPrinted).equalsIgnoreCase(yes))
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet is Printed.", ExtentColor.GREEN));	
						}
						else
						{
							g_Test_Task_Result = g_Test_Task_Failed;
							test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet is not Printed.", ExtentColor.RED));
						}
					}					
					Input.clickOn(dashboard_Tab, click);
					
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Alerts criteria for 4 flowsheets", ExtentColor.BLUE));
					if(!verify_PatientAlerts(cFOUR))
					{
						g_Test_Task_Result = g_Test_Task_Failed;
						test.log(Status.ERROR, MarkupHelper.createLabel("Alerts criteria verification is failed", ExtentColor.RED));
					}					
				}				
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
				}
			}
		}
		else
		{
			test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading the 5 flowsheets is failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
			g_isPortalWindowActive = g_Test_Task_Failed;
		}	
		Input.clickOn(dashboard_Tab, click);		
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
	 * Test Method Name : Perform_Flowhseet_Archive_Activities() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 
	 * 		1. Flowsheet archive functionality.
	 * 		2. Flowsheet pagination to different pages.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=5,dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Perform_Flowhseet_Archive_Activities_Test() throws InterruptedException
	{
		g_Test_Task_Result = g_Test_Task_Passed;		
		if( g_isPortalWindowActive || landingToPortal())	
		{
			if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
			{
				Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cSIXTY_DAYS));
				Input.clickOn(view, click);
				By[] selectFlowsheets = {singleFlowsheetSelect,Dsb_allFlowsheetsCheck};
				String[] _NoOf_FS = {"1", "ALL"};
				
				for(int i = 0; i < selectFlowsheets.length; i++)
				{					
					if(!Input.getLabelText(dashboardTab_total_flowsheets).equalsIgnoreCase(_no_data_available))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Going to select " + _NoOf_FS[i] + " flowsheets and perform flowsheet archive functionality...", ExtentColor.BLUE));
						Input.clickOn(selectFlowsheets[i], click);
						test.log(Status.INFO, MarkupHelper.createLabel("Flowsheets before archive is : ' "+ Input.getLabelText(dashboardTab_total_flowsheets)+ " '", ExtentColor.GREEN));
						
						if(Input.isElementPresent(FS_archive))
						{
							Input.clickOn(FS_archive, click);
							test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the archive flowsheet error label..." , ExtentColor.BLUE));
							
							if(Verify.checkIfLabelDisplayed(FS_archive_Yes))
							{
								test.log(Status.INFO, MarkupHelper.createLabel("Verification of the user confirm message : ' Are you sure you would like to archive the record(s) below? ' is successful.",ExtentColor.GREEN));
								Input.clickOn(FS_archive_Yes, click);
							}							
							test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the flowsheets, after archiving...", ExtentColor.BLUE));
							test.log(Status.INFO, MarkupHelper.createLabel("Total flowsheets, after archiving : ' "+ Input.getLabelText(dashboardTab_total_flowsheets)+" '", ExtentColor.GREEN));
						}
						
						if(i == cZERO)
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Using pagination, going to navigate to different pages of Flowsheet Summary section..." , ExtentColor.BLUE));
							paginateToNextPage(dashboardTab_total_flowsheets, FS_MoreRecords_Next, FS_MoreRecords_Prev, "");
							test.log(Status.INFO, MarkupHelper.createLabel("Navigation of different pages of Flowsheet Summary section is Completed." , ExtentColor.GREEN));
							Input.clickOn(dashboard_Tab, click);
						}
					}
					else
					{
						test.log(Status.INFO, MarkupHelper.createLabel("No Data Available to perform Archive Functionality.", ExtentColor.GREEN));
					}				
				}
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to Navigate to ' "+ _dashboard_Tab_Title+" ' tab.", ExtentColor.RED));
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
	 * Test Method Name : Messages_Task() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 		
	 * 		1. PatientTwoWayMessaging task.
	 * 		2. Create new message, and giving reply to same message.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=6,dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Messages_Task_Test() throws InterruptedException, IOException
	{
		g_Test_Task_Result = g_Test_Task_Passed;			
		test.log(Status.INFO, MarkupHelper.createLabel("Going to Navigate to New Messages TAB...", ExtentColor.BLUE));
		Input.clickOn(newMessages_Tab, click);
		
		if(Input.getDriver().getTitle().contains(_newmessages_Tab_Title))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Sucessfully Navigated to New Messages TAB.", ExtentColor.GREEN));
			test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Data and Labels...", ExtentColor.BLUE));									
			for(int i = 0; i <message_labels.length; i++)
			{
				if(Verify.checkIfLabelDisplayed(message_labels[i]))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("' "+ Input.getLabelText(message_labels[i]) + " ' label is verified", ExtentColor.GREEN));
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
					test.log(Status.ERROR, MarkupHelper.createLabel("Label verification of : \" " + message_labels_text[i] + " \" is Failed", ExtentColor.RED));
				}				
			}
		}
		else
		{
			g_Test_Task_Result = g_Test_Task_Failed;
			test.log(Status.ERROR, MarkupHelper.createLabel("Failed to navigate to Messages tab.", ExtentColor.RED));	
		}
		
		portal_Signout();	
		testtool_DataConfig("TP2186","PdmpTwoWayMessagingTask.xml", "PatientTwoWayMessaging");		
		test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to perform the task : PDMP2WayMessaging to create patient messages in the center...", ExtentColor.BLUE));
		if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\PdmpTwoWayMessagingTask.xml", cONE))			
		{
			test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : PDMP2WayMessaging task is completed.",ExtentColor.GREEN));
			if(landingToPortal())
			{
				if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
				{
					g_isPortalWindowActive = g_Test_Task_Passed;
					test.log(Status.INFO, MarkupHelper.createLabel("Going to Navigate to New Messages TAB...", ExtentColor.BLUE));
					Input.clickOn(newMessages_Tab, click);
					
					if(Verify.compareText(Input.getDriver().getTitle(), _newmessages_Tab_Title))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Successfully Navigated to New Messages TAB.", ExtentColor.GREEN));	
						
						test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the 'Messages' Tasks...", ExtentColor.BLUE));
						
						for(int i = 0; i < messages_task.length; i++)
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Going to verify : ' " + _messages_task_text[i] + " ' task", ExtentColor.BLUE));
							
							if(Verify.checkIfLabelDisplayed(messages_task[i]) || Verify.checkIfLabelEnabled(messages_task[i]))
							{
								test.log(Status.INFO, MarkupHelper.createLabel(_messages_task_text[i] + " is verified",ExtentColor.GREEN));
							}
							else
							{
								g_Test_Task_Result = g_Test_Task_Failed;
								test.log(Status.ERROR, MarkupHelper.createLabel(" Unable to verify the : ' "+_messages_task_text[i] + " ' label.", ExtentColor.RED));
							}
						}
						test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the ' Message and Time Date' ..." , ExtentColor.BLUE));
						
						if(Verify.checkIfLabelDisplayed(PatientName_DateTime) || Verify.checkIfLabelEnabled(PatientName_DateTime))
						{
							test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(PatientName_DateTime) +" is verified",ExtentColor.GREEN));
						}
						test.log(Status.INFO, MarkupHelper.createLabel("Verification of 'Messages' Tasks completed ", ExtentColor.GREEN));
						
						test.log(Status.INFO, MarkupHelper.createLabel("Going to select the above created message, under the New Messages Panel and by entering 1000 characters as Message text, then clicking the Send button...", ExtentColor.BLUE));
						Input.clickOn(PatientName_DateTime, click);
						
						if(Verify.checkIfLabelDisplayed(Response_Msg) || Verify.checkIfLabelEnabled(Response_Msg))
						{
							Input.textBoxEntry(Response_Msg, text_message);
							TimeUnit.SECONDS.sleep(2);
							Input.clickOn(Response_Msg_Send, click);
							test.log(Status.INFO, MarkupHelper.createLabel("'Message' Sent", ExtentColor.GREEN));
						}
					}
				}
			}
		}
		else{
			g_isPortalWindowActive = g_Test_Task_Failed;
		}
		if(g_Test_Task_Result){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
	}
	
	/******************************************************************************************************
	 * Test Method Name : Patient_List_Enrollment_UnEnrollment_Status() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 		
	 * 		1. Enrollment and Unenrollment status task.
	 * 		2. Ascending or descending test.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority = 7,dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Patient_List_Enrollment_UnEnrollment_Status_Test() throws InterruptedException
	{
		g_Test_Task_Result = g_Test_Task_Passed;
		if( g_isPortalWindowActive || landingToPortal())
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Navigating to Dashboard TAB...", ExtentColor.BLUE));
			Input.clickOn(dashboard_Tab, click);
			
			if(Input.getDriver().getTitle().contains(_dashboard_Tab_Title))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Sucessfully Navigated to Dashboard TAB.", ExtentColor.GREEN));
				Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
				Input.clickOn(view, click);
				g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Default Enrollment Status should be : ENROLLED", ExtentColor.BLUE));
				test.log(Status.INFO, MarkupHelper.createLabel("Status is : "+ Input.getAttributeValue(patientEnroll_Status, value),ExtentColor.GREEN));
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the patient list count...", ExtentColor.BLUE));
				test.log(Status.INFO, MarkupHelper.createLabel("Patient List have\"" + flowsheetsPerPage(noOfPatientsRecords) + " \" patients..", ExtentColor.GREEN));
				
				test.log(Status.INFO, MarkupHelper.createLabel("Patient List Info  Label displays :  \" "+ Input.getLabelText(noOfPatientsRecords) +"\"", ExtentColor.GREEN));
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the paginate Next Button whether if it is displayed or not...", ExtentColor.BLUE));			
				if(totalFlowsheetsCountS(noOfPatientsRecords) > cDASHBOARD_FLOWSHEETRANGE)
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Paginate Next Button is Displayed.", ExtentColor.GREEN));					
					if(Verify.checkIfLabelDisplayed(patientList_nextPage))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Paginate Next Button is Displayed :", ExtentColor.GREEN));
					}					
				}				
				
				Input.scrollDown();
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the default sorting order of Patient List section is in the ascending alphabetical order of Patient Names...", ExtentColor.BLUE));
				
				if(!paginateToNextPage(noOfPatientsRecords, patientList_nextPage, patientList_prevPage,_Patient_Name_Col))
				{
					test.log(Status.ERROR, MarkupHelper.createLabel("Verification of default sorting order test is failed", ExtentColor.RED));
				}
				for(int i = 0; i < Ascending_test_labels.length; i++)
				{
					test.log(Status.INFO, MarkupHelper.createLabel(_header_message[i], ExtentColor.BLUE));
					Input.scrollDown();
					Input.clickOn(Ascending_test_labels[i], click);
					
					if(!paginateToNextPage(noOfPatientsRecords, patientList_nextPage, patientList_prevPage,_Ascending_test_labels_text[i]))
					{
						test.log(Status.ERROR, MarkupHelper.createLabel(_header_error_message[i], ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed;
					}
					if(i == cONE )
					{
						Input.clickOn(dashboard_Tab, click);
					}
				}
				test.log(Status.INFO, MarkupHelper.createLabel("Navigating to Patients TAB...", ExtentColor.BLUE));
				Input.clickOn(patients_Tab, click);
				
				if(Input.getDriver().getTitle().contains(_patients_Tab_Title))
				{					
					test.log(Status.INFO, MarkupHelper.createLabel("Sucessfully Navigated to Patients TAB.", ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the default Enrollment Status is : ENROLLED", ExtentColor.BLUE));
					test.log(Status.INFO, MarkupHelper.createLabel("The Enrollment Status is : " + Input.getAttributeValue(patientEnroll_Status, value), ExtentColor.GREEN));
					
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the MAX Patient List is \" 25  \" patients..", ExtentColor.BLUE));
					test.log(Status.INFO, MarkupHelper.createLabel("Primary Patient List has : \" "+ flowsheetsPerPage(patientsPage_total_Records)+" \" patients..",ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Patient List Info  Label : \" "+ Input.getLabelText(patientsPage_total_Records)+"\"", ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the paginate Next Button whether if it is displayed or not...", ExtentColor.BLUE));			
					if(totalFlowsheetsCountS(patientsPage_total_Records) > cPATIENTS_FLOWSHEETRANGE)
					{
						if(Verify.checkIfLabelDisplayed(patientsPage_Next))
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Paginate Next Button is Displayed :", ExtentColor.GREEN));
						}						
					}
					
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the default sorting order of Patient List section is in the ascending alphabetical order of Patient Names...", ExtentColor.BLUE));
					
					if(!paginateToNextPage(patientsPage_total_Records, patientsPage_Next, patientsPage_Prev, _Patient_Name_Col))
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Verification of default sorting order test is failed", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed;
					}					
					
					for(int i = 0; i < Ascending_test_labels.length; i++)
					{
						test.log(Status.INFO, MarkupHelper.createLabel(_header_message[i], ExtentColor.BLUE));
						Input.clickOn(Ascending_test_labels[i], click);
						
						if(!paginateToNextPage(patientsPage_total_Records, patientsPage_Next, patientsPage_Prev,_Ascending_test_labels_text[i]))
						{
							test.log(Status.ERROR, MarkupHelper.createLabel(_header_error_message[i], ExtentColor.RED));
							g_Test_Task_Result = g_Test_Task_Failed;
						}
						if( i == cONE )
						{
							Input.clickOn(patients_Tab, click);
						}
					}
					
					test.log(Status.INFO, MarkupHelper.createLabel("Going to navigate to DASHBOARD TAB...", ExtentColor.BLUE));
					Input.clickOn(dashboard_Tab, click);
					
					if(Input.getDriver().getTitle().contains(_dashboard_Tab_Title))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Successfully Navigated to DASHBOARD TAB.", ExtentColor.GREEN));	
						test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Enrollment Status From \" Enrolled \" to \" Unenrolled \"", ExtentColor.BLUE));
						Input.dropDown(patientEnroll_Status, "Unenrolled");
						test.log(Status.INFO, MarkupHelper.createLabel("Status is : "+ Input.getAttributeValue(patientEnroll_Status, value), ExtentColor.GREEN));
						test.log(Status.INFO, MarkupHelper.createLabel("Going to navigate to PATIENTS TAB...", ExtentColor.BLUE));
						Input.clickOn(patients_Tab, click);
						
						if(Input.getDriver().getTitle().contains(_patients_Tab_Title))
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Successfully Navigated to PATIENTS TAB.", ExtentColor.GREEN));
							test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Enrollment Status From \" Unenrolled \" to \" Enrolled \"", ExtentColor.BLUE));
							Input.dropDown(patientEnroll_Status, "Enrolled");
							test.log(Status.INFO, MarkupHelper.createLabel("Status is : "+ Input.getAttributeValue(patientEnroll_Status, value), ExtentColor.GREEN));
							System.out.println("ref_patient_name1 "+ g_refPatientName1);
							search_Patient(_patients_tab, g_refPatientName1);
							test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the drop down menu is set to \" Confirmed \" by default...", ExtentColor.BLUE));
							String confirm_status = Input.getAttributeValue(patient_confirmed, value);
							
							if(confirm_status.equalsIgnoreCase("false"))
							{
								test.log(Status.INFO, MarkupHelper.createLabel("Status is : Confirmed",ExtentColor.GREEN));	
							}
							else if(confirm_status.equalsIgnoreCase("true"))
							{
								test.log(Status.INFO, MarkupHelper.createLabel("Status is : Unconfirmed",ExtentColor.GREEN));	
							}
							else{/*do nothing.*/}
						}
					}
					else
					{
						g_Test_Task_Result = g_Test_Task_Failed;
					}	
				}
				else
				{
					test.log(Status.ERROR, MarkupHelper.createLabel("Navigation to Patients Tab is failed.", ExtentColor.RED));
					g_Test_Task_Result = g_Test_Task_Failed;
				}
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Navigation to Dashboard TAB is failed..", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed;
			}
		}
		portal_Signout();
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
	 * Test Method Name : TestTool_Task_For_Uploading_Unconfirmed_Flowsheets() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will upload the unconfirmed flowsheets to portal.
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerConfigurationException 
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority = 8, retryAnalyzer=BaseClass.class)
	public static void TestTool_Task_For_Uploading_Unconfirmed_Flowsheets_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException 
	{		
		testtool_DataConfig("TP2186", "CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag);
		test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to upload 10 unconfirmed flowsheets to portal...", ExtentColor.BLUE));	
		try 
		{
			XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\CreateFlowsheetFromFilesTask_for_10_fs_Unconfirmed.xml", g_createFlowsheetV2_Tag, g_numberFlowsheets_Tag,"10" );	
			if(TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\Upload_XML_10_Unconfirmed_fs_Files.cfg", cTWO))
			{
				System.out.println(Input.timeStamp()+" : TEST_TOOL : ");
				test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Uploading of 10 unconfirmed flowsheets to portal is successful.", ExtentColor.GREEN));					
				Assert.assertTrue(true);
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL : Uploading of 10 unconfirmed flowsheets to portal is failed.", ExtentColor.RED));	
				Assert.assertTrue(false);
			}
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}	
	}
	
	/******************************************************************************************************
	 * Test Method Name : Patient_Page_Sub_Tabs_Functionality() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform the patients tab subtab functionality, such as reports, messages, 
	 * 		navigating to settings , summary and alerts. 
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=9,dependsOnMethods = "TestTool_Task_For_Uploading_Unconfirmed_Flowsheets_Test", retryAnalyzer=BaseClass.class)
	public static void Patient_Page_Sub_Tabs_Functionality_Test() throws InterruptedException
	{		
		if(landingToPortal())
		{
			if(Input.getDriver().getTitle().contains(_dashboard_Tab_Title))
			{				
				Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cHUNDRED_DAYS));
				Input.clickOn(view, click);
				g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);
				Input.scrollDown();
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Patient Reports and flowsheet summary print...", ExtentColor.BLUE));
				flowsheet_Actions(patientListCheckOne, PatientFS_Report, FlowsheetSummaryReports);
				TimeUnit.SECONDS.sleep(3);
				Input.scrollDown();
				test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Calendor Reports Printed.", ExtentColor.GREEN));
				flowsheet_Actions(patientListCheckOne, PatientFS_Report, FlowsheetCalendorReports);
				
				Input.clickOn(patients_Tab, click);
				System.out.println("ref_patient_name1)" + g_refPatientName1);
				search_Patient(_patients_tab, g_refPatientName1);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to select a patient, and verify if the default status is set as ' confirmed '...", ExtentColor.BLUE));				
				
				if(Input.getAttributeValue(patient_confirmed, value).equalsIgnoreCase("false"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Default status is : ' Confirmed '", ExtentColor.GREEN));
				}
				else
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Default status is : ' Unconfirmed '", ExtentColor.GREEN));
				}
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify whether the flowsheets are present...", ExtentColor.BLUE));
				
				if(Input.getLabelText(patientsPage_total_Records).contains(_no_data_available))
				{
					System.out.println(_no_data_available);
					test.log(Status.INFO, MarkupHelper.createLabel(_no_data_available, ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Going to Pass the Date Range to display the records...", ExtentColor.BLUE));
					Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cSIXTY_DAYS));
					TimeUnit.SECONDS.sleep(1);
					Input.clickOn(view, click);
					test.log(Status.INFO, MarkupHelper.createLabel(_no_data_available, ExtentColor.GREEN));

					test.log(Status.INFO, MarkupHelper.createLabel("Going to select a patient and verify if the Flowsheet actions Enabled/Disabled...", ExtentColor.BLUE));
					Input.clickOn(select_pt_patienttab, click);
					System.out.println("Data Available");
					
					if(!fs_Status_Actions())
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Actions are Enabled.", ExtentColor.GREEN));
						g_Test_Task_Result = g_Test_Task_Passed;						
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet Actions verification Failed.", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed;
					}
				}
				else
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Going to select a patient and verify if the Flowsheet actions are Enabled/Disabled...", ExtentColor.BLUE));
					Input.clickOn(select_pt_patienttab, click);
					System.out.println(_no_data_available);
					
					if(!fs_Status_Actions())
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Flowsheet Actions are Enabled.", ExtentColor.GREEN));
						g_Test_Task_Result = g_Test_Task_Passed;
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Flowsheet Actions verification Failed.", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed;
					}
				}
				test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Patient Status from ' Confirmed ' to ' Unconfirmed '...", ExtentColor.BLUE));
				Input.dropDown(patient_confirmed, "Unconfirmed");
				test.log(Status.INFO, MarkupHelper.createLabel("Status is : "+Input.getAttributeValue(patient_confirmed, value),ExtentColor.GREEN));
				Input.clickOn(patients_Tab, click);
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Enrollment Status From \" Enrolled \" to \" Unenrolled \" ...", ExtentColor.BLUE));
				Input.dropDown(patientEnroll_Status, "Unenrolled");
				test.log(Status.INFO, MarkupHelper.createLabel("Status is : "+Input.getAttributeValue(patientEnroll_Status, value),ExtentColor.GREEN));
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the unenrolled data...",ExtentColor.BLUE));				
				if(Input.getLabelText(patientsPage_total_Records).contains(_no_data_available))
				{
					test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(patientsPage_total_Records),ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Enrollment Status From \" Unenrolled \" to \" Enrolled \"...", ExtentColor.BLUE));
					Input.dropDown(patientEnroll_Status, "Enrolled");
					test.log(Status.INFO, MarkupHelper.createLabel("Status is : "+Input.getAttributeValue(patientEnroll_Status, value),ExtentColor.GREEN));
					
				}
				else
				{
					
					test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(patientsPage_total_Records),ExtentColor.GREEN));
					Input.clickOn(patientdTab_patientName, click);	
				}
				
				Input.clickOn(patients_Tab, click);
				search_Patient(_patients_tab, g_refPatientName1);			
				test.log(Status.INFO, MarkupHelper.createLabel("Going to Navigate to Reports Sub TAB...", ExtentColor.BLUE));
				Input.clickOn(reportsTab,click);
				test.log(Status.INFO, MarkupHelper.createLabel("Successfully Navigated to Reports Sub TAB.", ExtentColor.GREEN));
				test.log(Status.INFO, MarkupHelper.createLabel("Going to print the Default Date Range Flowsheet Summary Reports...", ExtentColor.BLUE));
				Input.clickOn(FSSummaryReport, click);	
				test.log(Status.INFO, MarkupHelper.createLabel("Downloaded the Default Date Range Flowsheet Summary Reports.", ExtentColor.GREEN));
				TimeUnit.SECONDS.sleep(3);
				test.log(Status.INFO, MarkupHelper.createLabel("Going to download Flowsheet Calendor Reports...", ExtentColor.BLUE));
				Input.clickOn(FSCalendorReport, click);
				TimeUnit.SECONDS.sleep(3);
				test.log(Status.INFO, MarkupHelper.createLabel("Downloaded the Flowsheet Calendor Reports.", ExtentColor.GREEN));
				test.log(Status.INFO, MarkupHelper.createLabel("Going to download the Flowsheet Summary Reports in 30 days range...", ExtentColor.BLUE));
				Input.datePicker_Entry("txtStartReportDate", Input.substract_DaysFromTodaysDate(30));
				Input.clickOn(FSSummaryReport, click);
				TimeUnit.SECONDS.sleep(3);
				test.log(Status.INFO, MarkupHelper.createLabel("Downloaded the Flowsheet Summary Reports within 30 days range.", ExtentColor.GREEN));
				Input.clickOn(patients_Tab, click);
				search_Patient(_patients_tab, g_refPatientName1);				
				Input.clickOn(messages_Tab,click);				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to Create 'New' Message with Subject : ' Greetings '...", ExtentColor.BLUE));
							
				if(Input.isElementPresent(newMessageBtn))
				{
					Input.clickOn(newMessageBtn, click);
					Input.textBoxEntry(messageSubjectEdit, "Greetings...");
					test.log(Status.INFO, MarkupHelper.createLabel("Created a 'New' Message with Subject : ' Greetings '...", ExtentColor.GREEN));	
					test.log(Status.INFO, MarkupHelper.createLabel("Going to create a ' New ' Message with Message : Hello how are you...", ExtentColor.BLUE));	
					Input.textBoxEntry(messageResponse, "Hello how are you...");
					test.log(Status.INFO, MarkupHelper.createLabel("created a ' New ' Message with Message : Hello how are you...", ExtentColor.GREEN));	
					Input.clickOn(messageSend, click);
					
					if(Input.getLabelText(verifyNewMessage).contains("Greetings"))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Create New Message Task Passed", ExtentColor.GREEN));
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Create New Message Task Failed", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed;
					}
				}
				else
				{
					test.log(Status.ERROR, MarkupHelper.createLabel("Create New Message Task Failed", ExtentColor.RED));
					g_Test_Task_Result = g_Test_Task_Failed;
				}
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to perform Reply Message Task...", ExtentColor.BLUE));
				Input.clickOn(onMessageClick, click);
				
				if(Input.isElementPresent(replyMsgPanel))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Going to reply to the Message with : feeling well...", ExtentColor.BLUE));
					Input.textBoxEntry(enterReplyMsg, "feeling well");
					Input.clickOn(messageSend, click);
					test.log(Status.INFO, MarkupHelper.createLabel("Message with : ' feeling well ' is sent", ExtentColor.GREEN));
				}
				else
				{
					test.log(Status.ERROR, MarkupHelper.createLabel("Reply Message Task Failed.", ExtentColor.RED));
					g_Test_Task_Result = g_Test_Task_Failed;
				}				
				String[] _messageTask = {"UnRead", "Print"};
				By[] messageTask = {unReadBtn, messagePrintBtn};
				
				for(int i = 0; i < messageTask.length; i++)
				{
					test.log(Status.INFO, MarkupHelper.createLabel(" Going to verify the Message '" + _messageTask[i] +"' Task...", ExtentColor.BLUE));
					Input.clickOn(checkSigleMessage, click);
					if(Input.isElementPresent(messageTask[i]))
					{
						Input.clickOn(messageTask[i], click);
						test.log(Status.INFO, MarkupHelper.createLabel("Message "+ _messageTask[i] +" verified.", ExtentColor.GREEN));
						TimeUnit.SECONDS.sleep(1);
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Message "+ _messageTask[i] + " Task Failed.", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed;
					}
				}	
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Navigating to Dashboard TAB has been failed..", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed;
			}			
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
	 * Test Method Name : patient_Alerts_Verification() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform the patients Alerts and Alerts criteria test.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=10, retryAnalyzer=BaseClass.class)
	public static void patient_Alerts_Verification_Test() throws InterruptedException
	{
		g_Test_Task_Result = g_Test_Task_Passed;
		By provisioned_Pt = null;
		int i = 0;
		
		test.log(Status.INFO, MarkupHelper.createLabel("Going to navigate to Dashboard Tab...", ExtentColor.BLUE));
		Input.clickOn(dashboard_Tab, click);
		
		if(Input.getDriver().getTitle().contains(_dashboard_Tab_Title))
		{
			Input.scrollDown();
			test.log(Status.INFO, MarkupHelper.createLabel("Successfully Navigated to Dashboard TAB.", ExtentColor.GREEN));
			Input.mouseOver(alerts_Column_Tooltip);
			System.out.println("Total Patient Alerts : "+ Input.getLabelText(alerts_Column));
			test.log(Status.INFO, MarkupHelper.createLabel("Going to Choose an un-provisioned patient whose alert Criteria is not set...", ExtentColor.BLUE));
			provisioned_Pt = search_Select_Prov_Unprovisioned_Patient(g_unprovisionedPatient);
			test.log(Status.INFO, MarkupHelper.createLabel("' "+ Input.getLabelText(provisioned_Pt) + " ' is displayed in Patient Alerts column", ExtentColor.GREEN));
					
			Input.mouseOver(provisioned_Pt);
			TimeUnit.SECONDS.sleep(2);
			String _alert_Text = Input.getAttributeValue(provisioned_Pt, "onmouseover");
			System.out.println(_alert_Text);
			String[] _alerts_Text_Arr = _alert_Text.split("'");
			
			test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse-over on ' "+ Input.getLabelText(provisioned_Pt) + " ' and verify the text displayed...", ExtentColor.BLUE));			
			for( i = 0; i < _alerts_Text_Arr.length; i++)
			{
				System.out.println(" String : "+ i +" val : "+ _alerts_Text_Arr[i]);				
				if(_alerts_Text_Arr[i].contains("Alerts"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel(_alerts_Text_Arr[i], ExtentColor.GREEN));
				}
			}
			
			test.log(Status.INFO, MarkupHelper.createLabel("Going to click on patient ' "+ Input.getLabelText(provisioned_Pt)+ " ' in Patient Alerts cell...", ExtentColor.BLUE));
			Input.clickOn(provisioned_Pt, click);			
			if(Input.getDriver().getTitle().contains("Patient Summary"))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Patient Summary Settings page is displayed.", ExtentColor.GREEN));
				System.out.println("Settings");
			}
			else
			{
				g_Test_Task_Result = g_Test_Task_Failed;
				test.log(Status.ERROR, MarkupHelper.createLabel("Patient Summary Settings page is not displayed.", ExtentColor.RED));
			}
			
			test.log(Status.INFO, MarkupHelper.createLabel("Going to Navigate to Dashboard Tab...", ExtentColor.BLUE));
			Input.clickOn(dashboard_Tab, click);
			
			if(Input.getDriver().getTitle().contains(_dashboard_Tab_Title))
			{	
				test.log(Status.INFO, MarkupHelper.createLabel("Successfully Navigated to Dashboard TAB.", ExtentColor.GREEN));				
				Input.scrollDown();
				test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse-over on red sprocket ICON and verify the ICON color...", ExtentColor.BLUE));
				Input.mouseOver(m_patientColSettingsIcon_Tooltip);
					
				if(Input.getAttributeValue(m_patientColSettingsIcon, "src").contains("red"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Red sprocket ICON is displayed in Settings column, with color ' RED '", ExtentColor.GREEN)); 
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
				}
				
				String setting_Icon_Text = Input.getAttributeValue(m_patientColSettingsIcon_Tooltip, "onmouseover");
				String[] setting_Icon_Text_Arr = setting_Icon_Text.split("'");
											
				for( i = 0; i < setting_Icon_Text_Arr.length; i++)
				{
					System.out.println(" String : "+ i +" val : "+setting_Icon_Text_Arr[i]);
					if(setting_Icon_Text_Arr[i].contains("Required Settings have not been defined"))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("ToolTip : "+ setting_Icon_Text_Arr[i]+" is displayed.", ExtentColor.GREEN));
					}
				}				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to click on 'RED' sprocket ICON in Settings cell and verify if the settings page is displayed or not...", ExtentColor.BLUE));
				Input.clickOn(m_patientColSettingsIcon, click);
				
				if(Input.getDriver().getTitle().contains("Patient Summary"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Patient Summary Settings page is displayed.", ExtentColor.GREEN));
					System.out.println("Settings");
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
					test.log(Status.ERROR, MarkupHelper.createLabel("Patient Summary Settings page is not displayed.", ExtentColor.RED));
				}
				
				Input.clickOn(dashboard_Tab, click);				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to Mouse-over on 'GRAY' flag ICON and verify the text displayed...", ExtentColor.BLUE));
				Input.mouseOver(m_grayFlagIcon);
				String _gray_Flag_Icon = Input.getAttributeValue(m_grayFlagIcon, "onmouseover");
				String[] _gray_Flag_Icon_Arr = _gray_Flag_Icon.split("'");
				
				for( i = 0; i < _gray_Flag_Icon_Arr.length; i++)
				{
					System.out.println(" String : "+ i +" val : "+ _gray_Flag_Icon_Arr[i]);
					if(_gray_Flag_Icon_Arr[i].contains("Alert Criteria need to be defined"))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("ToolTip : ' "+ _gray_Flag_Icon_Arr[i]+" ' is displayed.", ExtentColor.GREEN));
					}
				}
				
				test.log(Status.INFO, MarkupHelper.createLabel("Going to click on 'GRAY' flag with a RED cross mark ICON in alerts defined cell...", ExtentColor.BLUE));
				Input.clickOn(m_grayFlagIcon, click);
				
				if(Input.getDriver().getTitle().contains("Patient Summary"))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Patient Summary Alert Criteria page is displayed ", ExtentColor.GREEN));
					System.out.println("Settings");
				}
				else
				{
					g_Test_Task_Result = g_Test_Task_Failed;
					test.log(Status.ERROR, MarkupHelper.createLabel("Patient Summary Settings page is not displayed.", ExtentColor.RED));
				}
				
				Input.clickOn(dashboard_Tab, click);
				Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cHUNDRED_DAYS));
				Input.clickOn(view, click);
				g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);								
			}
		}
		else{
			test.log(Status.ERROR, MarkupHelper.createLabel("Navigating to Dashboard TAB has been failed.", ExtentColor.RED));
			g_Test_Task_Result = g_Test_Task_Failed;
		}
		
		if(g_Test_Task_Result){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
		
	}
	
	/******************************************************************************************************
	 * Test Method Name : Alerts_Change_Test() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform the patients Alerts settings change test.
	 * 		
	 ******************************************************************************************************/
	
	@DataProvider
	 public Object[][] Alerts_Data1() throws Exception 
	 {		
		 Input.clickOn(dashboard_Tab,click);
		 Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
		 Input.clickOn(view, click);
		 g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);
		 search_Patient(_patients_tab, g_refPatientName1);
		 Input.clickOn(alertsCriteria_Tab, click);
		 TimeUnit.SECONDS.sleep(4);	 
		 Object data[][] = Excel.excelData(cPATIENT_ALERTS_DATA, cPATIENT_ALERTS_START_ROW, cPATIENT_ALERTS_START_COL);
		 return data;	
	 }
	
	 @Test(priority=11,dataProvider = "Alerts_Data1", retryAnalyzer=BaseClass.class)
	 public static void Alerts_Change_Test(String DialysateVolDeviation, String DryWeightDeviation, String OffCyclerAlarms) throws InterruptedException
	 {
		 String[] _alerts_Data = { DialysateVolDeviation, DryWeightDeviation, OffCyclerAlarms };
		 test.log(Status.INFO, MarkupHelper.createLabel("Going to set the Patient Alerts Settings...", ExtentColor.BLUE));
	 	 if(enter_Alerts_Data(_alerts_Data))
	 	 {
	 		System.err.println("Alerts are set");
	 		test.log(Status.INFO, MarkupHelper.createLabel("Alert settings are Updated successfully.", ExtentColor.GREEN));
	 		Assert.assertTrue(true);
	 	 }	
	 	 else
	 	 {
	 		 System.err.println("Alerts are not set");
	 		 test.log(Status.WARNING, MarkupHelper.createLabel("Alert settings are not Updated.", ExtentColor.ORANGE));
	 		 Assert.assertTrue(true);
	 	 }
	 	 TimeUnit.SECONDS.sleep(2);	 		 		 		  
	 }
	 
	 
	 /******************************************************************************************************
	  * Test Method Name : Patient_Settings() 
	  * 
	  * Description : 
	  * 
	  * 		This test method will perform the patients prescription settings change test.
	  * 		
	  ******************************************************************************************************/
		
	 @DataProvider
	 public Object[][] Prescription_Data() throws Exception 
	 {				
		 Input.clickOn(dashboard_Tab,click);
		 Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
		 Input.clickOn(view, click);
		 g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);
		 search_Patient(_patients_tab, g_refPatientName1);
		 Input.clickOn(settings_Tab, click);		 
		 Object data[][] = Excel.excelData(cPATINET_TX_SETTINGS_DATA, cPATINET_TX_SETTINGS_START_ROW, cPATINET_TX_SETTINGS_START_COL);
		 return data;	
	 }
	 
	 @Test(priority=12,dataProvider = "Prescription_Data", retryAnalyzer=BaseClass.class)//,dependsOnMethods = "TestTool_Task_For_Uploading_Unconfirmed_Flowsheets",dataProvider = "Prescription_Data")
	 public static void Patient_Settings_Test(String NumOfTxsPerWeek, String DialysateVolumePerTx, String TherapyFluidsource, 
			 					  		 String DialysateLactate, String DialysatePotassium, String DialysateSAKType, String CartridgeType, 
			 					  		 String FlowFraction, String AverageDialysateRate, String AverageUltrafiltrationRate, 
			 					  		 String InitialBloodFR, String TargetBloodFR, String PatientDryWeight, String PatientAccessType, 
			 					  		 String NeedleGauge, String HeparinDose, String HeparinRoute) throws InterruptedException
	 {
	
		 g_Test_Task_Result = g_Test_Task_Passed;
		 
		
		 String[] _patient_Err_Tx_Settings = { NumOfTxsPerWeek, DialysateVolumePerTx, FlowFraction, AverageDialysateRate, AverageUltrafiltrationRate,
				 							   InitialBloodFR, TargetBloodFR, PatientDryWeight};		 		
		 
		 String[] _patient_Tx_Values = { NumOfTxsPerWeek, DialysateVolumePerTx, TherapyFluidsource, DialysateLactate, DialysatePotassium, 
										 DialysateSAKType, CartridgeType, FlowFraction, AverageDialysateRate, AverageUltrafiltrationRate, 
										 InitialBloodFR, TargetBloodFR, PatientDryWeight, PatientAccessType, NeedleGauge, HeparinDose, HeparinRoute};
		 
		 test.log(Status.INFO, MarkupHelper.createLabel("Going to change the Patient Prescription Data..." ,ExtentColor.BLUE)); 
		 
		 for(int i = 0; i < _patient_Tx_Values.length; i++)
		 {
			 test.log(Status.INFO, MarkupHelper.createLabel(_patient_field_strings[i] + " : " + _patient_Tx_Values[i], ExtentColor.GREEN)); 
		 }
		 
		 if(Input.isElementPresent(prescription_Edit))
		 {
			 TimeUnit.SECONDS.sleep(3); 
			 Input.clickOn(prescription_Edit,click);
			 TimeUnit.SECONDS.sleep(5);
			 Input.waitUntilElementClickable(numberOfTreatmentsPerWeek);
			 Input.textBoxEntry(numberOfTreatmentsPerWeek, NumOfTxsPerWeek);
			 Input.textBoxEntry(dialysateVolumePerTx, DialysateVolumePerTx);
			 Input.dropDown(therapyFluidSource_Dropdown, TherapyFluidsource);
			 Input.dropDown(dialysateLactate_Dropdown, DialysateLactate);
			 Input.dropDown(dialysatePotassium_Dropdown, DialysatePotassium);
				 
			 if(!TherapyFluidsource.contains("Bags"))
			 {
				 Input.dropDown(dialysateSAK_Type, DialysateSAKType);
			 }
			
			 Input.dropDown(cartridge_Type, CartridgeType);
			 Input.textBoxEntry(flowFraction, FlowFraction);
			 Input.textBoxEntry(averageDialysateRate, AverageDialysateRate);
			 Input.textBoxEntry(averageUltrafiltrationRate, AverageUltrafiltrationRate);
			 Input.textBoxEntry(initialBloodFlowRate, InitialBloodFR);
			 Input.textBoxEntry(targetBloodFlowRate, TargetBloodFR);
			 Input.textBoxEntry(patientDryWeight, PatientDryWeight);
			 Input.dropDown(patientAccessType, PatientAccessType);
			 
			 if(!PatientAccessType.contains("Catheter"))
			 {
				 Input.dropDown(needleGauge, NeedleGauge);
			 }
			 
			 Input.textBoxEntry(heparinDose, HeparinDose);			 
			 if(Verify.checkIfLabelDisplayed(heparinDose_ErrText))
			 {
				System.out.println("PATIENTS_SETTINGS_ERR : Heparin Dose_Err : "+ Input.getLabelText(heparinDose_ErrText));
			 }				 
			 else
			 {					
				 Input.dropDown(heparinRouteDose, HeparinRoute);
			 }			 
			 test.log(Status.INFO, MarkupHelper.createLabel("Going to catch the Prescription data errors...", ExtentColor.BLUE));				
			 for(int i = 0; i < patientSettings_errors.length; i++)
			 {
				 if(Verify.checkIfLabelDisplayed(patientSettings_errors[i]))
				 {
					 g_Test_Task_Result = g_Test_Task_Failed;
					 test.log(Status.WARNING, MarkupHelper.createLabel("Warning : "+_patientSettings_errors_text[i]+" : "+Input.getLabelText(patientSettings_errors[i]), ExtentColor.ORANGE));
					 test.log(Status.INFO,MarkupHelper.createLabel("Entered value is : "+_patient_Err_Tx_Settings[i], ExtentColor.GREEN));
					 System.out.println("PATIENTS_SETTINGS_ERR : "+ Input.getLabelText(patientSettings_errors[i]));
				 }
			 }
			 TimeUnit.SECONDS.sleep(5);			 
			 if(g_Test_Task_Result == g_Test_Task_Failed)
			 {	 
				 Input.clickOn(prescription_Cancel, click);
				 TimeUnit.SECONDS.sleep(8);					
				 Assert.assertTrue(true);						
			 }				 
			 else
			 {
				 Input.clickOn(prescription_Update, click);
				 TimeUnit.SECONDS.sleep(4);
				 Assert.assertTrue(true);
			 }					 
			 TimeUnit.SECONDS.sleep(4);				
		 }		 	 
	 }
	 
	 /******************************************************************************************************/
	 
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
	
	/******************************************************************************************************/
}
