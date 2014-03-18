package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.activity.DeviceManageActivity;

public class DeviceAdapter extends BaseAdapter
{
	
	private List<Device> devices = new ArrayList<Device>();;
	
	
	DeviceManageActivity deviceManageActivity;
	
	
	public DeviceAdapter(DeviceManageActivity activity)
	{
		deviceManageActivity = activity;
	}
	
	public  void addDevice(int index,String name) {
		Device book = new Device();
    	//book.setId(index);
    	book.setName(name);
    	//book.setBitmapId(R.drawable.b001);
    	devices.add(book);
	}
	public void waspping(int oldIndex, int newIndex) {
		Device book = devices.get(oldIndex);
		devices.remove(oldIndex);
		devices.add(newIndex, book);
	}
	
	@Override
	public int getCount() {
		//return 5;
		return devices.size();
	}

	@Override
	public Object getItem(int position) {
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return devices.get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//if (null == convertView) {
		Device b = devices.get(position);
		if(b.getSn().endsWith(""))
		{
			convertView = View.inflate(deviceManageActivity, R.layout.gridview_change_city_add, null);
		}
		else
		{
			convertView = View.inflate(deviceManageActivity, R.layout.gridview_change_city_item, null);
			((TextView)convertView.findViewById(R.id.city_name)).setText(b.getName());
		}
		
		
		
		//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
		
		
		
		return convertView;
	}
	
}