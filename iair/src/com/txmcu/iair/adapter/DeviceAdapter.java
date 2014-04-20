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
import com.txmcu.iair.common.iAirApplication;
/**
 * 设备信息适配器接口
 * @author Administrator
 *
 */
public class DeviceAdapter extends BaseAdapter
{

	
	private List<Device> devices = new ArrayList<Device>();;
	
	
	DeviceManageActivity deviceManageActivity;
	
	
	public DeviceAdapter(DeviceManageActivity activity)
	{
		deviceManageActivity = activity;
	}
	public void syncDevices(Home home){
		devices.clear();
		if (home==null) {
			return;
		}
		iAirApplication application = (iAirApplication)deviceManageActivity.getApplication();
		for (Device device : home.xiaoxins) {
			devices.add(device);
		}
//		List<String> snList = application.getXiaoxinSnList();
//		for (String sn : snList) {
//			Device xiaoxinDevice = application.getXiaoxin(sn);
//			devices.add(xiaoxinDevice);
//		}
		if (home.own.equals("1")) {
			devices.add(new Device(""));
		}
		
	}
//	public  void addDevice(String sn) {
//		Device book = new Device();
//    	book.setId(index);
//    	book.setSn(sn);
//    	//book.setBitmapId(R.drawable.b001);
//    	devices.add(book);
//	}
	public void waspping(int oldIndex, int newIndex) {
		if (oldIndex == devices.size()-1
				||newIndex == devices.size()-1) {
			return;
		}
		
		Device book = devices.get(oldIndex);
		Device newOneDevice = devices.get(newIndex);
		if( (book!=null && book.sn.equals("")) 
				||(newOneDevice!=null && newOneDevice.sn.equals(""))){
			return;
		}
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
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//if (null == convertView) {
		Device b = devices.get(position);
		if(b.sn.equals(""))
		{
			convertView = View.inflate(deviceManageActivity, 
					R.layout.gridview_device_add, null);
		}
		else
		{
			convertView = View.inflate(deviceManageActivity, R.layout.gridview_device_item, null);
			((TextView)convertView.findViewById(R.id.city_name)).setText(b.name);
			String idString = b.id;
			if (b.status.equals("0")) {
				idString+="/离线";
			}
			else {
				idString+="/在线";
			}
			((TextView)convertView.findViewById(R.id.grid_device_id)).setText(idString);
			
			if (b.share) {
				((TextView)convertView.findViewById(R.id.grid_device_share)).setText("共享");
			}
			else {
				((TextView)convertView.findViewById(R.id.grid_device_share)).setText("不共享");
			}
			if (deviceManageActivity.editMode) {
				convertView.findViewById(R.id.delete_btn).setVisibility(View.VISIBLE);
			}
			else {
				convertView.findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
			}
		}
		
		setitemSize(convertView);
		
	
		
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