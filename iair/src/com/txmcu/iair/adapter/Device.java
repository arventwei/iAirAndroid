package com.txmcu.iair.adapter;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Device  implements Serializable {

	private static final long	serialVersionUID	= 7973999356736512441L;

	private Integer id;
	
	private String sn="";
	
	private String name="";
	
	public float  temp;
	public float  humi;
	public float  pm25;
	
	public int  	switchOn;
	public int     	speed;
	public int    	lastUpdateStamp;
	
	
	
	public String ToJson()
	{
		try 
		{

	        JSONObject jsonObj = new JSONObject();
	        jsonObj.put("sn", sn);
	        jsonObj.put("temp",temp);
        
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
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
