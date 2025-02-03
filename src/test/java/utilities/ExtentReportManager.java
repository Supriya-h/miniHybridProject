package utilities;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{
	
	public  ExtentSparkReporter sparkReporter;
	public  ExtentTest test;
	public  ExtentReports extent;

	String repName;
	
	public void onStart(ITestContext testContext)
	{
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		repName="Test-Report-" + timestamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" +repName);
		
        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "OA");
        
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);
        
        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);
        
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty())
        {
        	extent.setSystemInfo("Groups", includedGroups.toString());
        }
	}
	
	public void onTestSuccess(ITestResult result) {
		
	    // Create a test node in Extent Report for passed test case
	    test = extent.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());
	    test.log(Status.PASS,result.getName()+ "got successfully executed ");
	
	}
	
	public void onTestFailure(ITestResult result) {
		
        // Create a test node in Extent Report for failed test case
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        
        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
	
	public void onTestSkipped(ITestResult result) {
			
	        test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups());
	        
	        test.log(Status.SKIP, result.getName() + " got skipped");
	        test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) {

        extent.flush();

        String pathofExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;

        File extentReport = new File(pathofExtentReport);

        try {

                Desktop.getDesktop().browse(extentReport.toURI());

        } catch (IOException e) {

                e.printStackTrace();

        }

        /*

        try { URL url = new

        URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);

        Desktop.getDesktop().browse(url.toURI());

        }catch (MalformedURLException ex) {

        ex.printStackTrace();

        }catch(IllegalArgumentException ex) {

        ex.printStackTrace();

        }catch (UnsupportedOperationException ex) {

        ex.printStackTrace();

        }catch (IOException ex) {

        ex.printStackTrace();

        }*/
	}
}
