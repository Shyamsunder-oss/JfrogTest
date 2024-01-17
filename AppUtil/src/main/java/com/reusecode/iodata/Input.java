package com.reusecode.iodata;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.reusecode.extentreports.ExtentReportsGenerator;
import com.reusecode.utilities.Excel;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class Input{

	public static WebDriver m_driver;
	
	public static WebDriverWait m_wait;
	
	public static int cIMPLICITLY_WAIT = 300;
	
	public static int cPAGELOAD_TIMEOUT = 180;
	
	public static int cNUMBER_OF_WINDOWS = 2;
	
	public static int cDRIVER_WAIT = 5;
	
	public static String m_browser_Version = null;;
	
	public static String driverVersion = null;	
	
	public static String m_browserName = null;
	
	public static String m_curDir = System.getProperty("user.dir");
	
	public static String m_mainWindow = null;
	
	public static String m_childWindow = null;
	
	public static boolean m_Task_Result = false;
	
	public static boolean m_Task_Failed = false;
	
	public static boolean m_Task_Passed = true;
	
	public static final long DEFAULT_TIMEOUT_SECONDS = 30;
    
	public static long m_timeOut = 5;
		
	public static int cPAGELOADTIME = 30;
	
	public static ArrayList<String> _patientList;		
		
	/**********************************************************************************************
	 * Method Name : setExcelPath()
	 * 
	 * @param excel_path
	 * @return True if setting is success, otherwise False.
	 * @throws IOException
	 * 
	 * Description : 
	 * 
	 * 		This method is used to set the excel path, used to store the input driven data.
	 *  
	 **********************************************************************************************/
	
	public static boolean setExcelPath(String excel_path)
	{
		m_Task_Result = m_Task_Failed;
		if(excel_path != null)
		{
			Excel._excelPath = excel_path;	
			m_Task_Result = m_Task_Passed;
		}
		return m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : setBrowser()
	 * 
	 * @param browsername
	 * @return True if browser setting is success, otherwise False.
	 * 
	 * Description : 
	 * 		
	 * 		This method is used to set the browser name ( chrome, IE, firefox, etc...).
	 * 
	 **********************************************************************************************/
	public static Boolean setBrowser(String browsername)
	{		
		m_Task_Result = m_Task_Failed;
		if(browsername != null)
		{
			m_browserName =  browsername;	
			m_Task_Result = m_Task_Passed;
		}
		return m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : urlNavigate()
	 * 
	 * @param baseURL
	 *            : URL to navigate location.
	 * @return : boolean, true if url navigation is success otherwise false.
	 * 
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 		
	 * 		This Method will perform the URL navigation.
	 * 
	 *********************************************************************************************/
	
	public static Boolean urlNavigate(String baseURL) throws InterruptedException, IllegalArgumentException 
	{		
		m_Task_Result = m_Task_Failed;				
		if(m_browserName.equalsIgnoreCase("Chrome") || m_browserName.equalsIgnoreCase("Google Chrome"))
		{			
			
			System.out.println(timeStamp()+" : Chrome Browser Selected +++++++++++++++++++");
			WebDriverManager.chromedriver().setup();	
			m_driver = new ChromeDriver();
			Capabilities caps = (((RemoteWebDriver) m_driver).getCapabilities());
			m_browserName = caps.getBrowserName();
			driverVersion = caps.getVersion();
			m_browser_Version = caps.getBrowserVersion();
			System.out.println(m_browserName + " " + m_browser_Version);
			m_Task_Result = m_Task_Passed;
		}
		else if(m_browserName.equalsIgnoreCase("Fire Fox") || m_browserName.equalsIgnoreCase("Mozilla Fire Fox"))
		{
			System.out.println(timeStamp()+" : Mozilla Fire Fox Browser Selected");
			WebDriverManager.firefoxdriver().setup();	
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability(FirefoxDriver.MARIONETTE, true);
			options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "normal");
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);	
			m_driver = new FirefoxDriver(options);
			m_Task_Result = m_Task_Passed;			
		}
		else if(m_browserName.equalsIgnoreCase("Internet Explorer") || m_browserName.equalsIgnoreCase("IE"))
		{			
			WebDriverManager.iedriver().setup();			
			InternetExplorerOptions capabilities = new InternetExplorerOptions();	

			capabilities.setCapability("nativeEvents", true);    
			capabilities.setCapability("unexpectedAlertBehaviour", "accept");
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("disable-popup-blocking", false);
			capabilities.setCapability("enablePersistentHover", true);
	        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
	        capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
	        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
	        capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);	        	    	        
	        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "normal");
	        
	        m_driver = new InternetExplorerDriver(capabilities); 	    	       
			System.out.println(m_browserName + " " + m_browser_Version);
			m_Task_Result = m_Task_Passed;
		}
		else if(m_browserName.equalsIgnoreCase("Microsoft Edge") || m_browserName.equalsIgnoreCase("Edge"))
		{			
			System.out.println(timeStamp()+" : Microsoft Edge Browser Selected");
			WebDriverManager.edgedriver().setup();	
			m_driver = new EdgeDriver();
			m_Task_Result = m_Task_Passed;
		}
		else
		{
			m_Task_Result = m_Task_Failed;
		}
		
		if(m_Task_Result == m_Task_Passed)
		{
			System.out.println(timeStamp()+" : Driver is Launching now..");			
			m_wait = new WebDriverWait(m_driver, cIMPLICITLY_WAIT);			
			m_mainWindow = m_driver.getWindowHandle();
			m_driver.manage().window().maximize();
			m_driver.get(baseURL);	
			
			m_driver.manage().timeouts().pageLoadTimeout(cPAGELOAD_TIMEOUT, TimeUnit.SECONDS);
			m_driver.manage().timeouts().implicitlyWait(cPAGELOAD_TIMEOUT, TimeUnit.SECONDS);
			while (!waitForPageToLoad(cPAGELOADTIME));
		}
		else
		{
			System.out.println(timeStamp()+" : Web Driver Selection is Failed..");
		}
		return m_Task_Result;
	}	
	/**********************************************************************************************
	 * Method Name : getDriver()
	 * 
	 * @return WebDriver
	 * 
	 * Description : 
	 * 		
	 * 		This method will return the driver instance. 
	 * 
	 **********************************************************************************************/
	
	public static WebDriver getDriver()
	{		
		return m_driver;
	}
	
	/**********************************************************************************************
	 * Method Name : clickOn()  and clickOn_js()
	 * 
	 * @param label
	 *            : Object Locator
	 * @param typeOf_click
	 *            : clicking type( single or double).
	 * @return : nothing
	 * 
	 * @throws InterruptedException
	 *  
	 * Description : 
	 * 
	 * 		This Method will perform the mouse click operations
	 *      like, single click or double click on the object labels
	 *
	 **********************************************************************************************/
	
	public static void clickOn(By click_Locator, String typeOf_click) throws InterruptedException, IllegalArgumentException 
	{
		if ((typeOf_click != null) && (click_Locator != null)) 
		{			
			waitUntilElementClickable(click_Locator);
			if (typeOf_click == "click") 
			{				
				try 
				{
					Input.getDriver().findElement(click_Locator).click();
				} catch (Exception e) 
				{
				     JavascriptExecutor executor = (JavascriptExecutor) Input.getDriver();
				     executor.executeScript("arguments[0].click();", Input.getDriver().findElement(click_Locator));				 
				}
			}
			else if (typeOf_click == "doubleclick") 
			{				
				Actions action = new Actions(m_driver);
				WebElement btn = m_driver.findElement(click_Locator);
				action.doubleClick(btn).perform();				
			}
			else if(typeOf_click == "rightclick") 
			{
				//Right click 
				Actions action = new Actions(m_driver);
				WebElement eleClick = m_driver.findElement(click_Locator);
				action.contextClick(eleClick).perform();    
			}			
			else {/* do nothing */}
		}
	}
	
	public static void clickOn_js(By click_Locator)
	{		
		mouseOver(click_Locator);		
		WebElement element = m_driver.findElement(click_Locator);
		JavascriptExecutor executor = (JavascriptExecutor)m_driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**********************************************************************************************
	 * Method Name : dropDown()
	 * 
	 * @param dropdown_Locator
	 *            : Object Locator
	 * @param selectiveOption
	 *            : option to select(String).
	 * @return : void
	 * 
	 * @throws InterruptedException 
	 *	Description : 
	 *
	 *		This Method will perform the drop down selection operations.
	 * 
	 **********************************************************************************************/
	
	public static void dropDown(By dropdown_Locator, String selectiveOption) throws InterruptedException 
	{
		if ((selectiveOption != null) && (dropdown_Locator != null)) 
		{
			waitUntilElementClickable(dropdown_Locator);
			Select dropDown = new Select(m_driver.findElement(dropdown_Locator));
			dropDown.selectByVisibleText(selectiveOption);
		}
	}

	/**********************************************************************************************
	 * Method Name : textBoxEntry() and textBoxEntry_js()
	 * 
	 * @param label
	 *            : Object Label
	 * @param messageTopass
	 *            : Text message
	 * @return : nothing
	 * 
	 *         Description : This Method will send the Data messages to the Text
	 *         box fields.
	 * 
	 **********************************************************************************************/
	
	public static void textBoxEntry(By textbox_label, String messageTopass) 
	{
		if ((messageTopass != null) && (textbox_label != null)) 
		{
			m_driver.findElement(textbox_label).sendKeys(messageTopass);
		}
	}
	
	public static void textBoxEntry_js(By textbox_label, String messageTopass) 
	{
		JavascriptExecutor jsExecutor = (JavascriptExecutor) m_driver;  
		jsExecutor.executeScript("document.getElementById('" + textbox_label + "').value='" + messageTopass+"'");   
	}

	/**********************************************************************************************
	 * Method Name : mouseOver()
	 * 
	 * @param mouseover_Locator
	 *            : Object Label
	 * @return : void 
	 * Description : This Method will perform the mouse over action on labels.
	 *
	 **********************************************************************************************/

	public static void mouseOver(By HoverElement) throws StaleElementReferenceException, NoSuchElementException 
	{
		try
		{
			Actions mouseover = new Actions(m_driver);
			WebElement element = m_driver.findElement(HoverElement);
			mouseover.moveToElement(element).perform();	
		}
		catch(Exception e)
		{
			WebElement element = m_driver.findElement(HoverElement);
			JavascriptExecutor jse = (JavascriptExecutor) m_driver;
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) m_driver).executeScript(mouseOverScript,element);			
		} 		
	}
	
	/**********************************************************************************************
	 * Method Name : getLabelText()
	 * 
	 * @param label
	 *            : Object Locator.
	 * @return : string , if success it return the Object Label text otherwise null.
	 * 
	 * Description : 
	 * 
	 * 		This Method is used to get the text of a web element and returns the web element label text.
	 * 
	 **********************************************************************************************/
	
	public static String getLabelText(By element) 
	{
		String cellData = null;
		if (element != null) 
		{		
			cellData = m_driver.findElement(element).getText();
		}
		return cellData;
	}

	/**********************************************************************************************
	 * Method Name : timeStamp()
	 * 
	 * @return : timestamp
	 * 
	 * Description : 
	 * 	
	 * 		This method is used to get the current timestamp.
	 * 
	 **********************************************************************************************/
	
	public static String timeStamp()
	{		
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}
	
	/**********************************************************************************************
	 * Method Name : getTime()
	 * 
	 * @return : Current Time
	 * 
	 * Description : 
	 * 	
	 * 		This method is used to get the current time.
	 * 
	 **********************************************************************************************/
	public static String getTime()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
	    Date date = new Date();  
	    return formatter.format(date); 
	}
	
	/**********************************************************************************************
	 * Method Name : getTime()
	 * 
	 * @return : Current Date and Time
	 * 
	 * Description : 
	 * 	
	 * 		This method is used to get the current date and time.
	 * 
	 **********************************************************************************************/
	public static String getDateTime()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa");  
	    Date date = new Date();  
	    return formatter.format(date); 
	}
	
	
	/**********************************************************************************************
	 * Method Name : clearTextBoxLabel
	 * 
	 * @param clear_label : web locator for clearing label.
	 * @return : void 
	 * 
	 * Description : 
	 * 
	 * 		This method will clear the Text box labels.
	 * 
	 **********************************************************************************************/
	
	public static void clearTextBoxLabel(By clear_label)
	{
		m_Task_Result = m_Task_Passed;
        while(m_Task_Result)
        {
            m_driver.findElement(clear_label).clear();
            if(!getLabelText(clear_label).contentEquals(""))
            {
            	m_Task_Result = m_Task_Passed;               
            }
            else
            {
                m_Task_Result = m_Task_Failed;
            }
        }
	}
	
	public static void clearTextBoxLabel_js(By clear_label)
	{		
		JavascriptExecutor jse = (JavascriptExecutor) m_driver; 
		jse.executeScript("document.getElementById('"+ clear_label +"').value='';");
	}
	
	
	/**********************************************************************************************
	 * Method Name : datePicker_Entry();
	 * 
	 * @param dateLocation : Type of date either start or end date.
	 * @param Date : Date to change
	 * @return void
	 * 
	 * Description : 
	 * 
	 * 		This method is used to enter the date picker entries.
	 * 
	 **********************************************************************************************/

	public static void datePicker_Entry(String dateLocation, String Date)
	{		
		if(dateLocation != null && Date != null && m_driver != null)
		{			
			JavascriptExecutor jse = ((JavascriptExecutor)m_driver);
			jse.executeScript("document.getElementById('" + dateLocation + "').value='" + Date + "'");
		}
	}
	
	/**********************************************************************************************
	 * Method Name :  waitVisibility()
	 * 
	 * @param webElement
	 * @return void
	 * 
	 * Description : 
	 * 
	 * 		This method will wait until the provided web element is visible.
	 * 
	 **********************************************************************************************/
	
	public static void waitVisibility(By webElement)
	{
        m_wait.until(ExpectedConditions.visibilityOfElementLocated(webElement));
    }
	
	/*******************************************************************************************************
	 * Method Name : switchChild_To_MainWindow()
	 * 
	 * @param expURL
	 * @throws InterruptedException
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to switch from child window to main window.
	 * 
	 *******************************************************************************************************/
	
	public static void switchChild_To_MainWindow(String expURL) throws InterruptedException
	{		
		m_wait.until(ExpectedConditions.numberOfWindowsToBe(cNUMBER_OF_WINDOWS));
		Set<String> allWindows = m_driver.getWindowHandles();
		Iterator<String> ite = allWindows.iterator();
		
		while(ite.hasNext())
		{
		    m_childWindow=ite.next().toString();
		    if(!m_childWindow.contains(m_mainWindow))
		    {
		        m_driver.switchTo().window(m_childWindow);
		        if(!waitForPageToLoad(cPAGELOAD_TIMEOUT)) 
		        {
					System.out.println(timeStamp() + " : PAGE_LOAD : Page Loading Failed.");
				}
		        m_wait.until(ExpectedConditions.titleContains(expURL));
		        TimeUnit.SECONDS.sleep(1);
		        
		        if(m_driver.getTitle() != null)
		        {
		        	 ExtentReportsGenerator.test.log(Status.INFO, MarkupHelper.createLabel("Sucessfully Navigated to child window : "+ m_driver.getTitle(),ExtentColor.PURPLE));
		        }
		        
		        ExtentReportsGenerator.test.log(Status.INFO, MarkupHelper.createLabel("Closed child window and Sucessfully Navigated to Main window : "+m_driver.getTitle(),ExtentColor.PURPLE));
		        m_driver.close();
		        TimeUnit.SECONDS.sleep(1);
		        m_driver.switchTo().window(m_mainWindow);
		    }
		}
	}
	
	/*******************************************************************************************************
	 * Method Name : switchToMainWindow()
	 * 
	 * @throws InterruptedException 
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method will perform switching operation from child window to main window.
	 *  
	 *******************************************************************************************************/
	
	public static void switchToMainWindow() throws InterruptedException
	{
		m_driver.switchTo().window(m_mainWindow);	
		if(!waitForPageToLoad(cPAGELOAD_TIMEOUT))
		{
			System.out.println(timeStamp() + " : PAGE_LOAD : Page Loading Failed.");
		}
	}
	
	/*******************************************************************************************************
	 * Method Name : switchToChildWindow()
	 * 
	 * @throws InterruptedException 
	 * @return void 
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method will perform switching operation from main window to child window.
	 * 
	 *******************************************************************************************************/
	
	public static void switchToChildWindow() throws InterruptedException
	{	
		 for (String winHandle : m_driver.getWindowHandles()) 
		 {
			 m_driver.switchTo().window(winHandle);
			 if(!waitForPageToLoad(cPAGELOAD_TIMEOUT))
			 {
				 System.out.println(timeStamp() + " : PAGE_LOAD : Page Loading Failed.");
			 }
		 }
	}
	
	/*******************************************************************************************************
	 * Method Name : waitUntilElementClickable()
	 * 
	 * @param ClickableElement
	 * @return void 
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method will wait until the provided web element is clickable.
	 * 
	 *******************************************************************************************************/
	
	public static void waitUntilElementClickable(By ClickableElement) throws InterruptedException
	{
		m_wait.until(ExpectedConditions.elementToBeClickable(ClickableElement));
	}

	/*******************************************************************************************************
	 * Method Name : waitForChildWindow()
	 * 
	 * @param expURL
	 * @return
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method will wait until the child window to invoke.
	 * 
	 *******************************************************************************************************/
	
	public static Boolean waitForChildWindow(String expURL) throws InterruptedException
	{
		m_wait.until(ExpectedConditions.numberOfWindowsToBe(cNUMBER_OF_WINDOWS));
		return (Boolean) m_wait.until(ExpectedConditions.titleContains(expURL));
	}
	
	/*******************************************************************************************************
	 * Method Name : getAttributeValue()
	 * 
	 * @param AttribLabel
	 * @param attrib
	 * @return string, if success return attribute value otherwise null; 
	 * 
	 * Description: 
	 *  	
	 *  	This method is used to get the attribute value of a web element.
	 * 
	 *******************************************************************************************************/
	
	public static String getAttributeValue(By AttribLabel, String attrib)
	{	
		return m_driver.findElement(AttribLabel).getAttribute(attrib);
	}
	
	/*******************************************************************************************************
	 * 
	 * Method Name : getCSSValue()
	 * 
	 * @param css_label
	 * @param css_attrib
	 * @return string, if success return CSS attribute value otherwise null; 
	 * 
	 * Description: 
	 *  	
	 *  	This method is used to get the CSS attribute value of a web element.
	 * 
	 *******************************************************************************************************/
	public static String getCSSValue(By css_label,String css_attrib)
	{
		return m_driver.findElement(css_label).getCssValue(css_attrib);	
	}
	
	/*******************************************************************************************************
	 * Method Name : scrollDown()
	 * 
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to scroll down the web page.
	 * 
	 *******************************************************************************************************/
	
	public static void scrollDown()
	{
		JavascriptExecutor js = (JavascriptExecutor) m_driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	/*******************************************************************************************************
     * Method Name : scrollUp()
     *
     * @return void
     *
     * Description :
     *
     *         This method is used to scroll up the web page.
     *
     *******************************************************************************************************/
   
    public static void scrollUp()
    {
        JavascriptExecutor js = (JavascriptExecutor) m_driver;
        js.executeScript("window.scrollTo(0, 0)");
    }
	
	/*******************************************************************************************************
	 * Method Name : rightClickOnLabel_OpenWithNewTab()
	 * 
	 * @param clickLocator
	 * @return void 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to right click on the web element to invoke with new tab.
	 * 
	 *******************************************************************************************************/
	
	public static void rightClickOnLabel_OpenWithNewTab(By clickLocator)
	{    	
    	WebElement oWE=m_driver.findElement(clickLocator);
        Actions oAction=new Actions(Input.getDriver());
        oAction.moveToElement(oWE);
        String keyPress = Keys.CONTROL.toString() + Keys.SHIFT.toString() + Keys.ENTER.toString();
        oWE.sendKeys(keyPress);
        m_driver.manage().timeouts().implicitlyWait(cIMPLICITLY_WAIT, TimeUnit.SECONDS);
		m_driver.manage().timeouts().pageLoadTimeout(cPAGELOAD_TIMEOUT, TimeUnit.SECONDS);        
    }
	
	/*******************************************************************************************************
	 * Method Name : isClickable()
	 * 
	 * @param webe
	 * @return boolean, true if element can be clicked and false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to check whether the element can be clicked or not.
	 * 
	 *******************************************************************************************************/
    
	public static boolean isClickable(By webe)      
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(m_driver, cDRIVER_WAIT);
            wait.until(ExpectedConditions.elementToBeClickable(webe));
            return m_Task_Passed;
        }
        catch (Exception e)
        {
            return m_Task_Failed;
        }
    }
	
	/*******************************************************************************************************
	 * Method Name : getTimeout()
	 * 
	 * @return long
	 * 
	 * Description : 
	 * 
	 * 		This method returns the timeout elapsed.
	 * 
	 *******************************************************************************************************/
	
	public static long getTimeout() 
	{
        return m_timeOut;
    }
	
	/*******************************************************************************************************
	 * Method Name : setTimeout()
	 * 
	 * @return long
	 * 
	 * Description : 
	 * 
	 * 		This method sets the timeout period.
	 * 
	 *******************************************************************************************************/
   
	public static void setTimeout(long timeout) 
	{
        m_timeOut = timeout;
    }
	
	/*******************************************************************************************************
	 * Method Name : isElementPresent() 
	 * 
	 * @param element_to_check
	 * @return boolean, returns true if element is present, and otherwise false; 
	 * 
	 * Description: 
	 * 
	 * 		This method is used to check whether the web element is present or not.
	 * 
	 *******************************************************************************************************/
	
    public static boolean isElementPresent(By element_to_check) 
    {    	
        m_Task_Result= m_Task_Passed;
        if (m_driver.findElements(element_to_check).isEmpty()) 
        {
        	m_Task_Result= m_Task_Failed;
        }
        m_driver.manage().timeouts().implicitlyWait(m_timeOut, TimeUnit.SECONDS);
        return m_Task_Result;
    }
    
    /*******************************************************************************************************
     * 
     * @param elementToCheck
     * @return
     *******************************************************************************************************/
    public static boolean checkIfElementIsPresent(By elementToCheck)
    {
    	m_Task_Result = m_Task_Passed;
    	//Set implict wait to 0
    	m_driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    	//Check for element's presence
    	List<WebElement> dynamicElement = m_driver.findElements(elementToCheck);
    	if(dynamicElement.size() != 0)
    	{
    		/*Element is present. */
    	}    	 
    	else
    	{
    		System.out.println("Element not present");
    		m_Task_Result = m_Task_Failed;
    	}    	 
    	//Revert back to default value of implicit wait
    	m_driver.manage().timeouts().implicitlyWait(cIMPLICITLY_WAIT, TimeUnit.SECONDS);
    	return m_Task_Result;
    }
    
	/*******************************************************************************************************
	 * Method Name : substract_DaysFromTodaysDate()
	 * 
	 * @param days
	 * @return string, subtracted date string 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to subtract the days from today's date and returns subtracted past date.
	 * 
	 *******************************************************************************************************/
	
	public static String substract_DaysFromTodaysDate(int days)
	{		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime then = now.minusDays(days);
	    String InputDate = now.format(format);
	    String outDate = then.format(format);
	    System.out.println("Now The Date is : "+InputDate+"\nThen : " + outDate);
		return outDate;
	}
	
	/*******************************************************************************************************
	 * Method Name : dateDifference()
	 * 
	 * @param StartDate
	 * @param EndDate
	 * @return long
	 * 
	 * Description: 
	 * 
	 * 		This method is used to find the date difference between two dates(start and end date) and 
	 * 		return the count of the days that are different. 
	 * 
	 *******************************************************************************************************/
	
	public static long dateDifference(String StartDate, String EndDate)
	{		
		String dateStart = StartDate + " 12:00:00";
		String dateEnd = EndDate + " 12:00:00";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		long diffDays = 0;
		Date d1 = null;
		Date d2 = null;
		try 
		{
			d1 = format.parse(dateStart);
			d2 = format.parse(dateEnd);
			long diff = d2.getTime() - d1.getTime();
			diffDays = diff / (24 * 60 * 60 * 1000);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return diffDays;
	}

	/*******************************************************************************************************
	 * Method Name : waitForPageToLoad()
	 * 
	 * @param timeOutInSeconds 
	 * @return boolean, return true if page load success and false otherwise. 
	 * @throws InterruptedException 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to wait until the page is to be loaded.
	 * 
	 *******************************************************************************************************/
    
    public static Boolean waitForPageToLoad(int timeOutInSeconds) throws InterruptedException 
    {    	
    	m_Task_Result=m_Task_Failed;
        JavascriptExecutor js = (JavascriptExecutor) m_driver;
        String jsCommand = "return document.readyState";
        // Validate readyState before doing any waits
        if (js.executeScript(jsCommand).toString().equals("complete")) 
        {
            m_Task_Result=m_Task_Passed;
        }       
        for (int i = 0; i < timeOutInSeconds; i++) 
        {
            TimeUnit.SECONDS.sleep(3);
            if (js.executeScript(jsCommand).toString().equals("complete")) 
            {
                m_Task_Result=m_Task_Passed;
                break;
            }
        }               
        return m_Task_Result;
    }
    
    /**********************************************************************************************
     * Method Name : driverInstanceKillProcess()
     *
     * @return 
     * @throws IOException
     * 
     * Description : 
     * 	
     * 		This method is used to kill the driver instances that is been placed in task manager.
     * 	
     **********************************************************************************************/
    public static void driverInstanceKillProcess() throws IOException
    {
    	Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
    	Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe /T");
    	Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe /T");  
    	Runtime.getRuntime().exec("taskkill /F /IM msedgedriver.exe /T");
    }
    
    /**********************************************************************************************
     * Method Name : deleteAllCookies()
     * 
     * @return void
     * 
     * Description :
     *  
     *  	This method is used to delete all the cookies from the browser.
     * 
     **********************************************************************************************/
    public static void deleteAllCookies()
    {
    	m_driver.manage().deleteAllCookies();
    }
    
    /**********************************************************************************************
     * Method Name : enterKey_Press()
     * 
     * @param Locator_To_Enter
     * 
     * Description :
     * 
     * 		This method is used to Hot Key "ENTER" press on the web element.
     * 
     **********************************************************************************************/
    public static void enterKey_Press(By Locator_To_Enter)
    {
		m_driver.findElement(Locator_To_Enter).sendKeys(Keys.ENTER);
    }
    
    /**********************************************************************************************
     * Method Name : switchToWindow()
     * 
     * @param windowHandle
     * 
     * Description :
     * 
     * 		This method is used to switch the window based on users choice.
     * 
     **********************************************************************************************/
    public static void switchToWindow(String windowHandle)
    {
    	m_driver.switchTo().window(windowHandle);
    }
    
    /**********************************************************************************************
     * 
     * @param alert_message
     * 
     **********************************************************************************************/
    
    public static void alertMessage(String alert_message)
    {
    	JFrame jf = new JFrame();  
    	jf.setSize(400, 400);  
		JOptionPane.showMessageDialog(jf,alert_message, "Alert",JOptionPane.INFORMATION_MESSAGE); 
    }
    
    /*******************************************************************************************************/
}
