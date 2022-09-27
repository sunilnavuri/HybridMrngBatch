package utilities;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
    XSSFWorkbook wb;
    //constructor for reading excel path
    public ExcelFileUtil(String excelpath) throws Throwable
    {
      FileInputStream fi = new FileInputStream(excelpath);
      wb = new XSSFWorkbook(fi);
    }
     //count no of rows in a sheet
    public int rowCount(String sheetname)
    {
    	return wb.getSheet(sheetname).getLastRowNum();
    }
	//count no of cells in row
    public int cellCount(String sheetname)
    {
    	return wb.getSheet(sheetname).getRow(0).getLastCellNum();
    	
   }
     //get cell data
    @SuppressWarnings("deprecation")
	public String getCellData(String sheetname,int row,int column)
    {
       String data="";
       if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
       {
    	   int celldata = (int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
    	   data = String.valueOf(celldata);
       }
       else
       {
    	   data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
       }
	return data;
    } 
    //write set cell data
    @SuppressWarnings("deprecation")
	public void setCellData(String sheetname,int row,int column,String status,String writeExcelpath) throws Throwable
    {
    //get sheet from wb
    	XSSFSheet ws = wb.getSheet(sheetname);
    	XSSFRow rowNum = ws.getRow(row);
    	XSSFCell cell = rowNum.createCell(column);
    	cell.setCellValue(status);
    	if(status.equalsIgnoreCase("pass"))
    	{
    		XSSFCellStyle style = wb.createCellStyle();
    		XSSFFont font =wb.createFont();
    		font.setColor(IndexedColors.GREEN.getIndex());
    		font.setBold(true);
    		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    		style.setFont(font);
    		rowNum.getCell(column).setCellStyle(style);
    	}
    	else if(status.equalsIgnoreCase("Fail"))
    	{
    		XSSFCellStyle style = wb.createCellStyle();
    		XSSFFont font =wb.createFont();
    		font.setColor(IndexedColors.RED.getIndex());
    		font.setBold(true);
    		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    		style.setFont(font);
    		rowNum.getCell(column).setCellStyle(style);
    	}
    	else if(status.equalsIgnoreCase("Blocked"))
    	{
    		XSSFCellStyle style = wb.createCellStyle();
    		XSSFFont font =wb.createFont();
    		font.setColor(IndexedColors.DARK_BLUE.getIndex());
    		font.setBold(true);
    		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    		style.setFont(font);
    		rowNum.getCell(column).setCellStyle(style);
    	}
    	FileOutputStream fo = new FileOutputStream(writeExcelpath);
    	wb.write(fo);
    			
    }
    public static void main(String[] args)throws Throwable{
		ExcelFileUtil xl = new ExcelFileUtil("F://Book.xlsx");
		//count no rows
		int rc = xl.rowCount("Login");
		int cc = xl.cellCount("Login");
		System.out.println(rc+"      "+cc);
		for(int i=1;i<=rc;i++)
		{
			String user =xl.getCellData("Login", i, 0);
			String pass =xl.getCellData("Login", i, 1);
			System.out.println(user+"       "+pass);
			//xl.setCellData("Login", i, 2, "pass","F:/Results.xlsx");
			//xl.setCellData("Login", i, 2, "Fail","F:/Results.xlsx");
		    xl.setCellData("Login", i, 2, "Blocked","F:/Results.xlsx");
		}
	}
}
