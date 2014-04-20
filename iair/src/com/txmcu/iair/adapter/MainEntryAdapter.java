package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;

/**
 * 主界面列表家和设备信息
 * @author Administrator
 *
 */
public class MainEntryAdapter extends BaseAdapter
{
	
	private List<City> cities = new ArrayList<City>();
	private List<Home> entries = new ArrayList<Home>();
	
	
	Activity deviceManageActivity;
	
	
	public MainEntryAdapter(Activity contentContext)
	{
		deviceManageActivity = contentContext;
		//Device header = new Device();
		

	}
	
	public void syncHomes() {
		entries.clear();
		cities.clear();
		cities.add(new City(""));//head
		
		//Device bj = new Device("1111111");
		//bj.name=deviceManageActivity.getString(R.string.beijing);
		//entries.add(bj);
		
		
		
		//Device bj_home = new Device("1111112");
		
		//double totalpm=0,totaltemp=0,totalhumi=0,totalpa=0;
		iAirApplication application = (iAirApplication)deviceManageActivity.getApplication();
		
		for (City city : application.cityList) {
			cities.add(city);
		}
		for (Home home : application.homeList) {
			entries.add(home);
		}
//		List<String> snList = application.getXiaoxinSnList();
//		for (String sn : snList) {
//			Device xiaoxinDevice = application.getXiaoxin(sn);
//			totalpm +=xiaoxinDevice.pm25;
//			totaltemp +=xiaoxinDevice.temp;
//			totalhumi +=xiaoxinDevice.humi;
//			totalpa +=xiaoxinDevice.pa;
//			//entries.add(xiaoxinDevice);
//		}
//		totalpm/=snList.size();
//		totaltemp/=snList.size();
//		totalhumi/=snList.size();
//		totalpa/=snList.size();
		
//		bj_home.pm25 = totalpm;
//		bj_home.temp = totaltemp;
//		bj_home.humi = totalhumi;
//		bj_home.pa = totalpa;
//		
//		//iAirApplication application = (iAirApplication)deviceManageActivity.getApplication();
//		bj_home.name=application.getNickName()+deviceManageActivity.getString(R.string.whos_home);
//		entries.add(bj_home);
	}
	
//	public void syncDevices()
//	{
//		entries.clear();
//		entries.add(new Device());//head
//		
//		
//		iAirApplication application = (iAirApplication)deviceManageActivity.getApplication();
////		List<String> snList = application.getXiaoxinSnList();
////		for (String sn : snList) {
////			Device xiaoxinDevice = application.getXiaoxin(sn);
////			entries.add(xiaoxinDevice);
////		}
//		
//		
//	}

	
	@Override
	public int getCount() {
		//return 5;
		return cities.size()+entries.size();
	}

	@Override
	public Object getItem(int position) {
		if (position <  cities.size()) {
			return cities.get(position);
		}
		else {
			return entries.get(position - cities.size());
		}
		//return entries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position==0 ) 
		{
			convertView = View.inflate(deviceManageActivity, R.layout.entry_new_main_entry_detail_header, null);			
		}
		else if(position >= 1)
		{
			convertView = View.inflate(deviceManageActivity, R.layout.entry_newmain_entry_detail, null);
			
			Object bObject =getItem(position);
			if (bObject.getClass() == City.class) {
				City b = (City)bObject;
				((TextView)convertView.findViewById(R.id.entry_name_label)).setText(b.name);
				((TextView)convertView.findViewById(R.id.entry_pm25_label)).setText(String.valueOf(b.pm25));
				((TextView)convertView.findViewById(R.id.entry_temp_label)).setText(String.valueOf(b.temp));
				((TextView)convertView.findViewById(R.id.entry_comment_label)).setText(String.valueOf(b.wind_info));
				//((TextView)convertView.findViewById(R.id.entry_form_label)).setText(String.valueOf(0));
			}
			else {
				Home b = (Home)bObject;
				((TextView)convertView.findViewById(R.id.entry_name_label)).setText(b.homename);
				((TextView)convertView.findViewById(R.id.entry_pm25_label)).setText(String.valueOf(b.pm25));
				((TextView)convertView.findViewById(R.id.entry_temp_label)).setText(String.valueOf(b.temp));
				((TextView)convertView.findViewById(R.id.entry_comment_label)).setText("甲醛:"+String.valueOf(b.pa));
				//((TextView)convertView.findViewById(R.id.entry_form_label)).setText(String.valueOf(b.pa));
				
			}
			//Home b = entries.get(position);
			
			//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
			
			
			
		}
		return convertView;
	}
	
}