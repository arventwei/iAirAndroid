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

public class DeviceModifyActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";

	EditText new_name_EditText ;
	Button   modify_name_Button;
	Device device ;
	String snString;
	iAirApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_modify);

		application = (iAirApplication)getApplication();
		 snString =this.getIntent().getStringExtra("sn");
		
		 device = application.getXiaoxin(snString);
		
		findViewById(R.id.back_img).setOnClickListener(this);
		new_name_EditText = (EditText)findViewById(R.id.new_name);
		modify_name_Button = (Button)findViewById(R.id.modify_name_btn);
		modify_name_Button.setOnClickListener(this);
		if (device!=null) {
			new_name_EditText.setText(device.name);
		}
		

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}
		else if (view.getId()==R.id.modify_name_btn) {
			
			XinServerManager.setxiaoxin_name(this, snString, new_name_EditText.getText().toString(),new XinServerManager.onSuccess() {
				
				@Override
				public void run(String response) {
					// TODO Auto-generated method stub
					if(DetailActivity.instance!=null)
					{
						DetailActivity.instance.adapter.updateView();
						
					}
					if (MainActivity.instance!=null) {
						MainActivity.instance.refreshlist();
					}
					if (DeviceManageActivity.instance!=null) {
						DeviceManageActivity.instance.adapter.syncDevices();
						DeviceManageActivity.instance.adapter.notifyDataSetChanged();
					}
					finish();
				}
			});
			
		}

	}

}
