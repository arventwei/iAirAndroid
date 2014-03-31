package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tencent.stat.event.AdditionEvent;
import com.tendcloud.tenddata.ad;
import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;

public class MainEntryAdapter extends BaseAdapter
{
	
	private List<Device> entries = new ArrayList<Device>();
	
	
	Activity deviceManageActivity;
	
	
	public MainEntryAdapter(Activity contentContext)
	{
		deviceManageActivity = contentContext;
		//Device header = new Device();
		

	}
	
	public void syncHomes() {
		entries.clear();
		entries.add(new Device());//head
		Device bj = new Device("1111111");
		bj.name=deviceManageActivity.getString(R.string.beijing);
		entries.add(bj);
		
		Device bj_home = new Device("1111112");
		iAirApplication application = (iAirApplication)deviceManageActivity.getApplication();
		bj_home.name=application.getNickName()+deviceManageActivity.getString(R.string.whos_home);
		entries.add(bj_home);
	}
	
	public void syncDevices()
	{
		entries.clear();
		entries.add(new Device());//head
		
		


		
		iAirApplication application = (iAirApplication)deviceManageActivity.getApplication();
		List<String> snList = application.getXiaoxinSnList();
		for (String sn : snList) {
			Device xiaoxinDevice = application.getXiaoxin(sn);
			entries.add(xiaoxinDevice);
		}
		
		
	}
//	public  void addDevice(int index,String name) {
//		MainEntry book = new MainEntry();
//    	book.setId(index);
//    	book.setName(name);
//    	//book.setBitmapId(R.drawable.b001);
//    	entries.add(book);
//	}
//	public void waspping(int oldIndex, int newIndex) {
//		MainEntry book = entries.get(oldIndex);
//		entries.remove(oldIndex);
//		entries.add(newIndex, book);
//	}
	
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
			convertView = View.inflate(deviceManageActivity, R.layout.main_entry_detail_functionbar, null);			
		}
		else if(position >= 1)
		{
			convertView = View.inflate(deviceManageActivity, R.layout.main_entry_detail_info, null);
			
			Device b = entries.get(position);
			
			//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
			
			((TextView)convertView.findViewById(R.id.entry_name_label)).setText(b.name);
			((TextView)convertView.findViewById(R.id.entry_pm25_label)).setText(String.valueOf((int)b.pm25));
			((TextView)convertView.findViewById(R.id.entry_temp_label)).setText(String.valueOf(b.temp));
			((TextView)convertView.findViewById(R.id.entry_comment_label)).setText(String.valueOf(b.humi));
			((TextView)convertView.findViewById(R.id.entry_form_label)).setText(String.valueOf(b.pa));
			
			
		}
		return convertView;
	}
	
}