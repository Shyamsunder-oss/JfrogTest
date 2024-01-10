package com.cs.ns.ClinicianPortal_Automation.PageObjects;

import org.openqa.selenium.By;

public class AdminPage extends NewMessagesPage {

	/********************************************************************************
	*Web Elements and Locators for ADMIN_PAGE. 
	*********************************************************************************/
	
	/*ADMIN Access. */
	
	public static By adminEdit = By.cssSelector("#MainContent_gvUsers > tbody > tr:nth-child(2) > td:nth-child(4) > a");
	
	public static By readOnlyEdit = By.cssSelector("#MainContent_gvUsers > tbody > tr.tbody.odd > td:nth-child(4) > a");
	
	public static By readWriteEdit = By.cssSelector("#MainContent_gvUsers > tbody > tr:nth-child(4) > td:nth-child(4) > a");
	
	public static By adminDropdown = By.id("MainContent_gvUsers_ddlRole_0");
	
	public static By adminUpdate = By.cssSelector("#MainContent_gvUsers > tbody > tr:nth-child(2) > td:nth-child(4) > a:nth-child(1)");
	
	public static By adminCancel = By.cssSelector("#MainContent_gvUsers > tbody > tr:nth-child(2) > td:nth-child(4) > a:nth-child(2)");
	
	public static By adminAccessView = By.id("MainContent_gvUsers_lblRole_0");
	
	/*Read Only Access. */
	
	public static By readOnlyDropdown = By.id("MainContent_gvUsers_ddlRole_1");
	
	public static By readOnlyUpdate = By.cssSelector("#MainContent_gvUsers > tbody > tr.tbody.odd > td:nth-child(4) > a:nth-child(1)");
	
	public static By readOnlyCancel = By.cssSelector("#MainContent_gvUsers > tbody > tr.tbody.odd > td:nth-child(4) > a:nth-child(2)");
	
	public static By readOnlyAccessView = By.id("MainContent_gvUsers_lblRole_1");
	
	/*Read and Write Access. */
	
	public static By readWriteDropdown = By.id("MainContent_gvUsers_ddlRole_2");
	
	public static By readWriteUpdate = By.cssSelector("#MainContent_gvUsers > tbody > tr:nth-child(4) > td:nth-child(4) > a:nth-child(1)");
	
	public static By readWriteCancel = By.cssSelector("#MainContent_gvUsers > tbody > tr:nth-child(4) > td:nth-child(4) > a:nth-child(2)");
	
	public static By readWriteAccessView = By.id("MainContent_gvUsers_lblRole_2");
	
}

