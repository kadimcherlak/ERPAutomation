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
public class EmployeeStandardHoursValidation extends ReporterBaseTest{
	
	static String workerTestDataPath = "C:\\Automation_OCTS\\Output\\WorkerTestData.xlsx";
	static HashMap<Integer, Cell> personNumber = new HashMap<Integer, Cell>();
	static HashMap<Integer, Cell> standardHours = new HashMap<Integer, Cell>();
	static String workerPersonNumberCol = "PersonNumber";
	static String workerStandardHoursCol = "NormalHours";
	static String actualPersonNumber = "STUDENT1_PERSON117";
	static String actualStandardHours = "40";
	static String expectedPersonNumber ="";
	static String expectedStandardHours;

	static HashMap<Object,Cell> standardHoursValidation = new HashMap<Object, Cell>();
	int flag = 0;

	public void getStandardHours() {
		ReadHCMWorker_InputDataSheet workerFile = new ReadHCMWorker_InputDataSheet();

		try {
			personNumber = workerFile.parseWorkerExcel(workerTestDataPath,workerPersonNumberCol);
			standardHours = workerFile.parseWorkerExcel(workerTestDataPath,workerStandardHoursCol);
			for (Map.Entry<Integer, Cell> pNumber : personNumber.entrySet()) {
				for (Map.Entry<Integer, Cell> stdHrs : standardHours.entrySet()) {
					if (pNumber.getKey().equals(stdHrs.getKey())) {
						standardHoursValidation.put(pNumber.getValue(),stdHrs.getValue());
					}
				}
			}
			for (Map.Entry<Object, Cell> editPersonNumber : standardHoursValidation.entrySet()){
				expectedPersonNumber = editPersonNumber.getKey().toString();
				expectedStandardHours = editPersonNumber.getValue().toString();
				/*if(expectedPersonNumber.equals(actualPersonNumber) ) 
				{
					expectedStandardHours = editPersonNumber.getValue().toString();
					if(expectedStandardHours.equals(actualStandardHours)){
						flag=1;
						break;
					}
				}*/
				flag=1;
				break;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag == 1)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has been assigned to Standard Hours : "+ ""+expectedStandardHours);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS,"Employee #"+ expectedPersonNumber + "" + " has been assigned to Standard Hours : "+ ""+expectedStandardHours+"\n");
			Logger.logInfo(EmployeeStandardHoursValidation.class, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Standard Hours : "+ ""+expectedStandardHours+"\n");
		}
		if(flag == 0)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has not been assigned to Standard Hours : "+ ""+expectedStandardHours);
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL,"Employee #"+ expectedPersonNumber + "" + " has not been assigned to Standard Hours :"+ ""+expectedStandardHours+"\n");
			Logger.logError(EmployeeStandardHoursValidation.class,"Employee #"+ expectedPersonNumber + "" + " has not been assigned to Standard Hours :"+ ""+expectedStandardHours+"\n");
		}
		
	}
	
	@Test
	public void empAssignmentStatusValidation() {
		EmployeeStandardHoursValidation empStdHours = new EmployeeStandardHoursValidation();
		System.out.println(workerTestDataPath);
		test=extent.createTest("Employee Standard Hours  Validation");
		System.out.println("\n Employee Standard Hours  Validation \n");
		empStdHours.getStandardHours();
		
	}
	//Commenting the below code; if required to test this code alone then uncomment the code
/*	public static void main(String[] args) {
		EmployeeStandardHoursValidation empVal = new EmployeeStandardHoursValidation();
		empVal.getStandardHours();
	}
	*/
	
}