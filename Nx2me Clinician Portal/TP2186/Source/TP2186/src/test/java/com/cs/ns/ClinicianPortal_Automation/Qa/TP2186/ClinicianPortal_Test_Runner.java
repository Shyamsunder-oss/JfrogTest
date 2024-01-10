package com.cs.ns.ClinicianPortal_Automation.Qa.TP2186;

import java.util.List;
import org.testng.TestNG;
import com.cs.ns.ClinicianPortal_Automation.Base_class.BaseClass;
import com.google.inject.internal.util.Lists;

public class ClinicianPortal_Test_Runner extends BaseClass{

	public static void main(String[] args)
	{
		TestNG testng = new TestNG();
	    List<String> suites = Lists.newArrayList();
	    suites.add("./Configuration/testng.xml");
	    testng.setTestSuites(suites);
	    testng.run();
	}
}
