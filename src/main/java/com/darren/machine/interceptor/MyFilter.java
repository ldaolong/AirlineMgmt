package com.darren.machine.interceptor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.darren.machine.util.PropertiesListenerConfig;
import com.darren.machine.util.SecurityModel;

@Component
@WebFilter(urlPatterns = "/Blogs",filterName = "myfilter")
public class MyFilter  implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
       //System.out.printf("过滤器实现");
        if("/".equals(request.getRequestURI()) ||
                request.getRequestURI().contains("/js/") ||
                request.getRequestURI().contains("/css/") ||
                request.getRequestURI().contains("/navbar") ||
                request.getRequestURI().contains("/footer")){
            filterChain.doFilter(request,response);  
        }  else if(!hasLicense()){
            RequestDispatcher rd = request.getRequestDispatcher("license");
            rd.forward(request, response);
            return;
        }else{
            filterChain.doFilter(request,response);  
        }
        
       // Object user=  request.getSession().getAttribute(Constants.SESSION_USER);
       // if(user == null){
           // RequestDispatcher rd = request.getRequestDispatcher("login");
            //rd.forward(request, response);
            //return;
       // }
        
    }

    public boolean hasLicense(){
        boolean has = false;
        
        try
        {
            String dateline =  SecurityModel.decrypt(PropertiesListenerConfig.getProperty("dateline"), "test");
            if(StringUtils.isNotEmpty(dateline) && dateline.length()>12){
                LocalDate date = LocalDate.parse(dateline.substring(4, 12), DateTimeFormatter.ofPattern("yyyyMMdd"));
                if(LocalDate.now().isBefore(date)){
                    has = true;
                }
            }
        }
        catch (Exception e)
        {
        }
        
        return has;
    }
    @Override
    public void destroy() {

    }
}
