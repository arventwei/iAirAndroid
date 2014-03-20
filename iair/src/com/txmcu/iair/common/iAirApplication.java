package com.txmcu.iair.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.txmcu.iair.adapter.Device;

public class iAirApplication extends Application {
	
	

	
	private static final String Userid="userid";

	private List<Device> xiaoxinList = new ArrayList<Device>();
	
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

