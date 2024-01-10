package com.cs.ns.ClinicianPortal_Automation.Base_class;

import javax.swing.JFrame;

import com.reusecode.extentreports.Listener;
import com.reusecode.iodata.Input;
import com.reusecode.utilities.Excel;

public class BaseClass extends Listener {

	/**********************************************************************************************
	 * STATIC VARIABLES :
	 **********************************************************************************************/
	
	public static String CUR_DIR = System.getProperty("user.dir");
	
	public static boolean EXCEL_SHEET_DIR = Input.setExcelPath(CUR_DIR + "\\Configuration\\Driven_Data\\Portal_Drive_Data_1.xlsx");
	
	public static String strExcelPath = CUR_DIR + "\\Configuration\\Driven_Data\\Portal_Drive_Data_1.xlsx";
	
	public static String click = "click";
	
	public static String doubleclick = "doubleclick";
	
	public static String yes = "Yes";
	
	public static String no = "No";
	
	public static String value = "value";
	
	public static String g_testtoolStandardDir = "C:\\SQA\\";
	
	public static String g_testDataDir = "/Test_Data";
	
	public static String g_createUploadFileDir = "/create_upload_files"; 
	
	public static boolean g_Test_Task_Result = false;
	
	public static boolean g_Test_Task_Failed = false;
	
	public static boolean g_Test_Task_Passed = true;
	
	public static boolean g_isPortalWindowActive = false;
	
	public static String g_refPatientName1 = null;
	
	public static String g_provisionedPatient = "provision";
	
	public static String g_unprovisionedPatient = "un-prvisioned";
	
	public static String g_pendingPatient = "pending";
	
	public static int cPAGELOADTIME = 15; 
	
	/* 
	 * UI and Functionality verification data sheets.
	 */
	
    public static  int cUI_FUNCTIONALITY_DATA = 0;
    
    public static int cPATINET_TX_SETTINGS_DATA = 1;
    
    public static int cPATIENT_ALERTS_DATA = 2;    
    
    /* 
     * URL    
     */
    
    public static int cURL_ROW = 6;
    
    public static int cURL_COL = 5;

    /* 
     * Browser type.
     */
    
    public static int cBROWSER_TYPE_ROW = 6;
    
    public static int cBROWSER_TYPE_COL = 4;
    
    public static boolean g_browser =  Input.setBrowser(Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cBROWSER_TYPE_ROW, cBROWSER_TYPE_COL));
   
    public static String g_baseURL = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cURL_ROW, cURL_COL);
    
    /* 
     * Login credentials for clinicina portal.
     */
    
    public static int cCENTER_ID_ROW = 2;
    public static int cCENTER_ID_COL = 0;
    
    public static int cUSER_ID_ROW = 2;
    public static int cUSER_ID_COL = 1;
    
    public static int cPASSWORD_ROW = 2;
    public static int cPASSWORD_COL = 2;
    
    public static int cNxCENTER_ID_ROW = 2;
    public static int cNxCENTER_ID_COL = 4;
    
    public static int cNxUSER_ID_ROW = 2;
    public static int cNxUSER_ID_COL = 5;
    
    public static int cNxPASSWORD_ROW = 2;
    public static int cNxPASSWORD_COL = 6;

    /* 
     * VPN credentials for testtool.
     */
    
    public static int cVPN_USER_ID_ROW = 7;
    public static int cVPN_USER_ID_COL = 0;
    
    public static int cVPN_PASSWORD_ROW = 7;
    public static int cVPN_PASSWORD_COL = 1;    

    /*
     * Center ID's and Patient ID's for xml
     */
    
    public static int cCID1_ROW = 12;
    public static int cCID1_COL = 0;
    
    public static int cCID2_ROW = 13;
    public static int cCID2_COL = 0;
    
    public static int cCID3_ROW = 14;
    public static int cCID3_COL = 0;

    public static int cPID1_ROW = 12;
    public static int cPID1_COL = 1;
    
    public static int cPID2_ROW = 13;
    public static int cPID2_COL = 1;
    
    public static int cPID3_ROW = 14;
    public static int cPID3_COL = 1;

    /*
     * TestTool data
     */
    
    public static int cTT_HEADER_VERSION_ROW = 19;
    public static int cTT_HEADER_VERSION_COL = 0;
    
    public static int cTT_DB_SERVER_ROW = 19;
    public static int cTT_DB_SERVER_COL = 1;
    
    public static int cTT_PDMP_SERVER_ROW = 19;
    public static int cTT_PDMP_SERVER_COL = 2;
         
    /*
     * Alerts
     */
    
    public static int cPATIENT_ALERTS_START_ROW = 2;
    public static int cPATIENT_ALERTS_START_COL = 0;
    
    public static int cDIALYSATE_VOLUME_DEVIATION_ROW = 4;
    public static int cDIALYSATE_VOLUME_DEVIATION_COL = 0;
    
    public static int cDRY_WEIGHT_DEVIATION_ROW = 4;
    public static int cDRY_WEIGHT_DEVIATION_COL = 1;
    
    public static int cOFF_CYCLER_ALARMS_ROW = 4;
    public static int cOFF_CYCLER_ALARMS_COL = 2;
    
    /* 
     * Patient TX Settings.
     */
    
    public static int cPATINET_TX_SETTINGS_START_ROW = 2;
    public static int cPATINET_TX_SETTINGS_START_COL = 0;
	
    public static String g_centerId = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cCENTER_ID_ROW, cCENTER_ID_COL);
    
	public static String g_userId = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cUSER_ID_ROW, cUSER_ID_COL);
	
	public static String g_userPWD = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cPASSWORD_ROW, cPASSWORD_COL);
	
	
	public static String g_centerId_ADMIN = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cNxCENTER_ID_ROW, cNxCENTER_ID_COL);
    
	public static String g_userId_ADMIN= Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cNxUSER_ID_ROW, cNxUSER_ID_COL);
	
	public static String g_userPWD_ADMIN = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cNxPASSWORD_ROW, cNxPASSWORD_COL);
		
	
	public static String g_PN1_centerID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cCID1_ROW, cCID1_COL);
	
	public static String g_PN1_patientID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cPID1_ROW, cPID1_COL);
	
	public static String g_PN2_centerID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cCID2_ROW, cCID2_COL);
	
	public static String g_PN2_patientID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cPID2_ROW, cPID2_COL);
	
	public static String g_PN3_centerID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cCID3_ROW, cCID3_COL);
	
	public static String g_PN3_patientID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cPID3_ROW, cPID3_COL);
	
	/*
	 * XML Main Tags
	 */
	public static String g_createFlowsheetV2_Tag = "CreateFlowsheetV2";
	
	public static String g_centerID_Tag = "CenterId";
	
	public static String g_patientID_Tag = "PatientId";
	
	public static String g_numberFlowsheets_Tag = "NumberFlowsheets";
	
	public static String g_treatmentDate_Tag = "TreatmentDate";	
	
	/*
	 * TestTool Version, DB and PDMP servers data.
	 */
	public static String g_Testtool_Version = "Connectivity.TestToolGui";
	 
    public static String g_Testtool_DB_Server = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cTT_DB_SERVER_ROW, cTT_DB_SERVER_COL);
    
    public static String g_Testtool_PDMP_Server = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cTT_PDMP_SERVER_ROW, cTT_PDMP_SERVER_COL);
    
    /* 
     * Nxstorage VPN Credentials to launch the testtool.
     */
    
    public static String g_VPN_ID = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cVPN_USER_ID_ROW, cVPN_USER_ID_COL);
    
    public static String g_VPN_Password = Excel.getDataFromExcel(cUI_FUNCTIONALITY_DATA, cVPN_PASSWORD_ROW, cVPN_PASSWORD_COL);
    
}
