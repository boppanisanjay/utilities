package datadriventesting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class Writedata {
	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		XSSFRow row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue(1234);
			row1.createCell(1).setCellValue("Java");
			row1.createCell(2).setCellValue("Selenium");
			
		XSSFRow row2 = sheet.createRow(1);
			row2.createCell(0).setCellValue(1234);
			row2.createCell(1).setCellValue("Java1");
			row2.createCell(2).setCellValue("Selenium1");
			
		XSSFRow row3 = sheet.createRow(2);
			row3.createCell(0).setCellValue(1234);
			row3.createCell(1).setCellValue("Java2");
			row3.createCell(2).setCellValue("Selenium2");
		
		FileOutputStream file1 = new FileOutputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata2.xlsx");
		workbook.write(file1);
		
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata2.xlsx");
		XSSFWorkbook workbook1 = new XSSFWorkbook(file);
		XSSFSheet sheet1 = workbook1.getSheet("sheet1");
		int rowcount = sheet1.getLastRowNum();
		int columncount = sheet1.getRow(0).getLastCellNum();
		
		System.out.println("Rowcount: "+rowcount +" Columncount: "+columncount);
		
		for(int r=0;r<=rowcount;r++) {
			XSSFRow row = sheet.getRow(r);
			for(int c=0;c<columncount;c++) {
				XSSFCell cell =row.getCell(c);
				System.out.print(" || Data at box: "+(r+1)+","+(c+1)+" is "+cell.toString());
			}
			System.out.println();
		}
		
		file.close();
		file1.close();
		workbook.close();
		workbook1.close();
	}

}
