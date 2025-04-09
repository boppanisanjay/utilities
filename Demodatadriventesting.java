package datadriventesting;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class Demodatadriventesting {
	public static void main(String[] args) throws IOException {

		//Excelfile - workbook - sheet - rows -cell

		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\Testdatafiles\\Testdata.xlsx");
		//FileInputStream file = new FileInputStream("C:\\Users\\sanjay.boppani\\eclipse-workspace\\SeleniumDemo\\Testdatafiles\\Testdata.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("sheet1");

		int rowcount = sheet.getLastRowNum();
		int columncount = sheet.getRow(1).getLastCellNum();

		System.out.println("Row count: "+rowcount + "\n" + "Coulmn count: "+columncount);

		for(int i=0; i<=rowcount;i++) {
			XSSFRow columnrow=sheet.getRow(i);
			for(int j =0;j<columncount;j++) {
				
				//XSSFCell celldata = columnrow.getCell(j);
				//System.out.println(celldata.toString());
				XSSFCell celldata = columnrow.getCell(j);
				String columndata =celldata.toString();			
				System.out.print(" || Data at box: "+(i+1)+","+(j+1)+" is"+columndata);	
			}
			System.out.println();

		}
		workbook.close();
		file.close();

	}
}