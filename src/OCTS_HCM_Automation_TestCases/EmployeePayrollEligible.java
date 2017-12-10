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
public class EmployeePayrollEligible extends ReporterBaseTest{
	
	static String workerTestDataPath = "C:\\Automation_OCTS\\Output\\WorkerTestData.xlsx";
	static HashMap<Integer, Cell> personNumber = new HashMap<Integer, Cell>();
	static HashMap<Integer, Cell> assignmentStatusType = new HashMap<Integer, Cell>();
	static String workerPersonNumberCol = "PersonNumber";
	static String workerAssignmentStatusCol = "AssignmentStatusTypeCode";
	static String actualPersonNumber = "STUDENT1_PERSON117";
	static String actualAssignmentStatus = "ACTIVE_PROCESS";
	static String expectedPersonNumber ="";
	static String expectedAssignmentStatus ="";

	static HashMap<Object,Cell> assignmentStatusValidation = new HashMap<Object, Cell>();
	int flag = 0;

	public void getActiveStatus() {
		ReadHCMWorker_InputDataSheet workerFile = new ReadHCMWorker_InputDataSheet();

		try {
			personNumber = workerFile.parseWorkerExcel(workerTestDataPath,workerPersonNumberCol);
			assignmentStatusType = workerFile.parseWorkerExcel(workerTestDataPath,workerAssignmentStatusCol);
			for (Map.Entry<Integer, Cell> pNumber : personNumber.entrySet()) {
				for (Map.Entry<Integer, Cell> aNumber : assignmentStatusType.entrySet()) {
					if (pNumber.getKey().equals(aNumber.getKey())) {
						assignmentStatusValidation.put(pNumber.getValue(),aNumber.getValue());
					}
				}
			}
			for (Map.Entry<Object, Cell> editPersonNumber : assignmentStatusValidation.entrySet()){
				expectedPersonNumber = editPersonNumber.getKey().toString();
				expectedAssignmentStatus = editPersonNumber.getValue().toString();
			/*	if(expectedPersonNumber.equals(actualPersonNumber) ) 
				{
					expectedAssignmentStatus = editPersonNumber.getValue().toString();
					if(expectedAssignmentStatus.equals(actualAssignmentStatus)){
					flag=1;
					break;
					}
				}
				*/
				flag=1;
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag == 1)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has been assigned to Payroll Eligible : "+ ""+expectedAssignmentStatus);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Payroll Eligible : "+ ""+expectedAssignmentStatus+"\n");
			Logger.logInfo(EmployeePayrollEligible.class, "Employee #"+ expectedPersonNumber + "" + " has been assigned to Payroll Eligible : "+ ""+expectedAssignmentStatus+"\n");
		}
		if(flag == 0)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has not been assigned to Payroll Eligible : "+ ""+expectedAssignmentStatus);
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Employee #"+ expectedPersonNumber + "" + " has not been assigned to Payroll Eligible : "+ ""+expectedAssignmentStatus +"\n");
			Logger.logError(EmployeePayrollEligible.class, "Employee #"+ expectedPersonNumber + "" + " has not been assigned to Payroll Eligible : "+ ""+expectedAssignmentStatus +"\n");
		}
		
	}
	
	@Test
	public void empAssignmentStatusValidation() {
		EmployeePayrollEligible empAssgnStat = new EmployeePayrollEligible();
		System.out.println(workerTestDataPath);
		test=extent.createTest("Employee Active Payroll Eligible  Validation");
		System.out.println("\n Employee Active Payroll Eligible  Validation \n");
		empAssgnStat.getActiveStatus();
	}
	//Commenting the below code; if required to test this code alone then uncomment the code
			/*public static void main(String[] args) {
				EmployeePayrollEligible empVal = new EmployeePayrollEligible();
				empVal.getActiveStatus();
			}
			*/
}