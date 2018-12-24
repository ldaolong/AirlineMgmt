package com.darren.machine.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PageImpl<T>
{
   
    private Long total;
    private List<T> rows;
    
    private int offset;// start row
    private int limit; // page rows
   
    public PageImpl(String offset, String limits)
    {
        super();
        if(!StringUtils.isEmpty(offset))
        this.offset = Integer.valueOf(offset);
        if(!StringUtils.isEmpty(limits)){
            this.limit = Integer.valueOf(limits);;
        }else{
            this.limit = 10;
        }
       
    }

    public Long getTotal()
    {
        return total;
    }

    public void setTotal(Long total)
    {
        this.total = total;
    }

    public List<T> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }
    
    public void setPageRows(List<T> rows){
        if (rows == null) return;
        this.total= (long)rows.size();
        
       if(offset+limit>this.total){
           this.rows = rows.subList(offset, rows.size());
       } else{
           this.rows = rows.subList(offset, offset+limit);
       }
    
    }
}
