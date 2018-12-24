package com.darren.machine.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.darren.machine.util.Constants;

public class BaseInfoEntity
{
    private String datasource_name; 
    private String variables;
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private Date searchDate;
   
    
    public BaseInfoEntity()
    {
        super();
    }


    public BaseInfoEntity(String datasource_name, String variables)
    {
        super();
        this.datasource_name = datasource_name;
        this.variables = variables;
    }
    

    public BaseInfoEntity(Date searchDate)
    {
        this.searchDate = searchDate;
    }



    public String getDatasource_name()
    {
        return datasource_name;
    }

    public void setDatasource_name(String datasource_name)
    {
        this.datasource_name = datasource_name;
    }

    public String getVariables()
    {
        return variables;
    }

    public void setVariables(String variables)
    {
        this.variables = variables;
    }

    public Date getSearchDate()
    {
        return searchDate;
    }

    public void setSearchDate(Date searchDate)
    {
        this.searchDate = searchDate;
    }


    
    
}
