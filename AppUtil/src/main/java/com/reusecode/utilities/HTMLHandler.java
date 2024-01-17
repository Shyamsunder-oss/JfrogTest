package com.reusecode.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.reusecode.extentreports.ExtentReportsGenerator;
import com.reusecode.iodata.Input;
import info.debatty.java.stringsimilarity.Levenshtein;

public class HTMLHandler{

	/*****************************************************************************************************************************
	 * 
	 * Class Name: Constants
	 *
	 *Description : 
	 *
	 *This class is for declaring constants of data types: integer, double and string
	 *These constants are used across all the methods
	 *
	 *****************************************************************************************************************************/
	
	public static class Constants {
		
		//The index of the web element in standard (library HTML) file
	    public static final int STD_COLUMN = 0;
	    
	  //The index of the web element in current HTML file
	    public static final int CUR_COLUMN = 1;
	    
	    public static final int COLUMN_TERMINATOR = 2;
	    
	    //Above this Levenshtein threshold, then comparison between 2 web elements are different
	    public static final double LEVENSHTEIN_THRESHOLD = 9.0;
	    
	    //String to display that a web element is not present (missing) in either Standard (library) HTML file (or) current HTML file
	    public static final String NOT_PRESENT = "<span class='label white-text red'>" + "Not present" + "</span>";
	    
	    //Count of the maximum differences allowed in the HTML failure report table displayed in the extent reports
	    public static final int MAX_DIFF_ALLOWED = 20;
	    
	    //Names of different browsers supported
	    public static final String CHROME = "Chrome";
	    
	    public static final String EDGE = "Edge";
	    
	    public static final String FIRE_FOX = "Fire Fox";
	    
	    public static final String INTERNET_EXPLORER = "IE";
	}
	
	/*****************************************************************************************************************************
	 * Method Name : verifyHTML()
	 * 
	 * @param PageName
	 * @return boolean (true / false)
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * 
	 * Description : 
	 * 
	 * 		This method compares the standard HTML with the current HTML of a web page which is passed as the input argument "PageName"
	 * 		It reads the standard HTML file from the path: ./Configuration/standard_HTML_Pages/t reads the current HTML file from 
	 * 		the path: ./Configuration/urrent_HTML_Pages/It uses JSOUP methods to read and parse the standard and current HTML files
	 * 		Web elements under the following Tags: span, input, img, select,a and li are retrieved from the standard HTML
	 * 		Similarly the web elements from the current HTML and stored in the current XML file in the same folder.
	 * 
	 * 		This method will return true if html file comparison is success, and false otherwise.
	 * 
	 *****************************************************************************************************************************/
		
	public static boolean verifyHTML(String PageName, String BrowserSetting, String TPNumber) throws IOException, InterruptedException, SAXException, TransformerException, ParserConfigurationException 
	{
		//Local variables
		int intCount;
		String strTemp = null;
		boolean blnResult = false;
		String strXml_Std = null, strXml_Cur = null;
		File flStd_Html, flCur_Html;	
		BufferedWriter bwStd_Pw = null, bwCur_Pw = null;
		String strStd_FileName, strCur_FileName, strStd_Folder_Name, strCur_Folder_Name;			
		
		//Frame the folder name based on the browser setting
		strStd_Folder_Name = stdFolderBasedOnBrowser(BrowserSetting, TPNumber);
		strCur_Folder_Name = curFolderBasedOnBrowser(BrowserSetting, TPNumber);
		
		//Standard XML file
		strStd_FileName = (Input.m_curDir + strStd_Folder_Name + PageName + ".xml");
		//Current XML file
		strCur_FileName = (Input.m_curDir + strCur_Folder_Name + PageName + ".xml");
		
		bwStd_Pw = new BufferedWriter(new FileWriter(strStd_FileName));
		bwCur_Pw = new BufferedWriter(new FileWriter(strCur_FileName));
		
		//Standard HTML file
		flStd_Html = new File(Input.m_curDir + strStd_Folder_Name + PageName + ".html");
		Document dcStd_Doc = Jsoup.parse(flStd_Html, null);
		
		//Current HTML file
		flCur_Html = new File(Input.m_curDir + strCur_Folder_Name + PageName + ".html");
		Document dcCur_Doc = Jsoup.parse(flCur_Html, null);
		
		//Web elements from the below tags are retrieved from the HTML file, to be stored in XML file
	    Elements eleStd_Divs_span_Tag = dcStd_Doc.getElementsByTag("span");
	    Elements eleCur_Divs_span_Tag = dcCur_Doc.getElementsByTag("span");
	    Elements eleStd_Divs_input_Tag = dcStd_Doc.getElementsByTag("input");
	    Elements eleCur_Divs_input_Tag = dcCur_Doc.getElementsByTag("input");
	    Elements eleStd_Divs_img_Tag = dcStd_Doc.getElementsByTag("img");
	    Elements eleCur_Divs_img_Tag = dcCur_Doc.getElementsByTag("img");
	    Elements eleStd_Divs_select_Tag = dcStd_Doc.getElementsByTag("select");
	    Elements eleCur_Divs_select_Tag = dcCur_Doc.getElementsByTag("select");
	    Elements eleStd_Divs_a_Tag = dcStd_Doc.getElementsByTag("a");
	    Elements eleCur_Divs_a_Tag = dcCur_Doc.getElementsByTag("a");
	    Elements eleStd_Divs_li_Tag = dcStd_Doc.getElementsByTag("li");
	    Elements eleCur_Divs_li_Tag = dcCur_Doc.getElementsByTag("li");
	    Elements eleStd_Divs_td_Tag = dcStd_Doc.getElementsByTag("td");
	    Elements eleCur_Divs_td_Tag = dcCur_Doc.getElementsByTag("td");
	    
	    //Write the XML header in the current XML file
	    bwCur_Pw.write("<?xml version='1.0' encoding='utf-8'?>");
		bwCur_Pw.write("\n");
		bwCur_Pw.write("<" + PageName + ">");
		bwCur_Pw.write("\n");
		//Writing the web elements that are under <span Tag into current XML file  		
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_span_Tag)
		{
			strXml_Cur = eleCur_Divs_span_Tag.get(intCount).toString();			
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Cur = excludeExtendedAscii(strXml_Cur);
			
			//End of extended ASCII - ignore
			//Add a space between the Web Element text and the Web Element ID before storing this web element into current XML file
			bwCur_Pw.write(strXml_Cur.replace(">", "> "));    			
			bwCur_Pw.write("\n");    			
			intCount++;
		}
		//Writing the web elements that are under <input Tag into current XML file
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_input_Tag)
		{
			strXml_Cur = eleCur_Divs_input_Tag.get(intCount).toString();
			//Do not write into XML file, if the text "hidden" is present
			if (!strXml_Cur.contains("hidden"))
			{
				//If the string contains "autocomplete=off" keyword then remove that keyword
				strXml_Cur = strXml_Cur.replace("autocomplete=\"off\" ", "");
				
				//Start of Extended ASCII characters ignore
    			//We are ignoring the below extended ASCII - Latin characters         
				strXml_Cur = excludeExtendedAscii(strXml_Cur);
				
    			//End of extended ASCII - ignore
    			//Store this web element in the current XML file
				bwCur_Pw.write(strXml_Cur);    				
				bwCur_Pw.write("\n");
			}  
			intCount++;
		}
		//Writing the web elements that are under <img Tag into current XML file
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_img_Tag)
		{
			strXml_Cur = eleCur_Divs_img_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters      
			strXml_Cur = excludeExtendedAscii(strXml_Cur);
			
			//End of extended ASCII - ignore
			//Store this web element in the current XML file
			bwCur_Pw.write(strXml_Cur);    			
			bwCur_Pw.write("\n");
			intCount++;
		}
		//Terminate the image tag in the current XML file
		bwCur_Pw.write("</img>");    		
		bwCur_Pw.write("\n");    		
		//Writing the web elements that are under <img Tag into current XML file
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_select_Tag)
		{
			strXml_Cur = eleCur_Divs_select_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Cur = excludeExtendedAscii(strXml_Cur);
			
			//End of extended ASCII - ignore
			//Store this web element in the current XML file    			 			   		
			bwCur_Pw.write(strXml_Cur);    			
			bwCur_Pw.write("\n");
			intCount++;
		}
		//Writing the web elements that are under <td Tag into current XML file
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_td_Tag)
		{
			strXml_Cur = eleCur_Divs_td_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Cur = excludeExtendedAscii(strXml_Cur);
			
			//End of extended ASCII - ignore
			//Store this web element in the current XML file    			 			   		
			bwCur_Pw.write(strXml_Cur);    			
			bwCur_Pw.write("\n");
			intCount++;			
		}
		
		//Writing the web elements that are under <a Tag into current XML file
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_a_Tag)
		{
			strXml_Cur = eleCur_Divs_a_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Cur = excludeExtendedAscii(strXml_Cur);
			
			//End of extended ASCII - ignore
			//Store this web element in the current XML file
			//Add a space between the Web Element text and the Web Element ID before storing this web element into current XML file
			bwCur_Pw.write(strXml_Cur.replace(">", "> "));    			
			bwCur_Pw.write("\n");
			intCount++;
		}
		//Writing the web elements that are under <li Tag into current XML file
		intCount = 0;
		for (Element eleTemp : eleCur_Divs_li_Tag)
		{
			strXml_Cur = eleCur_Divs_li_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Cur = excludeExtendedAscii(strXml_Cur);
			
			//End of extended ASCII - ignore
			//Store this web element in the current XML file
			//Remove the new line character and store the details of a web element in a single line
			strXml_Cur = strXml_Cur.replaceAll("(\\r|\\n)", "");    			
			bwCur_Pw.write(strXml_Cur);    			
			bwCur_Pw.write("\n");
			intCount++;
		}
		//Writing the web elements that are under <style Tag into current XML file
		Element eleStyle_Dot_Cur = dcCur_Doc.select("style").first();
        Matcher cssMatcher_Dot_Cur = Pattern.compile("[.](\\w+)\\s*[{]([^}]+)[}]").matcher(eleStyle_Dot_Cur.html());            
        while (cssMatcher_Dot_Cur.find()) {            	
        	strTemp = cssMatcher_Dot_Cur.group(2).replaceAll("(\\r|\\n)", "");            	
        	bwCur_Pw.write("<style " + cssMatcher_Dot_Cur.group(1).toString().trim() + ": " + strTemp+" </style>");            	
        	bwCur_Pw.write("\n");            	
        }    		
        Element eleStyle_Hash_Cur = dcStd_Doc.select("style").first();
        Matcher cssMatcher_Hash_Cur = Pattern.compile("[#](\\w+)\\s*[{]([^}]+)[}]").matcher(eleStyle_Hash_Cur.html());            
        while (cssMatcher_Hash_Cur.find()) {            	
        	strTemp = cssMatcher_Hash_Cur.group(2).replaceAll("(\\r|\\n)", "");            	
        	bwCur_Pw.write("<style " + cssMatcher_Hash_Cur.group(1).toString().trim() + ": " + strTemp+" </style>");            	
        	bwCur_Pw.write("\n");
        }
		//Ending the writing into current XML file
		bwCur_Pw.write("</" + PageName + ">");
		bwCur_Pw.flush();
		bwCur_Pw.close();
		//Write the XML header in the standard XML file
		bwStd_Pw.write("<?xml version='1.0' encoding='utf-8'?>");
	    bwStd_Pw.write("\n");
	    bwStd_Pw.write("<" + PageName + ">");
	    bwStd_Pw.write("\n");
	    //Writing the web elements that are under <span Tag into standard XML file    
	    intCount = 0;
		for (Element eleTemp : eleStd_Divs_span_Tag)
		{
			strXml_Std = eleStd_Divs_span_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Std = excludeExtendedAscii(strXml_Std);
			
			//End of extended ASCII - ignore
			//Add a space between the Web Element text and the Web Element ID and store this web element into standard XML file
			bwStd_Pw.write(strXml_Std.replace(">","> "));    			
			bwStd_Pw.write("\n");
			intCount++;
		}
		//Writing the web elements that are under <input Tag into standard XML file   
		intCount = 0;
		for (Element eleTemp : eleStd_Divs_input_Tag)
		{
			strXml_Std = eleStd_Divs_input_Tag.get(intCount).toString();
			//Do not write into XML file, if the text "hidden" is present
			if (!strXml_Std.contains("hidden"))
			{
				//If the string contains "autocomplete=off" keyword then remove that keyword
				strXml_Std = strXml_Std.replace("autocomplete=\"off\" ", "");
				
				//Start of Extended ASCII characters ignore
    			//We are ignoring the below extended ASCII - Latin characters 
				strXml_Std = excludeExtendedAscii(strXml_Std);
				
    			//End of extended ASCII - ignore
    			//Store this web element in the standard XML file
				bwStd_Pw.write(strXml_Std);    				
				bwStd_Pw.write("\n");
			}    			
			intCount++;
		}
		//Writing the web elements that are under <image Tag into standard XML file
		intCount = 0;
		for (Element eleTemp : eleStd_Divs_img_Tag)
		{
			strXml_Std = eleStd_Divs_img_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Std = excludeExtendedAscii(strXml_Std);
			
			//End of extended ASCII - ignore
			//Store this web element in the standard XML file
			bwStd_Pw.write(strXml_Std);    			
			bwStd_Pw.write("\n");
			intCount++;
		}
		//Terminate the image tag in the current XML file
		bwStd_Pw.write("</img>");    		
		bwStd_Pw.write("\n");
		//Writing the web elements that are under <select Tag into standard XML file
		intCount = 0;
		for (Element eleTemp : eleStd_Divs_select_Tag)
		{    			
			strXml_Std = eleStd_Divs_select_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Std = excludeExtendedAscii(strXml_Std);
			
			//End of extended ASCII - ignore
			//Store this web element in the standard XML file
			bwStd_Pw.write(strXml_Std);    			
			bwStd_Pw.write("\n");
			intCount++;
		}		
		
		//Writing the web elements that are under <td Tag into standard XML file
		intCount = 0;
		for (Element eleTemp : eleStd_Divs_td_Tag)
		{    			
			strXml_Std = eleStd_Divs_td_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Std = excludeExtendedAscii(strXml_Std);
			
			//End of extended ASCII - ignore
			//Store this web element in the standard XML file
			bwStd_Pw.write(strXml_Std);    			
			bwStd_Pw.write("\n");
			intCount++;			
		}	
				
		//Writing the web elements that are under <a Tag into standard XML file
		intCount = 0;
		for (Element eleTemp : eleStd_Divs_a_Tag)
		{
			strXml_Std = eleStd_Divs_a_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Std = excludeExtendedAscii(strXml_Std);
			
			//End of extended ASCII - ignore
			//Store this web element in the standard XML file
			//Add a space between the Web Element text and the Web Element ID and store this web element into Standard XML file
			bwStd_Pw.write(strXml_Std.replace(">", "> "));    			
			bwStd_Pw.write("\n");
			intCount++;
		}
		//Writing the web elements that are under <li Tag into standard XML file
		intCount = 0;
		for (Element eleTemp : eleStd_Divs_li_Tag)
		{
			strXml_Std = eleStd_Divs_li_Tag.get(intCount).toString();
			//Start of Extended ASCII characters ignore
			//We are ignoring the below extended ASCII - Latin characters 
			strXml_Std = excludeExtendedAscii(strXml_Std);			
			
			//End of extended ASCII - ignore
			//Store this web element in the standard XML file
			//Remove the newline and store this web element into Standard XML file
			strXml_Std = strXml_Std.replaceAll("(\\r|\\n)", "");    			
			bwStd_Pw.write(strXml_Std);    			
			bwStd_Pw.write("\n");
			intCount++;
		}
		//Writing the web elements that are under <style Tag into standard XML file
		Element eleStyle_Dot_Std = dcStd_Doc.select("style").first();
		Matcher cssMatcher_Dot_Std = Pattern.compile("[.](\\w+)\\s*[{]([^}]+)[}]").matcher(eleStyle_Dot_Std.html());            
        while (cssMatcher_Dot_Std.find()) {            	
        	strTemp = cssMatcher_Dot_Std.group(2).replaceAll("(\\r|\\n)", "");
        	bwStd_Pw.write("<style " + cssMatcher_Dot_Std.group(1).toString().trim() + ": " + strTemp + " </style>");
        	bwStd_Pw.write("\n");            	
        }    		
        Element style_Hash_Std = dcStd_Doc.select("style").first();
        Matcher cssMatcher_Hash_Std = Pattern.compile("[#](\\w+)\\s*[{]([^}]+)[}]").matcher(style_Hash_Std.html());
        while (cssMatcher_Hash_Std.find()) {            	
        	strTemp = cssMatcher_Hash_Std.group(2).replaceAll("(\\r|\\n)", "");
        	bwStd_Pw.write("<style " + cssMatcher_Hash_Std.group(1).toString().trim() + ": " + strTemp + " </style>");
        	bwStd_Pw.write("\n");            	
        }
        //Ending the writing into current XML file
		bwStd_Pw.write("</" + PageName + ">");    		
		bwStd_Pw.flush();
		bwStd_Pw.close();
		
		String strStandard_Xml_File = Input.m_curDir + strStd_Folder_Name + PageName + ".xml";
		String strCurrent_Xml_File = Input.m_curDir + strCur_Folder_Name + PageName + ".xml";
		
		blnResult = xmlComparison(strStandard_Xml_File, strCurrent_Xml_File,PageName);
		
        return blnResult;  				 
	}
	/*****************************************************************************************************************************
	 * Method Name : xmlComparison()
	 * 
	 * @param std_file_name
	 * @param cur_file_name
	 * @param web_page_name
	 * @return
	 * @throws IOException
	 * 
	 * Description : 
	 * 
	 * 		This method compares the standard xml with current xml using levenshtein-distance algorithm and the differences are highlighted, 
	 * 		and displayed in the extent reports as table format.
	 * 
	 * 		This method will return true if xml file comparison is success, and false otherwise.
	 * 
	 *****************************************************************************************************************************/
	public static boolean xmlComparison(String std_File_Name, String cur_File_Name, String web_Page_Name) throws IOException 
	{
		//Local variables
		boolean blnResult = true;
		int intMax_Rows, STANDARD = 0, CURRENT = 1;
		String strContent_Xml;
		BufferedReader brStdXml = null;
		BufferedReader brCurXml = null;
		String strCurrentLine;
		
		List<String> list_Std = new ArrayList<String>();
		List<String> list_Cur = new ArrayList<String>();
		
		List<String> listStd_Warn = new ArrayList<String>();
		List<String> listStd_Err = new ArrayList<String>();
		
		List<String> listCur_Warn = new ArrayList<String>();
		List<String> listCur_Err = new ArrayList<String>();
		
		//Going to read the web elements in standard XML and the current XML    			
		brStdXml = new BufferedReader(new FileReader(std_File_Name));
		brCurXml = new BufferedReader(new FileReader(cur_File_Name));
		
		//Reading the web elements from the Standard XML
		while ((strCurrentLine = brStdXml.readLine()) != null) 
		{
			list_Std.add(strCurrentLine);
		}
		
		//Reading the web elements from the current XML
		while ((strCurrentLine = brCurXml.readLine()) != null) 
		{
			list_Cur.add(strCurrentLine);
		}
		
		//Selecting the web elements that are present in standard XML but not present in current XML
		List<String> listTemp = new ArrayList<String>(list_Std);
		listTemp.removeAll(list_Cur);		
		//Walking through individual web elements in standard XML to identify if it is attention data or warning data
		if (listTemp.size() > 0)
		{    				
			for(int i = 0; i < listTemp.size(); i++){
				strContent_Xml = listTemp.get(i);
				//Checking if the web element contains the character '@' - is it email ID?
				if (strContent_Xml.contains("@"))
				{
					listStd_Warn.add(strContent_Xml.replace('<', ' '));	//It is email ID, then store in warning list					
				}
				else	//Store the web elements in Standard Error List
				{
					blnResult = Input.m_Task_Failed;
					listStd_Err.add(strContent_Xml.replace('<', ' '));        						
				}
			}    				       			
		}
		//Selecting the web elements that are present in current XML but not present in standard XML
		listTemp = list_Cur;
		listTemp.removeAll(list_Std);
		//Walking through individual web elements in current XML to identify if it is attention data or warning data
		if (listTemp.size() > 0)
		{
			for(int i = 0; i < listTemp.size(); i++){
				strContent_Xml = listTemp.get(i);
				//Checking if the web element contains the character '@' - is it email ID?
				if (strContent_Xml.contains("@"))
				{
					listCur_Warn.add(strContent_Xml.replace('<', ' '));    //It is email ID, then store in warning list						
				}
				else	//Store the web elements in Standard Error List
				{
					blnResult = Input.m_Task_Failed;    							
					listCur_Err.add(strContent_Xml.replace('<', ' '));					 						
				}
			}
		}
		// Going to display attention data
		if((listStd_Err.size() > 0) || (listCur_Err.size() > 0))
		{
			if((listStd_Err.size() < Constants.MAX_DIFF_ALLOWED) && (listCur_Err.size() < Constants.MAX_DIFF_ALLOWED))
			{
				intMax_Rows = listStd_Err.size() + listCur_Err.size();	//To find the maximum number of rows    					   			
    			
    			String[][] strAttention_Data = new String[intMax_Rows][Constants.COLUMN_TERMINATOR];
    			String[][] strAttention_Final_Data = new String[intMax_Rows][Constants.COLUMN_TERMINATOR];
    			String[][] strAttention_Highlight_Data = new String[intMax_Rows][Constants.COLUMN_TERMINATOR];
    			
    			//Calling Levenshtein algorithm to match the web elements present in standard column with the current column            			
    			strAttention_Final_Data = levenshtein_algorithm(strAttention_Data, intMax_Rows, listStd_Err, listCur_Err);              			
    			//Highlighting the changed / missing text in green and red color      			
    			strAttention_Highlight_Data = highlightChanged(strAttention_Final_Data, intMax_Rows);
    			
    			//truncate null entries in 2 dimensional array here            			
    			List<String> listCurrent = new ArrayList<String>();
                List<String> listStandard = new ArrayList<String>();                           
               
                for(int i = 0; i < strAttention_Highlight_Data.length; i++){           
                    for(int j = 0; j < Constants.COLUMN_TERMINATOR; j++){               
                        if(j == STANDARD){   
                        	if (strAttention_Highlight_Data[i][j] != null)
                        		listStandard.add(strAttention_Highlight_Data[i][j].toString());               
                        }
                        else if(j == CURRENT){
                        	if (strAttention_Highlight_Data[i][j] != null)
                        		listCurrent.add(strAttention_Highlight_Data[i][j].toString());       
                        }
                    }
                }     
                //Transferring the contents from list to 2 dimensional array here
                String[][] strAttention_Data_Final = new String[listStandard.size()][Constants.COLUMN_TERMINATOR];
                for(int a = 0; a < listStandard.size(); a++){
                    for(int b = 0; b < Constants.COLUMN_TERMINATOR; b++){      
                        if(b == STANDARD){
                            strAttention_Data_Final[a][b] = listStandard.get(a);
                        }
                        if(b == CURRENT){
                            strAttention_Data_Final[a][b] = listCurrent.get(a);
                        }                      
                    }
                }                          			
    			//Going to display attention data in the tabular format in content HTML report            			
    			//*****************************************************            			
				ExtentReportsGenerator.test.log(Status.ERROR, MarkupHelper.createLabel("Attention...! Difference noticed..!", ExtentColor.PINK));
				ExtentReportsGenerator.test.log(Status.INFO, MarkupHelper.createLabel(". . . . . . . . . . . . . . STANDARD HTML . . . . . . . . . . . . . . . . . . . . . . . ."
						+ " . . . . . . . . . . . . . . . . . . . . . . . . . . . . . CURRENT HTML  . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . ", ExtentColor.PURPLE));
				ExtentReportsGenerator.test.log(Status.INFO, MarkupHelper.createTable(strAttention_Data_Final));
			}    	
			else  // Too many differences between standard HTML and current HTML observed
			{
				ExtentReportsGenerator.test.log(Status.ERROR, MarkupHelper.createLabel("Attention...! Too many differences noticed between standard HTML and current HTML..!", ExtentColor.PINK));
			}
		}    						
		// Going to display warning data		
		if((listStd_Warn.size() > 0) || (listCur_Warn.size() > 0))
		{
			if((listStd_Warn.size() < Constants.MAX_DIFF_ALLOWED) && (listCur_Warn.size() < Constants.MAX_DIFF_ALLOWED))
			{
				if (listStd_Warn.size() >= listCur_Warn.size())
    			{
    				intMax_Rows = listStd_Warn.size();
    			}
    			else
    			{
    				intMax_Rows = listCur_Warn.size();
    			}
				
    			String[][] strWarning_Final_Data = new String[intMax_Rows][Constants.COLUMN_TERMINATOR];
    			String[][] strWarning_Highlight_Data = new String[intMax_Rows][Constants.COLUMN_TERMINATOR];           			
    			
    			//moving the warning data from List to 2 dimensional array            			
    			for(int a = 0; a < intMax_Rows; a++){
                    for(int b = 0; b < Constants.COLUMN_TERMINATOR; b++){      
                        if(b == STANDARD){
                        	strWarning_Final_Data[a][b] = listStd_Warn.get(a);
                        }
                        if(b == CURRENT){
                        	strWarning_Final_Data[a][b] = listCur_Warn.get(a);
                        }                      
                    }
                }
    			
    			//Highlighting the changed / missing text in green and red color
    			strWarning_Highlight_Data = highlightChanged(strWarning_Final_Data, intMax_Rows);
    			
    			//Going to display warning data in the tabular format in content HTML report
    			ExtentReportsGenerator.test.log(Status.WARNING, MarkupHelper.createLabel("Warning...! Difference ignored..!", ExtentColor.ORANGE));
    			ExtentReportsGenerator.test.log(Status.INFO, MarkupHelper.createLabel(". . . . . . . . . . . . . . STANDARD HTML . . . . . . . . . . . . . . . . . . . . . . . ."
                        									+ " . . . . . . . . . . . . . . . . . . . . . . . . . . . . . CURRENT HTML  . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . ", ExtentColor.PURPLE));
    			ExtentReportsGenerator.test.log(Status.INFO, MarkupHelper.createTable(strWarning_Highlight_Data));
				StatusHTML.setStatusOfHTML("Pass with warnings");
			}
			else	// Too many warnings between standard HTML and current HTML observed
			{
				StatusHTML.setStatusOfHTML("Pass with warnings");
				ExtentReportsGenerator.test.log(Status.WARNING, MarkupHelper.createLabel("Warning...! Too many warnings noticed between standard HTML and current HTML..!", ExtentColor.ORANGE));
			}
		}
		
		brStdXml.close();
		brCurXml.close();
		
		return blnResult;
	}  	
	    	
	/*****************************************************************************************************************************
	 * Method Name : levenshtein_algorithm()
	 * 
	 * @param input_Data
	 * @param max_rows
	 * @param std_err
	 * @param cur_err
	 * @return String[][]
	 * 
	 * Description :
	 * 
	 * 		This method uses Levenshtein-Distance Algorithm to match the web elements present in standard column with the current column
	 * 		It returns the data in 2 dimensional array where the web elements present in standard column is matching with the current
	 * 
	 * 		This method will return a two dimensional string containing web elements in standard column and current column in sorted
	 * 		order.
	 * 
	 *****************************************************************************************************************************/
	
    public static String[][] levenshtein_algorithm(String[][] input_Data, int max_Rows, List<String> std_Err, List<String> cur_Err)
    {
    	//Local variables
    	String[][] strFinal_Data = new String[max_Rows][Constants.COLUMN_TERMINATOR];
    	int intLast_Which_Row;
    	int intWhich_Row;
    	boolean blnNew_Entry = Input.m_Task_Failed;
    	double dblLevenshtein_Prev_Val = Constants.LEVENSHTEIN_THRESHOLD;
    	double dblLevenshtein_Cur_Val = Constants.LEVENSHTEIN_THRESHOLD;
    	
    	List<String> list_To_Append_1 = new ArrayList<String>();
    	List<String> list_To_Append_2 = new ArrayList<String>();
    	List<String> list_To_Append_3 = new ArrayList<String>();
    	List<String> list_To_Append_4 = new ArrayList<String>();
    	
    	Levenshtein similarity_Val = new Levenshtein();
    	
    	//Web elements present in standard column is more than or equal to the web elements present in current column  	
    	if (std_Err.size() >= cur_Err.size())
		{
    		for (intWhich_Row = 0; intWhich_Row < std_Err.size(); intWhich_Row++)
			{
				input_Data[intWhich_Row][Constants.STD_COLUMN] = std_Err.get(intWhich_Row);
				strFinal_Data[intWhich_Row][Constants.STD_COLUMN] = input_Data[intWhich_Row][Constants.STD_COLUMN];
				strFinal_Data[intWhich_Row][Constants.CUR_COLUMN] = Constants.NOT_PRESENT;
				dblLevenshtein_Prev_Val = Constants.LEVENSHTEIN_THRESHOLD;
				dblLevenshtein_Cur_Val = Constants.LEVENSHTEIN_THRESHOLD;
				for (int intSearch_Row = 0; intSearch_Row < cur_Err.size(); intSearch_Row++)
				{
					input_Data[intSearch_Row][Constants.CUR_COLUMN] = cur_Err.get(intSearch_Row);					
					dblLevenshtein_Cur_Val = similarity_Val.distance(input_Data[intWhich_Row][Constants.STD_COLUMN], input_Data[intSearch_Row][Constants.CUR_COLUMN]);
					//Going to find the matching row
					if (dblLevenshtein_Cur_Val < dblLevenshtein_Prev_Val)
    				{
						//matching row found here
    					strFinal_Data[intWhich_Row][Constants.CUR_COLUMN] = input_Data[intSearch_Row][Constants.CUR_COLUMN];
    					dblLevenshtein_Prev_Val = dblLevenshtein_Cur_Val;
    					//Keep track of the web elements for which match is successfully found in 2 lists for later use
    					list_To_Append_2.add(std_Err.get(intWhich_Row).toString());
    					list_To_Append_1.add(cur_Err.get(intSearch_Row).toString());
    				}					
				}        				
			}
    		//Store the index of the last web element 
			intLast_Which_Row = intWhich_Row;
			//Going to append the web elements if not present 
			for (intWhich_Row = 0; intWhich_Row < cur_Err.size(); intWhich_Row++)
			{
				input_Data[intWhich_Row][Constants.CUR_COLUMN] = cur_Err.get(intWhich_Row);
				blnNew_Entry = Input.m_Task_Failed;
				for (int i = 0; i < list_To_Append_1.size(); i++)
				{
					//Going to check if the web element is the one for which match was found earlier
					if (input_Data[intWhich_Row][Constants.CUR_COLUMN].equals(list_To_Append_1.get(i)))
					{
						blnNew_Entry = Input.m_Task_Passed;;
						break;
					}						
				}
				if (!blnNew_Entry)
				{
					//This web element is not present in standard column, so we need to append to the attention data
					strFinal_Data[intLast_Which_Row][Constants.CUR_COLUMN] = input_Data[intWhich_Row][Constants.CUR_COLUMN];
					strFinal_Data[intLast_Which_Row][Constants.STD_COLUMN] = Constants.NOT_PRESENT;
				}
			}			
		}
		else //Web elements present in current column is more than the web elements present in standard column
		{
			for (intWhich_Row = 0; intWhich_Row < cur_Err.size(); intWhich_Row++)
			{
				input_Data[intWhich_Row][Constants.CUR_COLUMN] = cur_Err.get(intWhich_Row);
				strFinal_Data[intWhich_Row][Constants.CUR_COLUMN] = input_Data[intWhich_Row][Constants.CUR_COLUMN];
				strFinal_Data[intWhich_Row][Constants.STD_COLUMN] = Constants.NOT_PRESENT;
				dblLevenshtein_Prev_Val = Constants.LEVENSHTEIN_THRESHOLD;
				dblLevenshtein_Cur_Val = Constants.LEVENSHTEIN_THRESHOLD;
				for (int intSearch_Row = 0; intSearch_Row < std_Err.size(); intSearch_Row++)
				{
					input_Data[intSearch_Row][Constants.STD_COLUMN] = std_Err.get(intSearch_Row);					
					dblLevenshtein_Cur_Val = similarity_Val.distance(input_Data[intWhich_Row][Constants.CUR_COLUMN], input_Data[intSearch_Row][Constants.STD_COLUMN]);
					//Going to find the matching row
					if (dblLevenshtein_Cur_Val < dblLevenshtein_Prev_Val)
    				{
						//matching row found here
    					strFinal_Data[intWhich_Row][Constants.STD_COLUMN] = input_Data[intSearch_Row][Constants.STD_COLUMN];
    					dblLevenshtein_Prev_Val = dblLevenshtein_Cur_Val;
    					//Keep track of the web elements for which match is successfully found in 2 lists for later use
    					list_To_Append_3.add(cur_Err.get(intWhich_Row).toString());
    					list_To_Append_4.add(std_Err.get(intSearch_Row).toString());
    				}					
				}        				
			}
			//Store the index of the last web element 
			intLast_Which_Row = intWhich_Row;
			//Going to append the web elements if not present			
			for (intWhich_Row = 0; intWhich_Row < std_Err.size(); intWhich_Row++)
			{
				input_Data[intWhich_Row][Constants.STD_COLUMN] = std_Err.get(intWhich_Row);
				blnNew_Entry = Input.m_Task_Failed;
				for (int i = 0; i < list_To_Append_4.size(); i++)
				{		
					//Going to check if the web element is the one for which match was found earlier
					if (input_Data[intWhich_Row][Constants.STD_COLUMN].equals(list_To_Append_4.get(i)))
					{
						blnNew_Entry =Input.m_Task_Passed;
						break;
					}						
				}
				if (!blnNew_Entry)
				{				
					//This web element is not present in standard column, so we need to append to the attention data
					strFinal_Data[intLast_Which_Row][Constants.STD_COLUMN] = input_Data[intWhich_Row][Constants.STD_COLUMN];
					strFinal_Data[intLast_Which_Row][Constants.CUR_COLUMN] = Constants.NOT_PRESENT;
				}
			}
		}     	
    	return strFinal_Data;
    } 	
    
    /****************************************************************************************************************************
     * Method Name : highlightChanged()
     * 
     * @param final_data_non_highlight
     * @param max_rows
     * @return
     * 
     * Description : 
     * 
     * 		This method highlights the changed / missing text in green color in standard column and red color in current column
     * 		It returns the data in 2 dimensional array where the web elements in standard and current column are highlighted
     * 		
     * 		This method will return two dimensional array contains highlighted data of web elements in standard and current column.
     *  
     ****************************************************************************************************************************/
	
    public static String[][] highlightChanged(String[][] final_Data_Non_Highlight, int max_Rows)
    {
    	//Local variables
    	String [][] strData_Non_Highlight = final_Data_Non_Highlight;
    	String [][] strData_Highlight = new String[max_Rows][Constants.COLUMN_TERMINATOR];
    	int intis_Last_Word = 0;    	
    	String strLeft_Highlighted_Data = null;
    	String strRight_Highlighted_Data = null;
    	    	
    	for (int intRow = 0; intRow < max_Rows; intRow++)
    	{
    		if (strData_Non_Highlight[intRow][Constants.STD_COLUMN] != null && strData_Non_Highlight[intRow][Constants.CUR_COLUMN] != null)
    		{
    			strLeft_Highlighted_Data = strData_Non_Highlight[intRow][Constants.STD_COLUMN];
        		strRight_Highlighted_Data = strData_Non_Highlight[intRow][Constants.CUR_COLUMN];
        		if (!strLeft_Highlighted_Data.contentEquals(Constants.NOT_PRESENT) && !strRight_Highlighted_Data.contentEquals(Constants.NOT_PRESENT))
        		{
            		String[] str_Left_Col = strLeft_Highlighted_Data.split("\\s+");
                    String[] str_Right_Col = strRight_Highlighted_Data.split("\\s+");  
                    
                    strLeft_Highlighted_Data = "";
                    strRight_Highlighted_Data = "";
                                        
                    // This is for highlighting the content of left column in green                    
                    if (str_Left_Col.length == str_Right_Col.length)	// number of words in standard is equal to current
                    {                    	
                    	intis_Last_Word = 0;
                    	for(String left_Col : str_Left_Col){                		
                            for(String right_Col : str_Right_Col){
                            	intis_Last_Word = intis_Last_Word + 1;
                            	if(left_Col.equals(right_Col)){
                                    strLeft_Highlighted_Data +=  "<font color=white>" + left_Col + "</font> ";                                    
                                    intis_Last_Word = 0;
                                    break;
                            	}
                                else if (intis_Last_Word == str_Right_Col.length)
                                {
                                	strLeft_Highlighted_Data += "<span class='label white-text green'>"+left_Col+"</span>";                                	
                                }                            	
                            }
                        }
                    }
                    else if (str_Left_Col.length > str_Right_Col.length) // number of words in standard is more than current
                    {                    	
                    	intis_Last_Word = 0;
                    	for(String left_Col : str_Left_Col){
                            for(String right_Col : str_Right_Col){
                            	intis_Last_Word = intis_Last_Word + 1;
                                if(left_Col.equals(right_Col)){
                                    strLeft_Highlighted_Data +=  "<font color=white>" + left_Col + "</font> ";                                    
                                    intis_Last_Word = 0;
                                    break;
                                }
                                else if (intis_Last_Word == str_Right_Col.length)
                                {
                                	strLeft_Highlighted_Data += "<span class='label white-text green'>" + left_Col + "</span>";                                	
                                }
                            }
                        }
                    }
                    else if (str_Right_Col.length > str_Left_Col.length) // number of words in current is more than standard
                    {                    	
                    	intis_Last_Word = 0;
                    	for(String left_Col : str_Left_Col){
                            for(String right_Col : str_Right_Col){
                            	intis_Last_Word = intis_Last_Word + 1;
                                if(left_Col.equals(right_Col)){
                                    strLeft_Highlighted_Data +=  "<font color=white>" + left_Col + "</font> ";                                    
                                    intis_Last_Word = 0;
                                    break;
                                }
                                else if (intis_Last_Word == str_Right_Col.length)
                                {
                                	strLeft_Highlighted_Data += "<span class='label white-text green'>" + left_Col + "</span>";                                	
                                }
                            }
                        }
                    }

                 // This is for highlighting the content of right column in red                                        
                    if (str_Right_Col.length == str_Left_Col.length) // number of words in current is equal to standard
                    {                    	
                    	for(String right_Col : str_Right_Col){
                    		intis_Last_Word = 0;
                            for(String left_Col : str_Left_Col){
                            	intis_Last_Word = intis_Last_Word + 1;
                                if(right_Col.equals(left_Col)){
                                    strRight_Highlighted_Data +=  "<font color=white>" + right_Col + "</font> ";
                                    intis_Last_Word = 0;
                                    break;
                                }
                                else if (intis_Last_Word == str_Left_Col.length)
                                {
                                	strRight_Highlighted_Data += "<span class='label white-text red'>" + right_Col + "</span>";                                	
                                }
                            }
                        }
                    }
                    else if(str_Right_Col.length > str_Left_Col.length)	// number of words in current is more than standard
                    {                    	
                    	intis_Last_Word = 0;
                    	for(String right_Col : str_Right_Col){
                    		intis_Last_Word = 0;
                            for(String left_Col : str_Left_Col){
                            	intis_Last_Word = intis_Last_Word + 1;
                                if(right_Col.equals(left_Col)){
                                    strRight_Highlighted_Data +=  "<font color=white>" + right_Col + "</font> ";
                                    intis_Last_Word = 0;
                                    break;
                                }
                                else if (intis_Last_Word == str_Left_Col.length)
                                {
                                	strRight_Highlighted_Data += "<span class='label white-text red'>" + right_Col + "</span>";                                	
                                }
                            }
                        }
                    }
                    else if (str_Left_Col.length > str_Right_Col.length)	// number of words in standard is more than current
                    {                    	
                    	intis_Last_Word = 0;
                    	for(String right_Col : str_Right_Col){
                    		intis_Last_Word = 0;
                            for(String left_Col : str_Left_Col){
                            	intis_Last_Word = intis_Last_Word + 1;
                                if(right_Col.equals(left_Col)){
                                    strRight_Highlighted_Data +=  "<font color=white>" + right_Col + "</font> ";
                                    intis_Last_Word = 0;
                                    break;
                                }
                                else if (intis_Last_Word == str_Left_Col.length)
                                {
                                	strRight_Highlighted_Data += "<span class='label white-text red'>" + right_Col + "</span>";                                	
                                }
                            }
                        }
                    }
                    //Completed highlighting one row of data
                    strData_Highlight[intRow][Constants.STD_COLUMN] = strLeft_Highlighted_Data;
                    strData_Highlight[intRow][Constants.CUR_COLUMN] = strRight_Highlighted_Data;
        		}
        		else
        		{
        			//Web elements here are "not present" data
        			strData_Highlight[intRow][Constants.STD_COLUMN] = strLeft_Highlighted_Data;
                    strData_Highlight[intRow][Constants.CUR_COLUMN] = strRight_Highlighted_Data;
        		}   
    		}    		 	
    	}        
    	return strData_Highlight;
    }
	    
    /****************************************************************************************************************************
     * Method Name : webPageSourceCollections()
     * 
     * @param fileName
     * @throws InterruptedException
     * 
     * Description : 
     * 
     * 		This method stores the current HTML file of a web page.
     * 
     ****************************************************************************************************************************/
  
    public static void webPageSourceCollections(String fileName) throws InterruptedException{
    	
    	String str = Input.getDriver().getPageSource();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(fileName));
			pw.write(str);
			pw.close();
			System.out.println(Input.timeStamp()+" : HTML Web Page Source writing done for "+"' " + fileName + " '");
		}
		catch (Exception en) {
			en.printStackTrace();
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
			} catch (Exception en) {
				en.printStackTrace();
			}
		}		
	}	
    
    /****************************************************************************************************************************
     * Method Name : excludeExtendedAscii()
     * 
     * @param input_text
     * @return
     * Description: 
     * 		
     * 		The following extended ASCII - Latin characters : "ç", "ñ", "©", "�", "–", "á", "’" are ignored
	 * 		during HTML file comparison of the current HTML file with the standard HTML file.
	 * 		This method will return string containing the text where the extended ASCII characters are removed.		
	 * 
     ****************************************************************************************************************************/
    
    public static String excludeExtendedAscii(String inputText)
	{
		String[] strExcludeXchars = {"ç", "ñ", "©", "�", "–", "á", "’"};
		
		for(int i = 0; i < strExcludeXchars.length; i++)
		{
			if (inputText.contains(strExcludeXchars[i]))
			{
				inputText = inputText.replaceAll(strExcludeXchars[i], "?");
			} 
		}								
		return inputText;
	}
    
    /****************************************************************************************************************************
     * Method Name : stdFolderBasedOnBrowser()
     * 
     * @param BrowserSetting
     * @return folderName
     * 
     * Description:
     * 				This method will return the standard folder name according to the TP Number of the project and also the 
     * browser setting mentioned in the excel sheet. It returns the string containing the standard folder Name along with the path.
     * 
     ****************************************************************************************************************************/
    
    public static String stdFolderBasedOnBrowser(String BrowserSetting, String TPNumber)
   	{
    	String strFolderName = null;
    	
    	if (BrowserSetting.equals(Constants.CHROME))
		{
    		strFolderName = "/configuration/" + TPNumber + "/standard_HTML_Pages_Chrome/";			
		}
		else if (BrowserSetting.equals(Constants.EDGE))
		{
			strFolderName = "/configuration/" + TPNumber + "/standard_HTML_Pages_Edge/";				
		}
		else if (BrowserSetting.equals(Constants.FIRE_FOX))
		{
			strFolderName = "/configuration/" + TPNumber + "/standard_HTML_Pages_FireFox/";	
		}
		else if (BrowserSetting.equals(Constants.INTERNET_EXPLORER))
		{
			strFolderName = "/configuration/" + TPNumber + "/standard_HTML_Pages_IE/";			
		}
    	return strFolderName;
   	}
    
    /****************************************************************************************************************************
     * Method Name : curFolderBasedOnBrowser()
     * 
     * @param BrowserSetting
     * @return folderName
     * 
     * Description:
     * 				This method will return the current folder name according to the according to the TP Number of the project and also
     *  the browser setting mentioned in the excel sheet. It returns the string containing the current folder Name along with the path.
     *  
     ****************************************************************************************************************************/
    public static String curFolderBasedOnBrowser(String BrowserSetting, String TPNumber)
   	{
    	String strFolderName = null;
    	
    	if (BrowserSetting.equals(Constants.CHROME))
		{
    		strFolderName = "/configuration/" + TPNumber + "/current_HTML_Pages_Chrome/";				
		}
		else if (BrowserSetting.equals(Constants.EDGE))
		{
			strFolderName = "/configuration/" + TPNumber + "/current_HTML_Pages_Edge/";	
		}
		else if (BrowserSetting.equals(Constants.FIRE_FOX))
		{
			strFolderName = "/configuration/" + TPNumber + "/current_HTML_Pages_FireFox/";
		}
		else if (BrowserSetting.equals(Constants.INTERNET_EXPLORER))
		{
			strFolderName = "/configuration/" + TPNumber + "/current_HTML_Pages_IE/";			
		}
    	return strFolderName;
   	}
    /****************************************************************************************************************************/
}
