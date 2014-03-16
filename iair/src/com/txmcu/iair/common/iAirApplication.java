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
	   // Log.v("HCApplication", "create");
	}
	  
	
	public void setUserid(String userid) {
		SharedPreferences.Editor mEditor = mPerferences.edit();  
	    mEditor.putString(Userid,userid);  
	    mEditor.commit(); 
	}
	public String getUserid() {
		return mPerferences.getString(Userid, "");
	}
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

