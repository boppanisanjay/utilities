package utilities;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {
    private String path;
    private FileInputStream fi;
    private FileOutputStream fo;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;
    private CellStyle style;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowcount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellcount = (row != null) ? row.getLastCellNum() : 0;
        workbook.close();
        fi.close();
        return cellcount;
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = (row != null) ? row.getCell(colnum) : null;
        DataFormatter formatter = new DataFormatter();
        String data = (cell != null) ? formatter.formatCellValue(cell) : "";
        workbook.close();
        fi.close();
        return data;
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
            fo.close();
        }
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        fi.close();
        
        if (workbook.getSheetIndex(sheetName) == -1)
            workbook.createSheet(sheetName);
        
        sheet = workbook.getSheet(sheetName);
        if (sheet.getRow(rownum) == null)
            sheet.createRow(rownum);
        
        row = sheet.getRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue(data);
        
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fo.close();
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        applyCellColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        applyCellColor(sheetName, rownum, colnum, IndexedColors.RED);
    }

    private void applyCellColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        fi.close();
        
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        if (row == null) row = sheet.createRow(rownum);
        cell = row.getCell(colnum);
        if (cell == null) cell = row.createCell(colnum);
        
        style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fo.close();
    }
}
