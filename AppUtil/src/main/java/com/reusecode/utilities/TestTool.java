package com.reusecode.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import com.jacob.com.LibraryLoader;
import com.reusecode.iodata.Input;

import autoitx4java.AutoItX;

public class TestTool{

	public static AutoItX m_autoItx;
	
	public static String m_testDataSourcePath = Input.m_curDir + "/Configuration/TestToolData";
	
	public static String m_testtool_TestReults_Dir = "C:/ProgramData/NxStage/TestResults";
	
	public static String m_testtoolOuputDir = "C:/SQA/Flowsheets";
	
	public static String m_testtool_SQA_Dir = "C:/SQA";
	
	public static String m_html_File_Name = "ConnectivityServerTestTool_Results_*.";
	
	public static String m_testtool_DB_Server = null; 
	
	public static String m_testtool_PDMP_WEB_Server = null; 
	
	public static String m_VPN_ID = null; 
	
	public static String m_VPN_Password = null; 
	
	public static String m_testtool_Version = null;
	
	public static String m_testtool_GUI_Path = " C:\\NxStage\\Connectivity\\TestTool\\Connectivity.TestToolGui";
	
	public static int cTIMER_DELAY = 1200;
	
	public static int cRETRY = 10;
	
	public static int cSECONDS_3 = 3;
	
	public static String testtool_version_header = null;
	
	/**********************************************************************************************
	 * Method Name : testtoolInit()
	 * 
	 * @throws InterruptedException
	 * 
	 * @return void
	 * 
	 * Description : 
	 * 
	 * 		This method is used to launch the testtool GUI.
	 * @throws IOException 
	 * 
	 **********************************************************************************************/
	
	public static void testtoolInit() throws InterruptedException, IOException 
	{	
		String runcmd = "runas /netonly /user:" + m_VPN_ID + m_testtool_GUI_Path;		
		String jacobDllVersionToUse = null;
		testtool_version_header = getTestToolVersion();
		
		if (System.getProperty("sun.arch.data.model").contains("86"))
		{			
			jacobDllVersionToUse = "jacob-1.19-x86.dll";
		} 
		else
		{			
			jacobDllVersionToUse = "jacob-1.19-x64.dll";
		}		
		
		File file = new File(Input.m_curDir + "/Configuration/libs/tools", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		m_autoItx = new AutoItX();
		
		if(m_autoItx.winExists(testtool_version_header))
		{
			m_autoItx.winClose(testtool_version_header);
			TimeUnit.SECONDS.sleep(3);
			System.out.println(Input.timeStamp()+" : Previous Instance of TestTool has been closed. ");
		}		
		TimeUnit.SECONDS.sleep(2);
		Runtime.getRuntime().exec("taskkill /F /IM Connectivity.TestToolGui.exe /T");
		TimeUnit.SECONDS.sleep(5);
		m_autoItx.run("cmd.exe", "", AutoItX.SW_SHOW);
		TimeUnit.SECONDS.sleep(2);
		
		if(m_autoItx.winExists("C:\\WIINDOWS\\SYSTEM32\\cmd.exe"))
		{			
			m_autoItx.winActivate("C:\\WIINDOWS\\SYSTEM32\\cmd.exe");
		}
		else if(m_autoItx.winExists("C:\\WIINDOWS\\System32\\cmd.exe"))
		{			
			m_autoItx.winActivate("C:\\WIINDOWS\\System32\\cmd.exe");
		}
		else{/*Do nothing.*/}
		
		TimeUnit.SECONDS.sleep(2);
		m_autoItx.send(runcmd);
		m_autoItx.send("{ENTER}!", false);
		TimeUnit.SECONDS.sleep(3);
		m_autoItx.send(m_VPN_Password);
		m_autoItx.send("{ENTER}!n", false);
		TimeUnit.SECONDS.sleep(3);
		m_autoItx.winWait(getTestToolVersion());
		
		TimeUnit.SECONDS.sleep(2);
		try
		{
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(Input.timeStamp()+" : TEST_TOOL_TASK_ERR : "+e);
		}
		TimeUnit.SECONDS.sleep(3);
	}
 
	/**
	 * This method is used to launch the testtool GUI on Domain.
	 * @throws IOException 
	 */
	
	public static void testtoolInit_OnDomain() throws InterruptedException, IOException 
	{			
		String jacobDllVersionToUse = null;	
		testtool_version_header = getTestToolVersion();
		m_autoItx = new AutoItX();
		if (System.getProperty("sun.arch.data.model").contains("86"))
		{			
			jacobDllVersionToUse = "jacob-1.19-x86.dll";
		} 
		else
		{			
			jacobDllVersionToUse = "jacob-1.19-x64.dll";
		}		
		File file = new File(Input.m_curDir + "/Configuration/libs/tools", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		
		if(m_autoItx.winExists(testtool_version_header))
		{
			m_autoItx.winClose(testtool_version_header);
			System.out.println(Input.timeStamp()+" : Previous Instance of TestTool has been closed. ");
			TimeUnit.SECONDS.sleep(3);
		}
		
		//Runtime.getRuntime().exec("taskkill /F /IM Connectivity.TestToolGui.exe /T");
		TimeUnit.SECONDS.sleep(3);
		m_autoItx.run("cmd.exe", "", AutoItX.SW_SHOW);
		TimeUnit.SECONDS.sleep(2);
		
		if(m_autoItx.winExists("C:\\WIINDOWS\\SYSTEM32\\cmd.exe"))
		{			
			m_autoItx.winActivate("C:\\WIINDOWS\\SYSTEM32\\cmd.exe");
		}
		else if(m_autoItx.winExists("C:\\WIINDOWS\\System32\\cmd.exe"))
		{			
			m_autoItx.winActivate("C:\\WIINDOWS\\System32\\cmd.exe");
		}
		else{/*Do nothing.*/}
		
		TimeUnit.SECONDS.sleep(2);
		m_autoItx.send(m_testtool_GUI_Path);
		m_autoItx.send("{ENTER}!", false);
		
		TimeUnit.SECONDS.sleep(3);
		try
		{
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(Input.timeStamp()+" : TEST_TOOL_TASK_ERR : "+e);
		}
		TimeUnit.SECONDS.sleep(6);
		
	}
 
	
	/**********************************************************************************************
	 * Method Name : createAndUploadFlowsheet()
	 * 
	 * @param passed_XML_CFG
	 * @return boolean : return true if success, and false otherwise.
	 * @throws InterruptedException
	 * 
	 * Description : 
	 * 
	 * 		This method is used tPre_NxConnDbo perform create and execute flow sheet task through the cfg file or xml file concept.
	 *  
	 **********************************************************************************************/
	
	public static Boolean createAndUploadFlowsheet(String passed_XML_CFG) throws InterruptedException 
	{		
		String _testtool_Ack = null;
		Input.m_Task_Result = Input.m_Task_Passed;
		String db_server = getTestToolDBServer();
		String pdmp_web_server = getTestToolPDMPWEB_Server();
		String testtool_version_header = getTestToolVersion();
		m_autoItx.send("{RIGHT}!", true);
		TimeUnit.SECONDS.sleep(3);
		m_autoItx.send("{RIGHT}!", false);
		TimeUnit.SECONDS.sleep(4);
		
		for(int i = 0; i < cRETRY; i++)
		{
			m_autoItx.controlFocus(testtool_version_header, "", "Edit3");
			TimeUnit.SECONDS.sleep(4);			
			m_autoItx.send(db_server);		
			TimeUnit.SECONDS.sleep(4);			
			String selectedDBServer = m_autoItx.controlGetText(testtool_version_header, "", "Edit3");
			System.out.println(Input.timeStamp() + " : Selected DB Server : " + selectedDBServer);
			TimeUnit.SECONDS.sleep(2);
			if(selectedDBServer.contains(db_server))
			{			
				System.out.println(Input.timeStamp() + " : Test Tool DB Server Selection Success.");
				TimeUnit.SECONDS.sleep(2);		
				break;
			}
			else
			{				
				System.out.println(Input.timeStamp() + " : Invalid Test Tool DB Server Selected.");
				TimeUnit.SECONDS.sleep(3);
				m_autoItx.ControlSetText(testtool_version_header,"","Edit3","");
				TimeUnit.SECONDS.sleep(3);			
			}
		}		
		for(int j = 0; j < cRETRY; j++)
		{			
			m_autoItx.controlFocus(testtool_version_header, "", "Edit4");
			TimeUnit.SECONDS.sleep(2);
			m_autoItx.send(pdmp_web_server);
			TimeUnit.SECONDS.sleep(4);			
			String selectedPDMPServer = m_autoItx.controlGetText(testtool_version_header, "", "Edit4");
			System.out.println(Input.timeStamp() + " : Selected PDMP Server : "+ selectedPDMPServer);
			TimeUnit.SECONDS.sleep(2);
			
			if(selectedPDMPServer.contains(pdmp_web_server))
			{				
				System.out.println(Input.timeStamp()+" : Test Tool PDMP WEB Server Selection Success...");
				TimeUnit.SECONDS.sleep(3);		
				break;
			}
			else
			{				
				System.out.println(Input.timeStamp()+" : Invalid Test Tool DB Server Selected...");
				TimeUnit.SECONDS.sleep(3);
				m_autoItx.ControlSetText(testtool_version_header, "", "Edit4", "");
				TimeUnit.SECONDS.sleep(3);			
			}
			
		}
		
		m_autoItx.controlFocus(testtool_version_header, "", "WindowsForms10.BUTTON.app.0.30495d1_r6_ad153");
		TimeUnit.SECONDS.sleep(3);		
		m_autoItx.controlClick(testtool_version_header, "", "WindowsForms10.BUTTON.app.0.30495d1_r6_ad153");
		TimeUnit.SECONDS.sleep(15);	
		
		for(int k = 0; k < cRETRY; k++)
		{
			TimeUnit.SECONDS.sleep(2);
			m_autoItx.send(passed_XML_CFG);
			
			TimeUnit.SECONDS.sleep(8);
			System.out.println(Input.timeStamp() + " Passed CFG File: " + passed_XML_CFG +" Right");
			String selectedCFGFile = m_autoItx.controlGetText("Test Tool Task / Cfg File", "", "Edit1");
			System.out.println(Input.timeStamp() + " TEST TOOL CFG File : "+ selectedCFGFile );
			
			if(selectedCFGFile.contains(passed_XML_CFG))
			{				
				System.out.println(Input.timeStamp()+" : CFG File Selected.");
				TimeUnit.SECONDS.sleep(3);
				break;
			}
			else
			{
				System.out.println(Input.timeStamp() + " : Invalid CFG File Selected.");
				TimeUnit.SECONDS.sleep(3);			
				m_autoItx.ControlSetText("Test Tool Task / Cfg File", "", "Edit1", "");
				TimeUnit.SECONDS.sleep(3);
			}			
			TimeUnit.SECONDS.sleep(3);	
		}		
		
		m_autoItx.controlFocus("Test Tool Task / Cfg File", "", "Button1");
		TimeUnit.SECONDS.sleep(8);
		m_autoItx.controlClick("Test Tool Task / Cfg File", "", "Button1");
		TimeUnit.SECONDS.sleep(8);		
		m_autoItx.controlFocus(testtool_version_header, "", "WindowsForms10.BUTTON.app.0.30495d1_r6_ad151");
		TimeUnit.SECONDS.sleep(5);		
		m_autoItx.controlClick(testtool_version_header, "", "WindowsForms10.BUTTON.app.0.30495d1_r6_ad151");		
		TimeUnit.SECONDS.sleep(5);
		
		if(m_autoItx.winWait("Test Tool Task Execution Results", "",3 ))
		{			
			String TestTool_Ack = m_autoItx.controlGetText("Test Tool Task Execution Results", "", "WindowsForms10.RichEdit20W.app.0.30495d1_r6_ad11");
			System.out.println(Input.timeStamp() + " : "+TestTool_Ack);
			Input.m_Task_Result = Input.m_Task_Failed;
		}
		
		_testtool_Ack = m_autoItx.controlGetText("Test Tool Task Execution Results", "", "WindowsForms10.RichEdit20W.app.0.30495d1_r6_ad11");
		System.out.println(Input.timeStamp() + " : " + _testtool_Ack);		
		
		if(_testtool_Ack.contains("No such host is known"))
		{			
			System.out.println(Input.timeStamp()+" : Test Tool Task Failed, Reason for Failing is Internet/Server issue in you PC");
			Input.m_Task_Result = Input.m_Task_Failed;
		}
		return Input.m_Task_Result;
	}	
	
	/**********************************************************************************************
	 * Method Name : testtoolDataConfiguration()
	 * 
	 * @param testTool_CreateUpload_Dir
	 * @param testTool_TestData_Dir
	 * @return boolean : return true if task success, false otherwise.
	 * 
	 * Description : 
	 *  	
	 *  	This method is used to configure the testtool test data in the local PC root directory.
	 * 
	 **********************************************************************************************/
	
	public static boolean testtoolDataConfiguration(String testTool_CreateUpload_Dir, String testTool_TestData_Dir )
	{		
		Input.m_Task_Result = Input.m_Task_Failed;
		if(!FileHandler.checkIfFolderExists(m_testtoolOuputDir))
		{			
			if(FileHandler.checkIfFolderExists_IfNotCreate(m_testtoolOuputDir) != null)
			{				
				if(FileHandler.isDirectoryEmpty(m_testtool_SQA_Dir))
				{
					FileHandler.copyFilesToDirectory(m_testDataSourcePath, m_testtool_SQA_Dir);
				}
				if(FileHandler.checkIfFolderExists_IfNotCreate(testTool_CreateUpload_Dir) != null)
				{					
					if(FileHandler.isDirectoryEmpty(testTool_CreateUpload_Dir))
					{
						System.out.println(Input.timeStamp()+" : Folder [ " + testTool_CreateUpload_Dir + " ] is Empty.");
					}
					else
					{
						System.out.println(Input.timeStamp()+" : Folder [ " + testTool_CreateUpload_Dir + " ] is not Empty.");
					}
				}
				if(FileHandler.checkIfFolderExists_IfNotCreate(testTool_TestData_Dir) != null)
				{					
					if(FileHandler.isDirectoryEmpty(testTool_TestData_Dir))
					{						
						System.out.println(Input.timeStamp() + " : Folder [ " + testTool_TestData_Dir + " ] is Empty.");
					}
					else
					{
						System.out.println(Input.timeStamp() + " : Folder [ " + testTool_TestData_Dir + " ] is not Empty.");
					} 
				} 
				Input.m_Task_Result = Input.m_Task_Passed;
			}
		}
		if( FileHandler.checkIfFolderExists(testTool_CreateUpload_Dir) && FileHandler.checkIfFolderExists(testTool_TestData_Dir) )
		{
			if( FileHandler.isDirectoryEmpty(testTool_CreateUpload_Dir) )
			{
				FileHandler.copyFilesToDirectory(m_testDataSourcePath,m_testtool_SQA_Dir); 
				Input.m_Task_Result = Input.m_Task_Passed;
				System.out.println(Input.timeStamp()+" File_Handler : Re-verification of directory [ " + testTool_CreateUpload_Dir + " ]");
			}
			if( FileHandler.isDirectoryEmpty(testTool_TestData_Dir) )
			{
				FileHandler.copyFilesToDirectory(m_testDataSourcePath,m_testtool_SQA_Dir); 
				System.out.println(Input.timeStamp() + " File_Handler : Re-verification of directory [ " + testTool_TestData_Dir + " ]");
				Input.m_Task_Result = Input.m_Task_Passed;
			}
		}
		else
		{
			FileHandler.copyFilesToDirectory(m_testDataSourcePath,m_testtool_SQA_Dir);  
			Input.m_Task_Result = Input.m_Task_Passed;
		}  
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : testtoolTaskExec()
	 * 
	 * @param passed_XML_CFG
	 * @param xml_entries_in_CFG
	 * @return boolean : return true if task success, false otherwise.
	 * 
	 * @throws InterruptedException
	 * 
	 * Description : 
	 * 
	 * 		This method will execute the testtool task.
	 * @throws IOException 
	 * 
	 **********************************************************************************************/
	
	public static Boolean testtoolTaskExec(String passed_XML_CFG, int xml_entries_in_CFG) throws InterruptedException, IOException
	{	
		Input.m_Task_Result = Input.m_Task_Failed;
		TimeUnit.SECONDS.sleep(3);
		testtoolInit();
		//testtoolInit_OnDomain();
		String timestamp = Input.timeStamp();
		if(createAndUploadFlowsheet(passed_XML_CFG))
		{
			if(taskTimer(timestamp))
			{
				System.out.println(Input.timeStamp() + " : TEST_TOOL :  PASSED");
				TimeUnit.SECONDS.sleep(1);
				if( checkHTMLReport(timestamp,xml_entries_in_CFG) )
				{
					System.out.println(Input.timeStamp() + " : TEST_TOOL : HTML Reports PASSED");
					Input.m_Task_Result = Input.m_Task_Passed;
					TimeUnit.SECONDS.sleep(1);
					testtoolExit();
				}
				else
				{
					System.out.println(Input.timeStamp()+" : TEST_TOOL : HTML Reports FAILED");
					Input.m_Task_Result = Input.m_Task_Failed;
					TimeUnit.SECONDS.sleep(1);
					testtoolExit();
				}
			}
		}
		else
		{
			System.out.println(Input.timeStamp() + " : TEST_TOOL : createAndUploadFlowsheet Failed");
		}			
		return Input.m_Task_Result;	
	}
	
	/**********************************************************************************************
	 * Method Name : testtoolExit()
	 * 
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method will perform the exit task of the testtool.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * 
	 **********************************************************************************************/
	
	public static void testtoolExit() throws IOException, InterruptedException
	{		
		TimeUnit.SECONDS.sleep(1);
		if(m_autoItx.winExists(getTestToolVersion()))
		{
			m_autoItx.winClose(getTestToolVersion());
			TimeUnit.SECONDS.sleep(1);
		}
		//Runtime.getRuntime().exec("taskkill /F /IM Connectivity.TestToolGui.exe /T");
	}
	
	/**********************************************************************************************
	 * Method Name : taskTimer()
	 * 
	 * @param timestamp
	 * @return boolean: return true if task success, false otherwise.
	 * @throws InterruptedException
	 * 
	 * description : 
	 * 
	 * 		This method is used to wait until the timer is expired or task to be completed, once task is successfully 
	 * 		complete then the timer will expire.
	 * 
	 **********************************************************************************************/
	
	public static boolean taskTimer(String timestamp) throws InterruptedException
	{	
		Input.m_Task_Result = Input.m_Task_Failed;
		boolean start_Timer = Input.m_Task_Passed;
	    int iCounter = 0;
	    System.out.print("Test Tool Execution is progress");
	    int timerDelay = 0;
	    
	    while(start_Timer)
	    {
	        TimeUnit.SECONDS.sleep(1);
	        timerDelay++;
	        if(timerDelay == cTIMER_DELAY)
	        {
	        	System.out.println("\n"+Input.timeStamp() + " : Timer expired, Going to kill timer....");
	        	start_Timer = Input.m_Task_Failed;
	        }
	        String uploadFileName = FileHandler.getNewestFile(m_testtool_TestReults_Dir, m_html_File_Name, "html").toString();
			String uploadFileName_timestamp = uploadFileName.substring(70, 85);
			int status = uploadFileName_timestamp.compareTo(timestamp);
			
			if(status > 0 || status == 0)
			{
				System.out.println("\n" + Input.timeStamp() + " : TEST_TOOL : TestTool Task Completed.");
				Input.m_Task_Result = Input.m_Task_Passed;
				start_Timer = Input.m_Task_Failed;
			}
			if(iCounter == cSECONDS_3)
			{
				System.out.print(".");
				iCounter = 0;
			}
			iCounter++;
	    }
	    System.out.println(Input.timeStamp() + " : timer has been killed");   
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : checkHTMLReport()
	 * 
	 * @param timestamp
	 * @param xml_entries_in_CFG
	 * @return boolean : return true if task success, false otherwise
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to check the HTML reports passed or failed status that are generated by testtool. 
	 * 
	 **********************************************************************************************/
	
	public static Boolean checkHTMLReport(String timestamp, int xml_entries_in_CFG) throws InterruptedException
	{		
		Input.m_Task_Result = Input.m_Task_Passed;		
		By single_xml_input = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(1)");		
		By CreateFSTaskName = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(2) > a");
		By UploadFSTask = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(3) > td:nth-child(2) > a");
		By ExtraTask1 = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(4) > td:nth-child(2) > a");
		By[] TaskNameElements = { CreateFSTaskName, UploadFSTask, ExtraTask1 };		
		By createFlowsheetTaskResult = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(3)");
		By uploadFlowsheetTaskResult = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(3) > td:nth-child(3)");
		By uploadAttachementsResults = By.cssSelector("body > table:nth-child(2) > tbody > tr:nth-child(4) > td:nth-child(3)");
		By[] TaskElementsResults = {createFlowsheetTaskResult, uploadFlowsheetTaskResult, uploadAttachementsResults};
		
		String uploadFileName = FileHandler.getNewestFile(m_testtool_TestReults_Dir, m_html_File_Name, "html").toString();
		String uploadFileName_timestamp = uploadFileName.substring(70, 85);
		int status = uploadFileName_timestamp.compareTo(timestamp);
		
		if(status > 0 || status == 0)
		{
			System.out.println(Input.timeStamp() + " : TEST_TOOL : TestTool Task Sucess.");
			System.out.println(Input.timeStamp() + " : TEST_TOOL : Verifying The Status for [ " + uploadFileName+" ] File is in progress.");
			System.out.println(Input.timeStamp() + " : HTML_FILE TO OPEN : " + uploadFileName);			
			Input.urlNavigate(uploadFileName);
			TimeUnit.SECONDS.sleep(3);			
			if(xml_entries_in_CFG == 1)
			{
				String color_of_status = Input.getAttributeValue(single_xml_input, "bgcolor");
				System.out.println("STATUS : " + color_of_status);
				if(color_of_status.contains("green") || color_of_status.equalsIgnoreCase("green"))
				{
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Input.timeStamp()+" : TEST_TOOL : " + Input.getLabelText(single_xml_input) + " : PASSED");
				}
				else
				{
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Input.timeStamp()+" : TEST_TOOL : Task Failed ");
					Input.m_Task_Result = Input.m_Task_Failed;
				}
			}
			else
			{
				for(int i = 0; i < xml_entries_in_CFG; i++)
				{
					TimeUnit.SECONDS.sleep(1);
					if(Input.getLabelText(TaskElementsResults[i]).contains("PASSED"))
					{
						System.out.println(Input.timeStamp() + " : TEST_TOOL : " + Input.getLabelText(TaskNameElements[i]) + " : PASSED");
					}
					else
					{
						System.out.println(Input.timeStamp() + " : TEST_TOOL : Task Failed ");
						Input.m_Task_Result = Input.m_Task_Failed;
					}
				}
			}
			Input.getDriver().close();
		}
		else if(status < 0)
		{
			System.out.println(Input.timeStamp() + " : TEST_TOOL : TestTool Task Failed, Since no new file found with latest timestamp.");
			Input.m_Task_Result = Input.m_Task_Failed;
		}
		else{/*Do Nothing*/}		
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : setTestToolDBServer()
	 * 
	 * @param DB_Server
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to set the testtool DB server.
	 * 
	 **********************************************************************************************/
	public static void setTestToolDBServer(String DB_Server)
	{		
		m_testtool_DB_Server = DB_Server;
	}
	
	/**********************************************************************************************
	 * Method Name : setTestToolPDMPWEBServer()
	 * 
	 * @param PDMP_WEB_Server
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to set the testtool PDMP WEB server.
	 * 
	 **********************************************************************************************/
	
	public static void setTestToolPDMPWEBServer(String PDMP_WEB_Server)
	{		
		m_testtool_PDMP_WEB_Server = PDMP_WEB_Server;
	}
	
	/**********************************************************************************************
	 * Method Name : getTestToolDBServer()
	 * 
	 * @return String 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the testtool DB server.
	 * 
	 **********************************************************************************************/
	
	public static String getTestToolDBServer()
	{		
		return m_testtool_DB_Server;
	}
	
	/**********************************************************************************************
	 * Method Name : getTestToolPDMPWEB_Server()
	 * 
	 * @return String 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the testtool PDMP WEB server.
	 * 
	 **********************************************************************************************/
	
	public static String getTestToolPDMPWEB_Server()
	{		
		return m_testtool_PDMP_WEB_Server;
	}
	
	/**********************************************************************************************
	 * Method Name : setTestToolVersion()
	 * 
	 * @param TestTool_version
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to set the testtool version header.
	 * 
	 **********************************************************************************************/
	
	public static void setTestToolVersion(String TestTool_version)
	{		
		m_testtool_Version = TestTool_version;
	}
	
	/**********************************************************************************************
	 * Method Name : getTestToolVersion()

	 * @return string 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the testtool version header.
	 * 
	 **********************************************************************************************/
	
	
	public static String getTestToolVersion()
	{		
		return m_testtool_Version;		
	}
	
	/**********************************************************************************************
	 * Method Name : set_VPNCredentials()
	 * 
	 * @param VPN_ID
	 * @return VPN_Password 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to set the VPN Id, and VPN Password to launch the test tool.
	 * 
	 **********************************************************************************************/
	
	public static void set_VPNCredentials(String VPN_ID, String VPN_Password)
	{		
		m_VPN_ID = VPN_ID;
		m_VPN_Password = VPN_Password;
	}
	
	/**********************************************************************************************/
}
