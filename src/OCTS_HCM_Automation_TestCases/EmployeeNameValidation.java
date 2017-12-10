package OCTS_HCM_Automation_TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Common_Utility.*;
import OCTS_Automation_Main_Modules.*;

@SuppressWarnings("unused")
public class EmployeeNameValidation extends ReporterBaseTest{
	
	static String workerTestDataPath = "C:\\Automation_OCTS\\Output\\WorkerTestData.xlsx";
	static HashMap<Integer, Cell> firstName = new HashMap<Integer, Cell>();
	static HashMap<Integer, Cell> lastName = new HashMap<Integer, Cell>();
	static String workerFirstNameCol = "FirstName";
	static String workerLastNameCol = "LastName";
	static String actualEmpName = "Rob, Morgan";
	static String trimActualEmpName = actualEmpName.replace(" ","");
	static String expectedEmpName ="";

	static HashMap<Object,Cell> empName = new HashMap<Object, Cell>();
	int flag = 0;

	public void getFirstAndLastName() {
		ReadHCMWorker_InputDataSheet workerFile = new ReadHCMWorker_InputDataSheet();

		try {
			firstName = workerFile.parseWorkerExcel(workerTestDataPath,workerFirstNameCol);
			lastName = workerFile.parseWorkerExcel(workerTestDataPath,workerLastNameCol);
			for (Map.Entry<Integer, Cell> fName : firstName.entrySet()) {
				for (Map.Entry<Integer, Cell> lName : lastName.entrySet()) {
					if (fName.getKey().equals(lName.getKey())) {
						empName.put(fName.getValue(),lName.getValue());
					}
				}
			}
			for (Map.Entry<Object, Cell> editEmpName : empName.entrySet()){
				expectedEmpName = editEmpName.getKey() +","+editEmpName.getValue();
				flag=1;
				break;
			/*	if(expectedEmpName.equals(trimActualEmpName))
				{
					flag=1;
					break;
				}
				*/
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag == 1)
		{
			System.out.println("Employee "+ expectedEmpName + "" + " hired.");
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Employee "+ expectedEmpName + "" + " hired."+"\n");
			Logger.logInfo(EmployeeNameValidation.class, "Employee "+ expectedEmpName + "" + " hired."+"\n");
		}
		if(flag == 0)
		{
			System.out.println("Employee "+ expectedEmpName + "" + " not hired.");
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Employee "+ expectedEmpName + "" + " not hired." +"\n");
			Logger.logError(EmployeeNameValidation.class, "Employee "+ expectedEmpName + "" + " not hired." +"\n");
		}
		
	}
	
	@Test
	public void empNameValidation() {
		EmployeeNameValidation empVal = new EmployeeNameValidation();
		System.out.println(workerTestDataPath);
		test=extent.createTest("Employee First and Last Name Validation");
		System.out.println("\n Employee Name Validation \n");
		empVal.getFirstAndLastName();
	}
	
	//Commenting the below code; if required to test this code alone then uncomment the code
/*	public static void main(String[] args) {
		EmployeeNameValidation empVal = new EmployeeNameValidation();
		empVal.getFirstAndLastName();
	}
	*/
}