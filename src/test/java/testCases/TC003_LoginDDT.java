package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*Data is valid---login success----test pass----logout
 *Data is valid---login failed----test fail
 
 *Data is invalid---login success----test fail----logout
 *Data is invalid---login failed----test pass
 */

public class TC003_LoginDDT extends BaseClass{

	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven") //dataProviderClass=DataProviders.class--> getting data from different class
	public void verify_loginDDT(String email, String pwd, String exp)
	{
	try {
		logger.info("***** Starting TC003_LoginDDT *****"); //this msg will be displayed in the log file
		
		//homepage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//loginpage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty(email));
		lp.setPassword(p.getProperty(pwd));
		lp.clickLogin();
		
		//myaccountpage
		MyAccountPage mcc=new MyAccountPage(driver);
		boolean targetPage=mcc.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				mcc.clickLogout();
				Assert.assertTrue(true); 
			}
			else
			{
				Assert.assertTrue(false);
			}
			
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
			
		{
			if(targetPage==true)
			{
				mcc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}	
		
		}
		
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***** Finished TC003_LoginDDT *****");
		
	}
	
}
