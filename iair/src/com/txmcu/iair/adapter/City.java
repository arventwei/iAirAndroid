package com.txmcu.iair.adapter;

import java.io.Serializable;

public class City  implements Serializable {

	private static final long	serialVersionUID	= 7973994356736512440L;


	public  City(String id)
	{
		areaId = id;
	}
	public String  areaId;
	public String  name;
	
	public String  temp;
	public String temp_info;
	public String  humi;
	public String  pm25;
	public String wind_info;
	public String wind_speed;
	public String today_car_limit;
	public String tmr_car_limit;
	public String    aqi;
	public String weather;
	public String weather_level;
	public int sortseq;
	
	
	public String aqi_us;
	public String datestr;
	public String weekstr;
	public String datecn;	
	
	
	
}
