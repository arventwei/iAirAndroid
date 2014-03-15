package com.txmcu.iair.common;

import org.json.JSONException;
import org.json.JSONObject;

public class XiaoXinInfoModel {
	public String sn;
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
}
