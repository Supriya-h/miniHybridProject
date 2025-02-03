package testCases;

import org.testng.Assert;


import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups={"regression","master"})
	public void verify_account_registration()
	{
		logger.info("***** Starting TC001_AccountRegistrationTest *****"); //this msg will be displayed in the log file
		
		try {
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		
		logger.info("Clicked on MyAccount link");
		
		hp.clickRegister();
		
		logger.info("Clicked on Register link");
		
		AccountRegistrationPage ar=new AccountRegistrationPage(driver);
		
		logger.info("Providing user details");
		
		ar.setFirstName(randomeString().toUpperCase()); //randomeString().toUpperCase
		ar.setLastName(randomeString().toUpperCase()); //
		ar.setEmail(randomeString()+"@gmail.com"); // random email string will generate
		ar.setTelephone(randomeNumber()); //randomeString()
		
		String password=randomAlphaNumeric();  //if call 2 times randomAlphaNumeric() then it will generate 2 different pw, we need to store that in a var
		
		ar.setPassword(password);
		ar.setConfirmPassword(password);
		
		ar.setPrivacyPolicy();
		ar.clickContinue();
		
		logger.info("Validating Expected message");
		
		String confmsg=ar.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed"); 
			logger.debug("Debug logs...");
			
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!ssss");
		}
		
		catch(Exception e)
		{
//			logger.error("Test Failed"); //if the test is failed then this message will display
//			
//			logger.debug("Debug logs..."); //test is got failed want to log debug, debug logs we require, this statement will capture all debug logs 
			
			Assert.fail();	
		}
		
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
	
}
