package datadriventesting;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.*;

public class Writedatadynamically {
	public static void main(String[] args) throws IOException{
		FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata3.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("sheet1");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of rows: ");
		int rowcount = sc.nextInt();
		System.out.println("Enter number of columns: ");
		int columncount = sc.nextInt();
		
		//XSSFRow row1 = sheet.createRow(0);
			//row1.createCell(0).setCellValue("Sanjay");
		for	(int r=0; r<=rowcount;r++) {
			XSSFRow row = sheet.createRow(r);
			for (int c=0;c<columncount;c++) {
				row.createCell(c).setCellValue(sc.next());
			}
		}
		workbook.write(file);
		file.close();
		workbook.close();
			
	}
}
