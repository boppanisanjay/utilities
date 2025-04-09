package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    // DataProvider 1
    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        // Taking xl file from testData
        String path = ".\\testData\\Opencart_LoginData.xlsx";

        // Creating an object for XLUtility
        ExcelUtility xlutil = new ExcelUtility(path);

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);

        // Created two-dimensional array to store data
        String[][] logindata = new String[totalrows][totalcols];

        // Read the data from xl and store in a two-dimensional array
        for (int i = 1; i <= totalrows; i++) { // i represents rows
            for (int j = 0; j < totalcols; j++) { // j represents columns
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
                /*
                 *added below statement to check whether logic is bale to fetch the data from excel sheet
                 * System.out.print(" "+xlutil.getCellData("Sheet1", i, j)); 
                 */
            }
        }

        // Returning two-dimensional array
        return logindata;
    }
   /* 
    * added below statement to check whether logic is bale to fetch the data from excel sheet
    * public static void main(String[] args) throws IOException {
    	DataProviders dp = new DataProviders();
    	dp.getData();	
	}*/
}
