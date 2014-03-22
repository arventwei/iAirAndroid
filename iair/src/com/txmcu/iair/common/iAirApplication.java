package com.txmcu.iair.common;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.txmcu.iair.adapter.Device;

public class iAirApplication extends Application {
	
	

	
	private static final String Userid="userid";

	//private List<String> xiaoxinList = new ArrayList<String>();
	
	private static final String xiaoxinlist_size="xiaoxinlist_size";
	private static final String xiaoxinlist="xiaoxinlist_";
	
	SharedPreferences mPerferences;
	
	public void onCreate()
	{
	  super.onCreate();
	  mPerferences = PreferenceManager.getDefaultSharedPreferences(this); 
	}
	  
	
	public void setUserid(String userid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(Userid,userid);  
	    mEditor.commit(); 
	}
	public String getUserid() {
		return mPerferences.getString(Userid, "");
	}
	
	public void setXiaoxinSnList(List<String> list) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
		
		mEditor.putInt(xiaoxinlist_size,list.size());
		for(int i=0;i<list.size();i++)
		{
			mEditor.putString(xiaoxinlist + i, list.get(i));//
		}
	    mEditor.commit(); 
	}
	public List<String> getXiaoxinSnList() {
		List<String> xiaoxinList = new ArrayList<String>();
		int size = mPerferences.getInt(xiaoxinlist_size, 0);
		for(int i = 0; i < size;i++)
		{
			String sn = mPerferences.getString(xiaoxinlist + i, "");
			xiaoxinList.add(sn);
		}
		return xiaoxinList;
		//return mPerferences.getString(Userid, "");
	}
	public void setXiaoxin(Device xiaoxin)
	{
		SharedPreferences.Editor mEditor = mPerferences.edit();  
		
		mEditor.putString("xiaoxin_"+xiaoxin.sn,xiaoxin.ToJson());
		
	    mEditor.commit(); 
	}
	public Device getXiaoxin(String sn)
	{
		Device ret = new Device();
		String xiaxinJsonString = mPerferences.getString("xiaoxin_"+sn, "");
		ret.FromJson(xiaxinJsonString);
		return ret;
		
	}
	
	///backup wifi info
	
	private static final String WifibackupNetId="WifibackupNetId";
	private static final String WifibackupSSID="WifibackupSSID";
	private static final String WifibackupChannel="WifibackupChannel";
	private static final String WifibackupPwd="WifibackupPwd";
	private static final String WifibackupAuthMode="WifibackupAuthMode";
	private static final String WifibackupEncrypType="WifibackupEncrypType";
	
	public void setWifibackupNetId(int netid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putInt(WifibackupNetId,netid);  
	    mEditor.commit(); 
	}
	public int getWifibackupNetId() {
		return mPerferences.getInt(WifibackupNetId, 0);
	}
	
	public void setWifibackupSSID(String ssid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(WifibackupSSID,ssid);  
	    mEditor.commit(); 
	}
	public String getWifibackupSSID() {
		return mPerferences.getString(WifibackupSSID, "");
	}

	public void setWifibackupPwd(String content) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(WifibackupPwd,content);  
	    mEditor.commit(); 
	}
	public String getWifibackupPwd() {
		return mPerferences.getString(WifibackupPwd, "");
	}
	public void setWifibackupChannel(String content) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(WifibackupChannel,content);  
	    mEditor.commit(); 
	}
	public String getWifibackupChannel() {
		return mPerferences.getString(WifibackupChannel, "");
	}
	
	public void setWifibackupAuthMode(String content) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(WifibackupAuthMode,content);  
	    mEditor.commit(); 
	}
	public String getWifibackupAuthMode() {
		return mPerferences.getString(WifibackupAuthMode, "");
	}
	public void setWifibackupEncrypType(String content) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(WifibackupEncrypType,content);  
	    mEditor.commit(); 
	}
	public String getWifibackupEncrypType() {
		return mPerferences.getString(WifibackupEncrypType, "");
	}
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

