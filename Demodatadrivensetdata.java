package datadriventesting;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class Demodatadrivensetdata {
	public static void main(String[] args) throws IOException{

		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("sheet1");
		XSSFRow row1 =sheet.createRow(0);
			row1.createCell(0).setCellValue("java");
			row1.createCell(1).setCellValue(1234);
			row1.createCell(2).setCellValue("selenium");

		XSSFRow row2 =sheet.createRow(1);
			row2.createCell(0).setCellValue("java1");
			row2.createCell(1).setCellValue(1234);
			row2.createCell(2).setCellValue("selenium1");

		XSSFRow row3 =sheet.createRow(2);
			row3.createCell(0).setCellValue("java2");
			row3.createCell(1).setCellValue(1234);
			row3.createCell(2).setCellValue("selenium2");

		FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata1.xlsx");			
		workbook.write(file);

		System.out.println(sheet.getLastRowNum());
		System.out.println(sheet.getRow(0).getLastCellNum());

		int	rowcount = sheet.getLastRowNum();
		int columncount = sheet.getRow(0).getLastCellNum();

		for(int r=0;r<=rowcount;r++) {
			//sheet.getRow(r);
			XSSFRow columnrow = sheet.getRow(r);
			for(int c=0;c<columncount;c++) {
				//System.out.println(sheet.getRow(r).getCell(c).toString());
				XSSFCell column =columnrow.getCell(c);
				System.out.print(" || Data at box: "+(r+1)+","+(c+1)+" is"+column.toString());
			}
			System.out.println();

		}


	}
}
