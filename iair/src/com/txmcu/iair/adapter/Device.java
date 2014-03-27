package com.txmcu.iair.adapter;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

public class Device  implements Serializable {

	private static final long	serialVersionUID	= 7973999356736512441L;

	private static int imaxid = 0;
	private Integer id;
	
	public String sn="";
	
	public String name="";
	
	public double  temp;
	public double  humi;
	public double  pm25;
	public double  form;
	
	public int  	switchOn;
	public int     	speed;
	public int    	lastUpdateStamp;
	
	public Device() {
		this.id = imaxid++;
		this.pm25 = 20;
		this.temp = 20;
		this.form = 8;
		this.humi = 15;
	}
	public Device(String sn) {
		this.id = imaxid++;
		this.sn = sn;
	}
	
	
	public String ToJson()
	{
		try 
		{

	        JSONObject jsonObj = new JSONObject();
	        jsonObj.put("sn", sn);
	        jsonObj.put("name",name);
	        jsonObj.put("temp",temp);
	        jsonObj.put("humi",humi);
	        jsonObj.put("pm25",pm25);
	        jsonObj.put("form",form);
	        jsonObj.put("switchOn",switchOn);
	        jsonObj.put("speed",speed);
	        jsonObj.put("lastUpdateStamp",lastUpdateStamp);
        
	        return jsonObj.toString();

		}
		catch(JSONException ex) {
		   ex.printStackTrace();
		}
		return null;
	}
	public void FromJson(String data)
	{
		try 
		{
			JSONObject jObj = new JSONObject(data);
			sn = jObj.getString("sn");
			name = jObj.getString("name");
			temp = jObj.getDouble("temp");
			humi = jObj.getDouble("humi");
			pm25 = jObj.getDouble("pm25");
			form = jObj.getDouble("form");
			
			switchOn = jObj.getInt("switchOn");
			speed = jObj.getInt("speed");
			lastUpdateStamp = jObj.getInt("lastUpdateStamp");

		}
		catch(JSONException ex) {
		   ex.printStackTrace();
		}
	}
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	
	
}
