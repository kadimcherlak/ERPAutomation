/*===============================================================================================================================
        CLASS Name:    ReporterBaseTest
        CREATED BY:    Raghavendran Ramasubramanian/Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Extent Reporter Class to create reporting for the application                   
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package Common_Utility;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;


public class ReporterBaseTest {
	public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    String timestamp;    
    File thedir;
    File thedir1;
    File thedir2;
    File thedir3;
    
    @BeforeSuite
    public void setUp()
    {
    	timestamp=new SimpleDateFormat("MM_dd_yyyy HHmmss").format(new java.util.Date());
        htmlReporter = new ExtentHtmlReporter("C:\\Automation_OCTS\\Results\\OCTS_TestResultsReport"+"_"+timestamp+".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
         
        extent.setSystemInfo("OS", "Windows 10");
        extent.setSystemInfo("Host Name", "Tester");
        extent.setSystemInfo("Environment", "Dev");
        extent.setSystemInfo("User Name", "Tester");
         
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Automation-OCTS");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
    
    }
     
    public void folderCreate() {
    	 thedir  = new File("C:\\Automation_OCTS\\");
    		thedir1 = new File("C:\\Automation_OCTS\\Data");
    		thedir2 = new File("C:\\Automation_OCTS\\Output");
    		thedir3 = new File("C:\\Automation_OCTS\\Results");
    		if(!thedir.exists() && !thedir1.exists() && !thedir2.exists() && !thedir3.exists())
    		{
    			boolean result = false;
    			
    			try
    			{
    				thedir.mkdir();
    				thedir1.mkdir();
    				thedir2.mkdir();
    				thedir3.mkdir();
    				result=true;
    				System.out.println("Folder Created");
    			}
    			catch(SecurityException se)
    			{
    				//handle it
    				se.printStackTrace();
    			}
    			if(result)
    			{
    				System.out.println("Base directory created");
    			}
     }
    }
    
    @AfterMethod
    public void getResult(ITestResult result)
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        else
        {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }
     
    @AfterSuite
    public void tearDown()
    {
        extent.flush();
        System.out.println("after suite");
    }

}