package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.activity.DeviceManageActivity;
import com.txmcu.iair.activity.HomeAddActivity;
import com.txmcu.iair.activity.HomeModifyActivity;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;

public class DeviceHomeEntryAdapter extends BaseAdapter
{
	
	private List<DeviceHomeEntry> entries = new ArrayList<DeviceHomeEntry>();
	
	
	Activity deviceActivity;
	
	
	public DeviceHomeEntryAdapter(Activity contentContext)
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
		//entries.add(new DeviceLog());//head
		
		
		iAirApplication application = (iAirApplication)deviceActivity.getApplication();
		for (Home home : application.homeList) {
			DeviceHomeEntry deviceHomeEntry = new DeviceHomeEntry();
			deviceHomeEntry.home = home;
			deviceHomeEntry.type = 0;
			entries.add(deviceHomeEntry );
		}

		entries.add(new DeviceHomeEntry(1) );
		entries.add(new DeviceHomeEntry(2) );
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
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

			convertView = View.inflate(deviceActivity, R.layout.entry_device_home_menu, null);
			
			final DeviceHomeEntry b = entries.get(position);
			
			if (b.type==0) {
				((TextView)convertView.findViewById(R.id.home_entry_name_label)).setText(b.home.homename);
				if (b.home.own.equals("1")) {
					((CheckBox)convertView.findViewById(R.id.home_entry_switch)).setEnabled(true);
		
				}
				else {
					((CheckBox)convertView.findViewById(R.id.home_entry_switch)).setEnabled(false);
					
				
				}
			//	((TextView)convertView.findViewById(R.id.home_entry_name_label))
			}
			else if (b.type == 1) {
				((TextView)convertView.findViewById(R.id.home_entry_name_label)).setText("新家/新小新");
			}
			else if (b.type == 2) {
				((TextView)convertView.findViewById(R.id.home_entry_name_label)).setText("购买小新");
			}
			CheckBox switchoff = (CheckBox)convertView.findViewById(R.id.home_entry_switch);
			switchoff.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//String sn = xiaoxinDevice.sn;
					int isOn = 0;
					if (((CheckBox) v).isChecked()) {
						isOn = 1;
					}
					if (b.type==1&&isOn==1) {
						//! new home/new xiaoxin
						Intent localIntent = new Intent(
								deviceActivity,
								HomeModifyActivity.class);
						localIntent.putExtra("type", 1);
						localIntent.putExtra("autoadd", 1);
						deviceActivity.startActivity(localIntent);
						deviceActivity.overridePendingTransition(R.anim.left_enter,
										R.anim.alpha_out);
						deviceActivity.finish();
						
					}
					else if (b.type==0&&isOn==1) {
						//! old home add xiaoxin
						Intent localIntent = new Intent(
								deviceActivity,
								DeviceManageActivity.class);
						XinSession.getSession().put("home", b.home);
						localIntent.putExtra("autoadd", 1);
					
						deviceActivity.startActivity(localIntent);
						deviceActivity.overridePendingTransition(R.anim.left_enter,
										R.anim.alpha_out);
						deviceActivity.finish();
					}
					//iAirUtil.toastMessage(deviceActivity, "aa");
					//TODO
//					XinServerManager.setxiaoxin_switch(pageContext,
//									pageContext.application.getUserid(), sn,
//									isOn, null);

				}

			});
			
			//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
			
//			((TextView)convertView.findViewById(R.id.entry_name_label)).setText(b.name);
//			((TextView)convertView.findViewById(R.id.entry_pm25_label)).setText(String.valueOf((int)b.pm25));
//			((TextView)convertView.findViewById(R.id.entry_temp_label)).setText(String.valueOf(b.temp));
//			((TextView)convertView.findViewById(R.id.entry_comment_label)).setText(String.valueOf(b.humi));
//			((TextView)convertView.findViewById(R.id.entry_form_label)).setText(String.valueOf(b.pa));
//			
			
			//convertView.setClickable(true);
		return convertView;
	}
	
}