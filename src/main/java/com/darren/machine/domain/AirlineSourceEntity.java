package com.darren.machine.domain;

import org.apache.commons.lang3.StringUtils;

public class AirlineSourceEntity extends BaseEntity
{
    private String url;
    private String url2;
    private String param;
    private String encode;
    
    public AirlineSourceEntity(){
        
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getParam()
    {
        return param;
    }

    public void setParam(String param)
    {
        this.param = param;
    }
    
    public String getFullAddress(){
        String address =this.url2;
        if(address.indexOf("index.do")>=0){
            address = address.replaceAll("index.do", "flightDynamicAction.do");
        }else if(address.indexOf("history.do")>=0){
            address = address.replaceAll("history.do", "flightDynamicHistoryAction.do");
        }
        
        if(StringUtils.isEmpty(this.param)){
            //nothing to do 
        }
        else if (this.url2.endsWith("?")|| this.param.startsWith("?")){
            address +=this.param;
        }else {
            address +="?"+ this.param;
        }
        
        return address;
    }

    public String getEncode()
    {
        return encode;
    }

    public void setEncode(String encode)
    {
        this.encode = encode;
    }

    public String getUrl2()
    {
        return url2;
    }

    public void setUrl2(String url2)
    {
        this.url2 = url2;
    }
    
}
