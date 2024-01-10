package com.cs.ns.ClinicianPortal_Automation.PageObjects;

import org.openqa.selenium.By;

public class TreatmentsPage extends PatientsPage {

	public static By FS_PrintWithAttachments = By.id("MainContent_ActionPane_PrintButtonWithAttachments");
	
	public static By isFlowsheetPrinted = By.id("MainContent_FlowsheetSummarySection_FlowsheetBeenPrinted");
	
	public static By goToCenterFlowsheetDetailPage = By.cssSelector("#Formtop > div > div:nth-child(4) > div:nth-child(9) > div:nth-child(3) > div > table > tbody > tr > td > div > div > div");
	
	public static By goToCenterFlowsheetDetailPage_Data = By.cssSelector("#Formtop > div > div:nth-child(4) > div:nth-child(9) > div:nth-child(3) > div > table > tbody > tr > td > div > div > p");
	
	public static By goToCenterFlowsheetDetailPage_Data2 = By.cssSelector("#Formtop > div > div:nth-child(4) > div:nth-child(9) > div:nth-child(3) > div > table > tbody > tr:nth-child(1) > td > div > div");
	
	public static By goToCenterFlowsheetDtailPage_CnDate = By.cssSelector("#Formtop > div > div:nth-child(4) > div:nth-child(9) > div:nth-child(3) > div > table > tbody > tr > td > div > div > div");

	public static By deleteCenterNote = By.cssSelector("#Formtop > div > div:nth-child(4) > div:nth-child(9) > div:nth-child(3) > div > table > tbody > tr:nth-child(1) > td > div > div > p > a");
	
	public static By deleteCenterNote_Yes = By.cssSelector("body > div.ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.ui-dialog-buttons.ui-draggable > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1)");
	
	public static By goToPatientFlowsheetDetailsPageText =  By.cssSelector("#Formtop > div > div:nth-child(4) > div:nth-child(5) > div:nth-child(2) > div > table > tbody > tr:nth-child(1) > td");
	

}
