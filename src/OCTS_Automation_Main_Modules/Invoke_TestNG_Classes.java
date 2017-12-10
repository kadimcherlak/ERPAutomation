/*===============================================================================================================================
        CLASS Name:    Invoke_TestNG_Classes
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Main Driver script                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_Automation_Main_Modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


@SuppressWarnings("unused")
public class Invoke_TestNG_Classes {
	static Map<Integer, String> col1Data = new LinkedHashMap<Integer, String>();
	static Map<Integer, String> col2Data = new LinkedHashMap<Integer, String>();
	static Map<String, String> finalKeyValuePair = new LinkedHashMap<String, String>();
	static List<String> testCaseList = new ArrayList<String>();
	//static String excelPath = "C:\\Automation_OCTS\\Data\\TestCaseExecution.xlsx";
	@SuppressWarnings({ "deprecation", "null" })
	public Map<String,String> readExcel(String excelPath) throws IOException {
		// Write a code to read test cases from excel and pass the arraylist to
		// testcase.add()
		Integer columnNo = null;
		String columnWanted;
		FileInputStream fileInputStream = new FileInputStream(new File(
				excelPath));
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Row firstRow = sheet.getRow(0);

		for (Cell cell : firstRow) {
			columnWanted = "Test Case Name";
			if (cell.getStringCellValue().equals(columnWanted)) {
				columnNo = cell.getColumnIndex();

			}
		}
		if (columnNo != null) {
			for (Row row : sheet) {
				Cell c = row.getCell(columnNo);
				if (c != null || c.getCellType() == Cell.CELL_TYPE_BLANK) {

					col1Data.put(row.getRowNum(), c.toString());
				}
			}
		}
		for (int rowNumber = 0; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
			XSSFRow row = sheet.getRow(rowNumber);
			for (int columnNumber = 0; columnNumber <= row.getLastCellNum(); columnNumber++) {
				XSSFCell cell = row.getCell(columnNumber);
				if (cell != null) {

					col2Data.put(row.getRowNum(), cell.toString());
				}
			}

		}

		for (Entry<Integer, String> col1map : col1Data.entrySet())
			for (Entry<Integer, String> col2map : col2Data.entrySet()) {
				if (col1map.getKey() != 0 && col2map.getKey() != 0
						&& col1map.getKey() == col2map.getKey()) {
					finalKeyValuePair.put(col1map.getValue(),
							col2map.getValue());
				}
			}
		/*System.out.println("Excel Values mapped :");
		for (Entry<String, String> map : finalKeyValuePair.entrySet()) {
			System.out.println(map);
		}*/
		return finalKeyValuePair;

	}
	
	@Test
	public String iterateThroughTestCases(String testFile, String InterfaceFIle, String testModule,String excelPath) throws IOException {
		 
		finalKeyValuePair= readExcel(excelPath);
		Iterator<Entry<String, String>> iterator = finalKeyValuePair.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> pair = iterator.next();
			if(pair.getValue().equals("Y"))
			{
				testCaseList.add(pair.getKey());
			}
		    
		}
		if(testCaseList.size()==0)
		{
			return "Mark atleast one test case with Flag as Y";
		}
		    // Creating a new Suite
		    XmlSuite suite = new XmlSuite();

		    for (Integer i = 0; i < testCaseList.size(); i++) {

		      // Creating a new Test
		      XmlTest test = new XmlTest(suite);

		      // Set Test name
		      test.setName("test-number-" + i);

		      // New list for the parameters
		      Map<String, String> testParams = new HashMap<String, String>();

		      // Add parameter to the list
		      testParams.put("testFile", testFile);
		      testParams.put("InterfaceFile", InterfaceFIle);
		      // Add parameters to test
		      test.setParameters(testParams);
		    
		      // New list for the classes
		      List<XmlClass> classes = new ArrayList<XmlClass>();

		      // Putting the classes to the list
		   //   classes.add(new XmlClass("OCTS_Finance_Automation_TestCases."+testCaseList.get(i)));
		      classes.add(new XmlClass(testModule+testCaseList.get(i)));
		      // Add classes to test
		      test.setClasses(classes);
		      

		    }

		    // New list for the Suites
		    List<XmlSuite> suites = new ArrayList<XmlSuite>();

		    // Add suite to the list
		    suites.add(suite);

		    // Creating the xml
		    TestListenerAdapter tla = new TestListenerAdapter();
		    TestNG tng = new TestNG();
		    tng.setXmlSuites(suites);
		    System.out.println(suite.toXml());
		 
		  //  tng.addListener(tla);
		    tng.run();
			return "Completed";
		
		/*TestListenerAdapter tla = new TestListenerAdapter();
		   TestNG testng = new TestNG();
		   testng.setTestClasses(new Class[] { ERP_FrontEnd.class });
		   testng.run();
		  */

		 
	}

}