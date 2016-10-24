package com.ljzforum.platform.util;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel 97~2003 xls格式 /2007~ xlsx格式
 * @author      heng.wang
 * @date        2014-10-16
 */
public class ExcelReader {
	private static     Logger logger = Logger.getLogger(ExcelReader.class);
 
    /**
     * 创建工作簿对象(xls格式)
     */
    public static final HSSFWorkbook createWb(InputStream inputStream) throws IOException {
    	
    	return new HSSFWorkbook(inputStream);
    }
    
    /**
     * 创建工作簿对象(xlsx格式)
     */
    public static final XSSFWorkbook createWbForXLSX(InputStream inputStream) throws IOException {
    	
    	return new XSSFWorkbook(inputStream);
    }
     
    public static final Sheet getSheet(Workbook wb ,String sheetName) {
        return wb.getSheet(sheetName) ;
    }
     
    public static final Sheet getSheet(Workbook wb ,int index) {
        return wb.getSheetAt(index) ;
    }
     
    public static final List<Object[]> listFromSheet(Sheet sheet) {
        int rowTotal = sheet.getPhysicalNumberOfRows();
        logger.debug(sheet.getSheetName()+"共有"+rowTotal+"行记录！");
         
        List<Object[]> list = new ArrayList<Object[]>();
        for(int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r ++) {
        	Row row = sheet.getRow(r);
            if(row == null) continue;
            // 不能用row.getPhysicalNumberOfCells()，可能会有空cell导致索引溢出
            // 使用row.getLastCellNum()至少可以保证索引不溢出，但会有很多Null值，如果使用集合的话，就不说了
            Object[] cells = new Object[row.getLastCellNum()];
            Cell cell = null;
            for(int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
                cell = row.getCell(c);
                if(cell == null) continue;
                cells[c] = getValueFromCell(cell);
            }
            list.add(cells);
        }
         
        return list;
    }
     
     
    /**
     * 获取单元格内文本信息
     * @param cell
     * @return
     * @date    2013-5-8
     */
    public static final String getValueFromCell(Cell cell) {
        if(cell == null) {
            return null ;
        }
        String value = null ;
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC :   // 数字
                if(HSSFDateUtil.isCellDateFormatted(cell)) {        // 如果是日期类型
                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()) ;
                }else{
                	value = String.valueOf(new DecimalFormat("#").format(cell.getNumericCellValue())) ;
                }
                break ;
            case Cell.CELL_TYPE_STRING:     // 字符串
                value = cell.getStringCellValue() ;
                break ;
            case Cell.CELL_TYPE_FORMULA:    // 公式
                // 用数字方式获取公式结果，根据值判断是否为日期类型
                double numericValue = cell.getNumericCellValue() ;
                if(HSSFDateUtil.isValidExcelDate(numericValue)) {   // 如果是日期类型
                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()) ;
                } else  value = String.valueOf(numericValue) ;
                break ;
            case Cell.CELL_TYPE_BLANK:              // 空白
                value ="";
                break ;
            case Cell.CELL_TYPE_BOOLEAN:            // Boolean
                value = String.valueOf(cell.getBooleanCellValue()) ;
                break ;
            case Cell.CELL_TYPE_ERROR:              // Error，返回错误码
                value = String.valueOf(cell.getErrorCellValue()) ;
                break ;
            default:value = StringUtils.EMPTY ;break ;
        }
  
        return value ;
    }  
     
}


