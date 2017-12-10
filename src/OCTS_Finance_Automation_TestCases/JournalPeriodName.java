/*===============================================================================================================================
        CLASS Name:    JournalPeriodName
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Journal Entry Period Name Validation                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/
package OCTS_Finance_Automation_TestCases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import OCTS_Automation_Main_Modules.ReadERPFinance_InputDataSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Common_Utility.Logger;
import Common_Utility.ReporterBaseTest;

public class JournalPeriodName extends ReporterBaseTest{
	
	/*static String zipFilePath = "C:\\Automation_OCTS\\Output\\70594.zip";  
    static String destDir = "C:\\Automation_OCTS\\Output\\";*/
  //  static String fileObj = ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
	static String periodName = "";
	static String[] splitNewLine = {};
	static String finalOutputString = "";
	static String getPeriodNameValue = "";
	static int flag =0;
	static String ColumnWanted = "SEJRRequestImport_PL2_JournalPeriodName";
	static ArrayList<Cell> outputValue = new ArrayList<Cell>();
	//static String excelPath = "C:\\Automation_OCTS\\Data\\ERP_Finance_InputDatasheet.xlsx";
	static String excelPath;
	static String fileObj;
	public static String getPeriodName(String periodName) {
		try {
			String line = null;
			String newLine = "";
			Pattern pattern;
			Matcher matcher;
			@SuppressWarnings("resource")
			BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileObj), "UTF-8"));

			line = line + bufRdr.readLine();

			while ((line = bufRdr.readLine()) != null) {
				if (line.contains(periodName)) {
				//if (line.contains("Feb-14")) {
					//System.out.println("Before trimming spaces\n" + line);
					newLine = line.trim();
					//System.out.println("After trimming spaces\n" + newLine);
					splitNewLine = newLine.split("\\s");
					for (String matchString : splitNewLine) {
						pattern = Pattern.compile(periodName);
						matcher = pattern.matcher(matchString);
						if (matcher.matches()){
							getPeriodNameValue = matchString;
							flag =1;
							}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPeriodNameValue;
	}
	@Test
	public void journalPeriodname() throws IOException {
ERP_Financial_Webservice_MainClass ef = new ERP_Financial_Webservice_MainClass();
excelPath = ERP_Financial_Webservice_MainClass.FinanceInputDataFile.toString();
		fileObj=ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
		System.out.println(fileObj);
		 test=extent.createTest("Journal Period Name");
		ReadERPFinance_InputDataSheet re = new ReadERPFinance_InputDataSheet();
		outputValue = re.parseInputExcelFile(excelPath,ColumnWanted);
		periodName = outputValue.get(0).toString();
		
		getPeriodName(periodName);
		finalOutputString = getPeriodNameValue;
		if(flag==1){
		System.out.println("Period Name extracted from the output file is \n" +finalOutputString);
		Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Step 1: Period Name extracted from the output file is - " +finalOutputString+" and this period is in open status ");
		 Logger.logInfo(JournalPeriodName.class, "Step 1: Period Name extracted from the output file is - " +finalOutputString+" and this period is in open status ");
		}
		else{
			System.out.println("Please check if the Period Name keyed is correct");
		Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Step 1: Period name is not matching or Unable to extract period name" );
		Logger.logError(JournalPeriodName.class, "Step 1: Period name is not matching or Unable to extract period name");
		
		}
	}

}