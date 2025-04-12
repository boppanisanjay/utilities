package datadriventesting;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Demo {
	public static void main(String[] args) throws IOException {
		
	
	 
	FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata.xlsx");
	//FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata.xlsx");
	XSSFWorkbook wb = new XSSFWorkbook(file);
	//XSSFWorkbook workbook = new XSSFWorkbook(file);
	
	XSSFSheet sheet = wb.getSheet("sheet1");
	XSSFRow row = sheet.getRow(0);
	XSSFCell cell = row.getCell(0);
		int rowcount = row.getLastCellNum();
		int cellcount = sheet.getRow(0).getLastCellNum();
		
		for(int r =0; r<=rowcount;r++) {
			XSSFRow row1 = sheet.getRow(r);
			for(int c=0;c<cellcount;c++) {
				XSSFCell cell1 = row1.getCell(c);
				System.out.print(" || Data at box: "+(r+1)+","+(c+1)+" is"+cell1.toString());
			}
			System.out.println();
		}

}
}