package com.reusecode.utilities;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import com.reusecode.iodata.Input;

public class FileHandler{

	/**********************************************************************************************
	 * Method Name : checkIfFolderExists()
	 * 
	 * @param FPath
	 * @return boolean, returns true if the directory is exists, false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This method will check whether folder is existed or not.
	 *  
	 **********************************************************************************************/
	
	public static Boolean checkIfFolderExists(String directory) 
	{		
		Input.m_Task_Result = Input.m_Task_Failed;
		File file = new File(directory);
		if (!file.exists()) 
		{
		    System.out.println(Input.timeStamp() +" : Folder [ " + directory + " ] Not exists");
		    Input.m_Task_Result = Input.m_Task_Failed;
		}
		else
		{
			System.out.println(Input.timeStamp() + " : Folder [ " + directory + " ] Already exists");
			Input.m_Task_Result = Input.m_Task_Passed;
		}
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : checkIfFolderExists_IfNotCreate() 
	 * 
	 * @param FilePath
	 * @return string, return directory string
	 * 
	 * Description :
	 * 
	 * 		This method is used to check whether the directory is exists or not, if exists it will return the directory,
	 * 		otherwise it will create the directory and return the directory string.
	 * 
	 **********************************************************************************************/
	
	public static String checkIfFolderExists_IfNotCreate(String FilePath) 
	{		  
		File file = new File(FilePath);
		if (!file.exists()) 
		{
			System.out.println(Input.timeStamp() + " : Folder Not exists, going to create { " + FilePath + " } folder.");
			Path path = Paths.get(FilePath);
			try 
			{
				Files.createDirectories(path);
				System.out.println(Input.timeStamp() + " : [ " + FilePath + " ] Folder created sucessfully.");
			} catch (IOException e) 
			{
				System.err.println(Input.timeStamp() + " : Cannot create directories - " + e);
			}      
		}
		else
		{
			System.out.println(Input.timeStamp() + " : Folder [ " + FilePath + " ] Already exists.");
		}
		return FilePath;
	}
	
	/**********************************************************************************************
	 * Method Name : isDirectoryEmpty()
	 * 
	 * @param dirPath
	 * @return boolean : return true if the directory is empty, false otherwise
	 * 
	 * Description : 
	 * 
	 * 		This method is used to find whether the directory is empty or not.
	 * 
	 **********************************************************************************************/
	
	public static Boolean isDirectoryEmpty(String dirPath)
	{		
		Input.m_Task_Result = Input.m_Task_Failed;
		File directory = new File(dirPath);
		if (directory.isDirectory()) 
		{
		   String[] files = directory.list();
		   if (files.length > 0) 
		   {
			   System.out.println(Input.timeStamp() + " : The directory [ " + directory.getPath() + " ] "
			   		+ "is not empty, it Contains files. size of the folder is : " + directory.length() );
			   
			   for(int i = 0; i < files.length; i++)
			   {
				   System.out.println(Input.timeStamp()+" : " + files[i]);
			   }
			   Input.m_Task_Result = Input.m_Task_Failed;
		   } 
		   else 
		   {
			   System.out.println(Input.timeStamp()+" : The directory [ " + directory.getPath() + " ] is empty.");
			   Input.m_Task_Result = Input.m_Task_Passed;
		   }
		}
		return Input.m_Task_Result;
	}
	
	/**********************************************************************************************
	 * Method Name : copyFilesToDirectory()
	 * 
	 * @param SourcePath
	 * @param DestPath
	 * @return boolean: returns true if file copy is success, false otherwise.
	 * 
	 * Description : 
	 * 
	 * 		This method will perform the file copy operation from source directory to destination directory.
	 * 
	 **********************************************************************************************/
	
	public static Boolean copyFilesToDirectory(String SourcePath, String DestPath)
	{		
		Input.m_Task_Result = Input.m_Task_Failed;
		System.out.println(Input.timeStamp() + " : Copying files to [ " + DestPath + " ] directory is processing...");
		File srcDir = new File(SourcePath);
		File destDir = new File(DestPath);
		try 
		{
			FileUtils.copyDirectory(srcDir, destDir);
		    System.out.println(Input.timeStamp() + " : Copying files to [ " + destDir + " ] is sucessfully completed...");
		    Input.m_Task_Result = Input.m_Task_Passed;
		} catch (IOException e) 
		{
			Input.m_Task_Result = Input.m_Task_Failed;
			e.printStackTrace();
		}
		return Input.m_Task_Result;
	}
	/**********************************************************************************************
	 * Method Name : getNewestFile()
	 * 
	 * @param filePath
	 *  @param file_Name
	 * @param ext
	 * @return File object
	 * 
	 * Description : 
	 * 
	 * 		This method is used to get the newest file from the provided directory.
	 * 
	 **********************************************************************************************/
	
	public static File getNewestFile(String filePath,String file_Name, String ext) 
	{		
	    File theNewestFile = null;
	    File dir = new File(filePath);
	    FileFilter fileFilter = new WildcardFileFilter(file_Name + ext);
	    File[] files = dir.listFiles(fileFilter);
	    if (files.length > 0) 
	    {
	        /** The newest file comes first **/
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];
	    }
	    return theNewestFile;
	}
	
	/**********************************************************************************************/
}
