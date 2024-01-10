package com.cs.ns.ClinicianPortal_Automation.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.cs.ns.ClinicianPortal_Automation.PageObjects.NxMoveAdminPage;
import com.google.common.collect.Ordering;
import com.reusecode.iodata.Input;
import com.reusecode.iodata.Verify;
import com.reusecode.utilities.TestTool;
import com.reusecode.utilities.XMLHandler;

public class ReusableMethods extends NxMoveAdminPage
{	
	public static int cRETRY = 5;
	
	public static int cZERO = 0;
	
	public static int cONE = 1;
	
	public static int cTWO = 2;
	
	public static int cTHREE = 3;
	
	public static int cFOUR = 4;
	
	public static int cFIVE = 5;
	
	public static int cEIGHT = 8;
	
	public static int cTEN_DAYS = 10;
	
	public static int cFOURTY_DAYS = 40;
	
	public static int cFIFTY_DAYS = 50;
	
	public static int cSIXTY_DAYS = 60;
	
	public static int cHUNDRED_DAYS = 100;
	
	public static int cDASHBOARD_FLOWSHEETRANGE = 15;
	
	public static int cPATIENTS_FLOWSHEETRANGE = 25;
	
	public static By m_fsElementToCheck = null;
	
	public static By m_flowsheet_Status = null;
	
	public static boolean m_task_Result;
	
	public static boolean m_task_Pass = true;
	
	public static boolean m_task_Fail = false;
	
	public static int m_unprovisioned_Count = 0;
	
	public static By m_updatedStatus = null;
	
	public static ArrayList<String> m_patientList;
	
	public static By m_patientColSettingsIcon = null;
	
	public static By m_patientColSettingsIcon_Tooltip = null;
	
	public static By m_grayFlagIcon = null;
	
	public static By m_iElement_Patient = null;
	
	/**********************************************************************************************
	 * Method Name : landingToPortal()
	 * 
	 * @throws InterruptedException 
	 * 
	 * Description :  
	 * 	
	 * 		This method is used to login into the clinician portal.
	 * 
	 **********************************************************************************************/
	
	public static Boolean landingToPortal() throws InterruptedException
	{		
		m_task_Result = m_task_Pass;	
		Input.urlNavigate(g_baseURL);
		
		for (int i = 0; i < cRETRY ; i++)
		{
			System.out.println("LOGIN TO PORTAL");
			test.log(Status.INFO, MarkupHelper.createLabel("Going to Login into Nx2Me Clinician Portal...", ExtentColor.BLUE));
			
			for(int m = 0; m < arrloginCredentials_WE.length; m++)
			{
				Input.clearTextBoxLabel(arrloginCredentials_WE[m]);
				TimeUnit.SECONDS.sleep(1/2);
				Input.textBoxEntry(arrloginCredentials_WE[m], arrLoginCredData[m]);
				TimeUnit.SECONDS.sleep(2/3);
			}						
			TimeUnit.SECONDS.sleep(1/2);
			Input.clickOn(signInButton_Label, click);
			
			if (Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Successfully Login into Nx2Me Clinician Portal.", ExtentColor.GREEN));				
				break;
			}
			else
			{
				test.log(Status.WARNING, MarkupHelper.createLabel("Login to Clinician Portal has been failed and retry to login again.", ExtentColor.RED));
			}
			if (i == (cRETRY - 1))
			{				
				Input.getDriver().quit();
				test.log(Status.WARNING, MarkupHelper.createLabel("Exceeded the retries count and going to verify the nxstage admin access... ", ExtentColor.RED));	
				nxadmin_Access_Verification();
				TimeUnit.SECONDS.sleep(1/2);
				
				Input.urlNavigate(g_baseURL);
				TimeUnit.SECONDS.sleep(1/2);
				Input.textBoxEntry(center_ID_Label, g_centerId);
				TimeUnit.SECONDS.sleep(1/2);
				Input.textBoxEntry(username_Label, g_userId);
				TimeUnit.SECONDS.sleep(1/2);
				Input.textBoxEntry(password_Label, g_userPWD);
				TimeUnit.SECONDS.sleep(1/2);
				Input.clickOn(signInButton_Label, click);
				
				if (Verify.compareText(Input.getDriver().getTitle(), _dashboard_Tab_Title))
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Successfully Login into Nx2Me Clinician Portal, after changing the center access in nxadmin portal..", ExtentColor.GREEN));				
					break;
				}
				else
				{
					m_task_Result = m_task_Fail;
					test.log(Status.ERROR, MarkupHelper.createLabel("Failure : Too many retries to Login to Clinician Portal.", ExtentColor.RED));
					Input.getDriver().quit();	
				}
			}
		}
		return m_task_Result; 
	}
	
	/**********************************************************************************************
	 * Method Name : landingToNxAdminPortal()
	 * 
	 * @throws InterruptedException 
	 * 
	 * Description :  
	 * 	
	 * 		This method is used to login into the nxmoveadmin clinician portal.
	 * 
	 **********************************************************************************************/
	
	public static Boolean landingToNxAdminPortal() throws InterruptedException
	{
		m_task_Result = m_task_Pass;		
		for (int i = 0; i < cRETRY; i++)
		{
			test.log(Status.INFO, MarkupHelper.createLabel("Going to login into Nx2Me Clinician Portal...", ExtentColor.BLUE));
			Input.urlNavigate(g_baseURL);
			Input.textBoxEntry(center_ID_Label, g_centerId_ADMIN);
			TimeUnit.SECONDS.sleep(2/3);
			Input.textBoxEntry(username_Label, g_userId_ADMIN);
			TimeUnit.SECONDS.sleep(2/3);
			Input.textBoxEntry(password_Label, g_userPWD_ADMIN);
			TimeUnit.SECONDS.sleep(2/3);
			Input.clickOn(signInButton_Label, click);
			while (!Input.waitForPageToLoad(cPAGELOADTIME));
			
			if (Verify.compareText(Input.getDriver().getTitle(), _NxMove_Center_Title))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Sucessfully Login to Nx2me Clinician Portal.", ExtentColor.GREEN));
				break;
			}
			else
			{
				Input.getDriver().close();
				test.log(Status.WARNING, MarkupHelper.createLabel("Login to NxMoveAdmin Clinician Portal has been failed and retry to login again.", ExtentColor.RED));	
			}
			if (i == (cRETRY - cONE) )
			{
				m_task_Result = m_task_Fail;
			}
		}
		return m_task_Result;
		
	}
	 /**********************************************************************************************
     * Method Name : portal_Signout()
     * 
     * @return void 
     * 
     * Description :
     * 
     * 		This method is used to logout from the portal.
     * 
     **********************************************************************************************/
    public static void portal_Signout() throws InterruptedException
    {
    	test.log(Status.INFO, MarkupHelper.createLabel("Going to Sign out from the Portal...", ExtentColor.BLUE));
    	Input.clickOn(portal_Logout_Label, click);
		TimeUnit.SECONDS.sleep(5);
		Input.getDriver().quit();
		test.log(Status.INFO, MarkupHelper.createLabel("Logged out from the Portal...", ExtentColor.GREEN));
    }
    
	/**********************************************************************************************
	 * Method Name : search_Select_Prov_Unprovisioned_Patient()
	 * 
	 * @param _typeOfProvisionAck
	 * @return webelement
	 * @throws InterruptedException
	 * 
	 * Description : 
	 * 
	 * 		This method is used to search and select either provisioned or unprovisioned patient.
	 * 
	 **********************************************************************************************/
	
	public static By search_Select_Prov_Unprovisioned_Patient(String _typeOfProvisionAck) throws InterruptedException
	{
		String _ptSettingsAck = null;
		String _ptSettingsDef = null;
		String _byPatientElement = null;
		String _def = null;
		String _ack = null;
		String _alertsTooltip = null;
		String _settingsIcon = null;
		String _settingsIconTooltip;
		String _grayflagIcon;
		
		By settingsAckEle = null;
		By settingsDefEle = null;		
		By iElement_Alerts = null;
		
		for (int i = 0; i<totalFlowsheetsCountS(noOfPatientsRecords); i++)
		{
			_ptSettingsAck = "MainContent_PatientListGridView_ttSettingsNotAck_" + i;
			_ptSettingsDef = "MainContent_PatientListGridView_ttSettingsNotDef_" + i;
			_byPatientElement = "MainContent_PatientListGridView_PatientLink1_" + i;
			_alertsTooltip = "MainContent_PatientListGridView_spanPatientSettingsWrapper_" + i;
			
			_settingsIconTooltip = "MainContent_PatientListGridView_ttSettingsNotDef_" + i;
			_settingsIcon = "MainContent_PatientListGridView_imgPatientSettingsNotDefined_" + i;
			_grayflagIcon = "MainContent_PatientListGridView_spanExceptionCountWrapper_" + i;
			
			settingsAckEle = By.id(_ptSettingsAck);
			settingsDefEle = By.id(_ptSettingsDef);
			
			if (Verify.checkIfLabelDisplayed(settingsDefEle) || Verify.checkIfLabelEnabled(settingsDefEle))
			{
				Input.mouseOver(settingsDefEle);
				_def = Input.getAttributeValue(settingsDefEle, "onmouseover");
				
				Input.mouseOver(settingsAckEle);
				_ack = Input.getAttributeValue(settingsAckEle, "onmouseover");
				
				if (_typeOfProvisionAck.contains(g_pendingPatient) || _typeOfProvisionAck.equalsIgnoreCase(g_pendingPatient))
				{
					if(_ack != null)
					{
						m_iElement_Patient = By.id(_byPatientElement);
						System.out.println("*****This is pending ack patient : " + _ack);	
						System.out.println("Selected patient is pending patient : "+Input.getLabelText(m_iElement_Patient));
							
						test.log(Status.INFO, MarkupHelper.createLabel("Selected patient '" + Input.getLabelText(m_iElement_Patient) + " ' status is pending.", ExtentColor.GREEN));
						Input.clickOn(m_iElement_Patient, click);
						m_unprovisioned_Count++;
						break;						
					}
				}
				else if (_typeOfProvisionAck.equalsIgnoreCase(g_provisionedPatient) || _typeOfProvisionAck.contains(g_provisionedPatient))
				{
					if (_ack != null || _def != null)
					{
						/*do nothing.*/						
					}
					else
					{
						m_iElement_Patient = By.id(_byPatientElement);
						test.log(Status.INFO, MarkupHelper.createLabel("Selected patient ' "+ Input.getLabelText(m_iElement_Patient) + " is provisioned patient.", ExtentColor.GREEN));
						m_unprovisioned_Count++;
						System.out.println("*****This is  Provisioned  patient : " + i);
						break;
					}
				}
				else if (_typeOfProvisionAck.equalsIgnoreCase(g_unprovisionedPatient) || _typeOfProvisionAck.contains(g_unprovisionedPatient))
				{
					if (_ack != null || _def != null)
					{
						if (_def != null)
						{							
							System.out.println("Selected Unprovisioned patient is : "+ Input.getLabelText(m_iElement_Patient));	
							System.out.println("*****This is  Unprovisioned  patient : "+i);
							m_iElement_Patient = By.id(_byPatientElement);
							test.log(Status.INFO, MarkupHelper.createLabel("Selected Unprovisioned patient is : "+ Input.getLabelText(m_iElement_Patient), ExtentColor.GREEN));
							iElement_Alerts = By.id(_alertsTooltip);
							m_patientColSettingsIcon = By.id(_settingsIcon);
							m_patientColSettingsIcon_Tooltip = By.id(_settingsIconTooltip);
							m_grayFlagIcon = By.id(_grayflagIcon);
							m_unprovisioned_Count++;
							break;
						}
					}
				}
				else {/*Do nothing.*/}
			}
		}
		return iElement_Alerts;
	}
	
	/**********************************************************************************************
	 * Method Name : select_Patient()
	 * 
	 * @param navTab
	 *            : Navigation Tab
	 * @param ptName
	 *            : Patient Name
	 * @throws InterruptedException
	 * @return : boolean : true if task success, false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This Method will used to select and click on the patient name from dashboad or patients tab.
	 * 
	 *********************************************************************************************/

	public static Boolean search_Patient(String _navTab, String _ptName) throws InterruptedException 
	{
		m_task_Result = m_task_Fail;
		String _ptListElement = null;
		String _ptList = null;
		int breakPoint = 0; 
		int k = 0;
		 
		By navigation_Tab = null;
		By patient_Name = null;
		By total_Records = null;
		By moreRecords = null;

		if ((_navTab != null) && (_ptName != null)) 
		{			
			if (_navTab == _patients_tab) 
			{
				navigation_Tab	= patients_Tab;
				total_Records = patientsPage_total_Records;
				_ptList = "MainContent_PatientListGridView_PatientLink1_";
				moreRecords = patientsPage_Next;
				breakPoint = cPATIENTS_FLOWSHEETRANGE;
			}
			else if (_navTab == _dashboard_tab)
			{
				navigation_Tab	= dashboard_Tab;
				total_Records = dashboardTab_total_flowsheets;
				_ptList = "MainContent_FlowsheetGridView_PatientLink1_";
				moreRecords = FS_MoreRecords_Next;
				breakPoint = cDASHBOARD_FLOWSHEETRANGE;				
			} else {/*Do nothing.*/}

			if (Input.getDriver().getTitle() != _login_page_Title) 
			{
				Input.clickOn(navigation_Tab, click);
				for (int i = 0; i < totalFlowsheetsCountS(total_Records); i++) 
				{
					_ptListElement = _ptList + k;
					patient_Name = By.id(_ptListElement);
					Input.mouseOver(patient_Name);
					TimeUnit.SECONDS.sleep(1/2);
					
					if (Input.getLabelText(patient_Name).equalsIgnoreCase(_ptName) || Input.getLabelText(patient_Name).contains(_ptName)) 
					{
						Input.clickOn(patient_Name, click);
						m_task_Result = m_task_Pass;
						break;
					}
					k++;

					if (k >= breakPoint) 
					{
						Input.clickOn(moreRecords, click);
						k = cZERO;
					}
				}
			}
		}
		return m_task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : flowsheet_Actions()
	 * 
	 * @param selectPatientFS
	 * @param moveToStatusBar
	 * @param selectAction
	 * @return void
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method will perform the flowsheet actions, such as 
	 * 		
	 * 		1. changing status of flowsheets, 
	 * 		2. add notes, 
	 * 		3. archive flowsheets,
	 * 		4. print flowsheets.
	 * 
	 **********************************************************************************************/
	
	public static void flowsheet_Actions(By selectPatientFS, By moveToStatusBar, By selectAction) throws InterruptedException
	{		
		if (selectPatientFS != null)
		{
			Input.clickOn(selectPatientFS, click);
		}
		if (moveToStatusBar != null)
		{
			Input.mouseOver(moveToStatusBar);	
		}
		if (selectAction != null)
		{
			Input.clickOn(selectAction, click);
		}	
	}
	
	
	/*******************************************************************************************************
     * Method Name : totalFlowsheetsCountS()
     * 
     * @param webElement
     * @return integer
     * 
     * Description : 
     * 
     * 		This method is used to get the total number of flowsheets that are displayed in the current 
     * 		date range.
     * 
     *******************************************************************************************************/
    
	public static int totalFlowsheetsCountS(By webElement)
	{	
		String _records = Input.getLabelText(webElement);
		String[] _records1 = _records.split("\\s");
		int count = 0;
		
		for (count = 0; count < _records1.length; count++) 
		{
			if (_records1[count + cONE].contains("record(s)")) 
			{
				break;
			}
		}
		return Integer.parseInt(_records1[count]);
	}
	
	/*******************************************************************************************************
	 * Method Name : flowsheetsPerPage()
	 * 
	 * @param fsWebElement
	 * @return integer
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the number of flowsheets that are present in each page.
	 * 
	 *******************************************************************************************************/
	
	public static int flowsheetsPerPage(By fsWebElement)
	{	
		String _flowsheets = Input.getLabelText(fsWebElement);
		String[] _records1 = _flowsheets.split("\\s");
		int count = 0;
		
		for ( count = 0; count < _records1.length; count++) 
		{
			if (_records1[count + cONE].contains("of")) 
			{
				break;
			}
		}
		return Integer.parseInt(_records1[count]);
	}
	
	/*******************************************************************************************************
	 * Method Name : searchForFlowsheetByAction()
	 * 
	 * @param totalNumFs
	 * @param actionOfFs
	 * @return boolean : true if success, false otherwise.
	 * @throws InterruptedException 
	 * 
	 * Description :
	 * 
	 * 		This method is used to search for a flowsheet based on the status, such as 'New', 'Pending', 
	 * 		'Call Patient', 'Approved', 'Rejected'.
	 * 
	 *******************************************************************************************************/
	
	public static Boolean searchForFlowsheetByAction(By totalNumFs, String actionOfFs) throws InterruptedException
	{			
		m_task_Result = m_task_Pass;
		int moreRecords = 0;
		int isFlowsheetThere = 0;
		
		for (isFlowsheetThere = 0; isFlowsheetThere < totalFlowsheetsCountS(totalNumFs); isFlowsheetThere++)
		{
			String _fs_Action = "#MainContent_FlowsheetGridView > tbody > tr:nth-child(" + (moreRecords + cTWO) + ") > td:nth-child(7)";
			String _fs_ListElementCheck = "MainContent_FlowsheetGridView_ChkSelect_" + moreRecords;
			m_flowsheet_Status = By.cssSelector(_fs_Action);
			m_fsElementToCheck = By.id(_fs_ListElementCheck);
			System.out.println(Input.timeStamp()+" : ACTUAL_STATUS : "+Input.getLabelText(m_flowsheet_Status) + "\t\tEXPECTED_STATUS  : "+ actionOfFs);
			
			Input.mouseOver(m_fsElementToCheck);
			if ( (Input.getLabelText(m_flowsheet_Status)).contains(actionOfFs) )
			{
				Input.clickOn(m_fsElementToCheck, click);
				break;
			}
			moreRecords++;			
			if (moreRecords >= cDASHBOARD_FLOWSHEETRANGE)
			{
				Input.clickOn(FS_MoreRecords_Next, click);
				moreRecords = cZERO;
			}
			if ( (totalFlowsheetsCountS(totalNumFs)) == (isFlowsheetThere + cONE) )
			{
				Input.clickOn(dashboard_Tab, click);
				m_task_Result = m_task_Fail;
				break;
			}			
		}
		return m_task_Result;		
	}
    
	/*******************************************************************************************************
	 * Method Name : paginateToNextPage() 
	 * 
	 * @param PaginateLabel
	 * @param NextButton
	 * @param PrevButton
	 * @param _ascendingTest
	 * @return boolean : true if success, false otherwise.
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to paginate to each patient list or flowsheets page and also perform the 
	 * 		ascending test based on the requirement. 
	 * 
	 *******************************************************************************************************/
	
    public static Boolean paginateToNextPage(By PaginateLabel, By NextButton, By PrevButton, String _ascendingTest) throws InterruptedException
    {	
    	m_task_Result = m_task_Fail;
    	m_patientList = new ArrayList<String>();
    	 
    	int quotient = 0;
    	int remainder = 0;
    	int quotientBegin = 0;
    	int splitRecords = totalFlowsheetsCountS(PaginateLabel);
    	int totalRecords = splitRecords;
    	int pTCount = 0;
    	int temprec = 0;
    	
    	if (Input.getDriver().getTitle().contains(_dashboard_Tab_Title))
    	{
    		pTCount = cDASHBOARD_FLOWSHEETRANGE;
    	}
    	else
    	{
    		pTCount = cPATIENTS_FLOWSHEETRANGE;
    	}
    	quotient = splitRecords / pTCount;
    	remainder = splitRecords % pTCount;
	    if (remainder == 0)
	    {				    	 
	    	for ( quotientBegin = 0; quotientBegin < quotient - 1; quotientBegin++)
	    	{	
	    		System.out.println("FS COUNT : "+ flowsheetsPerPage(PaginateLabel));
	    		if (totalRecords >= pTCount)
	    		{
    				temprec = pTCount;	
    			}
    			else
    			{
    				temprec = totalRecords;	
    			}
	    		testRun(_ascendingTest, temprec);
	    		totalRecords = totalRecords - temprec;
	    		Input.clickOn(NextButton, click);	    		
	    	}				    	 
	    	for (quotientBegin = 0; quotientBegin < quotient - 1; quotientBegin++)
	    	{				    	 
	    		Input.clickOn(PrevButton, click);
	    	} 
	    }	
        
	    if (remainder != 0)
	    {	
	    	if (quotient == 0)
	    	{
	    		testRun(_ascendingTest, totalRecords);
	    	}
	    	
	    	for (quotientBegin = 0; quotientBegin < quotient; quotientBegin++)
	    	{
	    		if (totalRecords >= pTCount)
	    		{
    				temprec = pTCount;	
    				System.out.println("TOTAL Records : "+ totalRecords +" Temp : "+temprec);
    			}
    			else
    			{
    				temprec = totalRecords;	
    				System.out.println("Last Temp : "+temprec);
    			}
	    		testRun(_ascendingTest, temprec);
	    		totalRecords = totalRecords - temprec;
	    		Input.clickOn(NextButton, click);
	    	}				    	
	    	for (quotientBegin = 0; quotientBegin < quotient; quotientBegin++)
	    	{
	    		if(quotientBegin == 0)
	    		{
	    			testRun(_ascendingTest, totalRecords);
	    		}
	    		Input.clickOn(PrevButton, click);
	    	} 
	    }
    	
	    if ( !_ascendingTest.equals("") )
	    {
	    	System.err.println("AscendingTest : " + _ascendingTest);
	    	check_Whether_Patient_List_Is_AscendingOrDescending(_ascendingTest);
	    	m_task_Result = m_task_Pass;
	    }
	    return m_task_Result;
    }
    
    /*******************************************************************************************************
     * Method Name : search_And_Select_FS_With_Or_Withour_Attachments()  
     * 
     * @param Total_FS
     * @param _patientHeaderText_Type
     * @param num_flowsheets
     * @return boolean :true if task success, false otherwise.
     * @throws InterruptedException 
     * 
     * Description : 
     * 
     * 		This method is used to search for a flowsheet which contains attachments or without attachments.
     * 
     *******************************************************************************************************/
    
    public static Boolean search_And_Select_FS_With_Or_Without_Attachments(By Total_FS, String _patientHeaderText_Type, int numFlowsheets) throws InterruptedException
    {	
		m_task_Result = m_task_Fail;
		int count = 0;
		int iCount = 0;
		String _patientFS = null;
		String _patientFS_Header = null;
		String _selcetFS = null;
		String _patientSelectCheck = "MainContent_FlowsheetGridView_ChkSelect_";
		By selectFSLoc = null;
		By patientFS_Locator = null;
		By patientFSHeader_Locator = null;
				
		for (int i = 0; i < totalFlowsheetsCountS(Total_FS); i++)
		{
			if (count == numFlowsheets)
			{
				break;
			}
			
			if (_patientHeaderText_Type == null)
			{
				m_task_Result=m_task_Pass;
				_selcetFS = _patientSelectCheck + i;
				selectFSLoc = By.id(_selcetFS);
				Input.clickOn(selectFSLoc, click);
				count++;	
			}
			else
			{
				_patientFS = "MainContent_FlowsheetGridView_ImageButton4_" + i;
				_patientFS_Header = "#MainContent_FlowsheetGridView > tbody > tr:nth-child(" + (i + cTWO) + ") > td:nth-child(8) > div:nth-child(2) > div > table > caption";
				patientFS_Locator = By.id(_patientFS);
				patientFSHeader_Locator = By.cssSelector(_patientFS_Header);
				
				if (Input.isElementPresent(patientFS_Locator))
				{
					Input.mouseOver(patientFS_Locator);
					if (Verify.checkIfLabelDisplayed(patientFSHeader_Locator))
					{
						if (Input.getLabelText(patientFSHeader_Locator).contains(_patientHeaderText_Type))
						{
							m_task_Result=m_task_Pass;
							_selcetFS = _patientSelectCheck+i;
							selectFSLoc = By.id(_selcetFS);
							Input.clickOn(selectFSLoc, click);
							count++;
						}
					}
				}
			}			
			iCount++;
			if (iCount >= cDASHBOARD_FLOWSHEETRANGE)
			{
				Input.clickOn(FS_MoreRecords_Next, click);
				iCount = cZERO;
			}
		}
		return m_task_Result;
	}
    
    /*******************************************************************************************************
     * Method Name : check_Whether_Patient_List_Is_AscendingOrDescending()  
     * 
     * @param sortTest
     * @return void 
     * 
     * Description : 
     * 
     * 		This method is used to perform the ascending and descending order test.
     * 
     *******************************************************************************************************/
    
    public static void check_Whether_Patient_List_Is_AscendingOrDescending(String _sortTest)
    {   
    	String[] _patientFieldsToVerify = {"Patient Name", "DOB", "MRN", "Last Tx Date", "Nx2me Status", "Last Sync Date"};
    	String _matchedVal = null;
    	
    	for (int i = 0; i < _patientFieldsToVerify.length; i++)
    	{
    		if ( _sortTest.equalsIgnoreCase(_patientFieldsToVerify[i]) || _sortTest.contains(_patientFieldsToVerify[i]) )
    		{
    			_matchedVal = _patientFieldsToVerify[i];
    		}
    	}
    	
    	if ( _sortTest.equalsIgnoreCase(_matchedVal) || _sortTest.contains(_matchedVal) )
   	 	{
    		if (Ordering.natural().isOrdered(m_patientList))
   		 	{
    			test.log(Status.INFO, MarkupHelper.createLabel("Patient List is in Ascending Order" , ExtentColor.GREEN));
   		 	}
   		 	else if (Ordering.natural().reverse().isOrdered(m_patientList))
   		 	{
   		 		test.log(Status.INFO, MarkupHelper.createLabel("Patient List is in Descending Order" , ExtentColor.GREEN));
   		 	} 
   		 	else{/*Do nothing.*/}
   	 	}
    }
    
    /*******************************************************************************************************
     * 
     * @param _testActivity
     * @param fsNum
     * 
     *******************************************************************************************************/
    
    public static void testRun(String _testActivity, int fsNum)
    {    	
    	int count = 0;
    	String[] records1 = null;
    	int i = 0;
    	ArrayList<String> _patientList_Index = new ArrayList<String>();    	
    	By clickLocator = null;
    	String _clickLocator = null;
    	
    	if (!_testActivity.equals(""))
    	{
    		System.out.println("_testActivity :" + _testActivity);
        	if (_testActivity.equalsIgnoreCase("Patient Name") || _testActivity.contains("Patient Name"))
        	{
    			for(i = 0; i < fsNum; i++)
    			{
    				_clickLocator = "MainContent_PatientListGridView_PatientLink1_" + i;
    	    		clickLocator = By.id(_clickLocator);
    	    		m_patientList.add(Input.getLabelText(clickLocator));
    	    	}
    	    }
    		else if ( _testActivity.equalsIgnoreCase("DOB") || _testActivity.contains("DOB") )
    		{
    			for (i = 0; i < fsNum; i++)
    			{
    	    		_clickLocator = "#MainContent_PatientListGridView > tbody > tr:nth-child("+ (i + cTWO) +") > td:nth-child(4)";
    	    		clickLocator = By.cssSelector(_clickLocator);    	    		    	    		
    	    		records1 = Input.getLabelText(clickLocator).split("/");    	    		    	    		
    	    		for ( count = 0; count < records1.length - cONE; count++) 
    	    		{
    	    			if (records1[count].length() == cTWO) 
    	    			{
    	    				_patientList_Index.add(records1[count]);
    	    			}
    	    			else
    	    			{
    	    				_patientList_Index.add("0" + records1[count]);	
    	    			}
    	    		}
    	    		m_patientList.add(records1[cTWO]);
    	    	}
    		}
    		else if ( _testActivity.equalsIgnoreCase("MRN") || _testActivity.contains("MRN") )
    		{
    			for (i = 0; i < fsNum; i++)
    			{
    	    		_clickLocator = "#MainContent_PatientListGridView > tbody > tr:nth-child("+ (i + cTWO) +") > td:nth-child(5)";
    	    		clickLocator = By.cssSelector(_clickLocator);
    	    		m_patientList.add(Input.getLabelText(clickLocator));
    	    		System.out.println("MRN :"+Input.getLabelText(clickLocator));
    	    	}
    		}
    		else if ( _testActivity.equalsIgnoreCase("Last Tx Date") || _testActivity.contains("Last Tx Date") )
    		{
    			for (i = 0; i < fsNum; i++)
    			{
    	    		_clickLocator = "#MainContent_PatientListGridView > tbody > tr:nth-child("+(i + cTWO)+") > td:nth-child(8)";
    	    		clickLocator = By.cssSelector(_clickLocator);
    	    		m_patientList.add(Input.getLabelText(clickLocator));
    	    		System.out.println("Last Tx Date :"+Input.getLabelText(clickLocator));
    	    	}
    		}
    		else if ( _testActivity.equalsIgnoreCase("Nx2me Status") || _testActivity.contains("Nx2me Status") )
    		{
    			for (i = 0; i < fsNum; i++)
    			{
    				_clickLocator = "MainContent_PatientListGridView_ttTreatmentStatus_" + i;
    				clickLocator = By.id(_clickLocator);
    				m_patientList.add(Input.getLabelText(clickLocator));
    	    		System.out.println("Nx2me Status"+Input.getLabelText(clickLocator));
    	    	}
    		}
    		else if ( _testActivity.equalsIgnoreCase("Last Sync Date") || _testActivity.contains("Last Sync Date") )
    		{
    			for (i = 0; i < fsNum; i++)
    			{
    				_clickLocator = "#MainContent_PatientListGridView > tbody > tr:nth-child("+(i + cTWO)+") > td:nth-child(10)";
    				clickLocator = By.cssSelector(_clickLocator);
    				m_patientList.add(Input.getLabelText(clickLocator));
    	    		System.out.println("Last Sync Date"+Input.getLabelText(clickLocator));
    	    	}
    		}
    		else{/*do nothing.*/}
    	}
    }
    
    /*******************************************************************************************************************
     * Method Name : flowsheet_Actions_Perform()
     * 
     * @param _selectFsType
     * @param StatusPerform
     * @param _updatedStatusIs
     * @return boolean : true if task success, false otherwise.
     * @throws InterruptedException
     * 
     * Description : 
     * 
     * 		This method will perform the flowsheet actions.
     * 
     *******************************************************************************************************************/
	public Boolean flowsheet_Actions_Perform(String _selectFsType, By StatusPerform, String _updatedStatusIs) throws InterruptedException
	{
		m_task_Result = m_task_Pass;
		System.out.println("Select_fs_type : '" + _selectFsType + " StatusPerform : " + _updatedStatusIs + " ' ");
		
		if (searchForFlowsheetByAction(dashboardTab_total_flowsheets, _selectFsType))
		{
			TimeUnit.SECONDS.sleep(6);
			Input.mouseOver(FS_status);
			Input.clickOn(StatusPerform, click);
			System.out.println("Flowsheet status is '" + _updatedStatusIs + "'");
			if (Input.getLabelText(m_updatedStatus).contains(_updatedStatusIs))
			{	
				System.out.println("Flowsheet status is ' " + StatusPerform + "' ");				
			}
		}
		else
		{
			m_task_Result = m_task_Fail;
		}
		return m_task_Result;
	}
	
	/******************************************************************************************************
	 * Method Name : flowsheet_Actions_Perform()
	 * 
	 * @return boolean :true if task success, false otherwise.
	 * 
	 * Description : 
     * 
     * 		This method will perform the flowsheet status actions.
	 * @throws InterruptedException 
	 * @throws StaleElementReferenceException 
	 * @throws NoSuchElementException 
     * 
	 * ****************************************************************************************************/
	
	public static Boolean fs_Status_Actions() throws NoSuchElementException, StaleElementReferenceException, InterruptedException
	{		
		m_task_Result = m_task_Pass;
		
		for (int i = 0; i < Arr_Flowsheet_Actions.length; i++)
		{
			if (!Verify.checkIfLabelDisplayed(Arr_Flowsheet_Actions[i]))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("Action : " + _Arr_Flowsheet_Actions_Text[i], ExtentColor.GREEN));
				Input.mouseOver(Arr_Disabled_FS_Actions[i]);
				System.out.println("Action Dis : " + _Arr_Flowsheet_Actions_Text[i]);
			}
			else
			{	
				test.log(Status.INFO, MarkupHelper.createLabel("Action : " + _Arr_Flowsheet_Actions_Text[i], ExtentColor.GREEN));
				Input.mouseOver(Arr_Flowsheet_Actions[i]);
				System.out.println("Action : " + _Arr_Flowsheet_Actions_Text[i]);
				
				if (_Arr_Flowsheet_Actions_Text[i].contains("Status"))
				{
					test.log(Status.INFO,  MarkupHelper.createLabel("Status Action Contains : " + _Arr_Flowsheet_Actions_Text[i], ExtentColor.GREEN));
					System.out.println("Action : Passed : " + _Arr_Flowsheet_Actions_Text[i]);
					Input.mouseOver(FS_status);
					
					for (int j = 0; j < FS_ACTNS.length; j++)
					{
						Input.mouseOver(FS_ACTNS[j]);
						test.log(Status.INFO, MarkupHelper.createLabel("Status : " + _Arr_FS_STAT_Text[j],ExtentColor.GREEN));
					}
				}
				m_task_Result = m_task_Fail;
			}
		}
		return m_task_Result;
	}
	/******************************************************************************************************
	 * Method Name : archieve_All_Flowsheets()
	 * 
	 * @param _patientInWhichTab
	 * @param withinDateRange
	 * @return boolean :true if task success, false otherwise.
	 * @throws InterruptedException
	 * 
	 * Description : 
	 * 
	 * 		This method will perform the archiving the flowsheets based on the date range.
	 * 
	 ******************************************************************************************************/
	
	public static boolean archieve_All_Flowsheets(String _patientInWhichTab, int withinDateRange) throws InterruptedException
	{		  
		By total_Records = null;
		By check_Pt_List = null;
		m_task_Result = m_task_Fail;	
		test.log(Status.INFO, MarkupHelper.createLabel("Going to archive flowsheets past ' "+ withinDateRange+" ' days...", ExtentColor.BLUE));	
		Input.datePicker_Entry(_startDate, Input.substract_DaysFromTodaysDate(withinDateRange));
		Input.clickOn(view, click);		  		
		  
		if ((_patientInWhichTab != null) && (withinDateRange != cZERO)) 
		{
			if (_patientInWhichTab.equalsIgnoreCase(_patients_tab) || _patientInWhichTab.contains(_patients_tab))
			{
				total_Records = patientsPage_total_Records;	
				check_Pt_List = Patientpage_allFlowsheets;
			}
			else if (_patientInWhichTab.equalsIgnoreCase(_dashboard_tab) || _patientInWhichTab.contains(_dashboard_tab))
			{
				total_Records = dashboardTab_total_flowsheets;
				check_Pt_List = Dsb_allFlowsheetsCheck;
			} else{/*Do nothing.*/}   
		}
		  
		for(;;)
		{
			if (Input.getLabelText(total_Records).contains(_no_data_available))
			{
				test.log(Status.INFO, MarkupHelper.createLabel("All flowsheest are deleted.", ExtentColor.GREEN));	
				TimeUnit.SECONDS.sleep(3);
				m_task_Result = m_task_Pass;
				break;
			}
			else
			{
				Input.clickOn(check_Pt_List, click);
				if (Input.isElementPresent(FS_archive))
				{
					Input.clickOn(FS_archive, click);
					if (Verify.checkIfLabelDisplayed(FS_archive_Yes))
					{
						Input.clickOn(FS_archive_Yes, click);					
						TimeUnit.SECONDS.sleep(1);
					}
				}
			}
		}
		return m_task_Result;		 
	}
    
    /**********************************************************************************************
	 * Method Name : verifyAllFlowsheetsSelected()
	 * 
	 * @param fsSelectLabel
	 * @return boolean :true if task success, false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to verify whether all the flowsheest are selected or not.
     * @throws InterruptedException 
	 * 
	 **********************************************************************************************/
	
	public static Boolean verifyAllFlowsheetsSelected(By fsSelectLabel) throws InterruptedException
	{		
		int status = 0;
		m_task_Result = m_task_Fail;
		By fsElement = null;
		
		for (int i = 0; i < flowsheetsPerPage(fsSelectLabel); i++) 
		{
			String FS_ListElement = "MainContent_FlowsheetGridView_ChkSelect_" + i;
			fsElement = By.id(FS_ListElement);
		
			if (Verify.checkIfLabelDisplayed(fsElement))
			{
				status++;				
			}
		}
		if (status == flowsheetsPerPage(fsSelectLabel))
		{
			m_task_Result = m_task_Pass;
		}
		return m_task_Result;
	}
    
	/**********************************************************************************************
	 * Method Name : verifyPrint_Status
	 * 
	 * @param Total_FS
	 * @param no_of_FS_to_verify_print_status
	 * @return boolean :true if task success, false otherwise.
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to verify print status of flowsheets.
	 * 
	 **********************************************************************************************/
	
	public static Boolean verifyPrint_Status(By Total_FS, int no_of_FS_to_verify_print_status) throws InterruptedException
	{		
		m_task_Result = m_task_Pass;
		int count = 0;
		int iCount = 0;
		String _status = null;
		By fsPrintStatus = null;
		
		for (int i = 0; i < totalFlowsheetsCountS(Total_FS); i++)
		{
			if (no_of_FS_to_verify_print_status == count)
			{
				break;
			}
			
			_status = "MainContent_FlowsheetGridView_imgPrinted_" + i;
			fsPrintStatus = By.id(_status);
			
			if (Input.isElementPresent(fsPrintStatus))
			{
				Input.mouseOver(fsPrintStatus);
				count++;
			}
			else {
				m_task_Result = m_task_Fail;
			}
			iCount++;
			
			if (iCount >= cDASHBOARD_FLOWSHEETRANGE)
			{
				Input.clickOn(FS_MoreRecords_Next, click);
				iCount = cZERO;
			}
		}
		if (m_task_Result)
		{
    		test.log(Status.INFO, MarkupHelper.createLabel("Print verification test successful for ' " + no_of_FS_to_verify_print_status+" ' flowsheets", ExtentColor.GREEN));
    	}
		return m_task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : verify_PatientAlerts
	 * 
	 * @param no_of_FS_to_verify_alerts
	 * @return boolean :true if task success, false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to verify patient alerts.
	 * @throws InterruptedException 
	 * @throws StaleElementReferenceException 
	 * @throws NoSuchElementException 
	 * 
	 * 
	 **********************************************************************************************/
    public static Boolean verify_PatientAlerts(int no_of_FS_to_verify_alerts) throws NoSuchElementException, StaleElementReferenceException, InterruptedException
    {
    	m_task_Result = m_task_Pass;
    	String alerts = null;
    	By alert_Label = null;
    	
    	for (int i = 0; i < no_of_FS_to_verify_alerts; i++)
    	{
    		alerts = "MainContent_FlowsheetGridView_lnkExceptionCount_" + i;
    		alert_Label = By.id(alerts);
    		Input.mouseOver(alert_Label);
    		
    		if (Input.getLabelText(alert_Label) != null)
    		{
    			test.log(Status.INFO, MarkupHelper.createLabel("Alert Criteria is verified, the alert number is ' "+Input.getLabelText(alert_Label)+" '", ExtentColor.GREEN));
    		}
    		else
    		{
    			m_task_Result = m_task_Fail;
    			test.log(Status.ERROR, MarkupHelper.createLabel("Alert Criteria is not set", ExtentColor.RED));
    		}
    	}
    	if (m_task_Result)
    	{
    		test.log(Status.INFO, MarkupHelper.createLabel("Alert Criteria test successful for ' " + no_of_FS_to_verify_alerts+" ' flowsheets", ExtentColor.GREEN));
    	}
		return m_task_Result;
    }
    
    /**********************************************************************************************
     * Method name : testtool_DataConfig()
     * 
     * @param TPxxxx_to_perform
     * @param cfg_or_xml_to_run
     * @param xml_main_header_tag
     * @return void  
     * 
     * Description : 
     * 
     * 		This method is used to configure the test tool data in the test pc, to run all test tool
     * 		related data.
     * 
     **********************************************************************************************/
    public static void testtool_DataConfig(String TPxxxx_to_perform, String cfg_or_xml_to_run, String xml_main_header_tag)
    {
    	TestTool.testtoolDataConfiguration(g_testtoolStandardDir + TPxxxx_to_perform + g_testDataDir, g_testtoolStandardDir + TPxxxx_to_perform + g_createUploadFileDir);    	
    	if (cfg_or_xml_to_run != null && xml_main_header_tag != null)
    	{
    		try 
    		{ 
    			XMLHandler.setXMLMainTagName(xml_main_header_tag);
    			XMLHandler.updateXmlTagValue(g_testtoolStandardDir + TPxxxx_to_perform + "/create_upload_files/" + cfg_or_xml_to_run, xml_main_header_tag, g_centerID_Tag, g_PN1_centerID);
    			XMLHandler.updateXmlTagValue(g_testtoolStandardDir + TPxxxx_to_perform + "/create_upload_files/" + cfg_or_xml_to_run, xml_main_header_tag, g_patientID_Tag, g_PN1_patientID);    		
    		} catch (TransformerConfigurationException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (TransformerFactoryConfigurationError e) {
    			e.printStackTrace();
    		} catch (TransformerException e) {
    			e.printStackTrace();
    		} catch (ParserConfigurationException e) {
    			e.printStackTrace();
    		}	
    	}		
    }
    
    /**********************************************************************************************
     * Method Name : flowsheet_Status_Verification()
     * 
     * @return boolean : true if task success, false otherwise.
     * 
     * Description : 
     * 
     * 		This method is used to perform the flowsheet status panel verifictaion.
     * @throws InterruptedException 
     * 
     **********************************************************************************************/
    
    public static Boolean flowsheet_Status_Verification() throws InterruptedException
    {
    	m_task_Result = m_task_Pass;  
    	int i = 0;
    	test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Flowsheet status panel displays...", ExtentColor.BLUE));
    	System.out.println("FS_StatusLabels_Value : "+FS_Status_stat_Labels_Value.length);
		 
    	for (i = 0; i < FS_Status_stat_Labels_Value.length; i++)
		{
			 if ( Input.isElementPresent(FS_Status_stat_Labels_Value[i]) || Input.isElementPresent(FS_Status_stat_Labels[i]) )
			 {
				 System.out.println(Input.getLabelText(FS_Status_stat_Labels[i])+" = " + Input.getLabelText(FS_Status_stat_Labels_Value[i]) );
				 test.log( Status.INFO, MarkupHelper.createLabel(Input.getLabelText(FS_Status_stat_Labels[i])+" = " + Input.getLabelText(FS_Status_stat_Labels_Value[i]),ExtentColor.GREEN) );
			 }
			 else
			 {
				 test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : "+_FS_Status_Panel_Texts[i] + " ' Label.", ExtentColor.RED));
				 System.out.println(_FS_Status_Panel_Texts[i] + " Label Not Found.");
				 m_task_Result = m_task_Fail;
			 }
		}
    	test.log( Status.INFO, MarkupHelper.createLabel("Flowsheet status verification is completed.",ExtentColor.GREEN));
		return m_task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : flowsheet_Statistics_Verification()
     * 
     * @return boolean : true if task success, false otherwise.
     * 
     * Description : 
     * 
     * 		This method is used to perform the flowsheet statistics panel verification.
     * @throws InterruptedException 
     * 
     **********************************************************************************************/
    
    
    public static Boolean flowsheet_Statistics_Verification() throws InterruptedException
    {
    	m_task_Result = m_task_Pass;    
    	int i = 0;
    	test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Flowsheet statistics panel displays :", ExtentColor.BLUE));
		for (i = 0; i < FS_Statistics_Labels_vals.length; i++)
		{
			if (Input.isElementPresent(FS_Statistics_Labels[i]) || Input.isElementPresent(FS_Statistics_Labels_vals[i]))
			{
				test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(FS_Statistics_Labels[i]) + " = " + Input.getLabelText(FS_Statistics_Labels_vals[i]),ExtentColor.GREEN));
			}
			else
			{
				test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : ' "+_FS_Statistics_Panel_Texts[i] + " ' Label.", ExtentColor.RED));
				m_task_Result = m_task_Fail;
			} 
		}
		test.log( Status.INFO, MarkupHelper.createLabel("Flowsheet statistics verification is completed.",ExtentColor.GREEN));
		return m_task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : patients_Statistics_Panel_Verification()
     * 
     * @return boolean : true if task success, false otherwise.
     * 
     * Description : 
     * 
     * 		This method is used to perform the patients statistics panel verifictaion.
     * @throws InterruptedException 
     * 
     **********************************************************************************************/
    public static Boolean patients_Statistics_Panel_Verification() throws InterruptedException
    {
    	m_task_Result = m_task_Pass;
    	int i = 0;
    	test.log(Status.INFO, MarkupHelper.createLabel("Going to verify the Patient panel displays...", ExtentColor.BLUE));
    	for (i = 0; i < Patient_Statistics_Panel_Arr_Lables.length; i++)
    	{
    		if (Input.isElementPresent(Patient_Statistics_Panel_Arr_Lables[i]) || Input.isElementPresent(Patient_Statistics_Panel_Arr_vals[i]))
    		{
    			test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(Patient_Statistics_Panel_Arr_Lables[i]) + " = " + Input.getLabelText(Patient_Statistics_Panel_Arr_vals[i]),ExtentColor.GREEN));
    		}
    		else
    		{
    			test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : ' "+_Patient_Statistics_Panel_text[i] + " ' Label.", ExtentColor.RED));
    			m_task_Result = m_task_Fail;
    		} 
    	}
    	test.log( Status.INFO, MarkupHelper.createLabel("Patients statistics verification is completed.",ExtentColor.GREEN));
		return m_task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : db_DeleteTask()
     * 
     * @param XML_Main_Tag
     * @param DeleteCenterId
     * @param Center_Number
     * @return boolean: true if task success, false otherwise.
     * 
     * @throws InterruptedException
     * @throws TransformerConfigurationException
     * @throws IOException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     * @throws ParserConfigurationException
     * 
     * Description :
     *  
     *  	This method will perfrom the DB delete task on the center.
     *
     **********************************************************************************************/
    
    public static Boolean db_DeleteTask(String XML_Main_Tag, String DeleteCenterId, String Center_Number) throws InterruptedException, TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
    {    	  
    	m_task_Result=m_task_Pass;
		test.log(Status.INFO, MarkupHelper.createLabel("Going to perform the DB_Delete Task...", ExtentColor.BLUE));		  
		XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\DbDeleteTask.xml", XML_Main_Tag, DeleteCenterId, Center_Number);

		if (TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\DbDeleteTask.xml", cONE))
		{	
			test.log(Status.INFO, MarkupHelper.createLabel("DB_Delete Task is successfully completed", ExtentColor.GREEN));
			test.log(Status.INFO, MarkupHelper.createLabel("Going to provision center '"+g_centerId+" '...", ExtentColor.BLUE));
			if (nxadmin_Access_Verification())
			{
				test.log(Status.INFO, MarkupHelper.createLabel("center '"+g_centerId+" ' is provisioned.", ExtentColor.GREEN));	
			}
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("DB_Delete Task Failed.", ExtentColor.RED));
			m_task_Result = m_task_Fail;
		}
		return m_task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : nxadmin_Access_Verification()
     * 
     * @return boolean: true if task success, false otherwise.
     * @throws InterruptedException
     * 
     * Description : 
     * 
     * 		This method will perform the nxmove access settings and verification.
     * 
     **********************************************************************************************/
    
    public static Boolean nxadmin_Access_Verification() throws InterruptedException
    {
    	m_task_Result=m_task_Pass;
    	if (landingToNxAdminPortal())
		{
			if (Verify.compareText(Input.getDriver().getTitle(), _NxMove_Center_Title))
			{
				Input.clickOn(NxMove_Center, click);						
				test.log(Status.INFO, MarkupHelper.createLabel("Going to change the settings for : " + g_centerId+" ...", ExtentColor.BLUE));
				Input.textBoxEntry(NxCenter_TextBox, g_centerId);					  
				TimeUnit.SECONDS.sleep(3);
				Input.clickOn(NxCenter_Search, click);
				TimeUnit.SECONDS.sleep(2);
			  
				for (int i = 0; i < 1; i++)
				{
					if (Input.getAttributeValue(Center_status, value).contains("Active") || Input.getAttributeValue(Center_status, value).equalsIgnoreCase("Active"))
					{
						test.log(Status.INFO, MarkupHelper.createLabel( "Status = " + Input.getAttributeValue(Center_status, value), ExtentColor.GREEN));
					}
					else
					{
						Input.dropDown(Center_status, "Active");
						TimeUnit.SECONDS.sleep(2);
						test.log(Status.INFO, MarkupHelper.createLabel( "Status changed to = " + Input.getAttributeValue(Center_status, value), ExtentColor.GREEN));							  
					}
					  
					for (int j = 0; j < NxMove_Change_Settings_For_Verification.length; j++)
					{
						if (Input.isElementPresent(NxMove_Change_Settings_For_Verification[j]))
						{
							if (Input.getAttributeValue(NxMove_Change_Settings_For_Verification[j], value).contains(yes) || Input.getAttributeValue(NxMove_Change_Settings_For_Verification[j], value).equalsIgnoreCase(yes))
							{
								test.log(Status.INFO, MarkupHelper.createLabel(_NxMove_Change_Settings_Text_For_Verification[j] + " = "+Input.getAttributeValue(NxMove_Change_Settings_For_Verification[j], value), ExtentColor.GREEN));
							}
							else
							{
								Input.dropDown(NxMove_Change_Settings_For_Verification[j], yes);
								TimeUnit.SECONDS.sleep(2);
								test.log(Status.INFO, MarkupHelper.createLabel(_NxMove_Change_Settings_Text_For_Verification[j] + " : Yes", ExtentColor.GREEN));
							}
						}
						else
						{
							test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the : ' "+_NxMove_Change_Settings_Text_For_Verification[j] + " ' Label.", ExtentColor.RED));
							m_task_Result = m_task_Fail;
						}
					}	
					TimeUnit.SECONDS.sleep(1);
					Input.dropDown(TimeZone, "(UTC-05:00) Eastern Time (US & Canada)");
					TimeUnit.SECONDS.sleep(1);
					
					Input.clickOn(NxCenter_Info_Update, click);
					TimeUnit.SECONDS.sleep(5);
					
					if ( Verify.checkIfLabelDisplayed(CenterInfo_UpdatePopup) ||  Verify.checkIfLabelEnabled(CenterInfo_UpdatePopup))
					{
						test.log(Status.INFO, MarkupHelper.createLabel(Input.getLabelText(CenterInfo_UpdatePopup), ExtentColor.GREEN));					  
					}
					else
					{
						m_task_Result = m_task_Fail;
						test.log(Status.FATAL, MarkupHelper.createLabel("Center settings are not updated",ExtentColor.RED));
					}
					  
					for (int j = 0; j < Nxm_Access_Edit.length; j++)
					{
						Input.scrollDown();
						Input.clickOn(Nxm_Access_Edit[j], click);	
						test.log(Status.INFO, MarkupHelper.createLabel("Going to change the user Access to : " + _Nxm_Access_Types[j], ExtentColor.BLUE));		
						Input.scrollDown();
						TimeUnit.SECONDS.sleep(2);
						Input.dropDown(Nxm_Access_Change[j], _Nxm_Access_Types[j]);							
						Input.scrollDown();
						TimeUnit.SECONDS.sleep(2);
						Input.clickOn(Nxm_Access_Update[j], click);		
						test.log(Status.INFO, MarkupHelper.createLabel("User Access changed to : " + _Nxm_Access_Types[j], ExtentColor.GREEN));
					}
					  
					/*if(Input.getLabelText(Adm_acess_text_verification).contains("Admin") || Input.getLabelText(Adm_acess_text_verification).equalsIgnoreCase("Admin"))
					{
						break;
					}*/
				}
				portal_Signout();
			}
			else
			{
				m_task_Result = m_task_Fail; 
			}
		}
		else 
		{
			m_task_Result = m_task_Fail;
		}
		return m_task_Result;

    }
    
    /**********************************************************************************************
     * Method Name : provisioning_Pateint_In_Center()
     * 
     * @param updateTags_before_provision
     * @param tag_vals
     * @return boolean : true if task success, false otherwise.
     * 
     * @throws TransformerConfigurationException
     * @throws IOException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws InterruptedException
     *
     * Description :
     * 
     * 		This method is used to provision the patient in a center.
     *
     **********************************************************************************************/
    
    public static Boolean provisioning_Pateint_In_Center(String[] updateTags_before_provision, String[] tag_vals) throws TransformerConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, InterruptedException
    {
    	/*
		 * Provisioning the patient using DashboardPatientSettingsTask
		 */
    	
    	String _cid = g_centerId;
		String _pid = null;
		String _pin = null;
		String _dob = null;		  
		String[] _tags = {"CenterId", "PatientId", "PatientPin", "PatientDOB"};
		  
    	m_task_Result=m_task_Pass; 
    	test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to Provision the patient using DashboardPatientSettingsTask...", ExtentColor.BLUE));
		  
    	for (int i = 0; i < updateTags_before_provision.length; i++)
		{
    		XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\DashboardPatientSettingsTask1.xml", "DashboardPatientSettings", updateTags_before_provision[i], tag_vals[i]);  
		}
		if (TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\DashboardPatientSettingsTask1.xml", cONE))
		{
			test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL :Task passed \" Provisioning a patient using DashboardPatientSettingsTask\"", ExtentColor.GREEN));
			if (landingToPortal())
			{				  
				search_Select_Prov_Unprovisioned_Patient(g_pendingPatient);
				if (m_unprovisioned_Count >= cONE)
				{
					m_unprovisioned_Count = cZERO;
					Input.clickOn(settings_Tab, click);					 
					_pid = Input.getLabelText(Nxstage_Pid);
					_pin = Input.getLabelText(patient_pin);
					_dob = Input.getLabelText(patient_settings_dob);		  
					String[] _tag_Data = {_cid, _pid, _pin, _dob};  
					
					for (int m = 0; m < _tags.length; m++)
					{
						XMLHandler.updateXmlTagValue(g_testtoolStandardDir + "TP2186\\create_upload_files\\PdmpPatientSettingsTask1.xml", "HomeViewPatientSettings", _tags[m], _tag_Data[m]);							  
					}					  
					Input.clickOn(dashboard_Tab, click);
					portal_Signout();
					test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : Going to perform the PdmpPatientSettingsTask1 using test tool...", ExtentColor.BLUE));
					if (TestTool.testtoolTaskExec(g_testtoolStandardDir + "TP2186\\create_upload_files\\PdmpPatientSettingsTask1.xml", cONE))
					{
						test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : PdmpPatientSettingsTask1 is successful.", ExtentColor.GREEN));
						if(landingToPortal())
						{							  
							search_Select_Prov_Unprovisioned_Patient(g_provisionedPatient);
							if(m_unprovisioned_Count >= cONE)
							{
								m_unprovisioned_Count = cZERO;								  
								System.out.println("Patient is Provisioned");
								portal_Signout();
							}
							else
							{
								test.log(Status.ERROR, MarkupHelper.createLabel("Patient Provisioning is failed", ExtentColor.RED));
								m_task_Result=m_task_Fail; 
							}							  								  
						}
						else
						{
							m_task_Result=m_task_Fail; 
						}	
					}
					else
					{
						test.log(Status.INFO, MarkupHelper.createLabel("TEST_TOOL : PdmpPatientSettingsTask1 is failed.", ExtentColor.GREEN));
						m_task_Result=m_task_Fail; 
					}				  
				}
				else 
				{
					m_task_Result=m_task_Fail; 
					test.log(Status.ERROR, MarkupHelper.createLabel("Unable to find the patient with status, Pending Acknowledgement.", ExtentColor.RED));					  
				}			  				  		
			}
			else
			{
				m_task_Result=m_task_Fail; 
			}
		}
		else
		{
			test.log(Status.ERROR, MarkupHelper.createLabel("TEST_TOOL: \"Provisioning a patient using DashboardPatientSettingsTask\" Task Failed ", ExtentColor.RED));
		}   	    	
		return m_task_Result;    	     	    	    	
    }
    
    /**********************************************************************************************
     * Method Name : enter_Alerts_Data()
     * 
     * @param _alerts_to_enter
     * @return boolean : true if task success, false otherwise.
     * @throws InterruptedException
     * 
     * Description :
     * 
     * 		This method is used to enter the alerts settings in alerts criteria section.
     *
     **********************************************************************************************/
    
    public static Boolean enter_Alerts_Data(String[] _alerts_to_enter) throws InterruptedException
    {
    	m_task_Result = m_task_Pass;
    	Input.clickOn_js(alertsCriteria_EditLabel);
		TimeUnit.SECONDS.sleep(7);
		int failureCount = 0;
		float stdMin = 0;
		float stdMax = 0;
		float alertActual = 0;				
		 		
		String[] _min_Boundaries = {"0", "0", "1"};
		String[] _max_Boundaries = {"60", "30", "50"};

		if (Input.isClickable(alerts_criteria_lock_warning_ok))
		{
			test.log(Status.WARNING, MarkupHelper.createLabel("Alert warning : " + Input.getLabelText(alerts_criteria_lock_warning), ExtentColor.ORANGE));				
			test.log(Status.WARNING, MarkupHelper.createLabel("Alert warning full text : " + Input.getLabelText(alerts_criteria_lock_warning_text), ExtentColor.ORANGE));							 
			System.out.println(Input.getLabelText(alerts_criteria_lock_warning));
			System.out.println(Input.getLabelText(alerts_criteria_lock_warning_text));
			Input.clickOn(alerts_criteria_lock_warning_ok, click);
			m_task_Result = m_task_Fail;
		}
		else
		{	
			test.log(Status.INFO, MarkupHelper.createLabel("Alert Settings are : ", ExtentColor.BLUE));
			for (int k = 0; k < _alerts_to_enter.length; k++)
			{
				System.out.println("ALERTS DATA ' "+_alerts_Text[k]+" ' : " + _alerts_to_enter[k]);				 
				test.log(Status.INFO, MarkupHelper.createLabel(_alerts_Text[k]+" ' : " + _alerts_to_enter[k], ExtentColor.GREEN));
				Input.clearTextBoxLabel(Alerts_Labels[k]);
				TimeUnit.SECONDS.sleep(2/3);
				Input.textBoxEntry(Alerts_Labels[k], _alerts_to_enter[k]);
				TimeUnit.SECONDS.sleep(1);
				Input.enterKey_Press(Alerts_Labels[k]);
				TimeUnit.SECONDS.sleep(2/3);
			}
			TimeUnit.SECONDS.sleep(2);
			for (int j = 0; j < _alerts_to_enter.length; j++)
			{
				stdMin = Float.parseFloat(_min_Boundaries[j]);
				stdMax = Float.parseFloat(_max_Boundaries[j]);
				alertActual = Float.parseFloat(_alerts_to_enter[j]);					
				
				if (stdMin <= alertActual && stdMax >= alertActual)
				{
					test.log(Status.INFO, MarkupHelper.createLabel("Alert for : " + _alerts_Text[j]+" = " + alertActual, ExtentColor.GREEN));										
				}
				else
				{
					TimeUnit.SECONDS.sleep(1);	
					m_task_Result = m_task_Fail;
					failureCount = failureCount + 1;
					test.log(Status.WARNING, MarkupHelper.createLabel("Warning : " + _err_data[j], ExtentColor.ORANGE));
					test.log(Status.INFO, MarkupHelper.createLabel("Entered value is : " + _alerts_Text[j] + " = " + alertActual,ExtentColor.GREEN));
					System.out.println("MSG : INVALID DATA : " + _err_data[j]);	
				}
			} 
			  
			if (failureCount > 0)
			{
				failureCount = 0;
				TimeUnit.SECONDS.sleep(2);					
				Input.clickOn_js(alertsCriteria_CancelLabel);
			}
			else
			{
				failureCount = 0;
				TimeUnit.SECONDS.sleep(2);
				Input.clickOn_js(alertsCriteria_UpdateLabel);
			}
			TimeUnit.SECONDS.sleep(7);
		} 
		return m_task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : single_Alert_Entry()
     * 
     * @param AlertLabel
     * @param _AlertValue
     * @return boolean: true if task success, false otherwise.
     * @throws InterruptedException
     * 
     * Description :
     * 
     * 		This method is used to enter the single alert setting in alerts section.
     * 
     **********************************************************************************************/
    
    public static Boolean single_Alert_Entry(By AlertLabel, String _AlertValue) throws InterruptedException
    {    	
    	m_task_Result = m_task_Pass; 
    	Input.clickOn(m_iElement_Patient, click);
		Input.clickOn(alertsCriteria_Tab, click);
		TimeUnit.SECONDS.sleep(2);	
		Input.clickOn_js(alertsCriteria_EditLabel);
		TimeUnit.SECONDS.sleep(10);
		Input.clearTextBoxLabel(AlertLabel);
		TimeUnit.SECONDS.sleep(1/2);
		Input.textBoxEntry(AlertLabel, _AlertValue);
		TimeUnit.SECONDS.sleep(1);
		Input.clickOn_js(alertsCriteria_UpdateLabel);
		TimeUnit.SECONDS.sleep(3);		
		return m_task_Result;
    }
    
}
