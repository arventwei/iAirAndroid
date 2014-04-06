package com.txmcu.iair.adapter;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Device  implements Serializable {

	private static final long	serialVersionUID	= 7973999356736512441L;

	private static int imaxid = 0;
	public String id;
	
	public String sn="";
	public String vsn="";
	public String name="";
	
	public String  temp;
	public String  humi;
	public String  pm25;
	public String  pa;
	
	public int  	switchOn;
	public int     	speed;
	public int    	lastUpdateStamp;
	
	public Boolean share=false;
	public Boolean isVirtual=false;
	public String refresh_interval = "30";
	public String status="0";
	public String mode;
	public String ontime;
	public String offtime;
	public int sortseq;

	//public int sortseq;
	
	public Device() {
		//this.id = imaxid++;
		//this.pm25 = 20;
		//this.temp = 20;
		//this.pa = 8;
		//this.humi = 15;
	}
	public Device(String sn) {
		//this.id = imaxid++;
		this.sn = sn;
		//this.pm25 = 234;
		//this.temp = 20;
		//this.pa = 8;
		//this.humi = 15;
	}
	
	
//	public String ToJson()
//	{
//		try 
//		{
//
//	        JSONObject jsonObj = new JSONObject();
//	        jsonObj.put("sn", sn);
//	        jsonObj.put("name",name);
//	        jsonObj.put("temp",temp);
//	        jsonObj.put("humi",humi);
//	        jsonObj.put("pm25",pm25);
//	        jsonObj.put("pa",pa);
//	        jsonObj.put("switchOn",switchOn);
//	        jsonObj.put("speed",speed);
//	        jsonObj.put("lastUpdateStamp",lastUpdateStamp);
//        
//	        return jsonObj.toString();
//
//		}
//		catch(JSONException ex) {
//		   ex.printStackTrace();
//		}
//		return null;
//	}
//	public void FromJson(String data)
//	{
//		try 
//		{
//			JSONObject jObj = new JSONObject(data);
//			sn = jObj.getString("sn");
//			name = jObj.getString("name");
//			temp = jObj.getDouble("temp");
//			humi = jObj.getDouble("humi");
//			pm25 = jObj.getDouble("pm25");
//			pa = jObj.getDouble("pa");
//			
//			switchOn = jObj.getInt("switchOn");
//			speed = jObj.getInt("speed");
//			lastUpdateStamp = jObj.getInt("lastUpdateStamp");
//
//		}
//		catch(JSONException ex) {
//		   ex.printStackTrace();
//		}
//	}
//	
//
//	/**
//	 * @return the id
//	 */
//	public Integer getId() {
//		return id;
//	}

	
	
}
