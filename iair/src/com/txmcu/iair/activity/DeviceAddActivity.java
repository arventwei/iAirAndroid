package com.txmcu.iair.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinStateManager;
import com.txmcu.xiaoxin.config.XinStateManager.ConfigType;
import com.txmcu.xiaoxin.config.XinStateManager.XinOperations;

public class DeviceAddActivity extends Activity  implements XinOperations,OnClickListener {

	private static final String TAG = "iair";

	XinStateManager xinMgr;
	EditText editSSIDEditText;
	EditText editPwdEditText;
	EditText editSnEditText;
	int cooldown;
	iAirApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_add);
		application = (iAirApplication)this.getApplication(); 
		findViewById(R.id.back_img).setOnClickListener(this);
		
		((Button) findViewById(R.id.nextstep)).setOnClickListener(this);
		editSSIDEditText = (EditText) findViewById(R.id.input_ssid);
		editPwdEditText = (EditText) findViewById(R.id.input_pwd);
		editSnEditText = (EditText) findViewById(R.id.input_sn);

		xinMgr = XinStateManager.getInstance(this, this);
		xinMgr.Init();

		iAirUtil.showProgressDialog(this);
	}
	@Override
	protected void onDestroy() {
		xinMgr.Destroy();
		xinMgr = null;
		super.onDestroy();
		// Log.v(TAG, "onDestroy");

	}

	
	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}else if (view.getId() == R.id.nextstep) {
			cooldown = 120;
			iAirUtil.showProgressDialog(this, getString(R.string.setting), getString(R.string.add_device_cooldown)+120+getString(R.string.second));
//			progress = ProgressDialog.show(this, "设置", "开始连接设备 剩余120秒", true,true,new DialogInterface.OnCancelListener(){
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    //YourTask.this.cancel(true);
//                	if(task!=null)
//                		task.cancel();
//                    finish();
//                }
//            });
			xinMgr.Config(editSSIDEditText.getText().toString(),
					editPwdEditText.getText().toString(),application.getUserid(),
					editSnEditText.getText().toString()
					);
			
			timer.schedule(task, 1000,1000);
			Log.i(TAG, "start config");
		}

	}
	
	@Override
	public void onBackPressed()
	{
	    finish();
	}

	private final Timer timer = new Timer();

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// 要做的事情
			if (msg.what ==1) {
				cooldown--;
				if(cooldown<0)
				{
					iAirUtil.dismissDialog();
					//progress.dismiss();
					if(task!=null)
						task.cancel();
					//Toast.makeText(SettingActivity.this, "设置失败", Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					iAirUtil.setProgressText(getString(R.string.add_device_cooldown)+cooldown+getString(R.string.second));
					//progress.setMessage("开始连接设备 剩余"+cooldown+"秒");
				}
				//Log.i(TAG, "PROGRESS");
				
			}
			super.handleMessage(msg);
		}

	};

	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};



	@Override
	public void initResult(boolean result, String SSID) {
		// TODO Auto-generated method stub
		editSSIDEditText.setText(SSID);
		iAirUtil.dismissDialog();
	}



	@Override
	public void configResult(ConfigType type) {
		// TODO Auto-generated method stub
		iAirUtil.dismissDialog();
		if(type == ConfigType.Succeed)
		{
			//! add xiaoxin
			//XiaoxinInfo info = new XiaoxinInfo();
			//MainActivity.scannlist.add(info);
		}
		finish();
	}

}
