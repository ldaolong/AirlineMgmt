package com.darren.machine.util;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractReportUtil
{
    public  Date reportDate;
    public abstract void createSheet(String string, int i);

    public abstract void createTableHeader(String[] strings, int[] is, int[] is2);

    public abstract void createTableContentRow(String[] strings, int[] is);

    public abstract String getReportName();
    
    public abstract void writeReportToClient(HttpServletResponse response,
            String reportName);
    
    public abstract Workbook getExcelReport();
    
}
