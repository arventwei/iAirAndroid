package com.txmcu.iair.adapter;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Device  implements Serializable {

	private static final long	serialVersionUID	= 7973999356736512441L;

	private Integer id;
	
	private String sn="";
	
	private String name="";
	
	public double  temp;
	public double  humi;
	public double  pm25;
	
	public int  	switchOn;
	public int     	speed;
	public int    	lastUpdateStamp;
	
	
	
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

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	/**
	 * @param id the id to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
