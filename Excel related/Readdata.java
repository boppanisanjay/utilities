package datadriventesting;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class Readdata {
	public static void main(String[] args) throws IOException {
		//FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata.xlsx");

		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata.xlsx");
		//FileInputStream file = new FileInputStream("C:\\Users\\sanjay.boppani\\eclipse-workspace\\SeleniumDemo\\Testdatafiles\\Testdata.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		XSSFSheet sheet = workbook.getSheet("sheet1");
		int rowcount =sheet.getLastRowNum();
		int columncount = sheet.getRow(0).getLastCellNum();
		System.out.println("Rowcount: "+rowcount +" Columncount: "+columncount);
		
		for(int r=0;r<=rowcount;r++) {
			XSSFRow row = sheet.getRow(r);
			for(int c=0;c<columncount;c++) {
				XSSFCell column = row.getCell(c);
				System.out.print(" || Data at box: "+(r+1)+","+(c+1)+" is"+column.toString());
			}
			System.out.println();
		}
	}

}
