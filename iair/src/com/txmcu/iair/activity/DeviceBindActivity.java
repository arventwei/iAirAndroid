package com.txmcu.iair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class DeviceBindActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";

	EditText new_sn_EditText ;
	Button   bindButton;
	//Device device ;
	String snString;
	iAirApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_bind);

		application = (iAirApplication)getApplication();
		 //snString =this.getIntent().getStringExtra("sn");
		
		 //device = application.getXiaoxin(snString);
		
		findViewById(R.id.back_img).setOnClickListener(this);
		new_sn_EditText = (EditText)findViewById(R.id.new_sn);
		bindButton = (Button)findViewById(R.id.bind_btn);
		bindButton.setOnClickListener(this);
//		if (device!=null) {
//			new_sn_EditText.setText(device.name);
//		}
		

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}
		else if (view.getId()==R.id.bind_btn) {
			
			String snString = new_sn_EditText.getText().toString();
			String userid = application.getUserid();
			XinServerManager.bind(this, userid, snString,new XinServerManager.onSuccess() {
				
				@Override
				public void run(String response) {
					// TODO Auto-generated method stub
						XinServerManager.query_bindlist(DeviceManageActivity.instance, application.getUserid(), new XinServerManager.onSuccess() {
						
						@Override
						public void run(String response) {
							// TODO Auto-generated method stub
							if(DeviceManageActivity.instance!=null)
							{
								DeviceManageActivity.instance.adapter.syncDevices();
								DeviceManageActivity.instance.adapter.notifyDataSetChanged();
								
								if (MainActivity.instance !=null) {
									MainActivity.instance.refreshlist();
								}
							}
							
						}
					});
					finish();
				}
			});
			
			
			
			
		
			
		}

	}

}
