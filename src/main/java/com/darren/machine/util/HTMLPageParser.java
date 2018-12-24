package com.darren.machine.util;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.darren.machine.domain.AirlineEntity;
import com.darren.machine.domain.AirlineRuleEntity;
import com.darren.machine.domain.AirlineSourceEntity;

public class HTMLPageParser {
    private static final Logger log = LoggerFactory.getLogger(HTMLPageParser.class);
    public static void main(String[] args) throws Exception {
       // String logURL = "http://localhost:8080/flightDynamicAction";
        String logURL = "https://www.jianshu.com/p/654895d0e254";
        
      //  System.out.println(LocalTime.parse("24:52").isBefore(LocalTime.parse("23:20")));
        LocalDateTime t1= LocalDateTime.now();
        LocalDateTime t2= LocalDateTime.now();
        t2=t2.plusDays(1);
        System.out.println(t1.isBefore(t2));
       System.out.println(t1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
       System.out.println(t2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        /**
        Connection.Response  res = 
                Jsoup.connect(logURL)
                    .data("mod","logging"
                            ,"action","login"
                            ,"loginsubmit","yes"
                            ,"loginhash","Lsc66"
                            ,"username","puqiuxiaomao"
                            ,"password","a1234567")
                    .method(Method.POST)
                    .execute();
        
        **/
        
        //File in = new File("C:\\11_Darren\\DHData\\20181107\\20181107.htm");

       // Document conTemp = Jsoup.parse(in, "UTF-8", "");
                
        Document conTemp = Jsoup.connect(logURL).get();
        //Document conTemp = Jsoup.parse(new URL(logURL).openStream(), "GBK", logURL);
      //  Elements elementsTemps = conTemp.getAllElements();
       // conTemp.getElementsByTag("iframe").get(1).children().getElementsByTag("table").getElementsByClass("aoc_table");
        List<Element> list = conTemp.getElementsByClass("aoc_table");
        
        
        for (Element elementsTemp :  list) {
            for (Element tmp :  elementsTemp.getElementsByTag("tr")) {
                for (Element tdtmp :  tmp.getElementsByTag("td")) {
                    System.out.printf(tdtmp.text()+"  ");
                }
                System.out.println("--------------");
            }
            System.out.println("****** test data ********");
        }
        
    }
    
    public static List<AirlineEntity> parseAirlineUrl(AirlineSourceEntity source,AirlineRuleEntity rule,Date date){
        List<AirlineEntity> entities = new  ArrayList<AirlineEntity>();
        try
        {
            String prefix="";
            if (rule != null && !StringUtils.isEmpty(rule.getRule())){
                prefix = rule.getRule().replace("%","").replace("*", "");
            }
            String address=source.getFullAddress();
            if(!StringUtils.isEmpty(address) && address.indexOf("yyyyMMdd")>0){
                address = address.replace("yyyyMMdd",  new SimpleDateFormat("yyyyMMdd").format(date ) );
                address +="&historyDate="+new SimpleDateFormat("yyyyMMdd").format(date );
            }else{
                address +="&historyDate="+new SimpleDateFormat("yyyyMMdd").format(date );
            }
            Document conTemp = Jsoup.parse(new URL(address).openStream(), source.getEncode(), source.getFullAddress());
           List<Element> list = conTemp.getElementsByClass("aoc_table");
           if(list != null && list.size()>0){
               getAirlines(entities, list, date,prefix);
           }
           // String body = conTemp.select("iframe").first().text();
           List<Element> frameList= conTemp.select("frame");
           if(frameList != null && frameList.size()>0){
               for(Element e: frameList){
                   Document ifmDoc = Jsoup.parseBodyFragment(e.text());
                   list = ifmDoc.getElementsByClass("aoc_table");
                   if(list != null && list.size()>0)
                       entities = getAirlines(entities, list, date,prefix);
               }
           }
        }
         catch(Exception e){
             log.error(e.getMessage());
         }
         
         return groupAirline(entities);
    }
    
    public static List<AirlineEntity> getAirlines(List<AirlineEntity> entities,  List<Element> list,Date date,String prefix){
        try{
        for (Element elementsTemp :  list) {
            for (Element tmp :  elementsTemp.getElementsByTag("tr")) {
                int i=0;
                AirlineEntity entity = null;
                for (Element tdtmp :  tmp.getElementsByTag("td")) {
                    if(entity == null) entity = new AirlineEntity();
                    switch(i) {
                        case 0:
                            entity.setDevice_number(tdtmp.text());
                            break;
                        case 1:
                            if(StringUtils.isEmpty(tdtmp.text())) break;
                            entity.setFlight_number(tdtmp.text().replaceAll("#", "").replaceAll("&", ""));
                            break;
                        case 2:
                            break;
                        case 3:
                            entity.setTake_off_loc(tdtmp.text());
                            break;
                        case 4:
                            if(StringUtils.isEmpty(tdtmp.text()))break;
                            entity.setTake_off_time(LocalTime.parse(tdtmp.text()));
                            break;
                        case 5:
                            if(StringUtils.isEmpty(tdtmp.text()))break;
                            entity.setTake_off_time(LocalTime.parse(tdtmp.text()));
                            break;    
                        case 6:
                            if(StringUtils.isEmpty(tdtmp.text()))break;
                            entity.setTake_off_time(LocalTime.parse(tdtmp.text()));
                            break;
                        case 10:
                            entity.setLanding_loc(tdtmp.text());
                           break;
                        case 11:
                            if(StringUtils.isEmpty(tdtmp.text()))break;
                            entity.setLanding_time(LocalTime.parse(tdtmp.text()));
                            break;
                        case 12:
                            if(StringUtils.isEmpty(tdtmp.text()))break;
                            entity.setLanding_time(LocalTime.parse(tdtmp.text()));
                            break;    
                        case 13:
                            if(StringUtils.isEmpty(tdtmp.text()))break;
                            entity.setLanding_time(LocalTime.parse(tdtmp.text()));
                            break;
                        default:
                        break;
                    }
                    i++;
                  //  System.out.printf(tdtmp.text()+"  ");
                }
                if(entity != null && entity.getFlight_number().startsWith(prefix)){
                    entity.setAirline_date(date);
                    entities.add(entity);
                }
             //   System.out.println("--------------");
            }
           // System.out.println("****** test data ********");
        }
    }
    catch (Exception e)
    {
        log.error(e.getMessage());
    }
    
    return entities;
    }
    
    public static List<AirlineEntity> groupAirline(List<AirlineEntity> entities){
        if(entities.size() == 0 ) return entities;
        List<AirlineEntity> newList = new ArrayList<AirlineEntity>();
        Map<String, List<AirlineEntity>> map = new HashMap<String, List<AirlineEntity>>();
        for(AirlineEntity entity: entities){
            if (map.containsKey(entity.getDevice_number())){
                map.get(entity.getDevice_number()).add(entity);
            }else{
                List<AirlineEntity> list =  new ArrayList<AirlineEntity>();
                list.add(entity);
                map.put(entity.getDevice_number(), list);
            }
        }
        
        for(Entry<String,List<AirlineEntity>> entry: map.entrySet()){
                newList.addAll(  calculateType( entry.getValue() ) );
        }
        
        
        
        return newList;
    }
    
    public static List<AirlineEntity> calculateType(List<AirlineEntity> entities){
        AirlineEntity earlyOne = null, latestOne= null;
//        LocalTime time;

        
        for(AirlineEntity entity: entities){
             if("南京".equals(entity.getTake_off_loc()) ){
                if(earlyOne == null ||earlyOne.getTake_off_time().isAfter(entity.getTake_off_time())){
                    earlyOne = entity;
                }
             }
             
             if ("南京".equals(entity.getLanding_loc())){
                 entity.setActual_datetime(LocalDateTime.of(LocalDate.now(), entity.getLanding_time()));
                 if(entity.getLanding_time().isBefore(LocalTime.parse("05:00"))){
                     entity.setActual_datetime( entity.getActual_datetime().plusDays(1) );
                 }
                 if(latestOne == null ){
                     latestOne = new AirlineEntity();
                     latestOne.setFlight_number(entity.getFlight_number());
                     latestOne.setLanding_time(entity.getLanding_time());
                     latestOne.setActual_datetime(entity.getActual_datetime());
                 }else {
                    
                     
                     if(latestOne.getActual_datetime().isBefore(entity.getActual_datetime())){
                         latestOne = new AirlineEntity();
                         latestOne.setFlight_number(entity.getFlight_number());
                         latestOne.setLanding_time(entity.getLanding_time());
                         latestOne.setActual_datetime(entity.getActual_datetime());
                     }
                 }
             
             }
        }
        
        
        for(AirlineEntity entity: entities){
            if(earlyOne != null && entity.getFlight_number().equals(earlyOne.getFlight_number())
                    && "南京".equals(entity.getTake_off_loc())){
                entity.setType("航前清洁");
            }else if(latestOne != null && entity.getFlight_number().equals(latestOne.getFlight_number())
                    && "南京".equals(entity.getLanding_loc())){
                entity.setType("航后清洁");
            }else{
                entity.setType("过站清洁");
            }
        }
        return entities;
    }
}

