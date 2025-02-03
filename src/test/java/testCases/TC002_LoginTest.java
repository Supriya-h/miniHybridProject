package testCases;

import org.testng.Assert;
import org.testng.annotations.*;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"sanity","master"})
	public void verify_login()
	{
		logger.info("***** Starting TC002_LoginTest *****"); //this msg will be displayed in the log file
		try {
			
		//homepage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//loginpage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//myaccountpage
		MyAccountPage mcc=new MyAccountPage(driver);
		boolean targetPage=mcc.isMyAccountPageExists();
		
		Assert.assertEquals(targetPage, true, "Login Failed");//Assert.assertTrue(targetPage); 
		}
		
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***** Starting TC002_LoginTest *****");
	}

}
