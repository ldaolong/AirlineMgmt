package com.darren.machine.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class Excel2003Util extends AbstractReportUtil
{
    protected String dateFormat;
    
    protected HSSFSheet sheet;
    
    protected HSSFWorkbook workbook;
    
    protected String reportName = "";
    
    protected HSSFCellStyle header_center;
    
    protected HSSFCellStyle def_hBoldLeft;
    
    protected HSSFCellStyle def_hBoldRight;
    
    protected HSSFCellStyle def_hBoldCenter;
    
    protected HSSFCellStyle def_left;
    
    protected HSSFCellStyle def_right;
    
    protected HSSFCellStyle def_center;
    protected HSSFCellStyle def_center_b;
    
    protected HSSFFont redfont;
    
    protected HSSFFont blackfont;
    protected HSSFFont whitefont;
    
    protected int curRow;
    
    protected int sheetIndex = 1;
    
    public static final int maxSheetRowCount = 65000;
    
    //for auto create sheet
    protected String def_sheetName;
    
    protected String def_titleName;
    
    protected int def_cellRanges;
    
    protected String[] def_headers;
    
    protected int[] def_headerAlign;
    
    protected int[] def_headerWidth;
    
    protected Excel2003Util()
    {
        init();
    }
    
    private Excel2003Util(String baseName, String dateFormat)
    {
        this.reportName = baseName;
        if (dateFormat == null)
            dateFormat = "";
        this.dateFormat = dateFormat;
        init();
    }
    
    private void init()
    {
        workbook = new HSSFWorkbook();
        initCellStyle();
    }
    
    public String getReportName()
    {
        
        
        return reportName +  ".xls";
    }
    
    public Workbook getExcelReport()
    {
        return workbook;
    }
    
    public void createSheet(String sheetName, int columnBreak)
    {
        sheet = workbook.createSheet(sheetName);
        setupPrint(sheet, columnBreak);
        curRow = 0;
    }
    
    /**
     * fixed the issue:rows is max than 65000. 
     * if records is more than 65000, create new sheet.
     * 
     */
    public void createNewSheetRow(String[] rowData)
    {
        if (curRow > maxSheetRowCount)
        {
            curRow = 0;
            createSheet(def_sheetName + (sheetIndex++), 10);
            if (StringUtils.isNotEmpty(def_titleName))
            {
                createTitle(def_titleName, 0, new CellRangeAddress(0, 0, 0,
                        def_cellRanges));
            }
            createTableHeader(def_headers, def_headerAlign, def_headerWidth);
        }
        
        HSSFRow row = sheet.createRow(curRow++);
        
        if (rowData != null)
        {
            int j = 0;
            for (String obj : rowData)
            {
                createNomalCell(row,
                        j++,
                        obj,
                        def_headerAlign[j] == 3 ? def_left : def_right);
                
            }
        }
    }
    
    /**
     * create title of excel
     * @param titleName
     * @param cellIndex
     * @param CellRangeAddress: new CellRangeAddress(0, 0, 0, 2)
     */
    public HSSFRow createTitle(String titleName, int cellIndex,
            CellRangeAddress cellRange)
    {
        if (sheet == null)
            createSheet("sheet1", 10);
        HSSFRow row = sheet.createRow(curRow++);
        if (cellRange != null)
            sheet.addMergedRegion(cellRange);
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(titleName);
        cell.setCellStyle(header_center);
        
        return row;
    }
    
    /**
     * create table header
     * @param titles
     * @param headerAlign: 3:left, 4: right
     * @param headerWidth
     */
    public void createTableHeader(String[] titles, int[] headerAlign,
            int[] headerWidth)
    {
        // set columns widths
        for (int k = 0; k < headerWidth.length; k++)
        {
            sheet.setColumnWidth(k, 256 * headerWidth[k]);
        }
        
        // second row
        HSSFRow row = sheet.createRow(curRow++);
        int j = 0;
        for (String val : titles)
        {
            HSSFCell cell = row.createCell(j++);
            if (StringUtils.isNotEmpty(val))
            {
                cell.setCellValue(val.replace("\\r", "\r").replace("\\n", "\n"));
            }
            
            HSSFCellStyle style = getCellStyle(headerAlign[j - 1]);
            style.cloneStyleFrom(style);
            style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            cell.setCellValue(val);
        }
        
    }
    
    /**
     * 
     * @param values:cell values of row
     * @param columnsAlign:cell align.
     * 1:styleLeft3,2:styleRight3, 3:hBoldLeft,4:hBoldRight,5:headerBold12
     */
    public void createTableContentRow(String[] values, int[] columnsAlign)
    {
        
        HSSFRow row = sheet.createRow(curRow++);
        int k = 0;
        HSSFCell cell;
        for (String val : values)
        {
            cell = row.createCell(k++);
            
            //style.cloneStyleFrom(getCellStyle(columnsAlign[k - 1]));
            if(curRow % 2 ==0){
              //  style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
             //   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
              //  style =def_center_b;
                cell.setCellStyle(def_center_b);
            }else{
                cell.setCellStyle(getCellStyle(columnsAlign[k - 1]));
            }
           
           
          //  cell.setCellStyle(getCellStyle(columnsAlign[k - 1]));
            cell.setCellValue(val);
        }
        
    }
    
    public void createTableTotalRow(String[] values, int[] columnsAlign)
    {
        
        HSSFRow row = sheet.createRow(curRow++);
        int k = 0;
        HSSFCellStyle style = null;
        for (String val : values)
        {
            HSSFCell cell = row.createCell(k++);
            style = (HSSFCellStyle) getCellStyle(columnsAlign[k - 1]);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cell.setCellStyle(style);
            cell.setCellValue(val);
        }
    }
    
    /**
     * create new row 
     * @return
     */
    public HSSFRow createRow()
    {
        return sheet.createRow(curRow++);
    }
    
    /**
     * create new cell
     * @param row
     * @param cellValue
     * @param cellIndex
     * @param cellStyle
     */
    public HSSFCell createNomalCell(HSSFRow row, int cellIndex,
            String cellValue, HSSFCellStyle cellStyle)
    {
        return createRangeCell(row, null, cellIndex, cellValue, cellStyle);
    }
    
    /**
     * create marge cell.
     * @param row
     * @param cellRange
     * @param cellValue
     * @param cellIndex
     * @param cellStyle
     * @return
     */
    public HSSFCell createRangeCell(HSSFRow row, CellRangeAddress cellRange,
            int cellIndex, String cellValue, HSSFCellStyle cellStyle)
    {
        if (cellRange != null)
            sheet.addMergedRegion(cellRange);
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(cellValue);
        
        if (cellStyle == null)
            cellStyle = def_left;
        cell.setCellStyle(cellStyle);
        return cell;
    }
    
    /**
     * 
     * @param type: 1:styleLeft3,2:styleRight3, 3:hBoldLeft,4:hBoldRight,5:headerBold12
     * @return
     */
    public HSSFCellStyle getCellStyle(int type)
    {
        HSSFCellStyle style;
        switch (type)
        {
            case 1:
                style = def_left;
                break;
            case 2:
                style = def_right;
                break;
            case 3:
                style = def_center;
                break;
            case 4:
                style = def_hBoldLeft;
                break;
            case 5:
                style = def_hBoldRight;
                break;
            case 6:
                style = def_hBoldCenter;
                break;
            case 7:
                style = header_center;
                break;
            default:
                style = def_left;
                break;
        }
        
        return style;
    }
    
    /**
     * init cell styles
     */
    protected void initCellStyle()
    {
        //      HSSFPalette palette = workbook.getCustomPalette(); // wb HSSFWorkbook
        //      palette.setColorAtIndex((short) 9, (byte) (181), (byte) (193),(byte) (230));
        blackfont = workbook.createFont();
        blackfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        blackfont.setColor(IndexedColors.BLACK.index);
        blackfont.setFontHeightInPoints((short) 10);
        
        whitefont = workbook.createFont();
        whitefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        whitefont.setColor(IndexedColors.WHITE.index);
        whitefont.setFontHeightInPoints((short) 10);
        
        // font bold black 10
        def_hBoldLeft = workbook.createCellStyle();
        def_hBoldLeft.setVerticalAlignment((short) 0);
        def_hBoldLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        def_hBoldLeft.setFont(whitefont);
        def_hBoldLeft.setWrapText(true);
        
        def_hBoldRight = workbook.createCellStyle();
        def_hBoldRight.setVerticalAlignment((short) 0);
        def_hBoldRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        def_hBoldRight.setFont(whitefont);
        def_hBoldRight.setWrapText(true);
        
        def_hBoldCenter = workbook.createCellStyle();
        def_hBoldCenter.setVerticalAlignment((short) 0);
        def_hBoldCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        def_hBoldCenter.setFont(whitefont);
        def_hBoldCenter.setWrapText(true);
        
        // bottom black 3
        def_left = workbook.createCellStyle();
        def_left.setBottomBorderColor(IndexedColors.BLACK.index);
        
        // style black right 3
        def_right = workbook.createCellStyle();
        def_right.setBottomBorderColor(IndexedColors.BLACK.index);
        def_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        def_right.setWrapText(true);
        
        def_center = workbook.createCellStyle();
        def_center.setBottomBorderColor(IndexedColors.BLACK.index);
        def_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        def_center.setWrapText(true);
        
        def_center_b = workbook.createCellStyle();
        def_center_b.setBottomBorderColor(IndexedColors.BLACK.index);
        def_center_b.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        def_center_b.setWrapText(true);
        def_center_b.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
        def_center_b.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        // header center
        header_center = workbook.createCellStyle();
        header_center.setBottomBorderColor(IndexedColors.BLACK.index);
        header_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        header_center.setVerticalAlignment((short) 1);
        //header_center.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //  header_center.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // header_center.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //  header_center.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //  header_center.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // header_center.setFillForegroundColor((short) 9); // HSSFColor.LIGHT_CORNFLOWER_BLUE.index
        header_center.setWrapText(true);
        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.LIGHT_BLUE.index, (byte) 180, (byte) 198, (byte) 231);
    }
    
    public void setupPrint(HSSFSheet sheet, int columnBreak)
    {
        if (columnBreak > 0)
        {
            sheet.setColumnBreak(columnBreak);
        }
        
        sheet.getPrintSetup().setLandscape(true);
        sheet.getPrintSetup().setVResolution((short) 600);
        
        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
    }
    
    /**
     * get instance of excelreportUtil
     * @param baseReportName
     * @param dateFormat
     * @return
     */
    public static Excel2003Util getReportInstance(String baseReportName,
            String dateFormat)
    {
        return new Excel2003Util(baseReportName, dateFormat);
    }
    
    public static void main(String[] args)
    {
        //demo of excel report.
        //step 1: create excel and get instance.
        Excel2003Util report = Excel2003Util.getReportInstance("demoReport",
                "yyyy-MM-dd");
        //step 2: create sheet and setup printer
        report.createSheet("sheet1", 4);
        
        //step 3: create excel title.
      //  report.createTitle("Chanel Stock Report", 0, new CellRangeAddress(0, 0,  0, 4));
        
        //step 4: create other header part.
        //todo other especial part.
        /**
        HSSFRow row = report.createRow();
        report.createNomalCell(row, 0, "value", report.getCellStyle(1));
        report.createRangeCell(row,
                new CellRangeAddress(0, 0, 0, 2),
                0,
                "value",
                null);
        **/
        //step 5: create table
        
        //step 5-1: create table header
        report.createTableHeader(new String[] { "MAWB NO", "DES", "PCS", "DATE" },
                new int[] { 4, 4, 4, 4 },
                new int[] { 40, 40, 50, 60 });
        
        //step 5-2:create table content
        report.createTableContentRow(new String[] { "MAWB NO", "DES", "PCS",
                "DATE" }, new int[] { 0, 0, 0, 0 });
        report.createTableContentRow(new String[] { "MAWB NO", "DES", "PCS",
        "DATE" }, new int[] { 0, 0, 0, 0 });
        report.createTableContentRow(new String[] { "MAWB NO", "DES", "PCS",
        "DATE" }, new int[] { 0, 0, 0, 0 });
        report.createTableContentRow(new String[] { "MAWB NO", "DES", "PCS",
        "DATE" }, new int[] { 0, 0, 0, 0 });
        report.createTableContentRow(new String[] { "MAWB NO", "DES", "PCS",
        "DATE" }, new int[] { 0, 0, 0, 0 });
        
        //get report name
        report.getReportName();
        
        //step 6: get excel
        //HSSFWorkbook workbook = report.getExcelReport();
        //or step 6:
        //      report.writeReportToClient(response, excelName);
        try
        {
           File f = new File("C:\\09_Download\\facility\\demo2.xls");
           FileOutputStream fos = new FileOutputStream(f);
           report.getExcelReport().write(fos);
        }
        catch (IOException e)
        {
            
        }
        
        //demo2: 
        /*prepare parameter:
        def_sheetName="Stock Report";
        def_titleName = "";
        def_cellRanges=2;
        def_headers= new String[] ();
        def_headerAlign= new int[] { 3, 3, 3, 4, 3, 3, 3, 3, 3, 4, 4 };
        def_headerWidthnew= new int[] { 10, 11, 13, 8, 13, 13, 23, 11, 8, 10,
                        10 };
        def_cellLeft = def_left;
        def_cellRight =def_right;
        */
        
        ArrayList<String[]> dataList = new ArrayList<String[]>();
        for (String[] str : dataList)
        {
            report.createNewSheetRow(str);
        }
    }
    
    public void writeReportToClient(HttpServletResponse response,
            String excelName)
    {
        try
        {
            if (workbook == null)
            {
                workbook = new HSSFWorkbook();
            }
            response.setContentType("multipart/form-data;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + excelName + ";filename*=utf-8''"
                            + URLEncoder.encode(excelName, "UTF-8"));
            
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            
        }
    }
}
