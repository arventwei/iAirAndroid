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

public class DeviceLogAdapter extends BaseAdapter
{
	
	private List<DeviceLog> entries = new ArrayList<DeviceLog>();
	
	
	Activity deviceActivity;
	
	
	public DeviceLogAdapter(Activity contentContext)
	{
		deviceActivity = contentContext;
		//Device header = new Device();
		

	}
	
//	public void syncHomes() {
//		entries.clear();
//		entries.add(new Device());//head
//		Device bj = new Device("1111111");
//		bj.name=deviceActivity.getString(R.string.beijing);
//		entries.add(bj);
//		
//		Device bj_home = new Device("1111112");
//		iAirApplication application = (iAirApplication)deviceActivity.getApplication();
//		bj_home.name=application.getNickName()+deviceActivity.getString(R.string.whos_home);
//		entries.add(bj_home);
//	}
//	
	public void syncDeviceLogs()
	{
		entries.clear();
		entries.add(new DeviceLog());//head
		
		
//		iAirApplication application = (iAirApplication)deviceActivity.getApplication();
//		List<String> snList = application.getXiaoxinSnList();
//		for (String sn : snList) {
//			DeviceLog xiaoxinDevice = application.getXiaoxin(sn);
//			entries.add(xiaoxinDevice);
//		}
		
		
	}

	
	@Override
	public int getCount() {
		//return 5;
		return entries.size();
	}

	@Override
	public Object getItem(int position) {
		return entries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return entries.get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position==0 ) 
		{
			convertView = View.inflate(deviceActivity, R.layout.entry_device_log_header, null);			
		}
		else if(position >= 1)
		{
			convertView = View.inflate(deviceActivity, R.layout.entry_device_log, null);
			
			DeviceLog b = entries.get(position);
			
			//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
			
//			((TextView)convertView.findViewById(R.id.entry_name_label)).setText(b.name);
//			((TextView)convertView.findViewById(R.id.entry_pm25_label)).setText(String.valueOf((int)b.pm25));
//			((TextView)convertView.findViewById(R.id.entry_temp_label)).setText(String.valueOf(b.temp));
//			((TextView)convertView.findViewById(R.id.entry_comment_label)).setText(String.valueOf(b.humi));
//			((TextView)convertView.findViewById(R.id.entry_form_label)).setText(String.valueOf(b.pa));
//			
			
		}
		return convertView;
	}
	
}