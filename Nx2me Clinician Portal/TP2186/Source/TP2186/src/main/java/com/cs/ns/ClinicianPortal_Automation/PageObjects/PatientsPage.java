package com.cs.ns.ClinicianPortal_Automation.PageObjects;

import org.openqa.selenium.By;

import com.reusecode.utilities.Excel;

public class PatientsPage extends DashboardPage {

	/*****************************************************************************************************************************
	 * Patients Page Web elements.
	 *****************************************************************************************************************************/
	
	public static By Patientpage_allFlowsheets = By.id("CheckMainContent_FlowsheetGridView_HeaderLevelCheckBox");
	
	public static By select_pt_patienttab = By.id("MainContent_FlowsheetGridView_ChkSelect_0");
	
	public static By patientEnroll_Status = By.id("MainContent_EnrollmentStatusDropDownList");
	
	public static By patientsPage_Prev = By.id("MainContent_DashboardGridviewPager1_btnFooterPrev");
	
	public static By patientsPage_Next = By.id("MainContent_DashboardGridviewPager1_btnFooterNext");
	
	public static By patientsPage_total_Records = By.id("MainContent_DashboardGridviewPager1_lblRecordCount");
	
	public static By patientdTab_patientName = By.id("MainContent_PatientListGridView_PatientLink1_0");
	
	public static By patient_confirmed = By.id("MainContent_DashboardGridviewPager1_FlowSheetTypeDropDownList");
	
	/*SETTINGS. */
	
	public static By patient_pin = By.id("MainContent_DashboardPatientGeneralInformation2_PIN");
	
	public static By patient_settings_dob = By.id("MainContent_DashboardPatientGeneralInformation2_PatientDOB");
	
	public static By Nxstage_Pid = By.id("MainContent_DashboardPatientGeneralInformation2_NxStagePatientID");
	
	public static By prescription_Edit = By.id("MainContent_btnEditPrescription");
	
	public static By prescription_Update = By.id("MainContent_btnSavePrescription");
	
	public static By prescription_Cancel = By.id("MainContent_btnCancelPrescription");
	
	public static By Nx2meAppSettings_Cancel = By.id("MainContent_btnCancelHomeView");
	
	public static By Nx2meAppSettings_Edit = By.id("MainContent_btnEditHomeView");
	
	public static By Nx2meAppSettings_Update = By.id("MainContent_btnSaveHomeView");
	
	/*SETTINGS : PATIENT PRESCRIPTION. */
	
	public static By numberOfTreatmentsPerWeek = By.id("MainContent_txtPRESTreatmentsPerWeek");
	
	public static By dialysateVolumePerTx = By.id("MainContent_txtPRESDialysateVolumePerWeek");
	
	public static By therapyFluidSource_Dropdown = By.id("MainContent_ddlPRESTherapyFluidSource"); 
	
	public static By dialysateLactate_Dropdown = By.id("MainContent_ddlPRESDialysateLactate");
	
	public static By dialysatePotassium_Dropdown = By.id("MainContent_ddlPRESDialysatePotassium");
	
	public static By dialysateSAK_Type = By.id("MainContent_ddlPRESDialysateSAKType");
	
	public static By cartridge_Type = By.id("MainContent_ddlPRESCartridgeType");
	
	public static By flowFraction	 = By.id("MainContent_txtPRESFlowFraction");
	
	public static By averageDialysateRate = By.id("MainContent_txtPRESTargetDialysateRate");
	
	public static By averageUltrafiltrationRate = By.id("MainContent_txtPRESTargetUFRate");
	
	public static By initialBloodFlowRate = By.id("MainContent_txtPRESInitialBloodFlowRate");
	
	public static By targetBloodFlowRate = By.id("MainContent_txtPRESTargetBloodFlowRate");
	
	public static By patientDryWeight = By.id("MainContent_txtPRESDryWeight");
	
	public static By patientAccessType = By.id("MainContent_ddlPRESPatientAccessType");
	
	public static By needleGauge = By.id("MainContent_ddlPRESNeedleGauge");
	
	public static By heparinDose = By.id("MainContent_txtPRESHeparinDose");
	
	public static By heparinRouteDose = By.id("MainContent_ddlPRESHeparinRoute");
	
	
	/*SETTINGS : PATIENT PRESCRIPTION :Error Text LAbels. */
	
	public static By numberOfTreatmentsPerWeek_ErrText = By.id("MainContent_val1");
	
	public static By dialysateVolumePerTx_ErrText = By.id("MainContent_val3");
	
	public static By flowFraction_ErrText = By.id("MainContent_valPRESFlowFraction");
	
	public static By averageDialysateRate_ErrText = By.id("MainContent_valPRESTargetDialysateRate");
	
	public static By averageUltrafiltrationRate_ErrText = By.id("MainContent_valPRESTargetUFRate");
	
	public static By initialBloodFlowRate_ErrText = By.id("MainContent_valPRESInitialBloodFlowRate");
	
	public static By targetBloodFlowRate_ErrText = By.id("MainContent_valPRESTargetBloodFlowRate");
	
	public static By patientDryWeight_ErrText = By.id("MainContent_valPRESDryWeight");
	
	public static By heparinDose_ErrText = By.id("MainContent_valPRESHeparinDose");
	
	public static By[] patientSettings_errors = { numberOfTreatmentsPerWeek_ErrText,dialysateVolumePerTx_ErrText,flowFraction_ErrText,
			 									  averageDialysateRate_ErrText,averageUltrafiltrationRate_ErrText,initialBloodFlowRate_ErrText,
			 									  targetBloodFlowRate_ErrText,patientDryWeight_ErrText};

	 public static String[] _patientSettings_errors_text = {"Number Of Treatments Per Week","Dialysate Volume Per Tx","Flow Fraction",
	   		  											    "Average Dialysate Rate","Average Ultrafiltration Rate","Initial Blood Flow Rate",
	   		  											    "Target Blood Flow Rate","Patient Dry Weight"};

	 public static String[] _patient_field_strings = {"Number of Tx's per week","Dialysate volume per Tx",	"Therapy fluid source","Dialysate lactate",	
			 										  "Dialysate potassium",	"Dialysate SAK type", "Cartridge type",	"Flow fraction",
			 										  "Average dialysate rate","Average ultrafiltration rate", "Initial blood flow rate",	
			 										  "Target blood flow rate", "Patient dry weight",	"Patient access type", "Needle gauge","Heparin dose",	
													  "Heparin route"};
	 
	/*NX2ME APP SETTINGS.*/
	
	public static By MRN = By.id("MainContent_txtCenterPatientID");
	
	public static By autoLogoutTimeout = By.id("MainContent_ddlHVAutoLogoutTimeout");
	
	public static By displayTechSupportPhone = By.id("MainContent_ddlHVDisplayTechSupportPhoneNumber");	
	
	public static By displayDialysisCenterPhone = By.id("MainContent_ddlHVDisplayDialysisCenterPhoneNumber");	
	
	public static By dialysisCenterPhone = By.id("MainContent_txtHVDialysisCenterNumber"); 	
	
	public static By CyclerTxValuesInterval = By.id("MainContent_ddlHVCyclerTreatmentInterval");
	
	public static By patientVitalsPromptInterval = By.id("MainContent_ddlHVPatientVitalsPromptInterval");
	
	public static By minimumSystolicBloodPressureAlert = By.id("MainContent_txtHVMinSysBP");	
	
	public static By maximumSystolicBloodPressureAlert = By.id("MainContent_txtHVMaxSysBP");
	
	public static By minimumDiastolicBloodPressureAlert = By.id("MainContent_txtHVMinDiastBP");	
	
	public static By maximumDiastolicBloodPressureAlert = By.id("MainContent_txtHVMaxDiastBP");
	
	public static By typeOfPre_TxAssessment = By.id("MainContent_ddlHVTypePreTreatmentAssessment");
	
	public static By typeOfPost_TxAssessment = By.id("MainContent_ddlHVTypePostTreatmentAssessment");
	
	public static By temperatureUnits = By.id("MainContent_ddlHVTemperatureUnits");	
	
	public static By maximumTemperaturePopupAlert = By.id("MainContent_txtHVMaxTempPopupAlert");
	
	public static By medicationAdministeredEntry = By.id("MainContent_ddlHVMedicationAdministeredEntry");	
	
	public static By yellowInfoCautionPopups = By.id("MainContent_ddlHVAllowInfoCautionPopup");	
	
	public static By allowPatientToEditDryWeight = By.id("MainContent_ddlHVPatientEditDryWeight");
	
	public static By remoteView = By.id("MainContent_ddlRemoteViewing");
	
	
	/*ALERTS CRITERIA. */
	
	public static By alertsCriteria_EditLabel = By.id("MainContent_btnEditAlerts");
	
	public static By alertsCriteria_UpdateLabel = By.id("MainContent_btnSaveAlerts");
	
	public static By alertsCriteria_CancelLabel = By.cssSelector("#MainContent_btnCancelAlerts");//By.id("MainContent_btnCancelAlerts");
	
	public static By dialysateVolumeDeviation = By.id("MainContent_txtAlertNewValue1");
	
	public static By dryWeightDeviation = By.id("MainContent_txtAlertNewValue2");
	
	public static By ofCyclerAlarms = By.id("MainContent_txtAlertNewValue3"); 
	
	public static By dialysateVolumeDeviation_Err = By.id("MainContent_valAlert1");
	
	public static By dryWeightDeviation_Err = By.id("MainContent_valAlert2");
	
	public static By ofCyclerAlarms_Err = By.id("MainContent_valAlert3"); 
	
	public static By alerts_criteria_lock_warning_text = By.id("msgDialog");
	
	public static By alerts_criteria_lock_warning = By.id("ui-id-1");
	
	public static By alerts_criteria_lock_warning_ok = By.cssSelector("body > div.ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.no-close.ui-dialog-buttons.ui-draggable.ui-resizable > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button");
	
	public static By[] Alerts_Labels = {dialysateVolumeDeviation, dryWeightDeviation, ofCyclerAlarms};
	
	public static By[] Alerts_errs = {dialysateVolumeDeviation_Err, dryWeightDeviation_Err, ofCyclerAlarms_Err};
			
	public static String[] _alerts_Text = {"Dialysate Volume Deviation","Dry Weight Deviation","Off Cycler alarms"};
	 
	public static String[] _err_data = {"Enter value within range: 0 - 60 in increments of 0.1", "Enter value within range: 0 - 30 in increments of 0.1",
	  									"Enter value within range: 1 - 50 in increments of 1"};

	public static String[] _updateTags_Before_Provision = {"UserId", "UserPassword", "CenterId", "PatientId"};
	
	public static String[] _tag_Vals_PN1 = { g_userId, g_userPWD, g_PN1_centerID, g_PN1_patientID };
	
	public static String[] _tag_Vals_PN2 = { g_userId, g_userPWD, g_PN2_centerID, g_PN2_patientID};
	
	public static String[] _tag_Vals_PN3 = { g_userId, g_userPWD, g_PN3_centerID, g_PN3_patientID};
	  
	public static String _dialysate_Vol_Deviation = Excel.getDataFromExcel(cPATIENT_ALERTS_DATA, cDIALYSATE_VOLUME_DEVIATION_ROW, cDIALYSATE_VOLUME_DEVIATION_COL);
	
	public static String _dry_Weight_Deviation = Excel.getDataFromExcel(cPATIENT_ALERTS_DATA, cDRY_WEIGHT_DEVIATION_ROW, cDRY_WEIGHT_DEVIATION_COL);
	  
	public static String _of_Cycler_Alarms = Excel.getDataFromExcel(cPATIENT_ALERTS_DATA, cOFF_CYCLER_ALARMS_ROW, cOFF_CYCLER_ALARMS_COL);
	
	public static String[] _alertsArr = {_dialysate_Vol_Deviation, _dry_Weight_Deviation, _of_Cycler_Alarms};
	
	/*MESSAGES. */
	
	public static By newMessageBtn = By.id("NewMsgButton");
	
	public static By unReadBtn = By.id("MarkUnreadButton");
	
	public static By messagePrintBtn = By.className("printMs");
	
	public static By messageRecieved = By.id("MainContent_MsgEdit_MessageChainPanel");
	
	public static By checkSigleMessage = By.id("MainContent_MessagesView_MessagesGridView_ChkSelect_0");
	
	public static By messageDataUnerolledWarning = By.id("MainContent_MsgEdit_MessagingUnavailable");
	
	public static By[] messageOptions = {newMessageBtn,unReadBtn,messagePrintBtn};
	
	public static By messageSubjectEdit = By.id("SubjectEdit");
	
	public static By messageResponse = By.id("ResponseMsg");
	
	public static By messageSend = By.id("Send");
	
	public static By verifyNewMessage = By.className("nmfld");
	
	public static By isMessagesThere = By.className("thead");
	
	public static By onMessageClick = By.className("nmfld");
	
	public static By replyMsgPanel = By.id("MainContent_MsgEdit_ReplyMsgPanel");
	
	public static By enterReplyMsg = By.id("ResponseMsg");

	
	/*REPORTS. */
	
	public static By reportsTab = By.id("MainContent_navReports");
	
	public static By FSSummaryReport = By.id("GenerateReport");
	
	public static By FSCalendorReport = By.id("GenerateReportCO");
	
	public static By patientsReportStartDate = By.id("txtStartReportDate");
	
	public static By patientsReportEndDate = By.id("txtEndReportDate");
	
	public static By reportErrMsg = By.id("ReportErrorMsg");
	
	public static By[] ptReports = {FSSummaryReport,FSCalendorReport};

}
