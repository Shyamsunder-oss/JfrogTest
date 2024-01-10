package com.cs.ns.ClinicianPortal_Automation.PageObjects;

import org.openqa.selenium.By;

import com.cs.ns.ClinicianPortal_Automation.Base_class.BaseClass;
//import com.cs.ns.ClinicianPortal_Automation.Utils.ExcelData_Conf;

public class LoginPage extends BaseClass {
	//public class LoginPage extends ExcelData_Conf {
	
	/********************************************************************************
	 * Error Label Texts
	 ********************************************************************************/
	
	public String _centerId_ErrText = "Center ID is required.";
	
	public String _username_ErrText = "Username is required.";
	
	public String _password_ErrText = "Password is required.";
	
	public String _failureNotification_ErrText =  "Login attempt failed. Please verify your Center ID, Username and Password login credentials.";
	
	/********************************************************************************
	*Web Elements and Locators of LOGIN_PAGE. 
	*********************************************************************************/

	public static By username_Label = By.id("MainContent_UserID");
	
	public static By password_Label = By.id("MainContent_Password");
	
	public static By center_ID_Label = By.id("MainContent_CenterId");
	
	public static By[] arrloginCredentials_WE = { center_ID_Label, username_Label, password_Label }; 
	
	public static String[] arrLoginCredData = { g_centerId, g_userId, g_userPWD };
	
	public static By signInButton_Label = By.id("MainContent_LoginButton");
	
	public static By portal_Logout_Label = By.id("btnLogout");
	
	public static By logo_Label = By.className("header");
	
	public static By loginFailure_Err = By.cssSelector("#yesJS > p");
	
	public static By centerID_ERR = By.cssSelector("#MainContent_LoginUserValidationSummary > ul > li:nth-child(1)");
	
	public static By username_ERR = By.cssSelector("#MainContent_LoginUserValidationSummary > ul > li:nth-child(2)");
	
	public static By password_ERR = By.cssSelector("#MainContent_LoginUserValidationSummary > ul > li:nth-child(3)");
	
	public static By forgotPassword_Label = By.id("MainContent_PasswordResetLink");
	
	public static By loginInstructions_Label = By.cssSelector("#MainContent_divMain > p");
	
	public static By loginText_Label = By.cssSelector("#MainContent_divMain > h1");
	
	public static By loginPageContext_Label = By.id("MainContent_divMaintenanceMode");
	
	/*********************************************************************************/
}
