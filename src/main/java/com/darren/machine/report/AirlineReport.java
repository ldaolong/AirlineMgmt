package com.darren.machine.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.darren.machine.domain.AirlineEntity;
import com.darren.machine.service.AirlineService;
import com.darren.machine.util.AbstractReportUtil;
import com.darren.machine.util.Constants;
import com.darren.machine.util.Excel2003Util;
import com.darren.machine.util.Excel2007Util;

public class AirlineReport
{
    
    protected AbstractReportUtil report;
    
    public AirlineReport()
    {
        super();
    }
    
    public AirlineReport(String file_suffix, String reportName)
            throws Exception
    {
        if ("XLS".equalsIgnoreCase(file_suffix))
            this.report = Excel2003Util.getReportInstance(reportName,
                    "yyyyMMdd");
        else if ("XLSX".equalsIgnoreCase(file_suffix))
        {
            this.report = Excel2007Util.getReportInstance(reportName,
                    "yyyyMMdd");
        }
        else
        {
            throw new Exception("do not support this type: " + file_suffix);
        }
    }
    
    public void generateReport(List<AirlineEntity> list)
    {
        //step 1: create sheet and setup printer
        report.createSheet("sheet1", 7);
        
        //step 2-1: create table header
        report.createTableHeader(new String[] { "机号", "航班号", "起飞", "实际起飞",
                "降落", "实际到达", "类型", "日期" },
                new int[] { 6, 6, 6, 6, 6, 6, 6, 6 },
                new int[] { 10, 11, 20, 10, 20, 10, 12, 12 });
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int[] align = new int[] { 3, 3, 3, 3, 3, 3, 3, 3 };
        //step 2-2:create table content
        for (AirlineEntity a : list)
        {
            report.createTableContentRow(new String[] {
                    a.getDevice_number(),
                    a.getFlight_number(),
                    a.getTake_off_loc(),
                    a.getTake_off_time()
                            .format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)),
                    a.getLanding_loc(),
                    a.getLanding_time()
                            .format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)),
                    a.getType(), sdf.format(a.getAirline_date()) },
                    align);
        }
        
    }
    
    public void outToClient(HttpServletResponse response)
    {
        report.writeReportToClient(response, report.getReportName());
    }
    
    public static void main(String args[]) throws Exception
    {
        AirlineReport report = new AirlineReport("xls", "xxsdd");
        report.generateReport(new AirlineService().mockData());
        
        try
        {
            File f = new File("C:\\tmp\\Hello1.xls");
            OutputStream out = new FileOutputStream(f);
            
            report.report.getExcelReport().write(out);
        }
        catch (IOException e)
        {
        }
    }
}
