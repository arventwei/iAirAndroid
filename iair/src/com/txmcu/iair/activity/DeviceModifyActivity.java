package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.plus.n;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

public class DeviceModifyActivity extends Activity implements OnClickListener {

	private static final String TAG = "DeviceModifyActivity";

	TextView titleTextView ;
	//Button   modify_name_Button;
	Device device ;
	String snString;
	String homeNameString;
	iAirApplication application;
	int type=0;	//0-add exist ,1-add new ,2-modify 
	
	EditText userNameEditText;
	EditText deviceIdEditText;
	EditText deviceNameEditText;
	EditText deviceRefreshEditText;
	CheckBox deviceShareBox;
	CheckBox deviceActiveBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_modify);

		application = (iAirApplication)getApplication();
		// snString =this.getIntent().getStringExtra("sn");
		Intent intent = this.getIntent();
		 type = intent.getIntExtra("type", 0);
		 homeNameString = intent.getStringExtra("homename");
		// device = null;//application.getXiaoxin(snString);
		 device = (Device)XinSession.getSession().get("device");
		 XinSession.getSession().cleanUpSession();
		
		findViewById(R.id.back_img).setOnClickListener(this);
		titleTextView = (TextView)findViewById(R.id.device_add_title);
		findViewById(R.id.ok_btn).setOnClickListener(this);
		findViewById(R.id.cancel_btn).setOnClickListener(this);
		findViewById(R.id.deviceActive).setOnClickListener(this);
		
		
		
		userNameEditText = (EditText)findViewById(R.id.userName);
		deviceIdEditText = (EditText)findViewById(R.id.deviceId);
		deviceNameEditText = (EditText)findViewById(R.id.deviceName);
		deviceRefreshEditText = (EditText)findViewById(R.id.deviceRefresh);
		deviceShareBox = (CheckBox)findViewById(R.id.deviceShare);
		deviceActiveBox = (CheckBox)findViewById(R.id.deviceActive);
		
		deviceIdEditText.setText(device.id);
		deviceNameEditText.setText(device.name);
		deviceShareBox.setChecked(device.share);
		deviceActiveBox.setChecked(!device.isVirtual);
		deviceRefreshEditText.setText(String.valueOf(device.refresh_interval));
		String titleString = application.getNickName();
		if (!homeNameString.equals("")) {
			titleString+="/";
			titleString+=homeNameString;
		}
				
		userNameEditText.setText(titleString);
		
		userNameEditText.setEnabled(false);
		deviceIdEditText.setEnabled(false);
		
		

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}
		else if (view.getId()==R.id.ok_btn) {
			
			String deviceName = deviceNameEditText.getText().toString();
			String devicefresh = deviceRefreshEditText.getText().toString();
			String shareString = deviceShareBox.isChecked()?"1":"0";
			
			device.name = deviceName;
			device.refresh_interval =devicefresh;
			device.share = Boolean.valueOf(shareString);
			
			//application.setNickName(userName);
			
			//XinServerManager.setuser_nickname(this, application.getUserid(), userName, null);
			XinServerManager.setxiaoxin_baseinfo(this, application.getUserid(),device.id,deviceName,
					shareString, devicefresh, new XinServerManager.onSuccess() {
						
						@Override
						public void run(JSONObject response) throws JSONException {
							// TODO Auto-generated method stub
							iAirUtil.toastMessage(DeviceModifyActivity.this, response.getString("ret"));
						//	iAirUtil.toastMessage(DeviceModifyActivity.this, "更新成功");
							DeviceModifyActivity.this.finish();
							if (DeviceManageActivity.instance!=null) {
								DeviceManageActivity.instance.requestlist();
							}
							if (DetailDeviceActivity.instance!=null) {
								DetailDeviceActivity.instance.requestlist();
							}
							if (HomeActivity.instance!=null) {
								HomeActivity.instance.refreshlist();
							}
							
						}
					});
			//TODO
//			XinServerManager.setxiaoxin_name(this, application.getUserid(),snString, new_name_EditText.getText().toString(),new XinServerManager.onSuccess() {
//				
//				@Override
//				public void run(String response) {
//					
//					if(DetailDeviceActivity.instance!=null)
//					{
//						DetailDeviceActivity.instance.adapter.updateView();
//						
//					}
//					if (HomeActivity.instance!=null) {
//						HomeActivity.instance.refreshlist();
//					}
//					if (DeviceManageActivity.instance!=null) {
//						DeviceManageActivity.instance.adapter.syncDevices();
//						DeviceManageActivity.instance.adapter.notifyDataSetChanged();
//					}
//					finish();
//				}
//			});
			
		}
		else if (view.getId()==R.id.cancel_btn) {
			DeviceModifyActivity.this.finish();
		}
		else  if (view.getId()==R.id.deviceActive) {
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
