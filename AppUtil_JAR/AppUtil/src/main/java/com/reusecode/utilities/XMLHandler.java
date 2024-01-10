package com.reusecode.utilities;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.reusecode.iodata.Input;

public class XMLHandler {
	
	public static String m_xmlMainTag = null;
	
	/********************************************************************************************************************
	 * Method Name : setXMLMainTagName()
	 * 
	 * @param xmlMainTag_Name
	 * @return void
	 * 
	 * Description :
	 * 
	 * 		This method is used to set xml main tag name.
	 *
	 ********************************************************************************************************************/
	
	public static void setXMLMainTagName(String xmlMainTag_Name)
	{	
		m_xmlMainTag = xmlMainTag_Name;
	}
	
	/********************************************************************************************************************
	 * Method Name : getXMLMainTagName()
	 * 
	 * @param xmlMainTag_Name
	 * @return string
	 * 
	 * Description :
	 * 
	 * 		This method is used to get xml main tag name.
	 *
	 ********************************************************************************************************************/
	
	public static String getXMLMainTagName()
	{	
		return m_xmlMainTag;
	}
	
	/********************************************************************************************************************
	 * Method Name : updateXmlTagValue()
	 * 
	 * @param standardXMLPath
	 * @param TagName
	 * @param XMLHeaderTagName
	 * @param valueToUpdate
	 * @return boolean : returns true if task success, false otherwise.
	 * 
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * 
	 * 
	 * Description : 
	 * 
	 * 		This method is used to update the xml tag values based on the tag name, and returns the boolean flag.
	 * 
	 ********************************************************************************************************************/
	
    public static Boolean updateXmlTagValue(String standardXMLPath, String XMLHeaderTagName, String TagName, String valueToUpdate) throws  IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
    {    	
    	Input.m_Task_Result = Input.m_Task_Failed;
        File xmlFile = new File(standardXMLPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        System.out.println(Input.timeStamp() + " : XML_FILE : " + standardXMLPath);
        try 
        {
            dBuilder = dbFactory.newDocumentBuilder();
            // parse xml file and load into document
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            // update Element value
            updateElementValue(doc, XMLHeaderTagName, TagName, valueToUpdate);
            writeXMLFile(doc, standardXMLPath);
            Input.m_Task_Result = Input.m_Task_Passed;
            
        } catch (SAXException  ex) 
        {
            ex.printStackTrace();
            Input.m_Task_Result = Input.m_Task_Failed;
        }
		return Input.m_Task_Result;
    }
    
    /********************************************************************************************************************
     * Method Name : writeXMLFile()
     * 
     * @param doc
     * @param standardXMLPath
     * @return void 
     * 
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * 
     * Description : 
     * 
     * 		This method is used to write the xml tag values in the xml.
     * 
     ********************************************************************************************************************/
    
    private static void  writeXMLFile(Document doc,String standardXMLPath) throws TransformerFactoryConfigurationError, 
    															TransformerConfigurationException, TransformerException 
    {    	
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(standardXMLPath));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println(Input.timeStamp()+" : XML file updated successfully");	
    }

    /********************************************************************************************************************
     * Method Name : updateElementValue()
     * 
     * @param doc
     * @param xmlHeaderTagName
     * @param tagToUpadte
     * @param updateTagVal
     * @return void
     * 
     * description : 
     * 
     * 		This method is used to update the tag values of xml doc.
     * 
     ********************************************************************************************************************/
    
    private static void updateElementValue(Document doc, String xmlHeaderTagName, String tagToUpadte, String updateTagVal) 
    {
        NodeList users = doc.getElementsByTagName(xmlHeaderTagName);
        Element user = null;
        // loop for each user
        for (int i = 0; i < users.getLength(); i++) 
        {
            user = (Element) users.item(i);
            Node name = user.getElementsByTagName(tagToUpadte).item(0).getFirstChild();
            System.out.println(Input.timeStamp() + " : TagToUpadte : " + tagToUpadte + "\tUpdatedTagVal : " + updateTagVal);
            name.setNodeValue(updateTagVal);
        }
    }
    
    /********************************************************************************************************************/
}