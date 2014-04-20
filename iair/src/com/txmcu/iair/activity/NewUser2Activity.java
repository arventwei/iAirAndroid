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
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;
/**
 * 用户引导第2个界面
 * @author Administrator
 *
 */
public class NewUser2Activity extends Activity implements OnClickListener {

	private static final String TAG = "NewUser2Activity";


	iAirApplication application;

	EditText userNameEditText;
	EditText homeIdEditText;
	EditText homeNameEditText;
	CheckBox homeShareCheckBox;
	EditText homerefreshInterval;
	Home home;
	public static NewUser2Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_newuser_2);
		application = (iAirApplication)getApplication();
		findViewById(R.id.prev_btn).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
		
		userNameEditText = (EditText)findViewById(R.id.userName);
		homeIdEditText = (EditText)findViewById(R.id.homeId);
		homeNameEditText = (EditText)findViewById(R.id.homeName);
		homerefreshInterval = (EditText)findViewById(R.id.homeRefresh);
		homeShareCheckBox = (CheckBox)findViewById(R.id.homeShare);
		
		userNameEditText.setText(application.getNickName());
		List<Home> homes = application.homeList;
		if (homes.size()==0) {
			iAirUtil.toastMessage(this, "homes.size()==0 error");
			return;
		}
		home = homes.get(0);
		homeIdEditText.setText(home.homeid);
		
		homeNameEditText.setText(home.homename);
		homerefreshInterval.setText(String.valueOf(home.refresh_interval));
		homeShareCheckBox.setChecked(home.share);
		

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.prev_btn) {
			finish();
		}
		else if (view.getId()==R.id.next_btn) {
			String homeName = homeNameEditText.getText().toString();
			String homerefresh = homerefreshInterval.getText().toString();
			String shareString = homeShareCheckBox.isChecked()?"1":"0";
			
			
			//application.setNickName(userName);
			
			//XinServerManager.setuser_nickname(this, application.getUserid(), userName, null);
			XinServerManager.sethome_baseinfo(this, application.getUserid(),home.homeid,homeName,
					shareString, homerefresh, null);

			Intent localIntent = new Intent(this,
					NewUser3Activity.class);
			
			this.startActivity(localIntent);
		}

	}

}
