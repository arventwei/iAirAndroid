package com.txmcu.iair.adapter;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Home  implements Serializable {

	private static final long	serialVersionUID	= 7979994356736512440L;
	private static int imaxid = 0;
	
	public Home() {
		this.id = imaxid++;
		
	}
	public Home(String sn) {
		this.id = imaxid++;
		this.sn = sn;
		
	}
	
	public Integer id;
	
	public String sn="";
	//private String title;
	
	public String name;
	
	public String ToJson()
	{
		try 
		{

	        JSONObject jsonObj = new JSONObject();
	        jsonObj.put("sn", sn);
	        jsonObj.put("name",name);
	     
        
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
			

		}
		catch(JSONException ex) {
		   ex.printStackTrace();
		}
	}


	
}
