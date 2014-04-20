package com.txmcu.iair.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.City;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.Home;

/**
 * 全局变量信息保存的地方
 * 得到用户名，昵称等等信息
 */
public class iAirApplication extends Application {
	
	

	
	
	
	private static final String Userid="userid";

	//private List<String> xiaoxinList = new ArrayList<String>();
	
	private static final String xiaoxinlist_size="xiaoxinlist_size";
	private static final String xiaoxinlist="xiaoxinlist_";
	
	private static final String homelist_size="homelist_size";
	private static final String homelist="homelist_";
	
	SharedPreferences mPerferences;
	
	public void onCreate()
	{
	  super.onCreate();
	  mPerferences = PreferenceManager.getDefaultSharedPreferences(this); 
	  String areaIdNameString = getFromRaw();
	  
	  try {
		  cityIdNameList.clear();
			JSONObject jsonObject = new JSONObject( areaIdNameString);
			JSONArray jArray = jsonObject.getJSONArray("area");
			for (int i = 0; i < jArray.length(); i++) {

				
				JSONObject obj  = jArray.getJSONObject(i);
				String area_id = obj.getString("area_id");
				String area_name = obj.getString("area_name");
				AreaIdName aName = new AreaIdName();
				aName.areaId = area_id;
				aName.cityName = area_name;
				cityIdNameList.add(aName);
				
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	  
	}
	  
	public String getAreaIdbyName(String name)
	{
		//name = name.replace("", newChar)
		for (AreaIdName anAreaIdName : cityIdNameList) {
			if (anAreaIdName.cityName.equals(name)) {
				return anAreaIdName.areaId;
			}
		}
		return "";
	}
	class AreaIdName
	{
		public String areaId;
		public String cityName;
	}
	List<AreaIdName> cityIdNameList= new ArrayList<AreaIdName>();
	////
	private Boolean isInitedBoolean = false;
	
	private static final String ENCODING = "UTF-8"; 

    //从resources中的raw 文件夹中获取文件并读取数据  
	private String getFromRaw(){  
        String result = "";  
            try {  
                InputStream in = getResources().openRawResource(R.raw.cityid);  
                //获取文件的字节数  
                int lenght = in.available();  
                //创建byte数组  
                byte[]  buffer = new byte[lenght];  
                //将文件中的数据读到byte数组中  
                in.read(buffer);  
                result = EncodingUtils.getString(buffer, ENCODING);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return result;  
    } 
	////
	
	public void setUserid(String userid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(Userid,userid);  
	    mEditor.commit(); 
	}
	public String getUserid() {
		return mPerferences.getString(Userid, "");
	}
	
public void setNickName(String nickName) {
		
	SharedPreferences.Editor mEditor = mPerferences.edit();  
	
	mEditor.putString("nickName",nickName);
	
    mEditor.commit(); 
	
	}
	public String getNickName()
	{

		String nickName = mPerferences.getString("nickName", "");
	
		return nickName;
	}
	
	/// new
	public List<Home> homeList = new ArrayList<Home>();
	public List<City> cityList = new ArrayList<City>();
	
	
	public Home getHome(String homeid)
	{
		for (Home home : homeList) {
			if (home.homeid.equals(homeid)) {
				return home;
			}
		}
		return null;
	}
	public Device  getXiaoxin(String sn) {
		for (Home home : homeList) {
			for (Device device:home.xiaoxins) {
				if (device.sn.equals(sn)) {
					return device;
				}
			}
		}
		return null;
	}
	
	public void setBuyUrl(String url)
	{
		SharedPreferences.Editor mEditor = mPerferences.edit();  
		
		mEditor.putString("BuyUrl",url);
		
	    mEditor.commit(); 
	}
	public String getBuy()
	{
		//Home ret = new Home();
		String xiaxinJsonString = mPerferences.getString("BuyUrl", "http://www.baidu.com/");
		//ret.FromJson(xiaxinJsonString);
		return xiaxinJsonString;
		
	}
//	public void setHomeList(List<Home> _homeList) {
//		homeList = _homeList;
//	}
//	public List<Home> getHomeList() {
//		return homeList;
//	}
	
	//// old
	
	
//	public void setXiaoxinSnList(List<String> list) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//		
//		mEditor.putInt(xiaoxinlist_size,list.size());
//		for(int i=0;i<list.size();i++)
//		{
//			mEditor.putString(xiaoxinlist + i, list.get(i));//
//		}
//	    mEditor.commit(); 
//	}
//	public List<String> getXiaoxinSnList() {
//		List<String> xiaoxinList = new ArrayList<String>();
//		int size = mPerferences.getInt(xiaoxinlist_size, 0);
//		for(int i = 0; i < size;i++)
//		{
//			String sn = mPerferences.getString(xiaoxinlist + i, "");
//			xiaoxinList.add(sn);
//		}
//		return xiaoxinList;
//		//return mPerferences.getString(Userid, "");
//	}
//	public void removeXiaoxin(String sn) {
//		
//		List<String> snList = getXiaoxinSnList();
//		snList.remove(sn);
//		setXiaoxinSnList(snList);
//	
//	}
//	public void setXiaoxin(Device xiaoxin)
//	{
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//		
//		mEditor.putString("xiaoxin_"+xiaoxin.sn,xiaoxin.ToJson());
//		
//	    mEditor.commit(); 
//	}
//	public Device getXiaoxin(String sn)
//	{
//		Device ret = new Device();
//		String xiaxinJsonString = mPerferences.getString("xiaoxin_"+sn, "");
//		ret.FromJson(xiaxinJsonString);
//		return ret;
//		
//	}
	
	///>home
//	public void setHomeSnList(List<String> list) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//		
//		mEditor.putInt(homelist_size,list.size());
//		for(int i=0;i<list.size();i++)
//		{
//			mEditor.putString(homelist + i, list.get(i));//
//		}
//	    mEditor.commit(); 
//	}
//	public List<String> getHomeSnList() {
//		List<String> homeList = new ArrayList<String>();
//		int size = mPerferences.getInt(homelist_size, 0);
//		for(int i = 0; i < size;i++)
//		{
//			String sn = mPerferences.getString(homelist + i, "");
//			homeList.add(sn);
//		}
//		return homeList;
//		//return mPerferences.getString(Userid, "");
//	}
//	public void removeHome(String sn) {
//		
//		List<String> snList = getHomeSnList();
//		snList.remove(sn);
//		setHomeSnList(snList);
//	
//	}
//	public void setHome(Home home)
//	{
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//		
//		mEditor.putString("home_"+home.sn,home.ToJson());
//		
//	    mEditor.commit(); 
//	}
//	public Home getHome(String sn)
//	{
//		Home ret = new Home();
//		String xiaxinJsonString = mPerferences.getString("home_"+sn, "");
//		ret.FromJson(xiaxinJsonString);
//		return ret;
//		
//	}
	///<
	
	///backup wifi info
	
	private static final String WifibackupNetId="WifibackupNetId";
	//private static final String WifibackupSSID="WifibackupSSID";
	//private static final String WifibackupChannel="WifibackupChannel";
	//private static final String WifibackupPwd="WifibackupPwd";
	//private static final String WifibackupAuthMode="WifibackupAuthMode";
	//private static final String WifibackupEncrypType="WifibackupEncrypType";
	
	public void setWifibackupNetId(int netid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(WifibackupNetId,netid);  
	    mEditor.commit(); 
	}
	public int getWifibackupNetId() {
		return mPerferences.getInt(WifibackupNetId, 0);
	}
	
//	public void setWifibackupSSID(String ssid) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putString(WifibackupSSID,ssid);  
//	    mEditor.commit(); 
//	}
//	public String getWifibackupSSID() {
//		return mPerferences.getString(WifibackupSSID, "");
//	}
//
//	public void setWifibackupPwd(String content) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putString(WifibackupPwd,content);  
//	    mEditor.commit(); 
//	}
//	public String getWifibackupPwd() {
//		return mPerferences.getString(WifibackupPwd, "");
//	}
//	public void setWifibackupChannel(String content) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putString(WifibackupChannel,content);  
//	    mEditor.commit(); 
//	}
//	public String getWifibackupChannel() {
//		return mPerferences.getString(WifibackupChannel, "");
//	}
//	
//	public void setWifibackupAuthMode(String content) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putString(WifibackupAuthMode,content);  
//	    mEditor.commit(); 
//	}
//	public String getWifibackupAuthMode() {
//		return mPerferences.getString(WifibackupAuthMode, "");
//	}
//	public void setWifibackupEncrypType(String content) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putString(WifibackupEncrypType,content);  
//	    mEditor.commit(); 
//	}
//	public String getWifibackupEncrypType() {
//		return mPerferences.getString(WifibackupEncrypType, "");
//	}
	///
//	
//	
//	
//	
//	public void setSimNo(int carid,String sim) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putString(simNo+carid,sim);  
//	    mEditor.commit(); 
//	}
//	public String getSimNo(int carid) {
//		return mPerferences.getString(simNo+carid, "");
//	}
	
	
	
}

