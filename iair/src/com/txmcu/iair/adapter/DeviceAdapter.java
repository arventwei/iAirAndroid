package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
	
	public  void addDevice(int index,String sn) {
		Device book = new Device();
    	book.setId(index);
    	book.setSn(sn);
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
		if(b.getSn().equals(""))
		{
			convertView = View.inflate(deviceManageActivity, 
					R.layout.gridview_device_add, null);
			//ImageView addbtn =(ImageView)convertView.findViewById(R.id.grid_device_add_btn);
			//addbtn.setOnClickListener(new DeviceAddClickListener(this));
		}
		else
		{
			convertView = View.inflate(deviceManageActivity, R.layout.gridview_device_item, null);
			((TextView)convertView.findViewById(R.id.city_name)).setText(b.getSn());
		}
		
		setitemSize(convertView);
		
//		int height = parent.getHeight();
//        if (height > 0) {
//            LayoutParams layoutParams = convertView.getLayoutParams();
//            layoutParams.height = (int) (height / rowsCount);
//        } 
		
		//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
		
		
		
		return convertView;
	}
	
	  void setitemSize(View paramView)
	  {
	    AbsListView.LayoutParams localLayoutParams = new AbsListView.LayoutParams(200,300);
	    localLayoutParams.width = 230;
	    localLayoutParams.height = 300;
	    paramView.setLayoutParams(localLayoutParams);
	  }
	  
	
}