package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path="./testData/Opencart_Login.xlsx";  //taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path); //creating object for xlutility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols]; //create for 2-d array which can store rows and cols
		
		for(int i=0;i<totalrows;i++) //1 //read the data from xl storing in 2-d array
		{
			for(int j=0;j<totalcols;j++)//0 //i is rows j is cols
			{
				logindata[i-1][j]=xlutil.getCellData("Shee1", i, j); //1,0
			}
		}
		return logindata; //returning 2-D A
	}
	
	//DataProvider 2
	
	//DataProvider 3
	
	//DataProvider 4

}
