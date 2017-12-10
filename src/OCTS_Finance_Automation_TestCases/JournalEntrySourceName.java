/*===============================================================================================================================
        CLASS Name:    JournalEntrySourceName
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Journal Entry Source Name Validation                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_Finance_Automation_TestCases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Common_Utility.ReporterBaseTest;
import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;
import Common_Utility.CommonUtilFunctions;
import Common_Utility.Details;
import Common_Utility.Logger;

import com.aventstack.extentreports.Status;


import OCTS_Automation_Main_Modules.ReadERPFinance_InputDataSheet;
public class JournalEntrySourceName extends ReporterBaseTest {
	
	// Required Variables to pull out the matching string
	/*static String zipFilePath = "C:\\Automation_OCTS\\Output\\73808.zip";  
    static String destDir = "C:\\Automation_OCTS\\Output\\";*/
	//static String fileObj = ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
	static String excelPath;
	//static String excelPath = "C:\\Automation_OCTS\\Data\\ERP_Finance_InputDatasheet.xlsx";
	static String entrySourceName = "";
	static String periodName = "";
	static String batchname;
	String newBatchName;
	static String[] splitNewLine = {};
	static String finalOutputString = "";
	static List<String> finalOutputValues = new ArrayList<String>();
	static ArrayList<Cell> outputValue = new ArrayList<Cell>();
	static ArrayList<Cell> outputValue1 = new ArrayList<Cell>();
	static String ColumnWanted = "SEJRRequestImport_PL2_JournalSource";
	static String ColumnWanted1="SEJRRequestImport_PL2_JournalPeriodName";
	int flag=0;
	static String fileObj;
	@Test
	public void journalEntrySourcename() {
		
		ERP_Financial_Webservice_MainClass ef = new ERP_Financial_Webservice_MainClass();
		excelPath = ERP_Financial_Webservice_MainClass.FinanceInputDataFile.toString();
		fileObj=ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
	
		System.out.println(fileObj);
		//fileObj = zipFilepath.replace("zip", "txt");
		 test=extent.createTest("Journal Entry Source Name");
		//Common_Utility.ReporterBaseTest.test = Common_Utility.ReporterBaseTest.extent.createTest("Journal Entry Source Name");
		ReadERPFinance_InputDataSheet re = new ReadERPFinance_InputDataSheet();
		try {
			outputValue = re.parseInputExcelFile(excelPath, ColumnWanted);
			outputValue1 = re.parseInputExcelFile(excelPath, ColumnWanted1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entrySourceName = outputValue.get(0).toString();
		periodName= outputValue1.get(0).toString();
		try {
			getEntrySourceName();
			 newBatchName=getBatchName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < finalOutputValues.size() - 1; i++) {
			if (finalOutputValues.get(0).equals(entrySourceName)) {
				finalOutputString = finalOutputValues.get(0);
				flag=1;
			} 
		}
		if(flag==1)
		{
		
		System.out
				.println("Entry Source Name extracted from the Output File \n"
						+ finalOutputString + newBatchName );
		Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Journal Entry Source Name matched with Output file source name - " +finalOutputString +"\n"+". Journal has been created with Batch Name – "+ batchname );
		  Logger.logInfo(JournalEntrySourceName.class, "Journal Entry Source Name matched with Output file source name - " +finalOutputString +"\n"+". Journal has been created with Batch Name – "+ batchname);
		}
		if(flag==0){
			System.out
			.println("Please check if the Entry Source Name keyed is correct");
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Journal Entry Source Name mismatch found. Input is: "+ entrySourceName +" and Output is "+ finalOutputValues.get(0));
			Logger.logError(JournalEntrySourceName.class, "Journal Entry Source Name mismatch found. Input is: "+ entrySourceName +" and Output is "+ finalOutputValues.get(0));
			}
	}

	// Method to parse the text file and pull out the Matching pattern to
	// validate the excel
	public List<String> getEntrySourceName() throws IOException {
		try {
			String line = null;
			String newLine = "";
			@SuppressWarnings("resource")
			BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileObj), "UTF-8"));

			line = line + bufRdr.readLine();

			while ((line = bufRdr.readLine()) != null) {
				//line = bufRdr.readLine();
				if (line.startsWith(entrySourceName)) {
					// System.out.println("Before trimming spaces\n" + line);
					newLine = line.trim();
					// System.out.println("After trimming spaces\n" + newLine);
					splitNewLine = newLine.split("\\s");
					for (String matchString : splitNewLine) {
						if ((!matchString.matches("\\d"))
								&& (matchString.length() != 0)
								&& (!matchString.matches("[^\\w.*]")))

						{

							finalOutputValues.add(matchString);
						}

					}
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return finalOutputValues;

	}
	// Method to parse the text file and pull out the Matching pattern to
		// validate the excel
		public String getBatchName() throws IOException {
			try {
				String line1 = null;
				String newLine = "";
				@SuppressWarnings("resource")
				BufferedReader bufRdr1 = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileObj), "UTF-8"));

				line1 = line1 + bufRdr1.readLine();

				while ((line1 = bufRdr1.readLine()) != null) {
				//	line1 = bufRdr1.readLine();
					if (line1.contains(periodName)) {		
						//newLine = line.trim();
						 batchname=line1.substring(1, 65).trim();
						 System.out.println(batchname);
					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return batchname;

		}
}