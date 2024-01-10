package com.reusecode.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.reusecode.iodata.Input;

public class Excel{
	
	public Excel(){
		super();
	}
	
	/********************************************************************************
	* STATIC VARIABLES:
	********************************************************************************/
	
	public static XSSFWorkbook wb;
	
	public static XSSFSheet sheet;
	
	public static FileInputStream fsInput;
	
	public static FileOutputStream fs1;
	
	public static Excel excelData;
	
	public static String _excelPath = null;
	
	/********************************************************************************
	 * EXCEL DATA: CONFIGURATION
	 ********************************************************************************/
	
	public Excel(String excelpath)
	{
		try 
		{
			fsInput = new FileInputStream(excelpath);
		} catch (FileNotFoundException e) 
		{
			System.out.println(Input.timeStamp()+" : EXCEL : Excel File Could not be find. { "+ e +" }");
			e.printStackTrace();
			
		}
		try 
		{
			wb = new XSSFWorkbook(fsInput);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}	
	
	/**********************************************************************************************
	 * Method Name : getDataFromExcel()
	 * 
	 * @param sheetnumber
	 *            : sheet index number
	 * @param row
	 *            : row number
	 * @param column
	 *            : column number
	 * @return : Cell data from the excel.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the data from the Excel sheet based on cells.
	 * 
	 **********************************************************************************************/

	public static String getDataFromExcel(int sheetnumber, int row, int column) 
	{
		excelData = new Excel(_excelPath);
		sheet = wb.getSheetAt(sheetnumber);
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(sheet.getRow(row).getCell(column));
	}

	/**********************************************************************************************
	 * Method Name : getRowCount()
	 * 
	 * @param sheetIndex
	 *            : index of sheet
	 * @return integer : Total Number of rows in an excel sheet.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the total row count from the excel sheet.
	 * 
	 **********************************************************************************************/

	public static int getRowCount(int sheetIndex) 
	{
		excelData = new Excel(_excelPath);
		return wb.getSheetAt(sheetIndex).getPhysicalNumberOfRows();
	}

	/**********************************************************************************************
	 * Method Name : getColCount()
	 * 
	 * @param sheetIndex
	 *            : index of sheet.
	 * @return integer : Total Number of columns in an excel sheet.
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the total column count from the excel sheet.
	 * 
	 **********************************************************************************************/

	public static int getColCount(int sheetIndex) 
	{
		excelData = new Excel(_excelPath);
		sheet = wb.getSheetAt(sheetIndex);
		return sheet.getRow(0).getLastCellNum();
	}

	/**********************************************************************************************
	 * Method Name : writeToExcel()
	 * 
	 * @param sheetNum
	 *            : Excel sheet Number
	 * @param row
	 *            : Data Row number
	 * @param col
	 *            : Data Column NUmber
	 * @param excelpath
	 *            : Path for the Driven excel sheet.
	 * @param setVal
	 *            : Excel cell set value.
	 * @return : Boolean if write successful into the excel, else false.
	 * 
	 * Description : 
	 * 
	 * 		This method will write the data to an Excel sheet.
	 * 
	 **********************************************************************************************/

	public static Boolean writeToExcel(int sheetNum, int row, int col, String excelpath, String setVal) 
	{
		Input.m_Task_Result = Input.m_Task_Failed;
		excelData = new Excel(_excelPath);
		FileOutputStream file_Out = null;
		sheet = wb.getSheetAt(sheetNum);
		sheet.getRow(row).createCell(col).setCellValue(setVal);
		try 
		{
			if(excelpath != null)
			{
				file_Out = new FileOutputStream(excelpath);
			}
			else
			{
				file_Out = new FileOutputStream(_excelPath);
			}
			
			try 
			{
				wb.write(file_Out);
				Input.m_Task_Result = Input.m_Task_Passed;
			}catch (IOException e) 
			{	
				e.printStackTrace();
				Input.m_Task_Result = Input.m_Task_Failed;
			}
			
		}catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
			Input.m_Task_Result = Input.m_Task_Failed;
		}
		return Input.m_Task_Result;
	}

	/**********************************************************************************************
	 * Method Name : excelData()
	 * 
	 * @param sheetIndex : Index number of Excel sheet
	 * @param rowFlag : Start Row Number 
	 * @param colFlag : Start Column Number
	 * @return : object of array data
	 * @throws Exception
	 *
	 * Description : 
	 * 
	 * 		This Method will read the data from Excel from the Start row to end row with respect of Cells column.
	 * 
	 **********************************************************************************************/
	
	public static Object[][] excelData(int sheetIndex, int rowFlag, int colFlag) throws Exception 
	{
		excelData = new Excel(_excelPath);
		int rowCount = getRowCount(sheetIndex);
		int colCount = getColCount(sheetIndex);
		Object[][] data = new Object[rowCount - rowFlag][colCount-colFlag];
		for (int row = rowFlag; row < rowCount; row++)
		{
			for (int col = colFlag; col < colCount; col++) 
			{
				data[row - rowFlag][col-colFlag] = getDataFromExcel(sheetIndex, row, col);				
			}			
		}
		return data;
	}
	
	/**********************************************************************************************/
}
