package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger; //loading log4j.xml file
	public Properties p;
	
	@BeforeTest(groups={"sanity","regression","Datadriven","master"})
	@Parameters({"os", "browser"})
	public void setUp(@Optional String os, String br) throws IOException
	{
		//loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties"); //read config file
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());  //loading log4j.xml file always fetch from resources path only
		
		switch(br.toLowerCase())
		{
		case "chrome": driver=new ChromeDriver(); break;
		case "firefox": driver=new FirefoxDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("Invalid Browser"); return;
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("https://tutorialsninja.com/demo/");
		
		driver.get(p.getProperty("appURL2"));  //read url from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"sanity","regression","Datadriven","master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	//this will generate alphabets value
	public String randomeString()
		{
			String generatedstring = RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		}
		
		//this will generate numbers value
		public String randomeNumber()
		{
			String generatednumber = RandomStringUtils.randomNumeric(10);
			return generatednumber;
		}
		
		//this will generate combination of alphabets and numbers value
		public String randomAlphaNumeric()
		{
			String generatedstring = RandomStringUtils.randomAlphabetic(3);
			String generatednumber = RandomStringUtils.randomNumeric(10);
			return (generatedstring+"@"+generatednumber);
		}

		public String captureScreen(String tname) throws IOException {

            String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            String targetFilePath = System.getProperty("user.dir") +"\\screenshots\\" + tname + "_" + timeStamp;

            File targetFile = new File(targetFilePath);

            sourceFile.renameTo(targetFile);

            return targetFilePath;

    }
}
