package com.darren.machine.domain;

public class EChartObject
{
    private int value;
    private String name;
    
    public EChartObject()
    {
    }
    public EChartObject( String name,int value)
    {
        this.value = value;
        this.name = name;
    }
    public int getValue()
    {
        return value;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    
    
}
