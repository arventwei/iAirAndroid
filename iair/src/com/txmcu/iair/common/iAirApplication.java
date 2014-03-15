package com.txmcu.iair.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class iAirApplication extends Application {
	
	

	
	private static final String boundNo="boundNo";

	
	

	private List<String> smsList = new ArrayList<String>();
	
	SharedPreferences mPerferences;
	
	public void onCreate()
	{
	  super.onCreate();
	  mPerferences = PreferenceManager.getDefaultSharedPreferences(this); 
	   // Log.v("HCApplication", "create");
	}
	  
	
//	public void setSelCarNo(int carid) {
//		SharedPreferences.Editor mEditor = mPerferences.edit();  
//	    mEditor.putInt(selCarNo,carid);  
//	    mEditor.commit(); 
//	}
//	public int getSelCarNo() {
//		return mPerferences.getInt(selCarNo, 0);
//	}
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

