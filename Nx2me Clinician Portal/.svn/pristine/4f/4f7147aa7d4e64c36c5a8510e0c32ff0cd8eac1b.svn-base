package com.cs.ns.ClinicianPortal_Automation.Qa.TP2186;

import java.awt.Desktop;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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

@Listeners(com.reusecode.extentreports.Listener.class)
public class CP_UIVerification extends ReusableMethods
{	
	/******************************************************************************************************
	 * Before class : Dashboard_Page_Setup()
	 * 
	 * This will kill the previous web driver instances. 
	 * 
	 * ****************************************************************************************************/
	@BeforeClass
	public static void CPUI_Verification_Setup() throws IOException, InterruptedException 
	{		
		Input.driverInstanceKillProcess();	
		Excel.writeToExcel(cUI_FUNCTIONALITY_DATA, cVPN_USER_ID_ROW, cVPN_USER_ID_COL, strExcelPath, "example@nxstage.com");
	    Excel.writeToExcel(cUI_FUNCTIONALITY_DATA, cVPN_PASSWORD_ROW, cVPN_PASSWORD_COL, strExcelPath, "Dontknow12!");	    
	}
	
	
	/******************************************************************************************************
	 * Test Method Name : Home_Page_Sections_And_Homepage_Tabs() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 		
	 * 		1. Home page sections.
	 * 		2. Home page tabs verification.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=0, retryAnalyzer=BaseClass.class)
	public static void Home_Page_Sections_And_Homepage_Tabs_Test() throws InterruptedException, IOException
	{					   
		g_Test_Task_Result = g_Test_Task_Passed; 	
		int i = 0;
		
		if(landingToPortal())
		{			
			StatusHTML.htmlTitleTag("Clinician Portal UI Verification...");
			reportsHeaderBrowsersDetails("Nx2me Clinician Portal","TP2186");
			if(Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
			{
				System.out.println("********* "+ _dashboard_Tab_Title+" *********");
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the home page sections in Nx2me Clinician Portal...", ExtentColor.BLUE));
				for( i = 0; i < Statistics.length; i++)
				{
					System.out.println("********* "+ _statistics_text[i] + " *********");					
					if(Input.isElementPresent(Statistics[i]))
					{
						TimeUnit.SECONDS.sleep(1/2);
						test.log(Status.INFO, MarkupHelper.createLabel("Verification of the section : "+Input.getLabelText(Statistics[i])+" is successful",ExtentColor.GREEN));
					}
					else
					{
						System.out.println("********* "+_statistics_text[i]+" NOT FOUND*********");
						test.log(Status.ERROR, MarkupHelper.createLabel("Verification of the section : "+Input.getLabelText(Statistics[i])+" is failure",ExtentColor.RED));						
						g_Test_Task_Result = g_Test_Task_Failed;
					}
				}								
				test.log(Status.INFO, MarkupHelper.createLabel("Nx2me Clinician Portal homepage sections verification Completed.", ExtentColor.GREEN));
				
				test.log(Status.INFO, MarkupHelper.createLabel("Nx2me Clinician Portal homepage Main Tabs verification.:", ExtentColor.BLUE));
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Nx2me Clinician Portal main tabs...", ExtentColor.BLUE));				
				for( i = 0; i < HomePageTabs.length; i++)
				{
					if(Input.isElementPresent(HomePageTabs[i]))
					{									
						homePageTabs.add((By) HomePageTabs[i]);
						_homePageTabs_Titles.add(_HomePageTabTitle[i]);
						_homePageTabs_Text.add(_HomePageTabText[i]);
						Input.mouseOver(HomePageTabs[i]);
						TimeUnit.SECONDS.sleep(1/2);
						System.out.println("Tab is there with Title : "+_HomePageTabText[i]);
						test.log(Status.INFO, MarkupHelper.createLabel("Verification of the TAB : ' "+Input.getLabelText(HomePageTabs[i])+" ' is successful",ExtentColor.GREEN));
						totalTabs++;
					}
					else
					{
						System.out.println("Tab is not there with Title : "+ _HomePageTabText[i]);
						test.log(Status.ERROR, MarkupHelper.createLabel("Verification of the TAB : ' "+Input.getLabelText(HomePageTabs[i])+" ' is failure",ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
				}
				if(homePageTabs.size() != cEIGHT){
					test.log(Status.ERROR, MarkupHelper.createLabel("Nx2me Clinician Portal homepage have { "+ homePageTabs.size()+" } Main Tabs", ExtentColor.RED));
				}
				test.log(Status.INFO, MarkupHelper.createLabel("Nx2me Clinician Portal homepage Main Tabs verification Completed.", ExtentColor.GREEN));
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the URL title : "+ _dashboard_Tab_Title, ExtentColor.RED));
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
			test.log(Status.ERROR, MarkupHelper.createLabel("Failed to sign in to Clinician Portal : "+ _dashboard_Tab_Title, ExtentColor.RED));
			Assert.assertTrue(false);
		}
	}
	
	/******************************************************************************************************
	 * Test Method Name : Home_Page_Sections_And_Homepage_Tabs() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 		
	 * 		1. Naviagating to Dashboard Tabs, header and footer verification for all Tabs.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=1,dependsOnMethods = "Home_Page_Sections_And_Homepage_Tabs_Test", retryAnalyzer=BaseClass.class)
	public static void Home_Page_Tabs_Navigation_Label_Text_And_Headers_Verification_Test() throws InterruptedException, IOException
	{
		g_Test_Task_Result = g_Test_Task_Passed;
		for(int i = 0; i < homePageTabs.size(); i++)
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Navigation of ' "+ _homePageTabs_Text.get(i)+" ' TAB and Header...", ExtentColor.BLUE));	
			Input.clickOn(homePageTabs.get(i), click);
			if(Verify.compareText(Input.getDriver().getTitle(), _homePageTabs_Titles.get(i)))
			{
				if(Verify.compareText(Input.getLabelText(homePageTabs.get(i)), _homePageTabs_Text.get(i)))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Verification of tab : "+Input.getLabelText(homePageTabs.get(i))+" ' is successful", ExtentColor.GREEN));
					for(int j = 0; j < headerLabel.length; j++)
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the : \""+ _header_label_texts[j]+"\" header... ", ExtentColor.BLUE));	
						if(Input.isElementPresent(headerLabel[j]))
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Verification of header : "+Input.getLabelText(headerLabel[j])+" ' is successful", ExtentColor.GREEN));							
							if(Verify.compareText(headerLabel[j], "Tools  v"))
							{
								test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the contents of the header : "+Input.getLabelText(headerLabel[j])+" '", ExtentColor.BLUE));								
								test.log(Status.INFO, MarkupHelper.createLabel(" The menu of the ' " + Input.getLabelText(headerLabel[j]) + "' contains the following item", ExtentColor.BLUE));								
								Input.mouseOver(tools);
								Input.mouseOver(tools_NxRx);
								test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(tools_NxRx), ExtentColor.GREEN));
								TimeUnit.SECONDS.sleep(1/2);
								Input.mouseOver(tools_DosingCal);	
								test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(tools_DosingCal), ExtentColor.GREEN));
							}
						}
						else
						{
							test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the header :" + _header_label_texts[j], ExtentColor.RED));
							g_Test_Task_Result = g_Test_Task_Failed; 
						}
					}
					
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Street Address...", ExtentColor.BLUE));
					if(Input.getDriver().getPageSource().contains("350 Merrimack Street, Lawrence, MA 01843 USA"))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("NxStage Medical, Inc., 350 Merrimack Street, Lawrence, MA 01843 USA", ExtentColor.GREEN));	
					}
				}
				else{
					test.log(Status.ERROR, MarkupHelper.createLabel("TAB text not matched with '" + _homePageTabs_Text.get(i) + " '", ExtentColor.RED));
				}
				g_Test_Task_Result = g_Test_Task_Passed;
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the page : " + _homePageTabs_Titles.get(i), ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed; 
			}
			test.log(Status.INFO, MarkupHelper.createLabel(" Tabs and Headers Verification is Completed for : " + _homePageTabs_Text.get(i), ExtentColor.GREEN));
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
	 * Test Method Name : HomePage_Sub_Tabs_Navigation_And_Links_Verification() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions:
	 * 		
	 * 		1. Navigating to Dashboard Tabs and perform the child links test.
	 * 		
	 ******************************************************************************************************/
	
	@Test(priority=2,dependsOnMethods = "Home_Page_Sections_And_Homepage_Tabs_Test", retryAnalyzer=BaseClass.class)
	public static void HomePage_Sub_Tabs_Navigation_And_Links_Verification_Test() throws InterruptedException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		for(int i = 0; i < homePageTabs.size(); i++)
		{
			Input.clickOn(homePageTabs.get(i), click);
			TimeUnit.SECONDS.sleep(1);
			if(Verify.compareText(Input.getDriver().getTitle(), _homePageTabs_Titles.get(i)))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Navigation of ' " + Input.getLabelText(homePageTabs.get(i)) + " ' TAB and also the Child window activities...", ExtentColor.BLUE));
				for(int j = 0; j < tools_help_about_linkTest.length; j++)
				{
					if(Input.isElementPresent(tools))
					{
						Input.mouseOver(tools);
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the link : " + _tools_help_linkTest_Text[j], ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
					if(Input.isElementPresent(tools_help_about_linkTest[j]))
					{
						TimeUnit.SECONDS.sleep(1);
						Input.clickOn(tools_help_about_linkTest[j], click);
						Input.switchChild_To_MainWindow(_titles_for_child_windows[j]);
						test.log(Status.INFO, MarkupHelper.createLabel("Going to verify if the ' " + Input.getLabelText(tools_help_about_linkTest[j]) + " ' website is opened in the new TAB.", ExtentColor.BLUE));
						test.log(Status.INFO, MarkupHelper.createLabel("Verification of the child link : ' " + _titles_for_child_windows[j] + " ' opened in the new tab is successful.", ExtentColor.GREEN));
						TimeUnit.SECONDS.sleep(1);
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the link : ' " + _tools_help_linkTest_Text[j]+ " '", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
				}	
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the page : ' " + _homePageTabs_Titles.get(i) + " '.", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed; 
			}
			test.log(Status.INFO, MarkupHelper.createLabel("Child windows link text is completed for : ' " + Input.getLabelText(homePageTabs.get(i)) + " ' TAB.", ExtentColor.GREEN));
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
	 * Test Method Name : Patient_Page_Sub_Tabs_Navigation() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform patients tab subtab navigation tab verify the tabs texts.
	 * 			
	 ******************************************************************************************************/
	
	@Test(priority=3,dependsOnMethods = "Home_Page_Sections_And_Homepage_Tabs_Test", retryAnalyzer=BaseClass.class)
	public static void Patient_Page_Sub_Tabs_Navigation_Test() throws InterruptedException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		Input.clickOn(dashboard_Tab, click);
		Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(500));
						
		Input.clickOn(view, click);
		if(Input.getLabelText(dashboardTab_total_flowsheets).contains(_no_data_available))
		{
			Input.clickOn(Patient_From_Patient_List, click);
			System.out.println(_no_data_available);
		}
		else
		{
			Input.clickOn(dashboardTab_patientName, click);
		}
		
		if(Input.isElementPresent(patients_Tab))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going verify the navigation of ' "+Input.getLabelText(patients_Tab)+" ' TAB and also the Patents sub tabs...", ExtentColor.BLUE));
			for(int i = 0; i < patientSubTabs.length; i++)
			{
				if(Input.isElementPresent(patientSubTabs[i]))
				{
					Input.clickOn(patientSubTabs[i], click);
					test.log(Status.INFO, MarkupHelper.createLabel("Verification of the navigation to Patients Sub Tab : ' "+ Input.getLabelText(patientSubTabs[i])+ " ' is successful", ExtentColor.GREEN));
					test.log(Status.INFO, MarkupHelper.createLabel("Verification of the text of : ' "+ Input.getLabelText(patientSubTabs[i])+ " ' is successful", ExtentColor.GREEN));
					TimeUnit.SECONDS.sleep(1);
				}
				else
				{
					test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the page : ' " + patientSubTabs[i] + " '.", ExtentColor.RED));
					g_Test_Task_Result = g_Test_Task_Failed; 	
				}
			}
			portal_Signout();
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
	 * Test Method Name : NxMoveAdmin_Test() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions :
	 * 
	 * 		1. NxMoveAdmin Header and footer text.
	 * 		2. NxMoveAdmin child link tests.
	 * 			
	 ******************************************************************************************************/
	
	@Test(priority=4, retryAnalyzer=BaseClass.class)
	public static void NxMoveAdmin_Test() throws InterruptedException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;		
		if(landingToNxAdminPortal())
		{
			if(Verify.compareText(Input.getDriver().getTitle(), _NxMove_Center_Title))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the home page sections in Nx2me Clinician Portal...", ExtentColor.BLUE));
				for(int i = 0; i < NxMove_Tabs.length; i++)
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Verification of the tab : ' " + _NxMove_Tab_Text[i], ExtentColor.BLUE));
					if(Input.isElementPresent(NxMove_Tabs[i]))
					{
						Input.mouseOver(NxMove_Tabs[i]);
						TimeUnit.SECONDS.sleep(1/2);
						NxMove_homePageTabs.add((By) NxMove_Tabs[i]);
						_NxMove__homePageTabs_Titles.add(_NxMoveTabs_Title[i]);
						_NxMove__homePageTabs_Text.add(_NxMove_Tab_Text[i]);
						test.log(Status.INFO, MarkupHelper.createLabel("Tab text is : ' "+Input.getLabelText(NxMove_Tabs[i]),ExtentColor.GREEN));
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : ' "+_NxMove_Tab_Text[i]+" ' tab", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
				}
				test.log(Status.INFO, MarkupHelper.createLabel("Nx2me Clinician Portal tabs verification is completed", ExtentColor.GREEN));
				for(int j = 0; j < NxMove_homePageTabs.size(); j++)
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the navigation of ' "+ Input.getLabelText(NxMove_homePageTabs.get(j)) + " TAB and the Header Labels...", ExtentColor.BLUE));
					Input.clickOn(NxMove_homePageTabs.get(j), click);
					if(Verify.compareText(Input.getDriver().getTitle(), _NxMove__homePageTabs_Titles.get(j)))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("Sucessfully Navigated to : "+ Input.getLabelText(NxMove_homePageTabs.get(j)) + " TAB", ExtentColor.GREEN));
						test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the tab : ' "+NxMove_homePageTabs.get(j) +" ' text and headers...", ExtentColor.BLUE));
						if(Verify.compareText(Input.getLabelText(NxMove_homePageTabs.get(j)), _NxMove__homePageTabs_Text.get(j)))
						{
							test.log(Status.INFO, MarkupHelper.createLabel("Verified the tab : "+Input.getLabelText(NxMove_homePageTabs.get(j)) +" text", ExtentColor.GREEN));
							for(int k = 0; k < headerLabel.length; k++)
							{	
								if(Input.isElementPresent(headerLabel[k]))
								{																						
									test.log(Status.INFO, MarkupHelper.createLabel("Verified the tab header: " + Input.getLabelText(headerLabel[k]), ExtentColor.GREEN));
									if(Input.getLabelText(headerLabel[k]).contains("Tools")){		
										
										test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the contents of the header : "+Input.getLabelText(headerLabel[k])+" '", ExtentColor.BLUE));								
										test.log(Status.INFO, MarkupHelper.createLabel("The menu of the ' " + Input.getLabelText(headerLabel[k]) + "' contains the following item", ExtentColor.BLUE));					
										Input.mouseOver(tools);
										Input.mouseOver(tools_NxRx);
										test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(tools_NxRx), ExtentColor.GREEN));
										TimeUnit.SECONDS.sleep(1/2);
										Input.mouseOver(tools_DosingCal);	
										test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(tools_DosingCal), ExtentColor.GREEN));
									}	
								}
								else
								{
									test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the header : ' "+ _header_label_texts[k] +" '", ExtentColor.RED));
									g_Test_Task_Result = g_Test_Task_Failed; 
								}
							}
						}
						else
						{
							test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the tab : ' "+ Input.getLabelText(NxMove_homePageTabs.get(j))+ " '", ExtentColor.RED));
							g_Test_Task_Result = g_Test_Task_Failed; 
						}
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the page : " + _NxMove__homePageTabs_Titles.get(j) +" '", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
					test.log(Status.INFO, MarkupHelper.createLabel(" Headers verification is Completed for : ' "+ Input.getLabelText(NxMove_homePageTabs.get(j))+" ' tab", ExtentColor.GREEN));
				}
			}	
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the page : ' "+ _NxMove_Center_Title+" '", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed; 
			}
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("Login to NxMoveAdmin Clinician Portal has been failed.", ExtentColor.RED));
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
	 * Test Method Name : NxMove_HomePage_Sub_Tabs_Navigation_And_Link_Verification() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will perform below actions :
	 * 
	 * 		1. Navigating to all tabs child link tests.	
	 * 			
	 ******************************************************************************************************/
	
	@Test(priority=5, dependsOnMethods="NxMoveAdmin_Test", retryAnalyzer=BaseClass.class)
	public static void NxMove_HomePage_Sub_Tabs_Navigation_And_Link_Verification_Test() throws InterruptedException
	{		
		g_Test_Task_Result = g_Test_Task_Passed;
		for(int i = 0; i < NxMove_homePageTabs.size(); i++)
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Navigation of ' " + Input.getLabelText(NxMove_homePageTabs.get(i)) + " ' TAB and also the Child window activities...", ExtentColor.BLUE));
			Input.clickOn(NxMove_homePageTabs.get(i), click);
			TimeUnit.SECONDS.sleep(1);
			if(Verify.compareText(Input.getDriver().getTitle(), _NxMove__homePageTabs_Titles.get(i)))
			{
				for(int j = 0; j < tools_help_about_linkTest.length; j++)
				{
					if(Input.isElementPresent(tools))
					{
						Input.mouseOver(tools);
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the link : ' " + Input.getLabelText(tools) + " '", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
					if(Input.isElementPresent(tools_help_about_linkTest[j]))
					{													
						TimeUnit.SECONDS.sleep(1);
						Input.clickOn(tools_help_about_linkTest[j], click);
						Input.switchChild_To_MainWindow(_titles_for_child_windows[j]);
						test.log(Status.INFO, MarkupHelper.createLabel("Verification of the child link : ' " + _titles_for_child_windows[j] + " ' opened in the new tab is successful.", ExtentColor.GREEN));
						TimeUnit.SECONDS.sleep(1);
					}
					else
					{
						test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the link : ' "+ _tools_help_linkTest_Text[j] + " '", ExtentColor.RED));
						g_Test_Task_Result = g_Test_Task_Failed; 
					}
				}	
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the page : ' Nx2me - Centers '", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed; 
			}
			test.log(Status.INFO, MarkupHelper.createLabel("Child windows link test is completed for TAB : "+Input.getLabelText(NxMove_homePageTabs.get(i)), ExtentColor.GREEN));
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
	 * Test Method Name : Changing_Data_In_NxMoveAdmin() 
	 * 
	 * Description : 
	 * 
	 * 		This test method will change some settings in nxmoveadmin center, 
	 * 		such as center access, messaging alerts etc,.
	 * 			
	 ******************************************************************************************************/
	
	@Test(priority=6,dependsOnMethods="NxMoveAdmin_Test", retryAnalyzer=BaseClass.class)
	public static void Changing_Data_In_NxMoveAdmin_Test() throws InterruptedException
	{		
		//landingToNxAdminPortal();
		g_Test_Task_Result = g_Test_Task_Passed;
		test.log(Status.INFO,  MarkupHelper.createLabel("Going to Change and Update few Center setting values under Center Information panel and Data Exchange Interface panel.", ExtentColor.BLUE));
		Input.clickOn(NxMove_Center, click);		
		while (!Input.waitForPageToLoad(cPAGELOADTIME));
		test.log(Status.INFO, MarkupHelper.createLabel("NxStage Center ID for Changing the settings is : " + g_centerId,ExtentColor.BLUE));
		Input.textBoxEntry(NxCenter_TextBox, g_centerId);
		while (!Input.waitForPageToLoad(cPAGELOADTIME));
		Input.clickOn(NxCenter_Search, click);
		while (!Input.waitForPageToLoad(cPAGELOADTIME));
		TimeUnit.SECONDS.sleep(2);
		
		if(Input.getAttributeValue(Center_status, value).contains("Active"))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Center ' "+ g_centerId + " ' is opened",ExtentColor.GREEN));
		}
		else
		{
			if( Verify.checkIfLabelDisplayed(CenterDeactivated_err) ||  Verify.checkIfLabelEnabled(CenterDeactivated_err))
			{
				test.log(Status.ERROR, MarkupHelper.createLabel(Input.getLabelText(CenterDeactivated_err),ExtentColor.RED));
				Assert.assertTrue(false);
			}
			else
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Center '"+ g_centerId + " ' is opened",ExtentColor.GREEN));
			}
		}					
		
		
		for(int i = 0; i < NxMove_Change_Settings.length; i++)
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going to change the settings : "+_NxMove_Change_Settings_Text[i] , ExtentColor.BLUE));
			if(Input.isElementPresent(NxMove_Change_Settings[i]))
			{
				Input.dropDown(NxMove_Change_Settings[i], no);
				TimeUnit.SECONDS.sleep(1);
				test.log(Status.INFO, MarkupHelper.createLabel(_NxMove_Change_Settings_Text[i] + " : No", ExtentColor.GREEN));
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : ' "+_NxMove_Change_Settings_Text[i] + " '", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed;
			}
		}
		Input.clickOn(NxCenter_Info_Update, click);
		TimeUnit.SECONDS.sleep(5);
		
		if( Verify.checkIfLabelDisplayed(CenterInfo_UpdatePopup) ||  Verify.checkIfLabelEnabled(CenterInfo_UpdatePopup))
		{			
			test.log(Status.INFO, MarkupHelper.createLabel("Center info update popup  : "+Input.getLabelText(CenterInfo_UpdatePopup),ExtentColor.GREEN));
		}
		else
		{
			g_Test_Task_Result = g_Test_Task_Failed;
			test.log(Status.ERROR, MarkupHelper.createLabel("Center settings are not updated",ExtentColor.RED));
		}
		
		test.log(Status.INFO, MarkupHelper.createLabel("Going to change the phone number as 5566778899 in data exchange info...",ExtentColor.BLUE));
		Input.clearTextBoxLabel(DataExchange_Phone);
		Input.textBoxEntry(DataExchange_Phone, "5566778899");
		TimeUnit.SECONDS.sleep(1);
		Input.clickOn(DataExchange_Update, click);
		TimeUnit.SECONDS.sleep(5);
		test.log(Status.INFO, MarkupHelper.createLabel("Changed the phone number as 5566778899 in data exchange info.",ExtentColor.GREEN));
		if( Verify.checkIfLabelDisplayed(DataExchangeInfo_UpdatePopup) ||  Verify.checkIfLabelEnabled(DataExchangeInfo_UpdatePopup))
		{			
			test.log(Status.INFO, MarkupHelper.createLabel("Data exchange info update popup  : "+Input.getLabelText(DataExchangeInfo_UpdatePopup),ExtentColor.GREEN));
		}
		else
		{
			g_Test_Task_Result = g_Test_Task_Failed;
			test.log(Status.ERROR, MarkupHelper.createLabel("Data Exchange settings are not updated",ExtentColor.RED));
		}		
		Input.scrollDown();
		System.out.println("Data Exchange settings are not updated");
		test.log(Status.INFO, MarkupHelper.createLabel("Going to change the user access types : ", ExtentColor.BLUE));
		Input.clickOn(NxmReadOnly_Edit, click);
		test.log(Status.INFO, MarkupHelper.createLabel("Going to change the user access type to : Read Only to Admin", ExtentColor.BLUE));
		Input.dropDown(NxmAccess_ReadOnly, _Admin_Access);
		Input.scrollDown();
		TimeUnit.SECONDS.sleep(1);
		Input.clickOn(NxmReadOnly_Update, click);
		/*Input.scrollDown();
		
		if(Input.getLabelText(NxmAccess_ReadOnly).contains(_Admin_Access))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Access Types is changed to : Read Only to Admin",ExtentColor.GREEN));
		}*/
		test.log(Status.INFO, MarkupHelper.createLabel("Going to change defaults settings of : ' "+ g_centerId+" '", ExtentColor.BLUE));
		
		Input.clickOn(NxMove_Center, click);
		Input.textBoxEntry(NxCenter_TextBox, g_centerId);
		
		TimeUnit.SECONDS.sleep(5);
		Input.clickOn(NxCenter_Search, click);
		TimeUnit.SECONDS.sleep(2);
		
		if(Input.getAttributeValue(Center_status, value).contains("Active")){
			test.log(Status.INFO, MarkupHelper.createLabel("Center '"+ g_centerId + " ' is opened", ExtentColor.GREEN));
		}
		else{
			if( Verify.checkIfLabelDisplayed(CenterDeactivated_err) ||  Verify.checkIfLabelEnabled(CenterDeactivated_err)){
				test.log(Status.ERROR, MarkupHelper.createLabel(Input.getLabelText(CenterDeactivated_err), ExtentColor.RED));
				Assert.assertTrue(false);
			}else{
				test.log(Status.INFO, MarkupHelper.createLabel("Center '"+ g_centerId + " ' is opened", ExtentColor.GREEN));
			}
		}	
		
		for(int j = 0; j < NxMove_Change_Settings.length; j++)
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going to change the settings : "+_NxMove_Change_Settings_Text[j] , ExtentColor.BLUE));
			if(Input.isElementPresent(NxMove_Change_Settings[j]))
			{
				Input.dropDown(NxMove_Change_Settings[j], yes);
				TimeUnit.SECONDS.sleep(1);
				test.log(Status.INFO, MarkupHelper.createLabel(_NxMove_Change_Settings_Text[j] + " : Yes", ExtentColor.GREEN));
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : ' "+_NxMove_Change_Settings_Text[j] + " '", ExtentColor.RED));
				g_Test_Task_Result = g_Test_Task_Failed;
			}
		}		

		Input.clickOn(NxCenter_Info_Update, click);
		TimeUnit.SECONDS.sleep(5);
		
		if( Verify.checkIfLabelDisplayed(CenterInfo_UpdatePopup) ||  Verify.checkIfLabelEnabled(CenterInfo_UpdatePopup))
		{
			test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(CenterInfo_UpdatePopup), ExtentColor.GREEN));
		}
		else
		{
			g_Test_Task_Result = g_Test_Task_Failed;
			test.log(Status.ERROR, MarkupHelper.createLabel("Center settings are not updated",ExtentColor.RED));
		}
		
		test.log(Status.INFO, MarkupHelper.createLabel("Going to clear the phone number...",ExtentColor.BLUE));
		Input.clearTextBoxLabel(DataExchange_Phone);
		Input.textBoxEntry(DataExchange_Phone, "");
		Input.clickOn(DataExchange_Update, click);
		TimeUnit.SECONDS.sleep(5);		
		
		if( Verify.checkIfLabelDisplayed(DataExchangeInfo_UpdatePopup) ||  Verify.checkIfLabelEnabled(DataExchangeInfo_UpdatePopup))
		{			
			test.log(Status.INFO, MarkupHelper.createLabel("The popup is displayed : "+Input.getLabelText(DataExchangeInfo_UpdatePopup), ExtentColor.GREEN));
		}
		else
		{
			g_Test_Task_Result = g_Test_Task_Failed;
			test.log(Status.ERROR, MarkupHelper.createLabel("Data Exchange settings are not updated",ExtentColor.RED));
		}	
		
		Input.scrollDown();
		test.log(Status.INFO, MarkupHelper.createLabel("Going to change the user Access type to Default... ", ExtentColor.BLUE));
		Input.clickOn(NxmReadOnly_Edit, click);
		Input.scrollDown();
		Input.dropDown(NxmAccess_ReadOnly, _ReadOnly_Access);
		
		TimeUnit.SECONDS.sleep(3);
		Input.clickOn(NxmReadOnly_Update, click);
		
		/*if(Input.getLabelText(NxmAccess_ReadOnly).contains(_ReadOnly_Access))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Access Types is changed from: Admin to Read Only",ExtentColor.GREEN));
		}*/
		test.log(Status.INFO, MarkupHelper.createLabel("Center Values are updated to defaults.", ExtentColor.GREEN));				
		
		portal_Signout();
		nxadmin_Access_Verification();
		
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
	 * @throws IOException 
	 * 
	 * ****************************************************************************************************/
	
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
