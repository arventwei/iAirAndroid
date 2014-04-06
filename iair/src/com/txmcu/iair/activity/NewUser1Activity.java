package com.txmcu.iair.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Instances;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class NewUser1Activity extends Activity implements OnClickListener {

	private static final String TAG = "NewUser1Activity";


	iAirApplication application;

	EditText userNameEditText;
	EditText cityNameEditText;
	public static NewUser1Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser_1);
		instance  = this;
		application = (iAirApplication)getApplication();
		findViewById(R.id.next_btn).setOnClickListener(this);
		userNameEditText = (EditText)findViewById(R.id.userName);
		cityNameEditText = (EditText)findViewById(R.id.deviceId);
		
		userNameEditText.setText(application.getNickName());
		cityNameEditText.setText("北京");

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.next_btn) {
			
			String userName = userNameEditText.getText().toString();
			String citName = cityNameEditText.getText().toString();
			
			application.setNickName(userName);
			
			XinServerManager.setuser_nickname(this, application.getUserid(), userName, null);
			XinServerManager.binduser_area(this, application.getUserid(), "001",null);

			Intent localIntent = new Intent(this,
					NewUser2Activity.class);
			
			this.startActivity(localIntent);
			//this.finish();
			
		}

	}

}
