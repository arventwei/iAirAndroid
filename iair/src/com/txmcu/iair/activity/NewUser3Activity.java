package com.txmcu.iair.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

public class NewUser3Activity extends Activity implements OnClickListener {

	private static final String TAG = "NewUser3Activity";

	iAirApplication application;
	EditText userNameEditText;
	EditText deviceIdEditText;
	EditText deviceNameEditText;
	EditText deviceRefreshEditText;
	CheckBox deviceShareBox;
	CheckBox deviceActiveBox;

	Home home;
	Device device;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser_3);
		application = (iAirApplication)getApplication();
		findViewById(R.id.prev_btn).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
		findViewById(R.id.deviceActive).setOnClickListener(this);
		
		userNameEditText = (EditText)findViewById(R.id.userName);
		deviceIdEditText = (EditText)findViewById(R.id.deviceId);
		deviceNameEditText = (EditText)findViewById(R.id.deviceName);
		deviceRefreshEditText = (EditText)findViewById(R.id.deviceRefresh);
		deviceShareBox = (CheckBox)findViewById(R.id.deviceShare);
		deviceActiveBox = (CheckBox)findViewById(R.id.deviceActive);
		
		List<Home> homes = application.homeList;
		if (homes.size()==0) {
			iAirUtil.toastMessage(this, "homes.size()==0 error");
			return;
		}
		home = homes.get(0);
		if (home.xiaoxins.size()==0) {
			iAirUtil.toastMessage(this, "xiaoxins.size()==0 error");
			return;
		}
		userNameEditText.setText(application.getNickName());
		device = home.xiaoxins.get(0);
		deviceIdEditText.setText(device.vsn);
		deviceNameEditText.setText(device.name);
		deviceShareBox.setChecked(device.share);
		deviceActiveBox.setChecked(!device.isVirtual);
		deviceRefreshEditText.setText(String.valueOf(device.refresh_interval));

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.prev_btn) {
			finish();
		}
		else if (view.getId()==R.id.next_btn) {
			String deviceName = deviceNameEditText.getText().toString();
			String devicefresh = deviceRefreshEditText.getText().toString();
			String shareString = deviceShareBox.isChecked()?"1":"0";
			
			device.name = deviceName;
			device.refresh_interval = Integer.valueOf(devicefresh);
			device.share = Boolean.valueOf(shareString);
			
			//application.setNickName(userName);
			
			//XinServerManager.setuser_nickname(this, application.getUserid(), userName, null);
			XinServerManager.setxiaoxin_baseinfo(this, application.getUserid(),device.id,deviceName,
					shareString, devicefresh, null);

			Intent localIntent = new Intent(this,
					NewUser4Activity.class);
			
			this.startActivity(localIntent);
			
		}
		else if(view.getId()==R.id.deviceActive){
			
			if (deviceActiveBox.isChecked()) {
				 Intent localIntent = new Intent(this,DeviceAddActivity.class);
				 localIntent.putExtra("homeId", "");
				 localIntent.putExtra("vsn", device.vsn);
				 startActivity(localIntent);
				 overridePendingTransition(R.anim.left_enter,  R.anim.alpha_out);
			}
			
		}

	}

}
