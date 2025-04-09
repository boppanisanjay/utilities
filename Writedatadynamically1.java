package datadriventesting;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class Writedatadynamically1 {
	public static void main(String[] args) throws IOException{
		FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata4.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
		XSSFRow row = sheet.createRow(4);
		row.createCell(2).setCellValue("Sanjay");
		
		workbook.write(file);
		workbook.close();
		file.close();
		System.out.println("Done");
	
		
	
	
	}

}
