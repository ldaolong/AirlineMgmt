package com.darren.machine.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.darren.machine.util.Constants;

public class AirlineEntity extends BaseEntity implements Serializable
{
    private String device_number;
    private String flight_number;
    private String take_off_loc;
    private LocalTime take_off_time;
    private String landing_loc;
    private LocalTime landing_time;
    private String type;
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private Date airline_date;
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private Date date_from;
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private Date date_to;
    
    private LocalDateTime actual_datetime;
    
    
    public LocalDateTime getActual_datetime()
    {
        return actual_datetime;
    }

    public void setActual_datetime(LocalDateTime actual_datetime)
    {
        this.actual_datetime = actual_datetime;
    }

    private int air_cnt;
    
    public AirlineEntity(){
        
    }
    
    public AirlineEntity(String device_number, String flight_number,
            String take_off_loc, LocalTime take_off_time, String landing_loc,
            LocalTime landing_time, String type)
    {
        super();
        this.device_number = device_number;
        this.flight_number = flight_number;
        this.take_off_loc = take_off_loc;
        this.take_off_time = take_off_time;
        this.landing_loc = landing_loc;
        this.landing_time = landing_time;
        this.type = type;
    }

    public AirlineEntity(String device_number, String flight_number,
            String take_off_loc, LocalTime take_off_time, String landing_loc,
            LocalTime landing_time, String type, Date airline_date)
    {
        super();
        this.device_number = device_number;
        this.flight_number = flight_number;
        this.take_off_loc = take_off_loc;
        this.take_off_time = take_off_time;
        this.landing_loc = landing_loc;
        this.landing_time = landing_time;
        this.type = type;
        this.airline_date = airline_date;
        this.createdAt = new Date();
        this.createdBy="Darren";
        this.updatedAt = new Date();
        this.updatedBy="Darren";
    }

    public String getDevice_number()
    {
        return device_number;
    }
    public void setDevice_number(String device_number)
    {
        this.device_number = device_number;
    }
    public String getFlight_number()
    {
        return flight_number;
    }
    public void setFlight_number(String flight_number)
    {
        this.flight_number = flight_number;
    }
    public String getTake_off_loc()
    {
        return take_off_loc;
    }
    public void setTake_off_loc(String take_off_loc)
    {
        this.take_off_loc = take_off_loc;
    }
    public LocalTime getTake_off_time()
    {
        return take_off_time;
    }
    public void setTake_off_time(LocalTime take_off_time)
    {
        this.take_off_time = take_off_time;
    }
    public void setTake_off_time_str(String take_off_time)
    {
        if (StringUtils.isEmpty(take_off_time)) return;
        
        this.take_off_time =LocalTime.parse(take_off_time);
    }    
    public String getLanding_loc()
    {
        return landing_loc;
    }
    public void setLanding_loc(String landing_loc)
    {
        this.landing_loc = landing_loc;
    }
    public LocalTime getLanding_time()
    {
        return landing_time;
    }
    public void setLanding_time(LocalTime landing_time)
    {
        this.landing_time = landing_time;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public Date getAirline_date()
    {
        return airline_date;
    }

    public void setAirline_date(Date airline_date)
    {
        this.airline_date = airline_date;
    }

    public Date getDate_from()
    {
        return date_from;
    }

    public void setDate_from(Date date_from)
    {
        this.date_from = date_from;
    }

    public Date getDate_to()
    {
        return date_to;
    }

    public void setDate_to(Date date_to)
    {
        this.date_to = date_to;
    }

    public int getAir_cnt()
    {
        return air_cnt;
    }

    public void setAir_cnt(int air_cnt)
    {
        this.air_cnt = air_cnt;
    }

}
