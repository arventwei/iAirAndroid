package com.txmcu.iair.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 家详情信息
 * @author Administrator
 *
 */
public class Home  implements Serializable {

	private static final long	serialVersionUID	= 7979994356736512440L;
//	private static int imaxid = 0;
	
	public Home() {
	//	this.id = imaxid++;
		
	}
	public Home(String sn) {
	//	this.id = imaxid++;
		this.homeid = sn;
		
	}
	
	//public Integer id;
	
	public String homeid="";
	public String homename="";
	
	
	public String temp;
	public String humi;
	public String pm25;
	public String pa;
	
	public Boolean share=false;
	public String refresh_interval = "30";
	public String status="0";
	
	public String own;
	public String ownernickname;
	public int sortseq;
	
	
	public List<Device> xiaoxins = new ArrayList<Device>();
	public List<MessageVo> notices = new ArrayList<MessageVo>();
	
	
	public void removeXiaoxin(Device xx) {
	
	List<Device> snList = xiaoxins;
	snList.remove(xx);
	xiaoxins = snList;

}
	//	public String ToJson()
//	{
//		try 
//		{
//
//	        JSONObject jsonObj = new JSONObject();
//	        jsonObj.put("sn", sn);
//	        jsonObj.put("name",name);
//	     
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
//			
//
//		}
//		catch(JSONException ex) {
//		   ex.printStackTrace();
//		}
//	}


	
}
