package com.darren.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.darren.machine.interceptor.MyInterceptor;
import com.darren.machine.util.PropertiesListener;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//@MapperScan("com.darren.machine.dao")
public class StartApplication {


	public static void main(String[] args) {
//	    SpringApplication.addListeners(new PropertiesListener("app-config.properties"));
		SpringApplication.run(StartApplication.class, args);
	}

    //mvc控制器
    @Configuration
    static class WebMvcConfigurer extends WebMvcConfigurerAdapter{
        //增加拦截器
        public void addInterceptors(InterceptorRegistry registry){
            new PropertiesListener("app-config.properties").onApplicationEvent(null);
            registry.addInterceptor(new MyInterceptor())   //指定拦截器类
                    .addPathPatterns("/Handles");        //指定该类拦截的url
        }
    }
}
