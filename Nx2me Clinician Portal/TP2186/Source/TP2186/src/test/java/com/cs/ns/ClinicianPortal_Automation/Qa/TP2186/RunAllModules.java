package com.cs.ns.ClinicianPortal_Automation.Qa.TP2186;

import java.awt.Desktop;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cs.ns.ClinicianPortal_Automation.Base_class.BaseClass;
import com.cs.ns.ClinicianPortal_Automation.utilities.ReusableMethods;
import com.reusecode.extentreports.Listener;
import com.reusecode.iodata.Input;
import com.reusecode.utilities.Excel;
import com.reusecode.utilities.TestTool;

@Listeners(com.reusecode.extentreports.Listener.class)
public class RunAllModules extends ReusableMethods 
{  
	@BeforeClass
	public void setup() throws IOException, InterruptedException 
	{
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
	 * CP UI VERIFICATION
	 * ****************************************************************************************************/
	
	@Test(priority=0, retryAnalyzer=BaseClass.class)
	public void Home_Page_Sections_And_Homepage_Tabs_Test() throws InterruptedException, IOException
	{	
		CP_UIVerification.Home_Page_Sections_And_Homepage_Tabs_Test();
	}
	
	/******************************************************************************************************
	 * @throws IOException 
	 * ****************************************************************************************************/
	
	@Test(priority=1, dependsOnMethods = "Home_Page_Sections_And_Homepage_Tabs_Test",retryAnalyzer=BaseClass.class)
	public void Home_Page_Tabs_Navigation_Label_Text_And_Headers_Verification_Test() throws InterruptedException, IOException
	{		
		CP_UIVerification.Home_Page_Tabs_Navigation_Label_Text_And_Headers_Verification_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=2, dependsOnMethods = "Home_Page_Sections_And_Homepage_Tabs_Test",retryAnalyzer=BaseClass.class)
	public void HomePage_Sub_Tabs_Navigation_And_Links_Verification_Test() throws InterruptedException
	{		
		CP_UIVerification.HomePage_Sub_Tabs_Navigation_And_Links_Verification_Test();
	}

	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=3, dependsOnMethods = "Home_Page_Sections_And_Homepage_Tabs_Test",retryAnalyzer=BaseClass.class)
	public void Patient_Page_Sub_Tabs_Navigation_Test() throws InterruptedException
	{		
		CP_UIVerification.Patient_Page_Sub_Tabs_Navigation_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=4,retryAnalyzer=BaseClass.class)
	public void NxMoveAdmin_Test_() throws InterruptedException
	{
		CP_UIVerification.NxMoveAdmin_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=5, dependsOnMethods="NxMoveAdmin_Test_",retryAnalyzer=BaseClass.class)
	public void NxMove_HomePage_Sub_Tabs_Navigation_And_Link_Verification_Test() throws InterruptedException
	{		
		CP_UIVerification.NxMove_HomePage_Sub_Tabs_Navigation_And_Link_Verification_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=6, dependsOnMethods="NxMoveAdmin_Test_",retryAnalyzer=BaseClass.class)
	public void Changing_Data_In_NxMoveAdmin_Test() throws InterruptedException
	{		
		CP_UIVerification.Changing_Data_In_NxMoveAdmin_Test();			
		Input.getDriver().quit();
	}
	
	/******************************************************************************************************
	 *FUNCTIONALITY VERIFICATION
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerConfigurationException 
	 * ****************************************************************************************************/
		
	@Test(priority=7,retryAnalyzer=BaseClass.class)
	public static void TestToolTask_For_Uploading_The_50_Flowsheets_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException 
	{		
		FunctionalityVerification.TestToolTask_For_Uploading_The_50_Flowsheets_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=8, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Login_To_Portal_And_Verify_Patient_Info_Actions_Test() throws InterruptedException 
	{		
		FunctionalityVerification.Login_To_Portal_And_Verify_Patient_Info_Actions_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
			
	@Test(priority=9, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Perform_Flowsheet_Actions_Test() throws InterruptedException
	{		
		FunctionalityVerification.Perform_Flowsheet_Actions_Test();
	}
	
	/******************************************************************************************************
	 * @throws InterruptedException 
	 * ****************************************************************************************************/
	
	@Test(priority=10, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Perform_Flowhseet_AddNotes_Activities_Test() throws InterruptedException
	{		
		FunctionalityVerification.Perform_Flowhseet_AddNotes_Activities_Test();
	}
	
	/**
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws IOException 
	 * @throws TransformerConfigurationException ****************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=11, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Perform_Print_Flowsheet_Actions_Test() throws InterruptedException, TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	{	
		FunctionalityVerification.Perform_Print_Flowsheet_Actions_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=12, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Perform_Flowhseet_Archive_Activities_Test() throws InterruptedException
	{
		FunctionalityVerification.Perform_Flowhseet_Archive_Activities_Test();
	}
	
	/******************************************************************************************************
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=13, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Messages_Task_Test() throws InterruptedException, IllegalArgumentException, IOException
	{
		FunctionalityVerification.Messages_Task_Test();
	}
	
	/******************************************************************************************************
	 * 
	 * ****************************************************************************************************/
	
	@Test(priority=14, dependsOnMethods = "TestToolTask_For_Uploading_The_50_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Patient_List_Enrollment_UnEnrollment_Status_Test() throws InterruptedException
	{
		FunctionalityVerification.Patient_List_Enrollment_UnEnrollment_Status_Test();
	}
	
	/**
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerConfigurationException ***************************************************************************************************
	 * 
	 *****************************************************************************************************/
	
	@Test(priority=15,retryAnalyzer=BaseClass.class)
	public void TestTool_Task_For_Uploading_Unconfirmed_Flowsheets_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException 
	{		
		FunctionalityVerification.TestTool_Task_For_Uploading_Unconfirmed_Flowsheets_Test();
	}
	
	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/
	
	@Test(priority=16, dependsOnMethods = "TestTool_Task_For_Uploading_Unconfirmed_Flowsheets_Test",retryAnalyzer=BaseClass.class)
	public void Patient_Page_Sub_Tabs_Functionality_Test() throws InterruptedException
	{		
		FunctionalityVerification.Patient_Page_Sub_Tabs_Functionality_Test();		
	}
	
	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/
	
	@Test(priority=17,retryAnalyzer=BaseClass.class)
	public void Patient_Alerts_Verification_Test() throws InterruptedException
	{
		FunctionalityVerification.patient_Alerts_Verification_Test();			
	}
	
	/*****************************************************************************************************
	 * 
	 *****************************************************************************************************/
	@DataProvider
	 public Object[][] Patient_Alerts_Data() throws Exception 
	 {		
		 Input.clickOn(dashboard_Tab,click);
		 Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(cFIFTY_DAYS));
		 Input.clickOn(view, click);
		 g_refPatientName1 = Input.getLabelText(dashboardTab_patientName);
		 search_Patient(_patients_tab, g_refPatientName1);
		 Input.clickOn(alertsCriteria_Tab, click);	  
		 Object data[][] = Excel.excelData(cPATIENT_ALERTS_DATA, cPATIENT_ALERTS_START_ROW, cPATIENT_ALERTS_START_COL);
		 return data;	
	 }
	
	 @Test(priority=18, dataProvider = "Patient_Alerts_Data",retryAnalyzer=BaseClass.class)
	 public void Alerts_Change_Test1(String DialysateVolDeviation, String DryWeightDeviation, String OffCyclerAlarms) throws InterruptedException
	 {
		 FunctionalityVerification.Alerts_Change_Test(DialysateVolDeviation, DryWeightDeviation, OffCyclerAlarms);
	 }
	 
	/******************************************************************************************************
	 * 
	 ******************************************************************************************************/
	
	 @DataProvider
	 public Object[][] Patient_Prescription_Data() throws Exception 
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
	 
	 @Test(priority=19, dataProvider = "Patient_Prescription_Data",retryAnalyzer=BaseClass.class)//,dependsOnMethods = "TestTool_Task_For_Uploading_Unconfirmed_Flowsheets",dataProvider = "Prescription_Data")
	 public void Patient_Settings_Test1(String NumOfTxsPerWeek, String DialysateVolumePerTx, String TherapyFluidsource, 
			 					  String DialysateLactate, String DialysatePotassium, String DialysateSAKType, String CartridgeType, 
			 					  String FlowFraction, String AverageDialysateRate, String AverageUltrafiltrationRate, 
			 					  String InitialBloodFR, String TargetBloodFR, String PatientDryWeight, String PatientAccessType, 
			 					  String NeedleGauge, String HeparinDose, String HeparinRoute) throws InterruptedException
	 {
	
		 FunctionalityVerification.Patient_Settings_Test(NumOfTxsPerWeek, DialysateVolumePerTx, TherapyFluidsource, DialysateLactate, DialysatePotassium, DialysateSAKType, 
				 									CartridgeType, FlowFraction, AverageDialysateRate, AverageUltrafiltrationRate, InitialBloodFR, TargetBloodFR, 
				 									PatientDryWeight, PatientAccessType, NeedleGauge, HeparinDose, HeparinRoute);
	 }

	/******************************************************************************************************
	 * FLOWSHEET STATISTICS VERIFICATION
	 ******************************************************************************************************/
	 @Test(priority=20,retryAnalyzer=BaseClass.class)
	 public void Flowsheet_Statistics_Test() throws InterruptedException, IOException
	 {  		
		 Input.getDriver().quit();		 
		 TP2186_FlowsheetStatistics.Flowsheet_Statistics_Test();
	 }
		
	 /******************************************************************************************************
	  * 
	  ******************************************************************************************************/
	
	 @Test(priority=21,retryAnalyzer=BaseClass.class)
	 public void Upload_50_Flwosheets_And_Do_Flowsheet_Statistics_Test1() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	 {		  			
		  TP2186_FlowsheetStatistics.Upload_50_Flwosheets_And_Do_Flowsheet_Statistics_Test();
	 }
		  
	 /******************************************************************************************************
	  * 
	  ******************************************************************************************************/  
	 @Test(priority=22,retryAnalyzer=BaseClass.class)
	 public void Upload_10_Unconfirmed_And_5_Confirmed_Flowhseets_And_Do_Verification_Test() throws InterruptedException, IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	 {  
		 TP2186_FlowsheetStatistics.Upload_10_Unconfirmed_And_5_Confirmed_Flowhseets_And_Do_Verification_Test();
	 }
		  
	 /******************************************************************************************************
	  * 
	  ******************************************************************************************************/
		  
	 @Test(priority=23,retryAnalyzer=BaseClass.class)
	 public void Alerts_Test_For_atert0_1_3_Test() throws InterruptedException, TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	 {  
		 TP2186_FlowsheetStatistics.Alerts_Test_For_atert0_1_3_Test();
	 }
	 
	 /******************************************************************************************************
	  * 
	  ******************************************************************************************************/
	 @Test(priority=24,retryAnalyzer=BaseClass.class)
	 public void DB_Delete_And_PatientProvision_Task_Test() throws TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, InterruptedException
	 {		  
		 TP2186_FlowsheetStatistics.DB_Delete_And_PatientProvision_Task_Test();
	 }	  
	  
	 /******************************************************************************************************
	  * 
	  ******************************************************************************************************/
	 
	 @AfterClass
	 public void close() throws IOException
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
